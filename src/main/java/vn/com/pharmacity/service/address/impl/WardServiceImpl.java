package vn.com.pharmacity.service.address.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.WardDto;
import vn.com.pharmacity.repository.AddressCommonRepository;
import vn.com.pharmacity.service.address.WardService;

@Service
@RequiredArgsConstructor
@CoreReadOnlyTx
public class WardServiceImpl implements WardService {
    
    private final AddressCommonRepository addressCommonRepository;
    
    @Override
    public List<WardDto> findByDistrictCode(String districtCode) {
        return addressCommonRepository.findWardsByDistrictCode(districtCode);
    }

    // Add methods to interact with the wardRepository or other business logic here

}
