package vn.com.pharmacity.rest.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineSupplierService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/supplier")
public class MedicineSupplierController extends BaseRestController<ObjectDataRes<SupplierDto>, SupplierDto> {
    
    @Autowired
    private MedicineSupplierService medicineSupplierService;

    public MedicineSupplierController(MedicineSupplierService  baseService) {
        super(baseService);
    }
    
    @GetMapping("/supplier-groups")
    public List<CommonDto> getSupplierGroups() {
        return medicineSupplierService.findAll().stream()
            .map(g -> new CommonDto(g.getId(),g.getCode(), g.getName()))
            .collect(Collectors.toList());
    }
}
