package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine")
public class MedicineManagementController extends BaseRestController<ObjectDataRes<MedicineDto>, MedicineDto> {

    public MedicineManagementController(MedicineService  baseService) {
        super(baseService);
    }
}
