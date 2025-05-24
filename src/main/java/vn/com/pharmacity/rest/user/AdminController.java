package vn.com.pharmacity.rest.user;

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
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.exception.SystemException;
import vn.com.pharmacity.service.user.AdminAccountService;
import vn.com.pharmacity.webapp.ResponseVO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/admin/account")
public class AdminController {

    @Autowired
    private AdminAccountService adminAccountService;
    
    @GetMapping("/all")
    public ResponseEntity<ResponseVO> searchAllMovie(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String userName) {
        // The return results include the movie that has been removed
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> userPage = adminAccountService.searchAllAccount(userName,pageable);
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
        boolean isDeleted = adminAccountService.deleteRole(id);
        if (!isDeleted) {
            throw new SystemException("Delete role failed");
        }
        return isDeleted ? ResponseVO.buildSuccess("Delete role successfully") : ResponseVO.buildFailure("Delete role failed");
    }

    @PostMapping("/edit")
    public ResponseVO updateAccount(@RequestBody UserDto editForm) {
        return adminAccountService.updateDataAccountForUser(editForm);
    }

    @PostMapping("/add")
    public ResponseVO addAccount(@RequestBody UserDto form) {
        return adminAccountService.saveAccountForUser(form);
    }
}
