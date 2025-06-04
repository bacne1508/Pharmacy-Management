package vn.com.pharmacity.rest.category;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineDto;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.rest.BaseRestController;
import vn.com.pharmacity.service.category.MedicineBranchService;
import vn.com.pharmacity.service.category.MedicineService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth/medicine")
public class MedicineManagementController extends BaseRestController<ObjectDataRes<MedicineDto>, MedicineDto> {
    
    @Autowired
    private MedicineService medicineService;

    public MedicineManagementController(MedicineService  baseService) {
        super(baseService);
    }
    
    @GetMapping("/medicine-groups")
    public List<CommonDto> getMedicineBranchGroups() {
        return medicineService.findAll().stream()
            .map(g -> new CommonDto(g.getId(),g.getCode(), g.getName()))
            .collect(Collectors.toList());
    }
    
    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=thuoc.xlsx");

        MedicineDto dto = new MedicineDto();
        List<Medicine> medicines = medicineService.getAll(dto); // Lấy danh sách thuốc

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách thuốc");
    
            // Tạo header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Drug code");
            header.createCell(1).setCellValue("Drug name");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Medicine groups code");
            header.createCell(4).setCellValue("Medicine units code");
            header.createCell(5).setCellValue("Medicine types code");
            header.createCell(6).setCellValue("Ingredient");
            header.createCell(7).setCellValue("Strength");
            header.createCell(8).setCellValue("Manufacturer");
            header.createCell(9).setCellValue("Origin Contry");
            header.createCell(10).setCellValue("Purchase Price");
            header.createCell(11).setCellValue("Sale Price");
            header.createCell(12).setCellValue("Quantity");
            header.createCell(13).setCellValue("Date Of Manufacturer");
            header.createCell(14).setCellValue("Product Expiry Date");
            header.createCell(15).setCellValue("Created By");
            header.createCell(16).setCellValue("Created Date");
            header.createCell(17).setCellValue("Updated By");
            header.createCell(18).setCellValue("Updated Date");
    
            // Ghi dữ liệu
            int rowIdx = 1;
            for (Medicine med : medicines) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(med.getCode());
                row.createCell(1).setCellValue(med.getName());
                row.createCell(2).setCellValue(med.getDescription() != null ? med.getDescription() : "");
                row.createCell(3).setCellValue(med.getMedicineGroupsCode() != null ? med.getMedicineGroupsCode() : "");
                row.createCell(4).setCellValue(med.getMedicineUnitsCode() != null ? med.getMedicineUnitsCode() : "");
                row.createCell(5).setCellValue(med.getMedicineTypesCode() != null ? med.getMedicineTypesCode() : "");
                row.createCell(6).setCellValue(med.getIngredient() != null ? med.getIngredient() : "");
                row.createCell(7).setCellValue(med.getStrength() != null ? med.getStrength() : "");
                row.createCell(8).setCellValue(med.getManufacturer() != null ? med.getManufacturer() : "");
                row.createCell(9).setCellValue(med.getOriginCountry() != null ? med.getOriginCountry() : "");
                row.createCell(10).setCellValue(med.getPurchasePrice() != null ? med.getPurchasePrice().toString() : "");
                row.createCell(11).setCellValue(med.getSalePrice() != null ? med.getSalePrice().toString() : "");
                row.createCell(12).setCellValue(med.getQuantity() != 0 ? med.getQuantity() : 0);
                row.createCell(13).setCellValue(med.getDateOfManufacture() != null ? med.getDateOfManufacture().toString() : "");
                row.createCell(14).setCellValue(med.getProductExpiryDate() != null ? med.getProductExpiryDate().toString() : "");
                row.createCell(15).setCellValue(med.getCreatedBy() != null ? med.getCreatedBy() : "");
                row.createCell(16).setCellValue(med.getCreatedDate() != null ? med.getCreatedDate().toString() : "");
                row.createCell(17).setCellValue(med.getUpdatedBy() != null ? med.getUpdatedBy() : "");
                row.createCell(18).setCellValue(med.getUpdatedDate() != null ? med.getUpdatedDate().toString() : "");
            }
    
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
