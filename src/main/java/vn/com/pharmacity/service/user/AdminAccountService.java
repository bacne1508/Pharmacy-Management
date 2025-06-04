package vn.com.pharmacity.service.user;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * @author Bac
 * @date 2025/5/20
 */
public interface AdminAccountService {
    /**
     * Get all accounts
     * @param username 
     * 
     * @param pageable
     * @return
     */
    Page<UserDto> searchAllAccount(String userName, Pageable pageable);

    boolean deleteRole(Integer id);

    ResponseVO updateDataAccountForUser(UserDto editForm);

    ResponseVO saveAccountForUser(UserDto form);

    ResponseVO updateUserInformation(UserDto editForm);

    Page<UserDto> searchAllUser(String userName, String fullName, String email, String phone, Pageable pageable);

    Page<UserDto> searchAllEmploy(String userName, String fullName, String email, String phone, Pageable pageable);

    Collection<CommonDto> findAll();

}
