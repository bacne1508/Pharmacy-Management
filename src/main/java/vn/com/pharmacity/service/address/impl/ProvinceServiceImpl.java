package vn.com.pharmacity.service.address.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.ProvinceDto;
import vn.com.pharmacity.repository.AddressCommonRepository;
import vn.com.pharmacity.service.address.ProvinceService;

@Service
@RequiredArgsConstructor
@CoreReadOnlyTx
public class ProvinceServiceImpl implements ProvinceService {
    
    @Autowired
    private AddressCommonRepository addressCommonRepository;
    
    @Override
    public List<ProvinceDto> getAll() {
        return addressCommonRepository.findAllProvinces();
    }

    // Add methods to interact with the medicineGroupRepository or other business
    // logic here

}
