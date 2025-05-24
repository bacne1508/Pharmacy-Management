package vn.com.pharmacity.service.category.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.MedicineGroupDto;
import vn.com.pharmacity.dto.MedicineTypeDto;
import vn.com.pharmacity.entity.MedicineGroup;
import vn.com.pharmacity.entity.MedicineType;
import vn.com.pharmacity.repository.MedicineTypeRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineGroupService;
import vn.com.pharmacity.service.category.MedicineTypeService;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
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
public class MedicineTypeServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<MedicineTypeDto>, MedicineTypeDto, Long>
implements MedicineTypeService {

    @Autowired
    private MedicineTypeRepository medicineTypeRepository;
    
    private static final String MEDICINE_GROUP_EXIST = "Medicine type already exists!";
    private static final String MEDICINE_GROUP_CREATE_ERROR = "Medicine type create error!";
    
    @Override
    protected List<MedicineTypeDto> findAllByCondition(MultiValueMap<String, String> params) {
        String code = params.getFirst("code");
        String name = params.getFirst("name");

        List<MedicineType> entities = medicineTypeRepository.searchAllByCondition(code, name);
        return entities.stream().map(MedicineTypeDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineTypeDto findById(Long id) {
        MedicineType entity = medicineTypeRepository.findOne(id);
        return entity != null ? new MedicineTypeDto(entity) : null;
    }

    @Override
    protected MedicineTypeDto saveEntity(MedicineTypeDto dto) {
        List<MedicineType> existing = medicineTypeRepository.getMedicineByCode(dto.getCode());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_GROUP_CREATE_ERROR);
            }
            medicineTypeRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_GROUP_EXIST);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(MEDICINE_GROUP_EXIST);
            }
            medicineTypeRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        MedicineType entity = medicineTypeRepository.findOne(id);
        if (entity != null) {
            entity.setDelFlag(0);
            medicineTypeRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineTypeDto> createDataRes(Page<MedicineTypeDto> page) {
        ObjectDataRes<MedicineTypeDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }
}
