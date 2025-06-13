package vn.com.pharmacity.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.Medicine;
import vn.com.pharmacity.entity.WalkInInvoiceItem;
import vn.com.pharmacity.entity.WalkInPurchaseInvoice;

@Getter
@Setter
public class WalkInPurchaseInvoiceDto extends AbstractCreatedTracking {
    
    private Long id;
    private String invoiceCode;
    private String customerName;
    private String gender;
    private Integer age;
    private String phone;
    private String cardNumber;
    private String address;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String notes;
    private String diagnosis;
    private List<WalkInInvoiceItem> medicines;
    
    public WalkInPurchaseInvoiceDto() {
        // Default constructor
    }
    
    public WalkInPurchaseInvoiceDto(WalkInPurchaseInvoice walkInPurchaseInvoice) {
        this.id = walkInPurchaseInvoice.getId();
        this.invoiceCode = walkInPurchaseInvoice.getInvoiceCode();
        this.customerName = walkInPurchaseInvoice.getCustomerName();
        this.gender = walkInPurchaseInvoice.getGender();
        this.age = walkInPurchaseInvoice.getAge();
        this.phone = walkInPurchaseInvoice.getPhone();
        this.cardNumber = walkInPurchaseInvoice.getCardNumber();
        this.address = walkInPurchaseInvoice.getAddress();
        this.totalAmount = walkInPurchaseInvoice.getTotalAmount();
        this.paymentStatus = walkInPurchaseInvoice.getPaymentStatus();
        this.notes = walkInPurchaseInvoice.getNotes();    
        this.diagnosis = walkInPurchaseInvoice.getDiagnosis();
        this.medicines = walkInPurchaseInvoice.getMedicines();
    }
}
