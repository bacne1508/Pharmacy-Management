package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.MedicineBranch;

/**
 * @author Bac
 * @date 2025/5/26
 */
@Getter
@Setter
public class MedicineBranchDto extends AbstractCreatedTracking {

    private Long id;
    private String branchesCode;
    private String branchesName;
    private String address;
    private String provinceCode;
    private String province;
    private String districtCode;
    private String district;
    private String wardCode;
    private String ward;
    private String phone;
    private String email;
    private String manager;
    
    public MedicineBranchDto() {
        // Default constructor
    }
    
    public MedicineBranchDto(MedicineBranch entity) {
        this.id = entity.getId();
        this.branchesCode = entity.getBranchesCode();
        this.branchesName = entity.getBranchesName();
        this.address = entity.getAddress();
        this.provinceCode = entity.getProvinceCode();
        this.province = entity.getProvince();
        this.districtCode = entity.getDistrictCode();
        this.district = entity.getDistrict();
        this.wardCode = entity.getWardCode();
        this.ward = entity.getWard();
        this.phone = entity.getPhone();
        this.createdBy = entity.getCreatedBy();
        this.createdDate = entity.getCreatedDate();
        this.updatedBy = entity.getUpdatedBy();
        this.updatedDate = entity.getUpdatedDate();
        this.deletedBy = entity.getDeletedBy();
        this.deletedDate = entity.getDeletedDate();
        this.email = entity.getEmail();
        this.manager = entity.getManager();
    }
}
