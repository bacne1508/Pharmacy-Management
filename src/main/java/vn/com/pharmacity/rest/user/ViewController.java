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
    public String getAdminMovieManage() {
        return "adminAccountManage";
    }
}
