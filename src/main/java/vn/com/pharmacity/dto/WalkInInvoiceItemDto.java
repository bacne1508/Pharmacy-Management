package vn.com.pharmacity.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.WalkInInvoiceItem;

@Getter
@Setter
public class WalkInInvoiceItemDto extends AbstractCreatedTracking {

    private Long id;
    private Long invoiceId;
    private Long medicineId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    
    public WalkInInvoiceItemDto() {
        // Default constructor
    }
    
    public WalkInInvoiceItemDto(WalkInInvoiceItem walkInInvoiceItem) {
        this.id = walkInInvoiceItem.getId();
        this.invoiceId = walkInInvoiceItem.getInvoiceId();
        this.medicineId = walkInInvoiceItem.getMedicineId();
        this.quantity = walkInInvoiceItem.getQuantity();
        this.unitPrice = walkInInvoiceItem.getUnitPrice();
        this.totalPrice = walkInInvoiceItem.getTotalPrice();       
    }
}
