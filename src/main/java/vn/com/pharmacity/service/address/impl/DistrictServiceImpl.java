package vn.com.pharmacity.service.address.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.DistrictDto;
import vn.com.pharmacity.repository.AddressCommonRepository;
import vn.com.pharmacity.service.address.DistrictService;

@Service
@RequiredArgsConstructor
@CoreReadOnlyTx
public class DistrictServiceImpl implements DistrictService {
    
    @Autowired
    private AddressCommonRepository addressCommonRepository;
    
    @Override
    public List<DistrictDto> findByProvinceCode(String provinceCode) {
        return addressCommonRepository.findDistrictsByProvinceCode(provinceCode);
    }

    // Add methods to interact with the districtRepository or other business logic
    // here

}
