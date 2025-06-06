package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends DbRepository<PurchaseOrder, Long> {

    PurchaseOrder findDraftByUserId(@Param("currentUser") String currentUser);

    String findMaxNo(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("prefix") String perfixCode);

    PurchaseOrder savePOFromRequest(@Param("po")  PurchaseOrder po);

    List<PurchaseOrder> searchAllByCondition(@Param("poCode") String username, @Param("status") String status);

    @Modifying
    void updateStatus(@Param("po")  PurchaseOrder po);

    @Modifying
    void updateStatusAndExpiry(@Param("po") PurchaseOrder po);

}
