package vn.com.pharmacity.rest.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.PurchaseOrderDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.purchase.PurchaseOrderService;

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
}
