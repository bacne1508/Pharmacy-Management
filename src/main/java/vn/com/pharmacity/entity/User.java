package vn.com.pharmacity.entity;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;
import com.miragesql.miragesql.annotation.Table;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_USERS)
public class User extends AbstractCreatedTracking{
    /**
     * identity
     * 0-PATIENT 
     * 1-ADMIN 
     * 2-RECEPTION(Đăng ký bệnh nhân) 
     * 3-DOCTOR 
     * 4-LAB ASSISTANT(Thực hiện & cập nhật kết quả xét nghiệm.)
     * 5-PHARMACY(Cấp phát thuốc, quản lý kho thuốc, xem đơn thuốc)
     */
    public static final Integer AUTH_PATIENT = 0;
    public static final Integer AUTH_ADMIN = 1;
    public static final Integer AUTH_RECEPTION = 2;
    public static final Integer AUTH_DOCTOR = 3;
    public static final Integer AUTH_LAB_ASSISTANT = 4;
    public static final Integer AUTH_PHARMACY = 5;
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_USERS)
    private Long id;
    
    @Column(name="Username")
    private String username; // Tên 
    
    @Column(name="Password")    
    private String password; // pass
    
    @Column(name="Full_Name")    
    private String fullName; 
    
    @Column(name="Email") 
    private String email;

    @Column(name="Phone")
    private String phone;
    
    @Column(name="auth")
    private Integer auth; 
    
    @Column(name="Role")
    private String role;
    
    @Column(name="Is_Active")
    private Boolean isActive; // Trạng thái hoạt động - bit
    
    @Column(name="address")
    private String address;
    
}
