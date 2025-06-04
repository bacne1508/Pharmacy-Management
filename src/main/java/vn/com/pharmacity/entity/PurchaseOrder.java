package vn.com.pharmacity.entity;

import java.util.Date;

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
@Table(name = AppCoreConstant.TABLE_PURCHASE_ORDER)
public class PurchaseOrder extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_PURCHASE_ORDER)
    private Long id;
    
    @Column(name = "po_code")
    private String poCode;
    
    @Column(name = "supplier_id")
    private int supplierId;
    
    @Column(name = "expected_delivery_date")  
    private Date expectedDeliveryDate;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "created_from")
    private String createdFrom;

}
