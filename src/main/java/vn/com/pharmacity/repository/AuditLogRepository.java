package vn.com.pharmacity.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.entity.AuditLog;

public interface AuditLogRepository extends DbRepository<AuditLog, Long> {

    @Modifying
    void saveLog(@Param("log") AuditLog log);

}
