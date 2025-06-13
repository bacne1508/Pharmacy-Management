package vn.com.pharmacity.repository;

import org.springframework.data.repository.query.Param;

import vn.com.pharmacity.dto.WalkInPurchaseInvoiceDto;
import vn.com.pharmacity.entity.WalkInPurchaseInvoice;

public interface WalkInPurchaseInvoiceRepository extends DbRepository<WalkInPurchaseInvoice, Long> {

    String findMaxNo(String tableName, String columnName, String perfixCode);

    WalkInPurchaseInvoiceDto saveData(@Param("dto") WalkInPurchaseInvoiceDto dto);

}
