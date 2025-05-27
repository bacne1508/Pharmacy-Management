package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.MedicineBranchDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineBranchService;

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
}
