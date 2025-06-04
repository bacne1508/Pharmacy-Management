package vn.com.pharmacity.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.PurchaseOrderDetail;

/*
 * This class represents a branch of medicine, which is a part of the pharmacy system.
 * It is mapped to the database table defined
 */
/**
 * @author Bac
 * @date 2025/5/26
 */
@Getter
@Setter
public class PurchaseOrderDetailDto extends AbstractCreatedTracking {
    private Long id;

    private Long purchaseOrderId;

    private Long medicineId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private String batchNo;

    private Date expiryDate;

    private String description;
    
    public PurchaseOrderDetailDto() {
        // Default constructor
    }
    
    public PurchaseOrderDetailDto(PurchaseOrderDetail purchaseOrderDetail) {
        this.id = purchaseOrderDetail.getId();
        this.purchaseOrderId = purchaseOrderDetail.getPurchaseOrderId();
        this.medicineId = purchaseOrderDetail.getMedicineId();
        this.quantity = purchaseOrderDetail.getQuantity();
        this.unitPrice = purchaseOrderDetail.getUnitPrice();
        this.batchNo = purchaseOrderDetail.getBatchNo();
        this.expiryDate = purchaseOrderDetail.getExpiryDate();
        this.description = purchaseOrderDetail.getDescription();
        this.createdBy = purchaseOrderDetail.getCreatedBy();
        this.createdDate = purchaseOrderDetail.getCreatedDate();
        this.updatedBy = purchaseOrderDetail.getUpdatedBy();
        this.updatedDate = purchaseOrderDetail.getUpdatedDate();
        this.deletedBy = purchaseOrderDetail.getDeletedBy();
        this.deletedDate = purchaseOrderDetail.getDeletedDate();
    }
}
