package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.pharmacity.entity.Medicine;

public interface MedicineRepository extends DbRepository<Medicine, Long> {

    List<Medicine> searchAllByCondition(@Param("code") String code, @Param("name") String name);

}
