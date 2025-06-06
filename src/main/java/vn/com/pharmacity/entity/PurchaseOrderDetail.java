package vn.com.pharmacity.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;
import com.miragesql.miragesql.annotation.Table;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

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
@Table(name = AppCoreConstant.TABLE_PURCHASE_ORDER_DETAILS)
public class PurchaseOrderDetail extends AbstractCreatedTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_PURCHASE_ORDER_DETAILS)
    private Long id;
    
    @Column(name = "Purchase_Order_Id")
    private Long purchaseOrderId;
    
    @Column(name = "Medicine_Id")
    private Long medicineId;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "batch_no")
    private String batchNo;
    
    @Column(name = "expiry_date")
    private Date expiryDate;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "po_request_id")
    private Long poRequestId; // This field is not present in the entity but is used for request purposes
    
    @Column(name = "po_request_group")
    private String poRequestGroup; // This field is not present in the entity but is used for request purposes

}
