package vn.com.pharmacity.service.purchase.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.AuditAction;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.dto.PurchaseOrderDto;
import vn.com.pharmacity.entity.AuditLog;
import vn.com.pharmacity.entity.MedicineStock;
import vn.com.pharmacity.entity.PurchaseOrder;
import vn.com.pharmacity.entity.PurchaseOrderDetail;
import vn.com.pharmacity.repository.AuditLogRepository;
import vn.com.pharmacity.repository.MedicineStockRepository;
import vn.com.pharmacity.repository.PurchaseOrderDetailsRepository;
import vn.com.pharmacity.repository.PurchaseOrderRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.service.purchase.PurchaseOrderService;

/**
 * Define user identity as a constant
 * 
 * author BacDzz
 * 
 * @date 2025/6/5
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
@Log4j
public class PurchaseOrderServiceImpl extends
        BaseRestServiceImpl<ObjectDataRes<PurchaseOrderDto>, PurchaseOrderDto, Long> implements PurchaseOrderService {

    @Autowired
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private MedicineStockRepository stockRepository;

    @Autowired
    private PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    @Override
    protected List<PurchaseOrderDto> findAllByCondition(MultiValueMap<String, String> params) {
        String poCode = params.getFirst("poCode");
        String status = params.getFirst("status");

        List<PurchaseOrder> entities = purchaseOrderRepository.searchAllByCondition(poCode, status);
        return entities.stream().map(PurchaseOrderDto::new).collect(Collectors.toList());
    }

    @Override
    protected PurchaseOrderDto findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PurchaseOrderDto saveEntity(PurchaseOrderDto entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void deleteEntity(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    protected ObjectDataRes<PurchaseOrderDto> createDataRes(Page<PurchaseOrderDto> page) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Generate a purchase order code based on the current date and a prefix. The
     * format is: PREFIX + YY + MM + . + 00001
     * 
     * @param tableName  the table name
     * @param columnName the column name
     * @param perfix     the prefix for the code
     * @param length     the length of the numeric part (optional)
     * @return generated purchase order code
     */
    @Override
    public String generatePoCode(String tableName, String columnName, String perfix, Integer length) {
        String codeNO = "";

        try {
            String yy = new SimpleDateFormat("yy").format(new Date());
            String mm = new SimpleDateFormat("MM").format(new Date());

            String perfixCode = perfix + yy + mm;
            String maxNO = purchaseOrderRepository.findMaxNo(tableName, columnName, perfixCode);

            String formatLength = "%05d";

            if (length != null) {
                formatLength = "%0".concat(String.valueOf(length)).concat("d");
            }

            if (maxNO == null || "".equals(maxNO.trim())) {
                codeNO = perfixCode + "." + String.format(formatLength, 1);
            } else {
                String[] lstForm = maxNO.split("\\.");
                String number = lstForm[1];
                String nextNumber = String.format(formatLength, Integer.valueOf(number) + 1);
                codeNO = lstForm[0] + "." + nextNumber;
            }
            log.info("CODE GENERALIZED: " + codeNO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return codeNO;
    }

    /**
     * Xử lý các hành động trên đơn hàng: phê duyệt, hủy, gửi, nhận
     * 
     * @param poId   ID của đơn hàng
     * @param action Hành động cần thực hiện (APPROVED, CANCELLED, SENT, RECEIVED)
     */
    @Override
    public void handleAction(Long poId, String action) {
        PurchaseOrder po = purchaseOrderRepository.findOne(poId);
        if (Objects.isNull(po)) {
            throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + poId);
        }

        String currentStatus = po.getStatus();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        switch (action.toUpperCase()) {
        case AppCoreConstant.APPROVED: //trường hợp phê duyệt đơn hàng
            if (!AppCoreConstant.DRAFT.equals(currentStatus)) {
                throw new RuntimeException("Chỉ đơn hàng ở trạng thái DRAFT mới được phê duyệt.");
            }
            approveOrder(po, username);
            break;

        case AppCoreConstant.CANCELLED: // trường hợp hủy đơn hàng
            if (!AppCoreConstant.DRAFT.equals(currentStatus)) {
                throw new RuntimeException("Chỉ đơn hàng ở trạng thái DRAFT mới được hủy.");
            }
            cancelOrder(po, username);
            break;

        case AppCoreConstant.SENT: // trường hợp gửi đơn hàng
            if (!AppCoreConstant.APPROVED.equals(currentStatus)) {
                throw new RuntimeException("Chỉ đơn hàng đã APPROVED mới được gửi.");
            }
            sendOrder(po, username);
            break;

        case AppCoreConstant.RECEIVED:// trường hợp xác nhận nhận hàng
            if (!AppCoreConstant.SENT.equals(currentStatus)) {
                throw new RuntimeException("Chỉ đơn hàng đã gửi mới được xác nhận nhận hàng.");
            }
            receiveOrder(po, username);
            break;

        default:
            throw new RuntimeException("Hành động không hợp lệ: " + action);
        }
        // Gửi email nếu cần
//            emailService.notifyOrderAction(po, action.toUpperCase());

    }

    /**
     * Phê duyệt đơn hàng, khóa số lượng thuốc trong kho
     * @param po       Đơn hàng cần phê duyệt
     * @param username Tên người dùng thực hiện hành động
     * @return void
     * @author BacDzz
     * @date 2025/6/5
     */
    @AuditAction(actionType = AppCoreConstant.APPROVED, entityType = "APPROVED_ORDER")
    private void approveOrder(PurchaseOrder po, String username) {
        // Kiểm tra trạng thái đơn hàng
        List<PurchaseOrderDetail> details = purchaseOrderDetailsRepository.findByPurchaseOrderId(po.getId());
        // Kiểm tra số lượng thuốc trong kho
        for (PurchaseOrderDetail detail : details) {
            int quantityToLock = detail.getQuantity();
            List<MedicineStock> stocks = stockRepository.searchAllByCondition(detail.getBatchNo() ,detail.getMedicineId());

            for (MedicineStock stock : stocks) {
                int available = stock.getQuantity() - stock.getUsedQuantity() - stock.getLockedQuantity();

                if (available <= 0) continue;

                int lockAmount = Math.min(available, quantityToLock);
                stock.setLockedQuantity(stock.getLockedQuantity() + lockAmount);
                quantityToLock -= lockAmount;

                // lưu log nếu cần
                stockRepository.setLockedQuantity(stock);

                if (quantityToLock == 0) break;
            }

            if (quantityToLock > 0) {
                throw new RuntimeException("Không đủ tồn kho để khóa thuốc: " + detail.getMedicineId());
            }
        }
        LocalDate localDate = LocalDate.now().plusDays(3);
        Date expectedDeliveryDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        po.setStatus(AppCoreConstant.APPROVED);
        po.setUpdatedBy(username);
        po.setUpdatedDate(new Date());
        po.setExpectedDeliveryDate(expectedDeliveryDate);
        purchaseOrderRepository.updateStatusAndExpiry(po);

        // Ghi log
        this.saveLog(po, username, AppCoreConstant.APPROVED_ORDER);
    }

    /**
     * hủy đơn hàng, trả lại số lượng thuốc đã khóa
     * @param po       Đơn hàng cần hủy
     * @param username Tên người dùng thực hiện hành động
     * @return void
     * @author BacDzz
     * @date 2025/6/5
     */
    @AuditAction(actionType = AppCoreConstant.CANCELLED, entityType = AppCoreConstant.CANCELLED_ORDER)
    private void cancelOrder(PurchaseOrder po, String username) {
        // Trả lại stock đã lock
        List<PurchaseOrderDetail> details = purchaseOrderDetailsRepository.findByPurchaseOrderId(po.getId());
        for (PurchaseOrderDetail detail : details) {
            // Giả định mỗi chi tiết chỉ lock đúng số lượng của nó
            int updated = stockRepository.unlockQuantity(detail.getMedicineId(), detail.getQuantity());
            if (updated == 0) {
                throw new RuntimeException("Không thể hoàn trả tồn kho thuốc ID: " + detail.getMedicineId());
            }
        }
        po.setStatus(AppCoreConstant.CANCELLED);
        po.setUpdatedBy(username);
        po.setUpdatedDate(new Date());
        purchaseOrderRepository.updateStatus(po);

        // Ghi log
        this.saveLog(po, username, AppCoreConstant.CANCELLED_ORDER);
    }

    /**
     * gửi đơn hàng, cập nhật trạng thái và gửi email thông báo
     * @param po       Đơn hàng cần gửi
     * @param username Tên người dùng thực hiện hành động
     * @return void
     * @author BacDzz
     * 
     */
    @AuditAction(actionType = AppCoreConstant.SENT, entityType = AppCoreConstant.SENT_ORDER)
    private void sendOrder(PurchaseOrder po, String username) {
        po.setStatus(AppCoreConstant.SENT);
        po.setUpdatedBy(username);
        po.setUpdatedDate(new Date());
        purchaseOrderRepository.updateStatus(po);

        this.saveLog(po, username, AppCoreConstant.SENT_ORDER);
    }

    /**
     * xác nhận nhận hàng, cập nhật trạng thái và trả lại số lượng thuốc vào kho
     * @param po       Đơn hàng cần nhận
     * @param username Tên người dùng thực hiện hành động
     * @return void
     * @author BacDzz
     * 
     */
    @AuditAction(actionType = AppCoreConstant.RECEIVED, entityType = AppCoreConstant.RECEIVED_ORDER)
    private void receiveOrder(PurchaseOrder po, String username) {
        List<PurchaseOrderDetail> details = purchaseOrderDetailsRepository.findByPurchaseOrderId(po.getId());

        for (PurchaseOrderDetail detail : details) {
            int qtyToReceive = detail.getQuantity();
            Optional<MedicineStock> optionalStocks = stockRepository.findAvailableStock(detail.getMedicineId());
            List<MedicineStock> stocks = new ArrayList<>();
            optionalStocks.ifPresent(stocks::add);
            
            for (MedicineStock stock : stocks) {
                int locked = stock.getLockedQuantity();
                if (locked <= 0) continue;

                int qty = Math.min(locked, qtyToReceive);
                stock.setLockedQuantity(locked - qty);
                stock.setUsedQuantity(stock.getUsedQuantity() + qty);
                stockRepository.updateLockedAndUsed(stock);

                qtyToReceive -= qty;
                if (qtyToReceive == 0) break;
            }

            if (qtyToReceive > 0) {
                throw new RuntimeException("Không đủ thuốc đã khóa để nhận cho medicineId = " + detail.getMedicineId());
            }
        }

        po.setStatus(AppCoreConstant.RECEIVED);
        po.setUpdatedBy(username);
        po.setUpdatedDate(new Date());
        purchaseOrderRepository.updateStatus(po);

        this.saveLog(po, username, AppCoreConstant.RECEIVED_ORDER);
    }

    private void saveLog(PurchaseOrder po, String username, String receivedOrder) {
        // Ghi log
        AuditLog log = new AuditLog();
        log.setRequestId(po.getId()); // đơn hàng
        log.setEntityType(receivedOrder);
        log.setActionType(AppCoreConstant.SENT);
        log.setActionBy(username);
        log.setActionTime(new Date());
        log.setRemarks("Success");

        auditLogRepository.saveLog(log);
    }
}
