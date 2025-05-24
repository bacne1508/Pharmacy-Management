package vn.com.pharmacity.service.category.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.MedicineUnitDto;
import vn.com.pharmacity.entity.MedicineUnit;
import vn.com.pharmacity.repository.MedicineUnitRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineUnitService;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;

/**
 * Define user identity as a constant
 * 
 * author Bac
 * @date 2025/5/20
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
public class MedicineUnitServiceImpl
    extends BaseRestServiceImpl<ObjectDataRes<MedicineUnitDto>, MedicineUnitDto, Long>
    implements MedicineUnitService {

    @Autowired
    private MedicineUnitRepository medicineUnitRepository;
    
    private static final String MEDICINE_GROUP_EXIST = "Medicine unit already exists!";
    private static final String MEDICINE_GROUP_CREATE_ERROR = "Medicine unit create error!";

    @Override
    protected List<MedicineUnitDto> findAllByCondition(MultiValueMap<String, String> params) {
        String code = params.getFirst("code");
        String name = params.getFirst("name");

        List<MedicineUnit> entities = medicineUnitRepository.searchAllByCondition(code, name);
        return entities.stream().map(MedicineUnitDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineUnitDto findById(Long id) {
        MedicineUnit entity = medicineUnitRepository.findOne(id);
        return entity != null ? new MedicineUnitDto(entity) : null;
    }

    @Override
    protected MedicineUnitDto saveEntity(MedicineUnitDto dto) {
        List<MedicineUnit> existing = medicineUnitRepository.getMedicineByCode(dto.getCode());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_GROUP_CREATE_ERROR);
            }
            medicineUnitRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_GROUP_EXIST);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(MEDICINE_GROUP_EXIST);
            }
            medicineUnitRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        MedicineUnit entity = medicineUnitRepository.findOne(id);
        if (entity != null) {
            entity.setDelFlag(0);
            medicineUnitRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineUnitDto> createDataRes(Page<MedicineUnitDto> page) {
        ObjectDataRes<MedicineUnitDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }
}
