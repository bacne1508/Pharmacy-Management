package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.entity.User;

/**
 * Define user identity as a constant
 * @author Bac
 * @date 2025/5/20
 */
@Repository
public interface AdminAccountRepository extends DbRepository<User, Long> {

    List<User> selectAllAccount(@Param("userName") String userName);

    @Modifying
    void updateUserDelete(@Param("en") User en);

    @Modifying
    void updateDataAccountForUserById(@Param("id") Long id, @Param("username") String username
            , @Param("auth") Integer authByAuthStr, @Param("role") String role);

    @Modifying
    void updateUserInformationById(@Param("user") UserDto user);

    List<User> selectAllUserByCondition(@Param("userName") String userName, @Param("fullName") String fullName, @Param("email") String email, @Param("phone") String phone);

    List<User> selectAllEmployByCondition(@Param("userName") String userName, @Param("fullName") String fullName, @Param("email") String email, @Param("phone") String phone);

}
