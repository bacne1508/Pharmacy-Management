package vn.com.pharmacity.rest.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.CommonDto;
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
    
    @Autowired
    private MedicineUnitService medicineUnitService;
    
    @GetMapping("/medicine-units")
    public List<CommonDto> getMedicineGroups() {
        return medicineUnitService.findAll().stream()
            .map(g -> new CommonDto(g.getCode(), g.getName()))
            .collect(Collectors.toList());
    }
}
