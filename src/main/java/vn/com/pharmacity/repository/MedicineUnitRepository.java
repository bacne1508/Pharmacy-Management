package vn.com.pharmacity.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineUnitDto;
import vn.com.pharmacity.entity.MedicineUnit;

/**
 * MedicineUnitRepository is an interface that extends DbRepository for
 * performing CRUD operations on MedicineUnit entities.
 * 
 * @author Bac
 * @date 2025/5/20
 */
public interface MedicineUnitRepository extends DbRepository<MedicineUnit, Long> {
    
    List<MedicineUnit> searchAllByCondition(@Param("code") String code, @Param("name") String name);

    List<MedicineUnit> getMedicineByCode(@Param("code") String code);

    @Modifying
    void saveData(@Param("form") MedicineUnitDto form);

    // delete
    @Modifying
    void updateDate(@Param("en") MedicineUnit en);

    // update
    @Modifying
    void updateData(@Param("en") MedicineUnitDto editForm);

    Collection<CommonDto> findAllTypes();

}
