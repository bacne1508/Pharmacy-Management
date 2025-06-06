package vn.com.pharmacity.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "request_audit_log")
public class AuditLog {
    
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "action_by")
    private String actionBy;

    @Column(name = "action_time")
    private Date actionTime;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "entity_type")
    private String entityType;
}
