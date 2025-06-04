package vn.com.pharmacity.rest.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineBranchDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineBranchService;
import vn.com.pharmacity.service.category.MedicineTypeService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/branch")
public class MedicineBranchController extends BaseRestController<ObjectDataRes<MedicineBranchDto>, MedicineBranchDto> {

    public MedicineBranchController(MedicineBranchService  baseService) {
        super(baseService);
    }
    
    @Autowired
    private MedicineBranchService MedicineBranchService;
    
    @GetMapping("/branch-groups")
    public List<CommonDto> getMedicineBranchGroups() {
        return MedicineBranchService.findAll().stream()
            .map(g -> new CommonDto(g.getId(),g.getCode(), g.getName()))
            .collect(Collectors.toList());
    }
}
