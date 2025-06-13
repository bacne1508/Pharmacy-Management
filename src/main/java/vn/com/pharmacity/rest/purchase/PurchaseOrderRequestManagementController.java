package vn.com.pharmacity.rest.purchase;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;

import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
import vn.com.pharmacity.dto.WalkInPurchaseInvoiceDto;
import vn.com.pharmacity.req.BulkActionRequest;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.purchase.PurchaseOrderRequestService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * @author Bac
 * @date 2025/6/4
 */
@RestController
@RequestMapping("/api/auth/purchase/order/request")
public class PurchaseOrderRequestManagementController extends BaseRestController<ObjectDataRes<PurchaseOrderRequestDto>, PurchaseOrderRequestDto> {
    
    @Autowired
    private PurchaseOrderRequestService purchaseOrderRequestService;
    public PurchaseOrderRequestManagementController(PurchaseOrderRequestService baseService) {
        super(baseService);
    }
    
    @PostMapping("/approve-multiple")
    public ResponseEntity<?> approveMultipleRequests(@RequestBody BulkActionRequest req) {
        try {
            purchaseOrderRequestService.approveRequestsByIds(req.getIds(), req.getReason());
            return ResponseEntity.ok(ResponseVO.buildSuccess());
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }

    @PostMapping("/reject-multiple")
    public ResponseEntity<?> rejectRequests(@RequestBody BulkActionRequest req) {
        purchaseOrderRequestService.rejectRequestsByIds(req.getIds(), req.getReason());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/generate-order-pdf")
    public ResponseEntity<ResponseVO> createPrescription(@RequestBody WalkInPurchaseInvoiceDto dto) {
        try {
            // 1. Lưu đơn thuốc (nếu bạn cần lưu vào DB)
            boolean created = purchaseOrderRequestService.savePdfRequest(dto);

            if (!created) {
                return ResponseEntity.ok(ResponseVO.buildFailure("Không thể lưu đơn thuốc"));
            }

            // 2. Đường dẫn đến file .p12 và thông tin chữ ký
            InputStream keystoreStream = new ClassPathResource(AppCoreConstant.KEYSTORE_PATH).getInputStream(); // 🔐 Cập nhật đường dẫn file .p12 của bạn
            String keystorePassword = AppCoreConstant.KEYSTORE_PASSWORD;
            String alias = AppCoreConstant.ALIAS;

            // 3. Gọi service để sinh + ký PDF
            String pdfUrl = purchaseOrderRequestService.generateAndSignPrescriptionPdf(dto, keystoreStream, keystorePassword, alias);

            return ResponseEntity.ok(ResponseVO.buildSuccess(Map.of(
                "success", true,
                "pdfUrl", pdfUrl
            )));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ResponseVO.buildFailure("Lỗi xử lý đơn thuốc: " + e.getMessage()));
        }
    }
    
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {
        Path file = Paths.get("files").resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
            .body(resource);
    }
}
