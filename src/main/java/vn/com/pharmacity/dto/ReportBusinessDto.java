package vn.com.pharmacity.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.ReportBusiness;

/**
 * Data Transfer Object for ReportBusiness. This class is used to transfer
 * report business data between layers. It extends AbstractCreatedTracking to
 * inherit common tracking fields.
 * 
 * @Author: Bac
 * @Date: 2025-06-13
 */
@Getter
@Setter
public class ReportBusinessDto extends AbstractCreatedTracking {

    private Long id;
    private String fileName;
    private String fileType;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String createdBy;
    private Date createdDate;
    private Long relatedInvoiceId;
    private String description;

    /**
     * Default constructor.
     */
    public ReportBusinessDto() {
        // Default constructor
    }
    
    public ReportBusinessDto(ReportBusiness reportBusiness) {
        this.id = reportBusiness.getId();
        this.fileName = reportBusiness.getFileName();
        this.fileType = reportBusiness.getFileType();
        this.filePath = reportBusiness.getFilePath();
        this.fileUrl = reportBusiness.getFileUrl();
        this.fileSize = reportBusiness.getFileSize();
        this.createdBy = reportBusiness.getCreatedBy();
        this.createdDate = reportBusiness.getCreatedDate();
        this.relatedInvoiceId = reportBusiness.getRelatedInvoiceId();
        this.description = reportBusiness.getDescription();
    }
}
