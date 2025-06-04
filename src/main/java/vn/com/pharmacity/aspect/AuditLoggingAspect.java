package vn.com.pharmacity.aspect;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vn.com.pharmacity.annotation.AuditAction;
import vn.com.pharmacity.entity.AuditLog;
import vn.com.pharmacity.repository.AuditLogRepository;

/**
 * Aspect for logging audit actions on methods annotated with @AuditAction. This
 * aspect captures method execution details and saves them to the database.
 * 
 * author Bac
 * 
 * @date 2025/6/4
 */
@Aspect
@Component
public class AuditLoggingAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Around("@annotation(auditAction)")
    public Object logAuditAction(ProceedingJoinPoint joinPoint, AuditAction auditAction) throws Throwable {
        Object result = null;
        Throwable error = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable ex) {
            error = ex;
            throw ex;
        } finally {
            saveAuditLog(joinPoint, auditAction, error == null);
        }
    }

    private void saveAuditLog(ProceedingJoinPoint joinPoint, AuditAction auditAction, boolean success) {
        Object[] args = joinPoint.getArgs();

        Long requestId = null;
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); 

        for (Object arg : args) {
            if (arg instanceof List) {
                List<?> list = (List<?>) arg;
                if (!list.isEmpty() && list.get(0) instanceof Long) {
                    requestId = (Long) list.get(0);
                }
            } else if (arg instanceof Long) {
                requestId = (Long) arg; 
            }
        }


        if (requestId != null) {
            AuditLog log = new AuditLog();
            log.setRequestId(requestId);
            log.setActionType(auditAction.actionType());
            log.setActionBy(username);
            log.setActionTime(new Date());
            log.setRemarks(success ? "Success" : "Failed");

            auditLogRepository.saveLog(log);
        }
    }
}

