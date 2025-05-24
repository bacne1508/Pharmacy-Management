package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.MedicineTypeDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineTypeService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/type")
public class MedicineTypeManagementController extends BaseRestController<ObjectDataRes<MedicineTypeDto>, MedicineTypeDto> {

    public MedicineTypeManagementController(MedicineTypeService  baseService) {
        super(baseService);
    }
}
