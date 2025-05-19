package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.UserForm;
import vn.com.pharmacity.entity.User;

@Repository
public interface AccountRepository extends DbRepository<User, Long> {

    User getAccountByName(@Param("username") String username);

    /**
     * Create a new account
     * 
     * @param username
     * @param password
     * @param roleName 
     * @return
     */
    @Modifying
    int createNewAccount(@Param("username") String username, @Param("password") String password,
            @Param("auth") Integer auth, @Param("roleName") String roleName);

    /**
     * Update password
     * 
     * @param userForm
     * @return
     */
    @Modifying
    int updatePassword(@Param("userForm") UserForm userForm);

    /**
     * Get all the theater characters
     * 
     * @return
     */
    List<User> getPharmacityRoles();

    /**
     * Delete the role according to ID
     * 
     * @param id
     * @return
     */
    @Modifying
    int deleteRoleById(@Param("id") Integer id);

    /**
     * Update role information
     * 
     * @param id
     * @param username
     * @param password
     * @param auth
     * @return
     */
    @Modifying
    int updateRoleById(@Param("id") Integer id, @Param("username") String username,
            @Param("password") String password, @Param("auth") Integer auth);

}
