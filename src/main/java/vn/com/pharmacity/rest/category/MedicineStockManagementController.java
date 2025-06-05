package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.MedicineStockDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineStockService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/stock")
public class MedicineStockManagementController extends BaseRestController<ObjectDataRes<MedicineStockDto>, MedicineStockDto> {
    
    public MedicineStockManagementController(MedicineStockService baseService) {
        super(baseService);
    }
}
