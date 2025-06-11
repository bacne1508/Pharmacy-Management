package vn.com.pharmacity.rest.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.PurchaseOrderDetailDto;
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

    @GetMapping("/allDetail")
    public ResponseEntity<ResponseVO> getAllDetail(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Long poId) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<PurchaseOrderDetailDto> result = purchaseOrderService.searchDetail(params, poId, pageable);
            if (result == null || result.getContent().isEmpty()) {
                return ResponseEntity.ok(ResponseVO.buildFailure("No data"));
            }
            Map<String, Object> response = new HashMap<>();
            response.put("users", result.getContent());
            response.put("currentPage", result.getNumber());
            response.put("totalItems", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());
            
            return ResponseEntity.ok(ResponseVO.buildSuccess(response));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }
}
