package vn.com.pharmacity.entity;

import java.math.BigDecimal;

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
@Table(name = AppCoreConstant.TABLE_WALK_IN_PURCHASE_ITEM)
public class WalkInInvoiceItem extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_WALK_IN_PURCHASE_INVOICE)
    private Long id;
    
    @Column(name = "invoice_id")
    private Long invoiceId;
    
    @Column(name = "medicine_id")
    private Long medicineId;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
