package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.Supplier;

@Getter
@Setter
public class SupplierDto extends AbstractCreatedTracking {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    
    public SupplierDto() {
        // Default constructor
    }
    
    public SupplierDto(Supplier entity) {
        this.id = entity.getId();
        this.fullName = entity.getFullName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.address = entity.getAddress();
        this.createdBy = entity.getCreatedBy();
        this.createdDate = entity.getCreatedDate();
        this.updatedBy = entity.getUpdatedBy();
        this.updatedDate = entity.getUpdatedDate();
        this.deletedBy = entity.getDeletedBy();
        this.deletedDate = entity.getDeletedDate();
    }
}
