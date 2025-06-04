package vn.com.pharmacity.service.purchase.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.entity.PurchaseOrder;
import vn.com.pharmacity.entity.PurchaseOrderDetail;
import vn.com.pharmacity.entity.PurchaseOrderRequest;
import vn.com.pharmacity.repository.MedicineRepository;
import vn.com.pharmacity.repository.PurchaseOrderDetailsRepository;
import vn.com.pharmacity.repository.PurchaseOrderRepository;
import vn.com.pharmacity.repository.PurchaseOrderRequestRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.service.purchase.PurchaseOrderRequestService;
import vn.com.pharmacity.service.purchase.PurchaseOrderService;

/**
 * Define user identity as a constant
 * 
 * author Bac
 * 
 * @date 2025/5/20
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
@Log4j
public class PurchaseOrderRequestServiceImpl
        extends BaseRestServiceImpl<ObjectDataRes<PurchaseOrderRequestDto>, PurchaseOrderRequestDto, Long>
        implements PurchaseOrderRequestService {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // Add any necessary repository or service dependencies here
    @Autowired
    PurchaseOrderRequestRepository purchaseOrderRequestRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    private static final String MEDICINE_NOT_EXIST = "Request not exists!";

    private static final String STORAGE_CREATE_ERROR = "Purchase order request create error!";

    @Override
    protected List<PurchaseOrderRequestDto> findAllByCondition(MultiValueMap<String, String> params) {
        String username = params.getFirst("username");
        String status = params.getFirst("status");

        List<PurchaseOrderRequest> entities = purchaseOrderRequestRepository.searchAllByCondition(username, status);
        return entities.stream().map(PurchaseOrderRequestDto::new).collect(Collectors.toList());
    }

    @Override
    protected PurchaseOrderRequestDto findById(Long id) {
        PurchaseOrderRequest entity = purchaseOrderRequestRepository.findOne(id);
        return entity != null ? new PurchaseOrderRequestDto(entity) : null;
    }

    @Override
    protected PurchaseOrderRequestDto saveEntity(PurchaseOrderRequestDto dto) {
//        List<PurchaseOrderRequest> existing = purchaseOrderRequestRepository.getDataByCondition(dto.getId());

        if (dto.getId() == 0) {
            // Create
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            purchaseOrderRequestRepository.saveData(dto);
        } else {
            PurchaseOrderRequest entity = purchaseOrderRequestRepository.findOne(dto.getId());
            // Update
            if (Objects.isNull(entity)) {
                throw new RuntimeException(MEDICINE_NOT_EXIST);
            }
            dto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setUpdatedDate(new Date());

            purchaseOrderRequestRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
//        PurchaseOrderRequest entity = purchaseOrderRequestRepository.findOne(id);
//        if (entity != null) {
//            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
//            entity.setDeletedDate(new Date());
//            purchaseOrderRequestRepository.updateDate(entity);
//        }
    }

    @Override
    protected ObjectDataRes<PurchaseOrderRequestDto> createDataRes(Page<PurchaseOrderRequestDto> page) {
        ObjectDataRes<PurchaseOrderRequestDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

    @Override
    @AuditAction(actionType = "APPROVED") // ghi log
    public void approveRequestsByIds(List<Long> ids, String reason) {
        List<PurchaseOrderRequestDto> requests = purchaseOrderRequestRepository.findAllById(ids);
        for (PurchaseOrderRequestDto reqDto : requests) {
            if (reqDto.getStatus().equals(AppCoreConstant.PENDING)) {
                reqDto.setStatus(AppCoreConstant.APPROVED);
                reqDto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                reqDto.setUpdatedDate(new Date());
                // Gửi mail
//                notificationService.sendApprovedNotification(reqDto.getCreatedBy(), reqDto.getId());
            }
            purchaseOrderRequestRepository.updateData(reqDto);
            // Tạp đơn hàng từ yêu cầu sau khi duyệt
            this.createPOFromRequest(reqDto);
            purchaseOrderRequestRepository.updatePoIdById(reqDto);
        }
    }

    @AuditAction(actionType = "DRAFT")
    private void createPOFromRequest(PurchaseOrderRequestDto reqDto) {
        Medicine medicine = medicineRepository.findOne(reqDto.getMedicineId());
        if (Objects.isNull(medicine)) {
            throw new RuntimeException(MEDICINE_NOT_EXIST);
        }
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        PurchaseOrder po = new PurchaseOrder();
        LocalDate localDate = LocalDate.now().plusDays(3);
        Date expectedDeliveryDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // set thông tin đơn hàng
        PurchaseOrder existingDraftOrder = purchaseOrderRepository.findDraftByUserId(currentUser);
        if (Objects.isNull(existingDraftOrder)) {
            // Tạo mới đơn hàng
            po.setPoCode(purchaseOrderService.generatePoCode("PurchaseOrders", "po_code", "PO_", 5)); // Gen mã đơn hàng
                                                                                                      // mới
            po.setSupplierId(medicine.getSupplierId());
            po.setStatus("DRAFT");
            po.setExpectedDeliveryDate(expectedDeliveryDate); // Ngày giao hàng dự kiến
            po.setCreatedFrom(currentUser);
            po.setCreatedDate(new Date());
            po.setCreatedBy(currentUser);
            po = purchaseOrderRepository.savePOFromRequest(po);
        }
        // set thông tin chi tiết đơn hàng
        PurchaseOrderDetail detail = new PurchaseOrderDetail();
        detail.setPurchaseOrderId(po.getId() == null ? existingDraftOrder.getId() : po.getId());
        detail.setMedicineId(reqDto.getMedicineId());
        detail.setQuantity(reqDto.getQuantity());
        detail.setUnitPrice(medicine.getSalePrice());
        detail.setExpiryDate(expectedDeliveryDate);
        detail.setCreatedDate(new Date());
        detail.setCreatedBy(currentUser);
        purchaseOrderDetailsRepository.saveDataRequestPO(detail);
        
        // Cập nhật trạng thái yêu cầu
        reqDto.setLinkedPoId(detail.getPurchaseOrderId());
    }

    @Override
    @AuditAction(actionType = "REJECTED")
    public void rejectRequestsByIds(List<Long> ids, String reason) {
        List<PurchaseOrderRequestDto> requests = purchaseOrderRequestRepository.findAllById(ids);
        for (PurchaseOrderRequestDto req : requests) {
            if (req.getStatus().equals(AppCoreConstant.PENDING)) {
                req.setStatus(AppCoreConstant.REJECTED);
                req.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                req.setUpdatedDate(new Date());
                req.setRejectReason(reason);
                // Gửi thông báo từ chối
//                notificationService.sendRejectedNotification(req.getCreatedBy(), req.getId(), reason);
            }
            purchaseOrderRequestRepository.updateData(req);
        }
    }

}
