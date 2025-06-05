package vn.com.pharmacity.service.category.impl;

import java.text.SimpleDateFormat;
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
import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.repository.MedicineRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.category.MedicineService;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.utils.BarcodeUtil;

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
public class MedicineServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<MedicineDto>, MedicineDto, Long>
implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;
    
    private static final String MEDICINE_EXIST = "Medicine already exists!";
    
    private static final String BRANCH_CREATE_ERROR = "Medicine create error!";

    @Override
    protected List<MedicineDto> findAllByCondition(MultiValueMap<String, String> params) {
        String code = params.getFirst("code");
        String name = params.getFirst("name");

        List<Medicine> entities = medicineRepository.searchAllByCondition(code, name);
        return entities.stream().map(MedicineDto::new).collect(Collectors.toList());
    }

    @Override
    protected MedicineDto findById(Long id) {
        Medicine entity = medicineRepository.findOne(id);
        return entity != null ? new MedicineDto(entity) : null;
    }

    @Override
    protected MedicineDto saveEntity(MedicineDto dto) {
        List<Medicine> existing = medicineRepository.getDataByCondition(dto.getCode());

        if (dto.getId() == 0) {
            // Create
            if (!existing.isEmpty()) {
                throw new RuntimeException(MEDICINE_EXIST);
            }
            dto.setCode(this.generalCode("medicine", "code", "M_", 5));
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            dto.setMedicineImages(dto.getBase64Images().get(0)); // Handle image conversion if needed
            try {
                dto.setBarcode(BarcodeUtil.generateBarcodeBase64(dto.getCode()));
            } catch (Exception e) {
                log.error("Error generating barcode for medicine: {}", e);
            }
            medicineRepository.saveData(dto);
        } else {
            // Update
            if (existing == null || existing.isEmpty()) {
                throw new RuntimeException(BRANCH_CREATE_ERROR);
            }
            if (existing.size() > 1) {
                throw new RuntimeException(MEDICINE_EXIST);
            }
            dto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setUpdatedDate(new Date());
            if (dto.getBase64Images() == null || dto.getBase64Images().isEmpty()) {
                dto.setMedicineImages(existing.get(0).getMedicineImages()); // Ensure at least one image is present    
            }else {
                dto.setMedicineImages(dto.getBase64Images().get(0));
            }
            
            medicineRepository.updateData(dto);
        }

        return dto;
    }

    private String generalCode(String tableName, String columnName, String perfix, Integer length) {
        String codeNO = "";

        try {
            String yy = new SimpleDateFormat("yy").format(new Date());
            String mm = new SimpleDateFormat("MM").format(new Date());

            String perfixCode = perfix + yy + mm;
            String maxNO = medicineRepository.findMaxNo(tableName, columnName, perfixCode);

            String formatLength = "%05d";

            if (length != null) {
                formatLength = "%0".concat(String.valueOf(length)).concat("d");
            }

            if (maxNO == null || "".equals(maxNO.trim())) {
                codeNO = perfixCode + "." + String.format(formatLength, 1);
            } else {
                String[] lstForm = maxNO.split("\\.");
                String number = lstForm[1];
                String nextNumber = String.format(formatLength, Integer.valueOf(number) + 1);
                codeNO = lstForm[0] + "." + nextNumber;
            }
            log.info("CODE GENERALIZED: " + codeNO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return codeNO;
    }

    @Override
    protected void deleteEntity(Long id) {
        Medicine entity = medicineRepository.findOne(id);
        if (entity != null) {
            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            entity.setDeletedDate(new Date());
            medicineRepository.updateDate(entity);
        }
    }

    @Override
    protected ObjectDataRes<MedicineDto> createDataRes(Page<MedicineDto> page) {
        ObjectDataRes<MedicineDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

    @Override
    public List<Medicine> getAll(MedicineDto dto) {
        return medicineRepository.searchAllByCondition(null, null);
    }

    @Override
    public Collection<CommonDto> findAll() {
        return medicineRepository.findAllMedicine();
    }

}
