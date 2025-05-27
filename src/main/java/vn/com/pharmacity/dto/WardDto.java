package vn.com.pharmacity.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * It is currently empty but can be extended in the future to include fields
 * and methods related to a Ward.
 * This DTO can be used to transfer data between different layers of the application,
 * @author Bac
 * @date 2025/5/26
 */
@Getter
@Setter
public class WardDto {
    private String xaId;
    private String code;
    private String name;
    private String maQh;
}
