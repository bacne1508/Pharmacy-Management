package vn.com.pharmacity.rest.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.com.pharmacity.dto.DistrictDto;
import vn.com.pharmacity.dto.ProvinceDto;
import vn.com.pharmacity.dto.WardDto;
import vn.com.pharmacity.service.address.DistrictService;
import vn.com.pharmacity.service.address.ProvinceService;
import vn.com.pharmacity.service.address.WardService;

/**
 * @author Bac
 * @date 2025/5/20
 */
@RestController
@RequestMapping("/api/auth")
public class AddressCommonController {
    
    @Autowired
    private ProvinceService provinceService;
    
    @Autowired
    private DistrictService districtService;
    
    @Autowired
    private WardService wardService;
    
    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceDto>> getAllProvinces() {
        List<ProvinceDto> provinces = provinceService.getAll();
        return ResponseEntity.ok(provinces);
    }
    
    @GetMapping("/districts")
    public ResponseEntity<List<DistrictDto>> getDistrictsByProvince(@RequestParam String provinceCode) {
        List<DistrictDto> districts = districtService.findByProvinceCode(provinceCode);
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/wards")
    public ResponseEntity<List<WardDto>> getWardsByDistrict(@RequestParam String districtCode) {
        List<WardDto> wards = wardService.findByDistrictCode(districtCode);
        return ResponseEntity.ok(wards);
    }

}
