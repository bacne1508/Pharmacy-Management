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
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.entity.Supplier;
import vn.com.pharmacity.repository.SupplierRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineSupplierService;
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
public class MedicineSupplierServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<SupplierDto>, SupplierDto, Long>
implements MedicineSupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    
    private static final String SUPPLIER_EXIST = "Supplier already exists!";
    private static final String SUPPLIER_GROUP_CREATE_ERROR = "Supplier unit create error!";
    
    @Override
    protected List<SupplierDto> findAllByCondition(MultiValueMap<String, String> params) {
        String fullName = params.getFirst("fullName");
        String email = params.getFirst("email");
        String phone = params.getFirst("phone");

        List<Supplier> entities = supplierRepository.searchAllByCondition(fullName, email, phone);
        return entities.stream().map(SupplierDto::new).collect(Collectors.toList());
    }

    @Override
    protected SupplierDto findById(Long id) {
        Supplier entity = supplierRepository.findOne(id);
        return entity != null ? new SupplierDto(entity) : null;
    }

    @Override
    protected SupplierDto saveEntity(SupplierDto dto) {
        List<Supplier> existing = supplierRepository.getDataByCondition(dto.getEmail());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(SUPPLIER_GROUP_CREATE_ERROR);
            }
            supplierRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(SUPPLIER_EXIST);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(SUPPLIER_EXIST);
            }
            supplierRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        Supplier entity = supplierRepository.findOne(id);
        if (entity != null) {
            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            entity.setDeletedDate(new Date());
            supplierRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<SupplierDto> createDataRes(Page<SupplierDto> page) {
        ObjectDataRes<SupplierDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

}
