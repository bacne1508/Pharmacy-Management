package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.MedicineStorageDto;
import vn.com.pharmacity.entity.MedicineStorage;

public interface MedicineStorageRepository extends DbRepository<MedicineStorage, Long> {

    List<MedicineStorage> searchAllByCondition(@Param("code") String code,
            @Param("name") String name);

    @Modifying
    void saveData(@Param("form") MedicineStorageDto dto);

    @Modifying
    void updateData(@Param("form") MedicineStorageDto dto);

    @Modifying
    MedicineStorageDto updateDate(@Param("form") MedicineStorage entity);

    List<MedicineStorage> getDataByCondition(@Param("code") String warehouseCode);
}
