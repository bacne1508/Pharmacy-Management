
package vn.com.pharmacity.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.entity.User;
import vn.com.pharmacity.repository.AdminAccountRepository;
import vn.com.pharmacity.service.user.AdminAccountService;

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

    private List<UserDto> movieList2MovieVOList(List<User> movieList) {
        List<UserDto> userList = new ArrayList<>();
        for (User user : movieList) {
            user.setPassword("******");
            userList.add(new UserDto(user));
        }
        return userList;
    }

    @Override
    public Page<UserDto> searchAllAccount(Pageable pageable) {
        try {
            List<User> userList = adminAccountRepository.selectAllAccount();
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
