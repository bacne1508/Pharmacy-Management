package vn.com.pharmacity.rest.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.CommonDto;
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
    
    @Autowired
    private MedicineTypeService medicineTypeService;
    
    @GetMapping("/medicine-types")
    public List<CommonDto> getMedicineGroups() {
        return medicineTypeService.findAll().stream()
            .map(g -> new CommonDto(g.getId(),g.getCode(), g.getName()))
            .collect(Collectors.toList());
    }
}
