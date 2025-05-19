package vn.com.pharmacity;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.exception.ErrorHandler;
import vn.com.pharmacity.exception.SuccessHandler;

/**
 * AbstractRest
 * 
 * @version 01-00
 * @since 01-00
 * @author BacLV
 */
@Getter
@Setter
public abstract class AbstractRest {

    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;
    
    @Autowired
    protected ObjectMapper objectMapper;    
}

