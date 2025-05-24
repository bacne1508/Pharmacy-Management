package vn.com.pharmacity.entity;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.Table;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_MEDICINE_SUPPLIER)
public class Supplier extends AbstractCreatedTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_MEDICINE_SUPPLIER)
    private Long id;
    
    @Column(name="Full_Name")    
    private String fullName; 
    
    @Column(name="Email") 
    private String email;

    @Column(name="Phone")
    private String phone;
    
    @Column(name="address")
    private String address;
}
