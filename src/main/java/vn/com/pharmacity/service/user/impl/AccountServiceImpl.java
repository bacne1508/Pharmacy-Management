package vn.com.pharmacity.service.user.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.constant.PharmacityConstant;
import vn.com.pharmacity.dto.UserForm;
import vn.com.pharmacity.entity.User;
import vn.com.pharmacity.exception.SystemException;
import vn.com.pharmacity.repository.AccountRepository;
import vn.com.pharmacity.service.user.AccountService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * Define user identity as a constant
 * @author Bac
 * @date 2025/5/20
 */
@CoreReadOnlyTx
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final String ACCOUNT_EXIST = "Username already exists!";
    private static final String REGISTRATION_FAILED = "Registration failed due to an unexpected error.";
    private static final String PASSWORD_TOO_SHORT = "Password must be at least 6 characters long."; 
    
    @Autowired
    private AccountRepository accountRepository;
    
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            if (Objects.nonNull(accountRepository.getAccountByName(userForm.getUsername()))) {
                throw new SystemException(ACCOUNT_EXIST);
            }
            String encodedPassword = passwordEncoder.encode(userForm.getPassword());
//            if (userForm.getPassword() == null || userForm.getPassword().length() < 6) {
//                throw new SystemException(PASSWORD_TOO_SHORT);
//            }
            accountRepository.createNewAccount(userForm.getUsername(), encodedPassword, User.AUTH_PATIENT, PharmacityConstant.ROLE_PATIENT);
        } catch (Exception e) {
            return ResponseVO.buildFailure(REGISTRATION_FAILED);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
//        User user = AccountRepository.getAccountByName(userForm.getUsername());
        User user = accountRepository.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO checkPassword(User user, String rawPassword) {
        if (user.getPassword().equals(rawPassword)) {
            return ResponseVO.buildSuccess();
        } else {
            return ResponseVO.buildFailure("fail");
        }
    }

    @Override
    public ResponseVO editPassword(UserForm userForm) {
        try {
            int res = accountRepository.updatePassword(userForm);
            if (res == 1) {
                return ResponseVO.buildSuccess();
            } else {
                return ResponseVO.buildFailure("Failed to change password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("Failed to change password!");
        }
    }
}
