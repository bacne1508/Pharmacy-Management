package vn.com.pharmacity.service.purchase.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.AuditAction;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
import vn.com.pharmacity.entity.PurchaseOrderRequest;
import vn.com.pharmacity.repository.PurchaseOrderRequestRepository;
import vn.com.pharmacity.req.BulkActionRequest;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.service.purchase.PurchaseOrderRequestService;

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
public class PurchaseOrderRequestServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<PurchaseOrderRequestDto>, PurchaseOrderRequestDto, Long>
implements PurchaseOrderRequestService {
    
    // Add any necessary repository or service dependencies here
    private final PurchaseOrderRequestRepository purchaseOrderRequestRepository;
    
    private static final String MEDICINE_NOT_EXIST = "Request not exists!";
    
    private static final String STORAGE_CREATE_ERROR = "Purchase order request create error!";
    
    @Override
    protected List<PurchaseOrderRequestDto> findAllByCondition(MultiValueMap<String, String> params) {
        String username = params.getFirst("username");
        String status = params.getFirst("status");

        List<PurchaseOrderRequest> entities = purchaseOrderRequestRepository.searchAllByCondition(username, status);
        return entities.stream().map(PurchaseOrderRequestDto::new).collect(Collectors.toList());
    }

    @Override
    protected PurchaseOrderRequestDto findById(Long id) {
        PurchaseOrderRequest entity = purchaseOrderRequestRepository.findOne(id);
        return entity != null ? new PurchaseOrderRequestDto(entity) : null;
    }

    @Override
    protected PurchaseOrderRequestDto saveEntity(PurchaseOrderRequestDto dto) {
//        List<PurchaseOrderRequest> existing = purchaseOrderRequestRepository.getDataByCondition(dto.getId());

        if (dto.getId() == 0) {
            // Create
            dto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setCreatedDate(new Date());
            purchaseOrderRequestRepository.saveData(dto);
        } else {
            PurchaseOrderRequest entity = purchaseOrderRequestRepository.findOne(dto.getId());
            // Update
            if (Objects.isNull(entity)) {
                throw new RuntimeException(MEDICINE_NOT_EXIST);
            }
            dto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setUpdatedDate(new Date());
            
            purchaseOrderRequestRepository.updateData(dto);
        }

        return dto;
    }

    @Override
    protected void deleteEntity(Long id) {
//        PurchaseOrderRequest entity = purchaseOrderRequestRepository.findOne(id);
//        if (entity != null) {
//            entity.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
//            entity.setDeletedDate(new Date());
//            purchaseOrderRequestRepository.updateDate(entity);
//        }
    }

    @Override
    protected ObjectDataRes<PurchaseOrderRequestDto> createDataRes(Page<PurchaseOrderRequestDto> page) {
        ObjectDataRes<PurchaseOrderRequestDto> response = new ObjectDataRes<>();
        response.setTotalData((int) page.getTotalElements());
        response.setDatas(page.getContent());
        return response;
    }

    @Override
    @AuditAction(actionType = "APPROVED")//ghi log
    public void approveRequestsByIds(List<Long> ids, String reason) {
        List<PurchaseOrderRequestDto> requests = purchaseOrderRequestRepository.findAllById(ids);
        for (PurchaseOrderRequestDto reqDto : requests) {
            if (reqDto.getStatus().equals(AppCoreConstant.PENDING)) {
                reqDto.setStatus(AppCoreConstant.APPROVED);
                reqDto.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                reqDto.setUpdatedDate(new Date());
                // Gửi mail
//                notificationService.sendApprovedNotification(reqDto.getCreatedBy(), reqDto.getId());
            }
            purchaseOrderRequestRepository.updateData(reqDto);
        }
    }

    @Override
    @AuditAction(actionType = "REJECTED")
    public void rejectRequestsByIds(List<Long> ids, String reason) {
        List<PurchaseOrderRequestDto> requests = purchaseOrderRequestRepository.findAllById(ids);
        for (PurchaseOrderRequestDto req : requests) {
            if (req.getStatus().equals(AppCoreConstant.PENDING)) {
                req.setStatus(AppCoreConstant.REJECTED);
                req.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                req.setUpdatedDate(new Date());
                req.setRejectReason(reason);
                // Gửi thông báo từ chối
//                notificationService.sendRejectedNotification(req.getCreatedBy(), req.getId(), reason);
            }
            purchaseOrderRequestRepository.updateData(req);
        }
    }

}
