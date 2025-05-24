
package vn.com.pharmacity.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.authentication.UserProfileUtils;
import vn.com.pharmacity.constant.PharmacityConstant;
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.entity.User;
import vn.com.pharmacity.exception.SystemException;
import vn.com.pharmacity.repository.AccountRepository;
import vn.com.pharmacity.repository.AdminAccountRepository;
import vn.com.pharmacity.repository.UsersRepository;
import vn.com.pharmacity.service.user.AdminAccountService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * Define user identity as a constant
 * 
 * author Bac
 * @date 2025/5/20
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private static final String ACCOUNT_EXIST = "Username already exists!";
    private static final String REGISTRATION_FAILED = "Registration failed due to an unexpected error.";
    private static final String PASSWORD_TOO_SHORT = "Password must be at least 6 characters long."; 

    private List<UserDto> movieList2MovieVOList(List<User> movieList) {
        List<UserDto> userList = new ArrayList<>();
        for (User user : movieList) {
            user.setPassword("******");
            userList.add(new UserDto(user));
        }
        return userList;
    }

    @Override
    public Page<UserDto> searchAllAccount(String userName, Pageable pageable) {
        try {
            List<User> userList = adminAccountRepository.selectAllAccount(userName);
            List<UserDto> userDtoList = movieList2MovieVOList(userList);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), userDtoList.size());
            List<UserDto> pagedList = userDtoList.subList(start, end);

            return new PageImpl<>(pagedList, pageable, userDtoList.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty(); // Return an empty page in case of an error
        }
    }

    @Override
    public boolean deleteRole(Integer id) {
        boolean isDeleted = true;
        try {
            User en = adminAccountRepository.findOne(Long.valueOf(id));
            if (en != null) {
                en.setDeletedBy(UserProfileUtils.getUserNameLogin());
                en.setDeletedDate(new Date());
                adminAccountRepository.updateUserDelete(en);
            }
        } catch (Exception e) {
            isDeleted = false;
        }
        return isDeleted;
    }

    @Override
    public ResponseVO updateDataAccountForUser(UserDto form) {
        try {
            Optional<User> user = usersRepository.findByUsername(form.getUsername());
            if (user.isPresent() && !user.get().getId().equals(form.getId())) {
                return ResponseVO.buildFailure("Username already exists!");
            }
            adminAccountRepository.updateDataAccountForUserById(form.getId(), form.getUsername(), getAuthByAuthStr(form.getRole()), form.getRole());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure("Failed to modify information!");
        }
    }

    public Integer getAuthByAuthStr(String authStr) {
        Integer auth = User.AUTH_ADMIN;
        switch (authStr) {
            case "DOCTOR":
                auth = User.AUTH_DOCTOR;
                break;
            case "PATIENT":
                auth = User.AUTH_PATIENT;
                break;
            case "LAB_ASSISTANT":
                auth = User.AUTH_LAB_ASSISTANT;
                break;
            case "RECEPTION":
                auth = User.AUTH_RECEPTION;
                break;
            case "PHARMACY":
                auth = User.AUTH_PHARMACY;
                break;
        }
        return auth;
    }

    @Override
    public ResponseVO saveAccountForUser(UserDto form) {
        try {
            if (Objects.nonNull(accountRepository.getAccountByName(form.getUsername()))) {
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            String encodedPassword = passwordEncoder.encode(form.getPassword());
          if (form.getPassword() == null || form.getPassword().length() < 6) {
              throw new SystemException(PASSWORD_TOO_SHORT);
          }
            accountRepository.createNewAccount(form.getUsername(), encodedPassword, User.AUTH_PATIENT, PharmacityConstant.ROLE_PATIENT);
      } catch (Exception e) {
          return ResponseVO.buildFailure(REGISTRATION_FAILED);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO updateUserInformation(UserDto form) {
        try {
            Optional<User> user = usersRepository.findByUsername(form.getUsername());
            if (!user.isPresent()) {
                return ResponseVO.buildFailure("Username does not exist!");
            }
         
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            form.setUpdatedBy(currentUsername); 
            form.setUpdatedDate(new Date());
            
            adminAccountRepository.updateUserInformationById(form);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure("Failed to modify information!");
        }
    }

    @Override
    public Page<UserDto> searchAllUser(String userName, String fullName, String email, String phone,
            Pageable pageable) {
        try {
            List<User> userList = adminAccountRepository.selectAllUserByCondition(userName, fullName, email, phone);
            List<UserDto> userDtoList = movieList2MovieVOList(userList);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), userDtoList.size());
            List<UserDto> pagedList = userDtoList.subList(start, end);

            return new PageImpl<>(pagedList, pageable, userDtoList.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty(); // Return an empty page in case of an error
        }
    }

    @Override
    public Page<UserDto> searchAllEmploy(String userName, String fullName, String email, String phone,
            Pageable pageable) {
        try {
            List<User> userList = adminAccountRepository.selectAllEmployByCondition(userName, fullName, email, phone);
            List<UserDto> userDtoList = movieList2MovieVOList(userList);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), userDtoList.size());
            List<UserDto> pagedList = userDtoList.subList(start, end);

            return new PageImpl<>(pagedList, pageable, userDtoList.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty(); // Return an empty page in case of an error
        }
    }
}
