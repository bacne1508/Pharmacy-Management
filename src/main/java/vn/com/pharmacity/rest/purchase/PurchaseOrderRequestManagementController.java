package vn.com.pharmacity.rest.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
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
}
