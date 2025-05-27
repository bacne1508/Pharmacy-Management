package vn.com.pharmacity.entity;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.Table;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

/*
 * This class represents a branch of medicine, which is a part of the pharmacy system.
 * It is mapped to the database table defined in AppCoreConstant.TABLE_MEDICINE_BRANCH.
 */
/**
 * @author Bac
 * @date 2025/5/26
 */
@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_MEDICINE_BRANCH)
public class MedicineBranch extends AbstractCreatedTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_MEDICINE_GROUP)
    private Long id;
    
    @Column(name="Branches_Code")
    private String branchesCode;
    
    @Column(name="Branches_Name")
    private String branchesName;
    
    @Column(name="Address")
    private String address;
    
    @Column(name="Province_code")
    private String provinceCode;
    
    @Column(name="Province")
    private String province;
    
    @Column(name="District_code")
    private String districtCode;
    
    @Column(name="District")
    private String district;
    
    @Column(name="Ward_code")
    private String wardCode;
    
    @Column(name="Ward")
    private String ward;
    
    @Column(name="Phone")
    private String phone;
    
    @Column(name="Manager")
    private String manager;
    
    @Column(name="Email")
    private String email;
    
    @Column(name="Is_Active")
    private Boolean isActive = Boolean.TRUE;
}
