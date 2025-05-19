package vn.com.pharmacity.rest.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.service.user.AdminAccountService;
import vn.com.pharmacity.webapp.ResponseVO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AdminController {

    @Autowired
    private AdminAccountService adminAccountService;
    
    @GetMapping("/admin/account/all")
    public ResponseEntity<ResponseVO> searchAllMovie(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // The return results include the movie that has been removed
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> userPage = adminAccountService.searchAllAccount(pageable);
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
}
