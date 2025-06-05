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
 * It is mapped to the database table defined in AppCoreConstant.TABLE_MEDICINE_BRANCH.
 */
/**
 * @author Bac
 * @date 2025/5/26
 */
@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_MEDICINE_STOCK)
public class MedicineStock extends AbstractCreatedTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_MEDICINE_STOCK)
    private Long id;
    
    @Column(name = "medicine_id")
    private int medicineId;
    
    @Column(name = "batch_no")
    private String batchNo;
    
    @Column(name = "expiry_date")
    private Date expiryDate;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "warehouse_id")
    private int warehouseId;
    
    @Column(name = "locked_quantity")
    private int lockedQuantity;
    
    @Column(name = "used_quantity")
    private int usedQuantity;
    
    private String medicineCode;
    private String warehouseCode;
}
