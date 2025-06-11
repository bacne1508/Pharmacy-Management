package vn.com.pharmacity.service.purchase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.pharmacity.dto.PurchaseOrderDetailDto;
import vn.com.pharmacity.dto.PurchaseOrderDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

public interface PurchaseOrderService extends BaseRestService<ObjectDataRes<PurchaseOrderDto>, PurchaseOrderDto> {

    String generatePoCode(String tableName, String columnName, String perfix, Integer length);

    void handleAction(Long id, String action);

    Page<PurchaseOrderDetailDto> searchDetail(MultiValueMap<String, String> params, Long poId, Pageable pageable);

}
