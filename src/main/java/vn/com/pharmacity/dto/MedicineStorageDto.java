package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.MedicineStorage;

/**
 * Data Transfer Object for Medicine Storage.
 * 
 * This class is currently empty and can be extended in the future to include
 * fields and methods relevant to medicine storage management.
 * 
 * @author Bac
 * @date 2025/5/20
 */
@Getter
@Setter
public class MedicineStorageDto extends AbstractCreatedTracking {
    private Long id;
    private String warehouseCode;
    private String warehouseName;
    private String warehouseType;
    private String branchesCode;
    private String managerName;
    private String phone;
    private boolean isActive;
    
    public MedicineStorageDto() {
        // Default constructor
    }
    
    public MedicineStorageDto(MedicineStorage storage) {
        this.id = storage.getId();
        this.warehouseCode = storage.getWarehouseCode();
        this.warehouseName = storage.getWarehouseName();
        this.warehouseType = storage.getWarehouseType();
        this.branchesCode = storage.getBranchesCode();
        this.managerName = storage.getManagerName();
        this.phone = storage.getPhone();
        this.isActive = storage.getIsActive() == 1; // Assuming isActive is stored as an integer
        this.createdBy = storage.getCreatedBy();
        this.createdDate = storage.getCreatedDate();
        this.updatedBy = storage.getUpdatedBy();
        this.updatedDate = storage.getUpdatedDate();
        this.deletedBy = storage.getDeletedBy();
        this.deletedDate = storage.getDeletedDate();
    }
}
