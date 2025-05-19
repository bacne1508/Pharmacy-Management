package vn.com.pharmacity.dto;

import com.miragesql.miragesql.annotation.Column;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.User;

@Getter
@Setter
public class UserDto {
    private Long id;
    
    private String username; // Tên 
       
    private String password; // pass
        
    private String fullName; 
    
    private String email;

    private String phone;
    
    private Integer auth; 
    
    private String role;
    
    private Boolean isActive; // Trạng thái hoạt động - bit

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.auth = user.getAuth();
        this.role = user.getRole();
        this.isActive = user.getIsActive();
    }
}
