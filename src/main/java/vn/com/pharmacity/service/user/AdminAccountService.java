package vn.com.pharmacity.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.com.pharmacity.dto.UserDto;

/**
 * @author Bac
 * @date 2025/5/20
 */
public interface AdminAccountService {
    /**
     * Get all accounts
     * 
     * @param pageable
     * @return
     */
    Page<UserDto> searchAllAccount(Pageable pageable);

}
