package vn.com.pharmacity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.entity.Supplier;

public interface SupplierRepository extends DbRepository<SupplierDto, Long> {
    /**
     * Get all suppliers
     * 
     * @param pageable
     * @return
     */
    List<Supplier> searchAllSupplier(@Param("fullName") String fullName, @Param("email") String email, @Param("phone") String phone);

    @Modifying
    void saveSupplier(@Param("form") SupplierDto form);

    @Modifying
    void updateSupplier(@Param("form") SupplierDto form);

    @Modifying
    SupplierDto deleteSupplier(@Param("form") SupplierDto form);

    Optional<Supplier> getDataByCondition(@Param("email") String email);

}
