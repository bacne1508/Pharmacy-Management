package vn.com.pharmacity.service.address;

import java.util.List;

import vn.com.pharmacity.dto.DistrictDto;

public interface DistrictService {

    List<DistrictDto> findByProvinceCode(String provinceCode);

}
