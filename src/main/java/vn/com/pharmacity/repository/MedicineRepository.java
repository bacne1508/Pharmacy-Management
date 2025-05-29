package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.entity.Medicine;

public interface MedicineRepository extends DbRepository<Medicine, Long> {

    List<Medicine> searchAllByCondition(@Param("code") String code,
            @Param("name") String name);

    @Modifying
    void saveData(@Param("form") MedicineDto dto);

    @Modifying
    void updateData(@Param("form") MedicineDto dto);

    @Modifying
    MedicineDto updateDate(@Param("form") Medicine entity);

    List<Medicine> getDataByCondition(@Param("code") String code);

}
