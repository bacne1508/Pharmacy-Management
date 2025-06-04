package vn.com.pharmacity.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.PurchaseOrderRequest;

@Getter
@Setter
public class PurchaseOrderRequestDto extends AbstractCreatedTracking {
    private Long id; // ID of the purchase order request
    private Long userId; // ID of the user who created the request
    private Long medicineId; // ID of the requested medicine
    private BigDecimal quantity; // Quantity of the medicine requested
    private String status; // Status of the request: 'PENDING', 'APPROVED', 'LINKED', 'REJECTED'
    private Long linkedPoId; // ID of the linked Purchase Order if applicable
    private String rejectReason; // Reason for rejection if applicable
    
    //user
    private String username; // Username of the user who created the request
    private String medicineName; // Name of the requested medicine
    private String medicineCode; // Code of the requested medicine
    
    public PurchaseOrderRequestDto() {
        // Default constructor
    }
    
    public PurchaseOrderRequestDto(PurchaseOrderRequest purchaseOrderRequest) {
        this.id = purchaseOrderRequest.getId();
        this.userId = purchaseOrderRequest.getUserId();
        this.medicineId = purchaseOrderRequest.getMedicineId();
        this.quantity = purchaseOrderRequest.getQuantity();
        this.status = purchaseOrderRequest.getStatus();
        this.linkedPoId = purchaseOrderRequest.getLinkedPoId();
        this.createdBy = purchaseOrderRequest.getCreatedBy();
        this.createdDate = purchaseOrderRequest.getCreatedDate();
        this.updatedBy = purchaseOrderRequest.getUpdatedBy();
        this.updatedDate = purchaseOrderRequest.getUpdatedDate();
        this.deletedBy = purchaseOrderRequest.getDeletedBy();
        this.deletedDate = purchaseOrderRequest.getDeletedDate();
        this.username = purchaseOrderRequest.getUsername();
        this.medicineName = purchaseOrderRequest.getMedicineName();
        this.medicineCode = purchaseOrderRequest.getMedicineCode();
        this.rejectReason = purchaseOrderRequest.getRejectReason();
    }
}
