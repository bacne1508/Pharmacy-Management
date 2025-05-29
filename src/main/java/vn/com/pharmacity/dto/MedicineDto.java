package vn.com.pharmacity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.Medicine;

@Getter
@Setter
public class MedicineDto extends AbstractCreatedTracking {
    
    private Long id;
    private String code; // Mã thuốc
    private String name; // Tên thuốc
    private String medicineImages; // Hình ảnh thuốc
    private String description; // Mô tả thuốc
    private String medicineGroupsCode; // Nhóm thuốc
    private String medicineUnitsCode; // Đơn vị thuốc
    private String medicineTypesCode; // Loại thuốc
    private String ingredient; // Thành phần thuốc
    private String strength; // Hàm lượng thuốc
    private String manufacturer; // Nhà sản xuất
    private String originCountry; // Nước sản xuất
    private BigDecimal purchasePrice; // Giá nhập
    private BigDecimal salePrice; // Giá bán
    private int quantity; // Số lượng thuốc
    private int isActive; // Trạng thái hoạt động - bit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfManufacture; // Ngày sản xuất
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date productExpiryDate; // Ngày hết hạn sản phẩm
    private List<String> base64Images; // Base64 encoded images
    private String fileName; // Tên file hình ảnh
    
    public MedicineDto() {
        // Default constructor
    }

    public MedicineDto(Medicine entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.medicineGroupsCode = entity.getMedicineGroupsCode();
        this.medicineUnitsCode = entity.getMedicineUnitsCode();
        this.medicineTypesCode = entity.getMedicineTypesCode();
        this.ingredient = entity.getIngredient();
        this.strength = entity.getStrength();
        this.manufacturer = entity.getManufacturer();
        this.originCountry = entity.getOriginCountry();
        this.purchasePrice = entity.getPurchasePrice();
        this.salePrice = entity.getSalePrice();
        this.quantity = entity.getQuantity();
        this.isActive = entity.getIsActive();
        this.medicineImages = entity.getMedicineImages();
        this.dateOfManufacture = entity.getDateOfManufacture();
        this.productExpiryDate = entity.getProductExpiryDate();
        this.fileName = entity.getFileName();
        this.createdBy = entity.getCreatedBy();
        this.createdDate = entity.getCreatedDate();
        this.updatedBy = entity.getUpdatedBy();
        this.updatedDate = entity.getUpdatedDate();
        this.deletedBy = entity.getDeletedBy();
        this.deletedDate = entity.getDeletedDate();
    }
}
