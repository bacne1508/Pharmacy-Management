package vn.com.pharmacity.service.category.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.dto.UserDto;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.entity.User;
import vn.com.pharmacity.repository.MedicineRepository;
import vn.com.pharmacity.service.category.MedicineService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * Define user identity as a constant
 * 
 * author Bac
 * @date 2025/5/20
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;
    
    private static final String MEDICINE_EXIST = "Medicine already exists!";

    private List<MedicineDto> list2VOList(List<Medicine> entityList) {
        List<MedicineDto> dtoList = new ArrayList<>();
        for (Medicine entity : entityList) {
            dtoList.add(new MedicineDto(entity));
        }
        return dtoList;
    }
    
    @Override
    public Page<MedicineDto> searchAllByCondition(String code, String name, Pageable pageable) {
        try {
            List<Medicine> entity = medicineRepository.searchAllByCondition(code, name);
            List<MedicineDto> dtoList = list2VOList(entity);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), dtoList.size());
            List<MedicineDto> pagedList = dtoList.subList(start, end);

            return new PageImpl<>(pagedList, pageable, dtoList.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty(); // Return an empty page in case of an error
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResponseVO updateDataByCondition(MedicineDto editForm) {
        // TODO Auto-generated method stub
        return null;
    }

    // Add your service methods here

}
