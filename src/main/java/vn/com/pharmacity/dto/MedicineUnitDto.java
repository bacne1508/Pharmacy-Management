package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.MedicineUnit;

@Getter
@Setter
public class MedicineUnitDto extends AbstractCreatedTracking{

    private Long id;

    private String code;

    private String name;

    private String description;
    
    private Integer delFlag;

    public MedicineUnitDto() {
        super();
    }

    public MedicineUnitDto(MedicineUnit entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.delFlag = entity.getDelFlag();
    }
}
