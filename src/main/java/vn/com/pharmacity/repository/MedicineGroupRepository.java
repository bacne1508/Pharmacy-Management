package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.MedicineGroupDto;
import vn.com.pharmacity.entity.MedicineGroup;

/**
 * MedicineGroupRepository is an interface that extends DbRepository for
 * performing CRUD operations on Medicine entities.
 * 
 * @author Bac
 * @date 2025/5/20
 */
public interface MedicineGroupRepository extends DbRepository<MedicineGroup, Long> {

    List<MedicineGroup> searchAllByCondition(@Param("code") String code, @Param("name") String name);

    List<MedicineGroup> getMedicineByCode(@Param("code") String code);

    @Modifying
    void saveData(@Param("form") MedicineGroupDto form);

    //delete
    @Modifying
    void updateDate(@Param("en") MedicineGroup en);

    //update
    @Modifying
    void updateData(@Param("en") MedicineGroupDto editForm);

}
