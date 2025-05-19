package vn.com.pharmacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "vn.com.pharmacity")
@ConfigurationPropertiesScan("vn.com.pharmacity")
public class PharmacyManagementApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PharmacyManagementApplication.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(PharmacyManagementApplication.class, args);
    }

}
