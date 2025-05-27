package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.pharmacity.dto.DistrictDto;
import vn.com.pharmacity.dto.ProvinceDto;
import vn.com.pharmacity.dto.WardDto;

public interface AddressCommonRepository extends DbRepository<Object, Long>{

    List<ProvinceDto> findAllProvinces();

    List<DistrictDto> findDistrictsByProvinceCode(@Param("provinceCode") String provinceCode);

    List<WardDto> findWardsByDistrictCode(@Param("districtCode") String districtCode);

}
