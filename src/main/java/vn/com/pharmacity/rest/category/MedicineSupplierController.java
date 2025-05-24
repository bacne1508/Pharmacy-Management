package vn.com.pharmacity.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineSupplierService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine/supplier")
public class MedicineSupplierController extends BaseRestController<ObjectDataRes<SupplierDto>, SupplierDto> {

    public MedicineSupplierController(MedicineSupplierService  baseService) {
        super(baseService);
    }
}
