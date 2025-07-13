package vn.com.pharmacity.rest.purchase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.constant.AppApiConstant;
import vn.com.pharmacity.dto.PurchaseBillDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.purchase.PurchaseBillService;

/**
 * @author Bac
 * @date 2025/7/12
 */
@RestController
@RequestMapping(AppApiConstant.API + AppApiConstant.API_AUTHEN + "/purchase/bill")
public class PurchaseBillManagementController extends BaseRestController<ObjectDataRes<PurchaseBillDto>, PurchaseBillDto> {

    public PurchaseBillManagementController(PurchaseBillService baseService) {
        super(baseService);
    }

}
