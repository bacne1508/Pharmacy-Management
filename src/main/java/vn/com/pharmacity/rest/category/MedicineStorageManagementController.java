package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.MedicineStorageDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineStorageService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/storage")
public class MedicineStorageManagementController extends BaseRestController<ObjectDataRes<MedicineStorageDto>, MedicineStorageDto> {

    public MedicineStorageManagementController(MedicineStorageService  baseService) {
        super(baseService);
    }

}
