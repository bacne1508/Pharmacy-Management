package vn.com.pharmacity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.MedicineStockDto;
import vn.com.pharmacity.entity.MedicineStock;

public interface MedicineStockRepository extends DbRepository<MedicineStock, Long> {

    List<MedicineStock> searchAllByCondition(@Param("batchNo") String batchNo, @Param("medicineId") String medicineId);

    List<MedicineStock> getDataByCondition(@Param("medicineId") int medicineId, @Param("batchNo") String batchNo, @Param("warehouseId") int warehouseId);

    @Modifying
    void saveData(@Param("form") MedicineStockDto dto);
    
    @Modifying
    void updateData(@Param("form") MedicineStockDto dto);

    @Modifying
    void updateDate(@Param("form") MedicineStock entity);

    Optional<MedicineStock> findAvailableStock(@Param("medicineId") Long medicineId);

    @Modifying
    int lockStock(@Param("medicineId") Long medicineId, @Param("warehouseId") int warehouseId, @Param("batchNo") String batchNo, @Param("quantity") Integer quantity);
    

}
