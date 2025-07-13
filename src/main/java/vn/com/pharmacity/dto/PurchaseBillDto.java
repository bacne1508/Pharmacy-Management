package vn.com.pharmacity.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.entity.AbstractCreatedTracking;
import vn.com.pharmacity.entity.PurchaseBill;

@Getter
@Setter
public class PurchaseBillDto extends AbstractCreatedTracking {
    private Long id;
    private String billCode;
    private String billType;
    private int orderId;
    private int customerId;
    private int employeeId;
    private BigDecimal totalAmount;
    private String status;
    private String customerName;

    public PurchaseBillDto() {
        // Default constructor
    }

    public PurchaseBillDto(PurchaseBill purchaseBill) {
        this.id = purchaseBill.getId();
        this.billCode = purchaseBill.getPoCode();
        this.billType = purchaseBill.getBillType();
        this.orderId = purchaseBill.getOrderId();
        this.customerId = purchaseBill.getCustomerId();
        this.employeeId = purchaseBill.getEmployeeId();
        this.totalAmount = purchaseBill.getTotalAmount();
        this.status = purchaseBill.getStatus();
        this.createdBy = purchaseBill.getCreatedBy();
        this.createdDate = purchaseBill.getCreatedDate();
        this.updatedBy = purchaseBill.getUpdatedBy();
        this.updatedDate = purchaseBill.getUpdatedDate();
        this.customerName = purchaseBill.getCustomerName();
    }
}
