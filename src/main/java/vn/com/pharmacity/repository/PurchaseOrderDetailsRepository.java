package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.entity.PurchaseOrderDetail;

public interface PurchaseOrderDetailsRepository extends DbRepository<PurchaseOrderDetail, Long> {

    @Modifying
    void saveDataRequestPO(@Param("po") PurchaseOrderDetail detail);

    List<PurchaseOrderDetail> findByPurchaseOrderId(@Param("poId") Long id);

}
