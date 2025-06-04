package vn.com.pharmacity.repository;

import org.springframework.data.repository.query.Param;

import vn.com.pharmacity.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends DbRepository<PurchaseOrder, Long> {

    PurchaseOrder findDraftByUserId(@Param("currentUser") String currentUser);

    String findMaxNo(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("prefix") String perfixCode);

    PurchaseOrder savePOFromRequest(@Param("po")  PurchaseOrder po);

}
