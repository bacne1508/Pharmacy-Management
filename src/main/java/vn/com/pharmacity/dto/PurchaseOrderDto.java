package vn.com.pharmacity.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.PurchaseOrder;

@Getter
@Setter
public class PurchaseOrderDto extends AbstractCreatedTracking {

    private Long id;
    private String poCode;
    private int supplierId;
    private Date expectedDeliveryDate;
    private String status;
    private String createdFrom;
    private String supplierCode;

    public PurchaseOrderDto() {
        // Default constructor
    }

    public PurchaseOrderDto(PurchaseOrder purchaseOrder) {
        this.id = purchaseOrder.getId();
        this.poCode = purchaseOrder.getPoCode();
        this.supplierId = purchaseOrder.getSupplierId();
        this.expectedDeliveryDate = purchaseOrder.getExpectedDeliveryDate();
        this.status = purchaseOrder.getStatus();
        this.createdFrom = purchaseOrder.getCreatedFrom();
        this.supplierCode = purchaseOrder.getSupplierCode();
        this.createdBy = purchaseOrder.getCreatedBy();
        this.createdDate = purchaseOrder.getCreatedDate();
    }
}
