INSERT INTO ReportBusiness (
    file_name,
    file_type,
    file_path,
    file_url,
    file_size,
    created_date,
    created_by,
    related_invoice_id,
    description
) VALUES (
    /*report.fileName*/,         -- Tên file (signedFile.getName())
    /*report.fileType*/,         -- Loại file (CommonConstant.FILE_EXTENSION_PDF)
    /*report.filePath*/,         -- Đường dẫn file tuyệt đối (signedFile.getAbsolutePath())
    /*report.fileUrl*/,          -- URL file ("/files/" + signedFile.getName())
    /*report.fileSize*/,         -- Kích thước file (signedFile.length())
    /*report.createdDate*/,      -- Ngày tạo (new Date())
    /*report.createdBy*/,        -- Người tạo (SecurityContextHolder.getContext().getAuthentication().getName())
    /*report.relatedInvoiceId*/, -- ID hóa đơn liên quan (dto.getId())
    /*report.description*/       -- Mô tả ("Đơn thuốc ngoại trú đã ký số")
);