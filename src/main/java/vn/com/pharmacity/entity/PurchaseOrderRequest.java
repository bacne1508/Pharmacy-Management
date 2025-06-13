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
@Table(name = AppCoreConstant.TABLE_PURCHASE_ORDER_REQUEST)
public class PurchaseOrderRequest extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_PURCHASE_ORDER_REQUEST)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "medicine_id")
    private Long medicineId;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "status")
    private String status; // 'PENDING', 'APPROVED', 'LINKED', 'REJECTED'
    
    @Column(name = "linked_po_id")
    private Long linkedPoId; // ID of the linked Purchase Order if applicable
    
    @Column(name = "reject_reason")
    private String rejectReason; // Reason for rejection if applicable
    
    @Column(name = "request_group")
    private String requestGroup; // Automatically generated group for the request
    
    @Column(name = "Request_Flag")
    private int requestFlag;
    
    private String username; // Username of the user who created the request
    private String medicineName; // Name of the requested medicine
    private String medicineCode; // Code of the requested medicine
}
