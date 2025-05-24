package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.MedicineGroup;

@Getter
@Setter
public class MedicineGroupDto extends AbstractCreatedTracking{

    private Long id;

    private String code;

    private String name;

    private String description;
    
    private Integer delFlag;

    public MedicineGroupDto() {
        super();
    }

    public MedicineGroupDto(MedicineGroup entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.delFlag = entity.getDelFlag();
    }
}
