package vn.com.pharmacity.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import vn.com.pharmacity.service.CommonService;
import vn.com.pharmacity.service.impl.CommonServiceImpl;

@Configuration
public class BeanCoreConfiguration {
	
	@Bean
    public CommonService commonService() {
        return new CommonServiceImpl();
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
