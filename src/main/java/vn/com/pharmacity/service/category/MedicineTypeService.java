package vn.com.pharmacity.service.category;

import java.util.Collection;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineTypeDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

/**
 * @author Bac
 * @date 2025/5/20
 */
public interface MedicineTypeService extends BaseRestService<ObjectDataRes<MedicineTypeDto>, MedicineTypeDto> {

    Collection<CommonDto> findAll();

    /*
     * Page<MedicineTypeDto> searchAllByCondition(String code, String name, Pageable
     * pageable);
     * 
     * boolean deleteById(Integer id);
     * 
     * ResponseVO updateDataByCondition(MedicineTypeDto editForm);
     * 
     * ResponseVO saveDataByCondition(MedicineTypeDto form);
     * 
     */
}
