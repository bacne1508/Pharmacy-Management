package vn.com.pharmacity.service.user;

import vn.com.pharmacity.dto.UserForm;
import vn.com.pharmacity.entity.User;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * @author Bac
 * @date 2025/5/4
 */
public interface AccountService {

    /**
     * Registered account
     * 
     * @return
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * User login, login successfully will save user information in session in
     * session
     * 
     * @return
     */
    public User login(UserForm userForm);

    /**
     * Dete whether the password is correct
     * 
     * @param user
     * @param rawPassword
     * @return
     */
    public ResponseVO checkPassword(User user, String rawPassword);

    /**
     * Modify the password
     * 
     * @param userForm
     * @return
     */
    public ResponseVO editPassword(UserForm userForm);

}
