package vn.com.pharmacity.service.category;

import java.util.Collection;
import java.util.List;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

/**
*@author Bac
 * @date 2025/5/20
 */
public interface MedicineService extends BaseRestService<ObjectDataRes<MedicineDto>, MedicineDto> {

    List<Medicine> getAll(MedicineDto dto);

    Collection<CommonDto> findAll();

//    Page<MedicineDto> searchAllByCondition(String code, String name, Pageable pageable);
//
//    boolean deleteById(Integer id);
//
//    ResponseVO updateDataByCondition(MedicineDto editForm);

}
