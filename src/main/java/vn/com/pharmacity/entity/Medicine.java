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

@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_MEDICINE)
public class Medicine extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_MEDICINE)
    private Long id;

    @Column(name="code")
    private String code;
    
    @Column(name="name")
    private String name;
    
    @Column(name="medicine_Images")
    private String medicineImages;
    
    @Column(name="description")
    private String description;
    
    @Column(name="medicine_Groups_Code")
    private String medicineGroupsCode;
    
    @Column(name="medicine_Units_Code")
    private String medicineUnitsCode;
    
    @Column(name="medicine_Types_Code")
    private String medicineTypesCode;
    
    @Column(name="ingredient")
    private String ingredient;
    
    @Column(name="strength")
    private String strength;
    
    @Column(name="manufacturer")
    private String manufacturer;
    
    @Column(name="origin_Country")
    private String originCountry;
    
    @Column(name="purchase_Price")
    private BigDecimal purchasePrice;
    
    @Column(name="sale_Price")
    private BigDecimal salePrice;
    
    @Column(name="quantity")
    private int quantity;
    
    @Column(name="date_Of_Manufacture")
    private Date dateOfManufacture;
    
    @Column(name="product_Expiry_Date")
    private Date productExpiryDate;
    
    @Column(name="is_Active")
    private int isActive;
}
