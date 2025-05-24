package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.MedicineTypeDto;
import vn.com.pharmacity.entity.MedicineType;

/**
 * MedicineTypeRepository is an interface that extends DbRepository for
 * performing CRUD operations on MedicineType entities.
 * 
 * @author Bac
 * @date 2025/5/20
 */
public interface MedicineTypeRepository extends DbRepository<MedicineType, Long> {

    List<MedicineType> searchAllByCondition(@Param("code") String code, @Param("name") String name);

    List<MedicineType> getMedicineByCode(@Param("code") String code);

    @Modifying
    void saveData(@Param("form") MedicineTypeDto form);

    //delete
    @Modifying
    void updateDate(@Param("en") MedicineType en);

    //update
    @Modifying
    void updateData(@Param("en") MedicineTypeDto editForm);

}
