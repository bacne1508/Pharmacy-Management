package vn.com.pharmacity.service.purchase;

import java.io.InputStream;
import java.util.List;

import vn.com.pharmacity.dto.PurchaseOrderRequestDto;
import vn.com.pharmacity.dto.WalkInPurchaseInvoiceDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

public interface PurchaseOrderRequestService extends BaseRestService<ObjectDataRes<PurchaseOrderRequestDto>, PurchaseOrderRequestDto> {

    void approveRequestsByIds(List<Long> ids, String reason);

    void rejectRequestsByIds(List<Long> ids, String reason);

    boolean savePdfRequest(WalkInPurchaseInvoiceDto dto);

    String generateAndSignPrescriptionPdf(WalkInPurchaseInvoiceDto dto, InputStream keystoreStream, String keystorePassword,
            String alias) throws Exception;

}
