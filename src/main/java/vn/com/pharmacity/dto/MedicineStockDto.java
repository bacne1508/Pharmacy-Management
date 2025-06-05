package vn.com.pharmacity.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.MedicineStock;

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
public class MedicineStockDto extends AbstractCreatedTracking {

    private Long id;

    private int medicineId;

    private String batchNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date expiryDate;

    private int quantity;

    private BigDecimal unitPrice;

    private int warehouseId;
    private int lockedQuantity;
    private int usedQuantity;
    
    private String medicineCode;
    private String warehouseCode;

    public MedicineStockDto() {
        // Default constructor
    }
    
    public MedicineStockDto(MedicineStock entity) {
        this.id = entity.getId();
        this.medicineId = entity.getMedicineId();
        this.batchNo = entity.getBatchNo();
        this.expiryDate = entity.getExpiryDate();
        this.quantity = entity.getQuantity();
        this.unitPrice = entity.getUnitPrice();
        this.warehouseId = entity.getWarehouseId();
        this.createdBy = entity.getCreatedBy();
        this.createdDate = entity.getCreatedDate();
        this.updatedBy = entity.getUpdatedBy();
        this.updatedDate = entity.getUpdatedDate();
        this.lockedQuantity = entity.getLockedQuantity();
        this.usedQuantity = entity.getUsedQuantity();
        this.medicineCode = entity.getMedicineCode();
        this.warehouseCode = entity.getWarehouseCode();
    }

}
