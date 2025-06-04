package vn.com.pharmacity.entity;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = AppCoreConstant.TABLE_PURCHASE_ORDER_DETAILS)
public class PurchaseOrderDetails extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_PURCHASE_ORDER_DETAILS)
    private Long id;
    
    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;
    
    @Column(name = "medicine_id")
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
}
