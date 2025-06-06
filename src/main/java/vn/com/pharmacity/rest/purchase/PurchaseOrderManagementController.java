package vn.com.pharmacity.rest.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.PurchaseOrderDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.purchase.PurchaseOrderService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * @author Bac
 * @date 2025/6/4
 */
@RestController
@RequestMapping("/api/auth/purchase/order")
public class PurchaseOrderManagementController extends BaseRestController<ObjectDataRes<PurchaseOrderDto>, PurchaseOrderDto> {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    public PurchaseOrderManagementController(PurchaseOrderService baseService) {
        super(baseService);
    }
    
    @PostMapping("/{id}/{action}")
    public ResponseEntity<?> handleAction(@PathVariable Long id, @PathVariable String action) {// APPROVED, CANCELLED, SENT, RECEIVED
        try {
            purchaseOrderService.handleAction(id, action);
            return ResponseEntity.ok(ResponseVO.buildSuccess("Update status successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }
}
