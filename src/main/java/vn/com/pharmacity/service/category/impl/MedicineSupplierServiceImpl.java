package vn.com.pharmacity.service.category.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.authentication.UserProfileUtils;
import vn.com.pharmacity.dto.SupplierDto;
import vn.com.pharmacity.entity.Supplier;
import vn.com.pharmacity.entity.User;
import vn.com.pharmacity.repository.SupplierRepository;
import vn.com.pharmacity.service.category.MedicineSupplierService;
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
public class MedicineSupplierServiceImpl implements MedicineSupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    
    private static final String SUPPLIER_EXIST = "Supplier already exists!";

    private List<SupplierDto> movieList2MovieVOList(List<Supplier> movieList) {
        List<SupplierDto> userList = new ArrayList<>();
        for (Supplier user : movieList) {
            userList.add(new SupplierDto(user));
        }
        return userList;
    }
    
    @Override
    public Page<SupplierDto> searchAllSupplier(String fullName, String email, String phone, Pageable pageable) {
        try {
            List<Supplier> userList = supplierRepository.searchAllSupplier(fullName, email, phone);
            List<SupplierDto> userDtoList = movieList2MovieVOList(userList);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), userDtoList.size());
            List<SupplierDto> pagedList = userDtoList.subList(start, end);

            return new PageImpl<>(pagedList, pageable, userDtoList.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty(); // Return an empty page in case of an error
        }
    }

    @Override
    public ResponseVO saveSupplier(SupplierDto form) {
        try {
            if (!supplierRepository.getDataByCondition(form.getEmail()).isEmpty()) {
                return ResponseVO.buildFailure(SUPPLIER_EXIST);
            }
            form.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            form.setCreatedDate(new Date());
            supplierRepository.saveSupplier(form);
      } catch (Exception e) {
          return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO updateSupplier(SupplierDto form) {
        try {
            Optional<Supplier> user = supplierRepository.getDataByCondition(form.getEmail());
            if (!user.isPresent()) {
                return ResponseVO.buildFailure("Supplier does not exist!");
            }
         
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            form.setUpdatedBy(currentUsername); 
            form.setUpdatedDate(new Date());
            
            supplierRepository.updateSupplier(form);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure("Failed to modify information!");
        }
    }

    @Override
    public boolean deleteSupplier(Integer id) {
        boolean isDeleted = true;
        try {
            SupplierDto en = supplierRepository.findOne(Long.valueOf(id));
            if (en != null) {
                en.setDeletedBy(UserProfileUtils.getUserNameLogin());
                en.setDeletedDate(new Date());
                supplierRepository.deleteSupplier(en);
            }
        } catch (Exception e) {
            isDeleted = false;
        }
        return isDeleted;
    }

}
