package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.MedicineUnitDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineUnitService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/unit")
public class MedicineUnitManagementController extends BaseRestController<ObjectDataRes<MedicineUnitDto>, MedicineUnitDto> {

    public MedicineUnitManagementController(MedicineUnitService  baseService) {
        super(baseService);
    }
}
