package vn.com.pharmacity.rest.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @GetMapping("/admin/dashboard")
    public String getAdminPharmacityManage() {
        return "adminPharmacityManage";
    }

    @GetMapping("/admin/account/manage")
    public String geAccountManage() {
        return "adminAccountManage";
    }

    @GetMapping("/user/manage")
    public String getUserManage() {
        return "userManage";
    }

    @GetMapping("/employ/manage")
    public String getEmployManage() {
        return "employManage";
    }

    @GetMapping("/admin/medicine/manage")
    public String getMedicineManage() {
        return "category/medicineManage";
    }

    @GetMapping("/admin/medicine/group/manage")
    public String getMedicineGroupManage() {
        return "category/medicineGroupManage";
    }

    @GetMapping("/admin/medicine/type/manage")
    public String getMedicineTypeManage() {
        return "category/medicineTypeManage";
    }

    @GetMapping("/admin/medicine/unit/manage")
    public String getMedicineUnitManage() {
        return "category/medicineUnitManage";
    }

    @GetMapping("/admin/medicine/supplier/manage")
    public String getMedicineSupplierManage() {
        return "category/medicineSupplierManage";
    }

    @GetMapping("/admin/medicine/branch/manage")
    public String getMedicineBranchManage() {
        return "category/medicineBranchManage";
    }

    @GetMapping("/admin/medicine/storage/manage")
    public String getMedicineStorageManage() {
        return "category/medicineStorageManage";
    }

    @GetMapping("/admin/medicine/stock/manage")
    public String getMedicineStockManage() {
        return "category/medicineStockManage";
    }

    @GetMapping("/admin/purchase/order/request/manage")
    public String getPurchaseOrderRequestManage() {
        return "Purchase/purchaseOrderRequestManage";
    }

    @GetMapping("/admin/purchase/order/manage")
    public String getPurchaseOrderManage() {
        return "Purchase/purchaseOrderManage";
    }

    @GetMapping("/admin/purchase/bill/manage")
    public String getPurchaseBillManage() {
        return "Purchase/purchaseBillManage";
    }
}
