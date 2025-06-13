package vn.com.pharmacity.entity;

import java.math.BigDecimal;
import java.util.List;

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
@Table(name = AppCoreConstant.TABLE_WALK_IN_PURCHASE_INVOICE)
public class WalkInPurchaseInvoice extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_WALK_IN_PURCHASE_INVOICE)
    private Long id;
    
    @Column(name = "invoice_code")
    private String invoiceCode;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "card_number")
    private String cardNumber;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    @Column(name = "payment_status")
    private String paymentStatus;
    
    @Column(name = "notes")
    private String notes;
    
    @Column(name = "Diagnosis")
    private String diagnosis;

    private List<WalkInInvoiceItem> medicines;
}
