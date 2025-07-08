package vn.com.pharmacity.service.category.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.miragesql.miragesql.util.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.MedicineStockDto;
import vn.com.pharmacity.entity.MedicineStock;
import vn.com.pharmacity.repository.MedicineStockRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineStockService;
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
public class MedicineStockServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<MedicineStockDto>, MedicineStockDto, Long>
implements MedicineStockService {
    
    private final MedicineStockRepository medicineStockRepository;
    
    private static final String STOCK_EXIST = "Stock already exists!";
    
    private static final String STOCK_CREATE_ERROR = "Stock create error!";
    
    @Override
    protected List<MedicineStockDto> findAllByCondition(MultiValueMap<String, String> params) {
        String batchNo = params.getFirst("batchNo");
        String medicineId = params.getFirst("medicineId");
        if(StringUtil.isBlank(medicineId)) {
            medicineId = "0";
        }
        List<MedicineStock> entities = medicineStockRepository.searchAllByCondition(batchNo, Long.parseLong(medicineId));
        return entities.stream().map(MedicineStockDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineStockDto findById(Long id) {
        MedicineStock entity = medicineStockRepository.findOne(id);
        return entity != null ? new MedicineStockDto(entity) : null;
    }

    @Override
    protected MedicineStockDto saveEntity(MedicineStockDto dto) {
        List<MedicineStock> existing = medicineStockRepository.getDataByCondition(dto.getMedicineId(), dto.getBatchNo(), dto.getWarehouseId());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(STOCK_EXIST);
            }
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            medicineStockRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(STOCK_CREATE_ERROR);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(STOCK_EXIST);
            }
            dto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setUpdatedDate(new Date());
            
            medicineStockRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
        MedicineStock entity = medicineStockRepository.findOne(id);
        if (entity != null) {
            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            entity.setDeletedDate(new Date());
            medicineStockRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineStockDto> createDataRes(Page<MedicineStockDto> page) {
        ObjectDataRes<MedicineStockDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

}
