package vn.com.pharmacity.service.category.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.authentication.UserProfileUtils;
import vn.com.pharmacity.dto.MedicineBranchDto;
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.entity.MedicineBranch;
import vn.com.pharmacity.entity.Supplier;
import vn.com.pharmacity.repository.MedicineBranchRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineBranchService;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;

/**
 * @author Bac
 * @date 2025/5/26
 */
/**
 * This service class is responsible for handling operations related to medicine
 * branches. It is annotated with @CoreReadOnlyTx to indicate that it operates
 * in a read-only transaction context.
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
public class MedicineBranchServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<MedicineBranchDto>, MedicineBranchDto, Long>
implements MedicineBranchService {

    @Autowired
    private MedicineBranchRepository medicineBranchRepository;
    
    private static final String BRANCH_EXIST = "Branch already exists!";
    private static final String BRANCH_CREATE_ERROR = "Branch create error!";
    
    @Override
    protected List<MedicineBranchDto> findAllByCondition(MultiValueMap<String, String> params) {
        String branchCode = params.getFirst("branchesCode");
        String branchName = params.getFirst("branchesName");
        String phone = params.getFirst("phone");
        String manager = params.getFirst("manager");

        List<MedicineBranch> entities = medicineBranchRepository.searchAllByCondition(branchCode, branchName, phone, manager);
        return entities.stream().map(MedicineBranchDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineBranchDto findById(Long id) {
        MedicineBranch entity = medicineBranchRepository.findOne(id);
        return entity != null ? new MedicineBranchDto(entity) : null;
    }

    @Override
    protected MedicineBranchDto saveEntity(MedicineBranchDto dto) {
        List<MedicineBranch> existing = medicineBranchRepository.getDataByCondition(dto.getBranchesCode());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(BRANCH_CREATE_ERROR);
            }
            dto.setCreatedBy(UserProfileUtils.getUserNameLogin());
            dto.setCreatedDate(new Date());
            medicineBranchRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(BRANCH_EXIST);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(BRANCH_EXIST);
            }
            dto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setUpdatedDate(new Date());
            medicineBranchRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        MedicineBranch entity = medicineBranchRepository.findOne(id);
        if (entity != null) {
            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            entity.setDeletedDate(new Date());
            medicineBranchRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineBranchDto> createDataRes(Page<MedicineBranchDto> page) {
        ObjectDataRes<MedicineBranchDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

}
