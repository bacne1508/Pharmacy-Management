package vn.com.pharmacity.entity;

import java.util.Date;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractCreatedTracking {
	@Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "DELETED_DATE")
    private Date DELETED_BYDate;

    @Column(name = "DELETED_BY")
    private String deletedBy;
}
