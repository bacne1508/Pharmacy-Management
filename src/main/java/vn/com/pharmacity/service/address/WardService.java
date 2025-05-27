package vn.com.pharmacity.service.address;

import java.util.List;

import vn.com.pharmacity.dto.WardDto;

public interface WardService {

    List<WardDto> findByDistrictCode(String districtCode);

}
