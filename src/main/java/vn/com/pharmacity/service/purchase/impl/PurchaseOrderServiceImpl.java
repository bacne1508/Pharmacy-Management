package vn.com.pharmacity.service.purchase.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.PurchaseOrderDto;
import vn.com.pharmacity.repository.PurchaseOrderRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.service.purchase.PurchaseOrderService;

/**
 * Define user identity as a constant
 * 
 * author Bac
 * @date 2025/6/4
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
@Log4j
public class PurchaseOrderServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<PurchaseOrderDto>, PurchaseOrderDto, Long>
implements PurchaseOrderService {
    
    @Autowired
    private final PurchaseOrderRepository purchaseOrderRepository;
    
    @Override
    protected List<PurchaseOrderDto> findAllByCondition(MultiValueMap<String, String> params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PurchaseOrderDto findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PurchaseOrderDto saveEntity(PurchaseOrderDto entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void deleteEntity(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected ObjectDataRes<PurchaseOrderDto> createDataRes(Page<PurchaseOrderDto> page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String generatePoCode(String tableName, String columnName, String perfix, Integer length) {
        String codeNO = "";

        try {
            String yy = new SimpleDateFormat("yy").format(new Date());
            String mm = new SimpleDateFormat("MM").format(new Date());

            String perfixCode = perfix + yy + mm;
            String maxNO = purchaseOrderRepository.findMaxNo(tableName, columnName, perfixCode);

            String formatLength = "%05d";

            if (length != null) {
                formatLength = "%0".concat(String.valueOf(length)).concat("d");
            }

            if (maxNO == null || "".equals(maxNO.trim())) {
                codeNO = perfixCode + "." + String.format(formatLength, 1);
            } else {
                String[] lstForm = maxNO.split("\\.");
                String number = lstForm[1];
                String nextNumber = String.format(formatLength, Integer.valueOf(number) + 1);
                codeNO = lstForm[0] + "." + nextNumber;
            }
            log.info("CODE GENERALIZED: " + codeNO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return codeNO;
    }

}
