package vn.com.pharmacity.entity;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;
import com.miragesql.miragesql.annotation.Table;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

/**
 * Represents a storage entity for medicines. This class extends
 * AbstractCreatedTracking to inherit created tracking features. It is annotated
 * with @Table to map it to the database table defined in AppCoreConstant.
 * 
 * <p>
 * The MedicineStorage class is used to manage medicine storage information in
 * the application.
 * </p>
 *
 * /**
 * 
 * @author Bac
 * @date 2025/5/26
 */
@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_MEDICINE_STORAGE)
public class MedicineStorage extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_MEDICINE_STORAGE)
    private Long id;

    @Column(name="Warehouse_code")
    private String warehouseCode;
    
    @Column(name="Warehouse_Name")
    private String warehouseName;
    
    @Column(name="warehouse_type")
    private String warehouseType;
    
    @Column(name="Branches_Code")
    private String branchesCode;
    
    @Column(name="Manager_name")
    private String managerName;
    
    @Column(name="phone")
    private String phone;
    
    @Column(name="is_Active")
    private int isActive;
}
