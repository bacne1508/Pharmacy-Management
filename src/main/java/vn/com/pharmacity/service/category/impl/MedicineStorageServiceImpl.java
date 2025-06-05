package vn.com.pharmacity.service.category.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineStorageDto;
import vn.com.pharmacity.entity.MedicineStorage;
import vn.com.pharmacity.repository.MedicineStorageRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineStorageService;
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
@Log4j
public class MedicineStorageServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<MedicineStorageDto>, MedicineStorageDto, Long>
        implements MedicineStorageService {
    
    @Autowired
    private MedicineStorageRepository medicineStorageRepository;
    
    private static final String MEDICINE_EXIST = "Storage already exists!";
    
    private static final String STORAGE_CREATE_ERROR = "Storage create error!";
    
    @Override
    protected List<MedicineStorageDto> findAllByCondition(MultiValueMap<String, String> params) {
        String code = params.getFirst("code");
        String name = params.getFirst("name");

        List<MedicineStorage> entities = medicineStorageRepository.searchAllByCondition(code, name);
        return entities.stream().map(MedicineStorageDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineStorageDto findById(Long id) {
        MedicineStorage entity = medicineStorageRepository.findOne(id);
        return entity != null ? new MedicineStorageDto(entity) : null;
    }

    @Override
    protected MedicineStorageDto saveEntity(MedicineStorageDto dto) {
        List<MedicineStorage> existing = medicineStorageRepository.getDataByCondition(dto.getWarehouseCode());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_EXIST);
            }
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            medicineStorageRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(STORAGE_CREATE_ERROR);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(MEDICINE_EXIST);
            }
            dto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setUpdatedDate(new Date());
            
            medicineStorageRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        MedicineStorage entity = medicineStorageRepository.findOne(id);
        if (entity != null) {
            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            entity.setDeletedDate(new Date());
            medicineStorageRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineStorageDto> createDataRes(Page<MedicineStorageDto> page) {
        ObjectDataRes<MedicineStorageDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

    @Override
    public Collection<CommonDto> findAll() {
        return medicineStorageRepository.getAllStorage();
    }

}
