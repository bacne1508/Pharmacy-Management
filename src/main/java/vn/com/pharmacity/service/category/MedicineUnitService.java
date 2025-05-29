package vn.com.pharmacity.service.category;

import java.util.Collection;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineUnitDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

/**
 * @author Bac
 * @date 2025/5/20
 */
public interface MedicineUnitService extends BaseRestService<ObjectDataRes<MedicineUnitDto>, MedicineUnitDto>{

    Collection<CommonDto> findAll();

    /*
     * Page<MedicineUnitDto> searchAllByCondition(String code, String name, Pageable
     * pageable);
     * 
     * boolean deleteById(Integer id);
     * 
     * ResponseVO updateDataByCondition(MedicineUnitDto editForm);
     * 
     * ResponseVO saveDataByCondition(MedicineUnitDto form);
     */


}
