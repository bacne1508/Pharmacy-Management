package vn.com.pharmacity.service.purchase.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.AuditAction;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
import vn.com.pharmacity.dto.WalkInPurchaseInvoiceDto;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.entity.MedicineStock;
import vn.com.pharmacity.entity.MedicineUnit;
import vn.com.pharmacity.entity.PurchaseOrder;
import vn.com.pharmacity.entity.PurchaseOrderDetail;
import vn.com.pharmacity.entity.PurchaseOrderRequest;
import vn.com.pharmacity.entity.WalkInInvoiceItem;
import vn.com.pharmacity.repository.MedicineRepository;
import vn.com.pharmacity.repository.MedicineStockRepository;
import vn.com.pharmacity.repository.MedicineUnitRepository;
import vn.com.pharmacity.repository.PurchaseOrderDetailsRepository;
import vn.com.pharmacity.repository.PurchaseOrderRepository;
import vn.com.pharmacity.repository.PurchaseOrderRequestRepository;
import vn.com.pharmacity.repository.WalkInInvoiceItemRepository;
import vn.com.pharmacity.repository.WalkInPurchaseInvoiceRepository;
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

    @Autowired
    MedicineStockRepository stockRepository;
    
    @Autowired
    WalkInPurchaseInvoiceRepository walkInPurchaseInvoiceRepository;
    
    @Autowired
    WalkInInvoiceItemRepository walkInInvoiceItemRepository;
    
    @Autowired
    MedicineUnitRepository medicineUnitRepository;

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
            String requestGroup = UUID.randomUUID().toString();
            // Create
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            dto.setRequestGroup(requestGroup);
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

    /** 
     * luồng tạo đơn hàng từ yêu cầu: 
     * 1 Kiểm tra tồn kho đủ để xử lý không 
     * 2 Khóa locked_quantity nếu đủ 
     * 3 Tạo đơn hàng nếu chưa có 
     * 4 Ghi PurchaseOrderDetail có thông tin batch 
     * 5 Gắn linkedPoId vào reqDto để phản hồi
     * 
     * @param reqDto
     */
    @AuditAction(actionType = "DRAFT")
    private void createPOFromRequest(PurchaseOrderRequestDto reqDto) {
        Medicine medicine = medicineRepository.findOne(reqDto.getMedicineId());
        if (medicine == null) {
            throw new RuntimeException(MEDICINE_NOT_EXIST);
        }

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDate localDate = LocalDate.now().plusDays(3);
        Date expectedDeliveryDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 1. Kiểm tra tồn kho theo thuốc (và có thể theo batch/warehouse)
        Optional<MedicineStock> stockOpt = stockRepository.findAvailableStock(medicine.getId());
        if (stockOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tồn kho cho thuốc [" + medicine.getName() + "]");
        }

        MedicineStock stock = stockOpt.get();
        int availableQty = stock.getQuantity() - stock.getLockedQuantity() - stock.getUsedQuantity();
        if (availableQty < reqDto.getQuantity()) {
            throw new RuntimeException("Không đủ tồn kho: cần " + reqDto.getQuantity() + ", còn " + availableQty);
        }

        // 2. Nếu đủ kho thì lock lại: nếu số lượng tồn kho không đủ thì sẽ ném
        // exception
        int updated = stockRepository.lockStock(medicine.getId(), stock.getWarehouseId(), stock.getBatchNo(),
                reqDto.getQuantity());
        if (updated == 0) {
            throw new RuntimeException("Không thể khóa tồn kho, có thể bị tranh chấp hoặc không đủ số lượng.");
        }

        // 3. Tạo mới hoặc lấy đơn hàng DRAFT của user
        PurchaseOrder po = purchaseOrderRepository.findDraftByUserId(currentUser);
        if (po == null) {
            po = new PurchaseOrder();
            po.setPoCode(purchaseOrderService.generatePoCode("PurchaseOrders", "po_code", "PO_", 5));
            po.setSupplierId(medicine.getSupplierId());
            po.setStatus("DRAFT");
            po.setExpectedDeliveryDate(expectedDeliveryDate);
            po.setCreatedFrom(currentUser);
            po.setCreatedDate(new Date());
            po.setCreatedBy(currentUser);
            po = purchaseOrderRepository.savePOFromRequest(po);
        }

        // 4. Tạo chi tiết đơn hàng
        PurchaseOrderDetail detail = new PurchaseOrderDetail();
        detail.setPurchaseOrderId(po.getId());
        detail.setMedicineId(reqDto.getMedicineId());
        detail.setQuantity(reqDto.getQuantity());
        detail.setUnitPrice(medicine.getSalePrice());
        detail.setExpiryDate(expectedDeliveryDate);
        detail.setCreatedDate(new Date());
        detail.setCreatedBy(currentUser);
        detail.setBatchNo(stock.getBatchNo()); // Ghi nhận batch đã sử dụng trong detail
        detail.setPoRequestId(reqDto.getId()); // Gắn ID yêu cầu vào chi tiết đơn hàng
        detail.setPoRequestGroup(reqDto.getRequestGroup()); // Gắn nhóm yêu cầu để tracking
        purchaseOrderDetailsRepository.saveDataRequestPO(detail);

        // 5. Gắn ID PO vào yêu cầu để tracking
        reqDto.setLinkedPoId(po.getId());
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

    public String generateAndSignPrescriptionPdf(WalkInPurchaseInvoiceDto dto, InputStream keystorePath, String keystorePassword, String alias) 
            throws Exception {
        // 1. Tạo PDF đơn thuốc
        String staticPath = new File("src/main/resources/static/files/").getAbsolutePath();
        File folder = new File(staticPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = "prescription_" + System.currentTimeMillis() + ".pdf";
        String outputPath = staticPath + File.separator + fileName;
        
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        FileOutputStream fos = new FileOutputStream(outputPath);
        PdfWriter.getInstance(document, fos);
        document.open();

        InputStream fontStream = getClass().getClassLoader().getResourceAsStream("static/font/arial.ttf");

        if (fontStream == null) {
            throw new FileNotFoundException("Không tìm thấy font: static/font/arial.ttf");
        }

        // Đọc font dưới dạng byte[]
        byte[] fontBytes = fontStream.readAllBytes();

        // Tạo BaseFont từ byte[]
        BaseFont baseFont = BaseFont.createFont(
                "arial.ttf",         // Tên file tạm (có thể là bất kỳ)
                BaseFont.IDENTITY_H, // Cho phép Unicode (tiếng Việt, Nhật, v.v.)
                BaseFont.EMBEDDED,
                false,
                fontBytes,
                null
        );

        // Tạo Font từ BaseFont
        Font font = new Font(baseFont, 12);
        Font boldFont = new Font(baseFont, 11, Font.BOLD);

        Paragraph title = new Paragraph("ĐƠN THUỐC THANG ĐIỀU TRỊ NGOẠI TRÚ", new Font(baseFont, 13, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("(Dành cho kê đơn dược liệu, vị thuốc cổ truyền)", font));
        document.add(new Paragraph("\nTên cơ sở KBCB: .................................................   Mã bệnh: .....................................................", font));
        document.add(new Paragraph("Họ tên: " + dto.getCustomerName() + "     Tuổi: " + dto.getAge() + "     Giới tính: " + dto.getGender(), font));
        document.add(new Paragraph("Địa chỉ: " + dto.getAddress(), font));
        document.add(new Paragraph("Đối tượng: Viện phí: ............   BHYT ............   Khác ............", font));
        document.add(new Paragraph("Chẩn đoán: " + dto.getDiagnosis(), font));
        document.add(new Paragraph("Thuốc sử dụng từ ngày ......... đến ngày .........      Số thang: ..........", font));
        document.add(new Paragraph("\n"));

        // Table
        PdfPTable table = new PdfPTable(new float[]{1.0f, 4.5f, 2.0f, 2.0f, 3.0f});
        table.setWidthPercentage(100);
        String[] headers = {"TT", "Tên thuốc", "Số lượng", "Đơn vị tính", "Ghi chú"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, boldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        int stt = 1;
        for (WalkInInvoiceItem m : dto.getMedicines()) {
            table.addCell(new Phrase(String.valueOf(stt++), font));
            Medicine medicine = medicineRepository.findOne(m.getMedicineId());
            if(Objects.isNull(medicine)) {
                throw new RuntimeException("Không tìm thấy thuốc với ID = " + m.getMedicineId());
            }
            MedicineUnit unit = medicineUnitRepository.getMedicineByCode(medicine.getMedicineUnitsCode()).get(0);
            if(Objects.isNull(unit)) {
                throw new RuntimeException("Không tìm thấy unit với code = " + medicine.getMedicineUnitsCode());
            }
            table.addCell(new Phrase(medicine.getCode() + " - " + medicine.getName(), font));
            table.addCell(new Phrase(String.valueOf(m.getQuantity()), font));
            table.addCell(new Phrase(unit.getCode() != null ? unit.getName() : "Vỉ", font));
            table.addCell(new Phrase(medicine.getDescription(), font));
        }

        for (int i = dto.getMedicines().size(); i < 10; i++) {
            for (int j = 0; j < 5; j++) table.addCell(new Phrase(" ", font));
        }

        document.add(table);

        document.add(new Paragraph("\nHướng dẫn sử dụng:", boldFont));
        document.add(new Paragraph("Cách sắc thuốc: ...........................................................................................", font));
        document.add(new Paragraph("Cách dùng: ...............................................................................................", font));
        document.add(new Paragraph("Những điều cần lưu ý: .................................................................................", font));
        document.add(new Paragraph("Hẹn ngày khám lại (nếu cần thiết): ..............................................................", font));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                                                           ............., ngày ...... tháng ..... năm 20....", font));
        document.add(new Paragraph("Người bệnh                                                                                                          Người kê đơn", boldFont));
        document.add(new Paragraph("                                                                                                                (ký và ghi rõ họ tên)", font));

        document.close();

        // 2. Ký số PDF
        String signedOutputPath = outputPath.replace(".pdf", "_signed.pdf");
        signPdf(outputPath, signedOutputPath, keystorePath, keystorePassword, alias);

        return "/files/" + new File(signedOutputPath).getName();
    }

    //tạo chữ ký với RSA + SHA256
    public static void signPdf(String src, String dest, InputStream keystoreStream, String keystorePassword, String alias) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(keystoreStream, keystorePassword.toCharArray());

        PrivateKey pk = (PrivateKey) ks.getKey(alias, keystorePassword.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);

        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason("Kê đơn thuốc");
        appearance.setLocation("Phòng khám");
//        appearance.setVisibleSignature(new Rectangle(390, 110, 550, 140), 1, "sig"); // Tọa độ chữ ký
        appearance.setVisibleSignature(new Rectangle(410, 250, 540, 200), 1, "sig");

        ExternalDigest digest = new BouncyCastleDigest();
        ExternalSignature signature = new PrivateKeySignature(pk, "SHA256", "BC");

        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
    }

    @Override
    public boolean savePdfRequest(WalkInPurchaseInvoiceDto dto) {
        try {
            BigDecimal totalAmount = BigDecimal.ZERO;
            dto.setInvoiceCode(generatePoCode("WalkInPurchaseInvoice", "invoice_code", "WIP_", 5));
            // Create
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            for(WalkInInvoiceItem itemMedicine : dto.getMedicines()) {
                MedicineStock stock = stockRepository.findInforByMedicineId(itemMedicine.getMedicineId());
                if(Objects.nonNull(stock)) {
                    BigDecimal lineAmount = stock.getUnitPrice().multiply(BigDecimal.valueOf(itemMedicine.getQuantity()));
                    totalAmount = totalAmount.add(lineAmount);
                }else {
                    throw new RuntimeException("Không tìm thấy giá thuốc cho medicineId = " + itemMedicine.getMedicineId());
                }
            }
            dto.setTotalAmount(totalAmount);
            WalkInPurchaseInvoiceDto dtoSaved = walkInPurchaseInvoiceRepository.saveData(dto);
            
            //save item
            for(WalkInInvoiceItem itemMedicine : dto.getMedicines()) {
                MedicineStock stock = stockRepository.findInforByMedicineId(itemMedicine.getMedicineId());
                if(Objects.nonNull(stock)) {
                    itemMedicine.setUnitPrice(stock.getUnitPrice());
                    walkInInvoiceItemRepository.saveData(dtoSaved, itemMedicine); 
                }
            }
        } catch (Exception e) {
            log.error("Error saving entity", e);
            throw new RuntimeException(STORAGE_CREATE_ERROR);
        }
        return true;
    }

    public String generatePoCode(String tableName, String columnName, String perfix, Integer length) {
        String codeNO = "";

        try {
            String yy = new SimpleDateFormat("yy").format(new Date());
            String mm = new SimpleDateFormat("MM").format(new Date());

            String perfixCode = perfix + yy + mm;
            String maxNO = walkInPurchaseInvoiceRepository.findMaxNo(tableName, columnName, perfixCode);

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

}
