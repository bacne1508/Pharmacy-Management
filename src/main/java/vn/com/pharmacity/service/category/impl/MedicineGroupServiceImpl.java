package vn.com.pharmacity.service.category.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineGroupDto;
import vn.com.pharmacity.entity.MedicineGroup;
import vn.com.pharmacity.repository.MedicineGroupRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineGroupService;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;

@Service
@RequiredArgsConstructor
public class MedicineGroupServiceImpl
    extends BaseRestServiceImpl<ObjectDataRes<MedicineGroupDto>, MedicineGroupDto, Long>
    implements MedicineGroupService {

    private final MedicineGroupRepository medicineGroupRepository;

    private static final String MEDICINE_GROUP_EXIST = "Medicine group already exists!";
    private static final String MEDICINE_GROUP_NOT_EXIST = "Medicine group does not exist!";
    private static final String MEDICINE_GROUP_CREATE_ERROR = "Medicine group create error!";

    @Override
    protected List<MedicineGroupDto> findAllByCondition(MultiValueMap<String, String> params) {
        String code = params.getFirst("code");
        String name = params.getFirst("name");

        List<MedicineGroup> entities = medicineGroupRepository.searchAllByCondition(code, name);
        return entities.stream().map(MedicineGroupDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineGroupDto findById(Long id) {
        MedicineGroup entity = medicineGroupRepository.findOne(id);
        return entity != null ? new MedicineGroupDto(entity) : null;
    }

    @Override
    protected MedicineGroupDto saveEntity(MedicineGroupDto dto) {
        List<MedicineGroup> existing = medicineGroupRepository.getMedicineByCode(dto.getCode());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_GROUP_EXIST);
            }
            medicineGroupRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_GROUP_NOT_EXIST);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(MEDICINE_GROUP_EXIST);
            }
            medicineGroupRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        MedicineGroup entity = medicineGroupRepository.findOne(id);
        if (entity != null) {
            entity.setDelFlag(0);
            medicineGroupRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineGroupDto> createDataRes(Page<MedicineGroupDto> page) {
        ObjectDataRes<MedicineGroupDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

    @Override
    public Collection<CommonDto> findAll() {
        return medicineGroupRepository.findAllGroup();
    }
}
