package vn.com.pharmacity.service.category;

import java.util.Collection;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

public interface MedicineSupplierService extends BaseRestService<ObjectDataRes<SupplierDto>, SupplierDto>{

    Collection<CommonDto> findAll();
    /**
     * Get all suppliers
     * 
     * @param pageable
     * @return
     */
//    Page<SupplierDto> searchAllSupplier(String fullName, String email, String phone, Pageable pageable);
//
//    ResponseVO saveSupplier(SupplierDto form);
//
//    ResponseVO updateSupplier(SupplierDto form);
//
//    boolean deleteSupplier(Integer id);
}
