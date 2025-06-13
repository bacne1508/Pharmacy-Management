package vn.com.pharmacity.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.WalkInPurchaseInvoiceDto;
import vn.com.pharmacity.entity.WalkInInvoiceItem;

public interface WalkInInvoiceItemRepository extends DbRepository<WalkInInvoiceItem, Long> {

    @Modifying
    void saveData(@Param("dto") WalkInPurchaseInvoiceDto dto, @Param("itemMedicine")  WalkInInvoiceItem itemMedicine);

}
