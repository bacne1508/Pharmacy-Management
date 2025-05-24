package vn.com.pharmacity.entity;

import java.util.Date;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractCreatedTracking {
	@Column(name = "CREATED_DATE")
    protected Date createdDate;

    @Column(name = "CREATED_BY")
    protected String createdBy;
    
    @Column(name = "UPDATED_DATE")
    protected Date updatedDate;

    @Column(name = "UPDATED_BY")
    protected String updatedBy;

    @Column(name = "DELETED_DATE")
    protected Date deletedDate;

    @Column(name = "DELETED_BY")
    protected String deletedBy;
}
