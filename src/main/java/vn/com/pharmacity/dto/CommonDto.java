package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDto {
    private String id;
    private String code;
    private String name;
    private String text;
    private String customField1;
    private String customField2;
    
    public CommonDto(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
