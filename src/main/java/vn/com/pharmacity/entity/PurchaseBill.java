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
@Table(name = AppCoreConstant.TABLE_BILL)
public class PurchaseBill extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_BILL)
    private Long id;
    
    @Column(name = "bill_code")
    private String poCode;
    
    @Column(name = "bill_type")
    private String billType;
    
    @Column(name = "order_id")
    private int orderId;
    
    @Column(name = "customer_id")
    private int customerId;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "employee_id")
    private int employeeId;
    
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    @Column(name = "status")
    private String status;
}
