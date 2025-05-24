package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.User;

@Getter
@Setter
public class UserDto extends AbstractCreatedTracking{
    private Long id;
    
    private String username; // Tên 
       
    private String password; // pass
        
    private String fullName; 
    
    private String email;

    private String phone;
    
    private Integer auth; 
    
    private String role;
    
    private Boolean isActive; // Trạng thái hoạt động - bit

    private String address;
    
    public UserDto() {
        // Default constructor
    }

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
        this.address = user.getAddress();
        this.createdDate = user.getCreatedDate();
        this.createdBy = user.getCreatedBy();
        this.updatedDate = user.getUpdatedDate();
        this.updatedBy = user.getUpdatedBy();
    }
}
