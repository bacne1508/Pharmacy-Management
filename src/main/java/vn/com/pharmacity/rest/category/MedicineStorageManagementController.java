package vn.com.pharmacity.rest.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.CommonDto;
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
    
    @Autowired
    private MedicineStorageService medicineStorageService;

    public MedicineStorageManagementController(MedicineStorageService  baseService) {
        super(baseService);
    }
    
    @GetMapping("/storage-groups")
    public List<CommonDto> getStorageGroups() {
        return medicineStorageService.findAll().stream()
            .map(g -> new CommonDto(g.getId(),g.getCode(), g.getName()))
            .collect(Collectors.toList());
    }

}
