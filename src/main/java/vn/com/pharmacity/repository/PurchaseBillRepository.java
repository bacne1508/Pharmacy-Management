package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.pharmacity.entity.PurchaseBill;

public interface PurchaseBillRepository extends DbRepository<PurchaseBill, Long> {

    List<PurchaseBill> searchAllByCondition(@Param("billCode") String billCode,@Param("billType") String billType);

}
