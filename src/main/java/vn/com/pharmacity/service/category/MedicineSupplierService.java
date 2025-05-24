package vn.com.pharmacity.service.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.webapp.ResponseVO;

public interface MedicineSupplierService {
    /**
     * Get all suppliers
     * 
     * @param pageable
     * @return
     */
    Page<SupplierDto> searchAllSupplier(String fullName, String email, String phone, Pageable pageable);

    ResponseVO saveSupplier(SupplierDto form);

    ResponseVO updateSupplier(SupplierDto form);

    boolean deleteSupplier(Integer id);
}
