package vn.com.pharmacity.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;
import com.miragesql.miragesql.annotation.Table;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

/**
 * Represents a business report entity. This class is currently empty and can be
 * extended in the future to include properties and methods relevant to business
 * reports.
 */
/*
 * @Author: Bac
 * @Date: 2025-06-13
 */
@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_REPORT_BUSINESS)
public class ReportBusiness extends AbstractCreatedTracking {
    
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY, generator = AppCoreConstant.SEQ + AppCoreConstant.TABLE_REPORT_BUSINESS)
    private Long id;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "file_type")
    private String fileType;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "file_url")
    private String fileUrl;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "related_invoice_id")
    private Long relatedInvoiceId;
    
    @Column(name = "description")
    private String description;
    
}
