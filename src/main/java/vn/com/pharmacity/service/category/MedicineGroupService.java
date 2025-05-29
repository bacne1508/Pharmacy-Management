package vn.com.pharmacity.service.category;

import java.util.Collection;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineGroupDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

/**
*@author Bac
 * @date 2025/5/20
 */
public interface MedicineGroupService extends BaseRestService<ObjectDataRes<MedicineGroupDto>, MedicineGroupDto> {

    Collection<CommonDto> findAll();

//    Page<MedicineGroupDto> searchAllByCondition(String code, String name, Pageable pageable);
//
//    boolean deleteById(Integer id);
//
//    ResponseVO updateDataByCondition(MedicineGroupDto editForm);
//
//    ResponseVO saveDataByCondition(MedicineGroupDto form);

}
