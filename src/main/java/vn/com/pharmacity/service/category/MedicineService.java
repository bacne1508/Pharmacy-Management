package vn.com.pharmacity.service.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.webapp.ResponseVO;

/**
*@author Bac
 * @date 2025/5/20
 */
public interface MedicineService {

    Page<MedicineDto> searchAllByCondition(String code, String name, Pageable pageable);

    boolean deleteById(Integer id);

    ResponseVO updateDataByCondition(MedicineDto editForm);

}
