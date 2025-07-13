package vn.com.pharmacity.service.purchase.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.PurchaseBillDto;
import vn.com.pharmacity.entity.PurchaseBill;
import vn.com.pharmacity.repository.PurchaseBillRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.service.purchase.PurchaseBillService;

/**
 * Define user identity as a constant
 * 
 * author BacDzz
 * 
 * @date 2025/7/12
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
@Log4j
public class PurchaseBillServiceImpl extends
    BaseRestServiceImpl<ObjectDataRes<PurchaseBillDto>, PurchaseBillDto, Long> implements PurchaseBillService {
    
    private final PurchaseBillRepository purchaseBillRepository;
    
    @Override
    protected List<PurchaseBillDto> findAllByCondition(MultiValueMap<String, String> params) {
        String billCode = params.getFirst("billCode");
        String billType = params.getFirst("billType");

        List<PurchaseBill> entities = purchaseBillRepository.searchAllByCondition(billCode, billType);
        return entities.stream().map(PurchaseBillDto::new).collect(Collectors.toList());
    }

    @Override
    protected PurchaseBillDto findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PurchaseBillDto saveEntity(PurchaseBillDto entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void deleteEntity(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected ObjectDataRes<PurchaseBillDto> createDataRes(Page<PurchaseBillDto> page) {
        // TODO Auto-generated method stub
        return null;
    }

}
