package vn.com.pharmacity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditAction {
    String actionType(); // e.g., "APPROVE", "REJECT", "DRAFT", "SENT", "RECEIVED", "CANCELED", "DELETED"
}
