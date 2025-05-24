package vn.com.pharmacity.rest.category;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.exception.SystemException;
import vn.com.pharmacity.service.category.MedicineSupplierService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/medicine/branch")
public class MedicineBranchController {

    @Autowired
    private MedicineSupplierService medicineSupplierService;
    
    @GetMapping("/all")
    public ResponseEntity<ResponseVO> searchAllMovie(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone) {
        // The return results include the movie that has been removed
        Pageable pageable = PageRequest.of(page, size);
        Page<SupplierDto> userPage = medicineSupplierService.searchAllSupplier(fullName, email, phone ,pageable);
        if (userPage == null || userPage.getContent().isEmpty()) {
            return ResponseEntity.ok(ResponseVO.buildFailure("No data"));
        }
        Map<String, Object> response = new HashMap<>();
        response.put("users", userPage.getContent());
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return ResponseEntity.ok(ResponseVO.buildSuccess(response));
    }

    @GetMapping("/delete")
    public ResponseVO deleteRole(@RequestParam("id") Integer id) {
        boolean isDeleted = medicineSupplierService.deleteSupplier(id);
        if (!isDeleted) {
            throw new SystemException("Delete user failed");
        }
        return isDeleted ? ResponseVO.buildSuccess("Delete user successfully") : ResponseVO.buildFailure("Delete user failed");
    }

    @PostMapping("/edit")
    public ResponseVO updateAccount(@RequestBody SupplierDto editForm) {
        return medicineSupplierService.updateSupplier(editForm);
    }

    @PostMapping("/add")
    public ResponseVO addData(@RequestBody SupplierDto form) {
        return medicineSupplierService.saveSupplier(form);
    }
}
