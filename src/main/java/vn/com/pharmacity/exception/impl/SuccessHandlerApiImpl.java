package vn.com.pharmacity.exception.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.exception.SuccessHandler;
import vn.com.pharmacity.webapp.PharmacityApiResponse;

/**
 * SuccessHandlerApiImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author BacLV
 */
@Component
public class SuccessHandlerApiImpl implements SuccessHandler  {

    public PharmacityApiResponse handlerSuccess(Object data, long start) {
        long took = System.currentTimeMillis() - start;
        return new PharmacityApiResponse(AppCoreConstant.SUCCESS_CODE, AppCoreConstant.SUCCESS, StringUtils.EMPTY, took, data);
    }

    public PharmacityApiResponse handlerSuccessAdmin(Object data) {
        return new PharmacityApiResponse(AppCoreConstant.SUCCESS_CODE, AppCoreConstant.SUCCESS, data,null);
    }
    
    public PharmacityApiResponse handlerSuccess(Object data, Integer statusCode ,long start) {
        long took = System.currentTimeMillis() - start;
        return new PharmacityApiResponse(statusCode, AppCoreConstant.SUCCESS, StringUtils.EMPTY, took, data);
    }
}
