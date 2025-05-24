package vn.com.pharmacity.entity;

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
@Table(name = AppCoreConstant.TABLE_MEDICINE_GROUP)
public class MedicineGroup extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_MEDICINE_GROUP)
    private Long id;
    
    @Column(name="code")
    private String code;
    
    @Column(name="name")
    private String name;
    
    @Column(name="description")
    private String description;
    
    @Column(name="del_flag")
    private Integer delFlag;
}
