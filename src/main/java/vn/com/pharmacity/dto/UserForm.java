package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Getter
@Setter
public class UserForm {
    /**
     * Username, not repeated
     */
    private String username;
    /**
     * User password
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
