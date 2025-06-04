package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
import vn.com.pharmacity.entity.PurchaseOrderRequest;

public interface PurchaseOrderRequestRepository extends DbRepository<PurchaseOrderRequest, Long> {
    
    List<PurchaseOrderRequest> searchAllByCondition(@Param("username") String username,
            @Param("status") String status);

    @Modifying
    void saveData(@Param("form") PurchaseOrderRequestDto dto);

    @Modifying
    void updateData(@Param("form") PurchaseOrderRequestDto dto);

    List<PurchaseOrderRequestDto> findAllById(@Param("ids") List<Long> ids);

    @Modifying
    void updatePoIdById(@Param("form") PurchaseOrderRequestDto reqDto);

}
