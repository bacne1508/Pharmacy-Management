package vn.com.pharmacity.rest.report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;

import vn.com.pharmacity.dto.ReportBusinessDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.report.ReportBusinessService;

/**
 * @author Bac
 * @date 2025/6/13
 */
@RestController
@RequestMapping("/api/auth/report/report-business")
public class ReportBusinessController extends BaseRestController<ObjectDataRes<ReportBusinessDto>, ReportBusinessDto> {
    
    @Autowired
    private ReportBusinessService reportBusinessService;
    
    public ReportBusinessController(ReportBusinessService baseService) {
        super(baseService);
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        ReportBusinessDto report = reportBusinessService.findById(id);
        if (report == null || report.getFilePath() == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(report.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("File không tồn tại hoặc không thể đọc: " + report.getFilePath());
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getFileName() + "\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(resource);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long id) throws IOException {
        ReportBusinessDto report = reportBusinessService.findById(id);
        if (report == null || report.getFilePath() == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(report.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("File không tồn tại hoặc không thể đọc: " + report.getFilePath());
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + report.getFileName() + "\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(resource);
    }
}
