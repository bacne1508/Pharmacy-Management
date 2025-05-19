package vn.com.pharmacity.exception;

import vn.com.pharmacity.webapp.PharmacityApiResponse;

public interface SuccessHandler {

	public PharmacityApiResponse handlerSuccess(Object data, long start);

    public PharmacityApiResponse handlerSuccessAdmin(Object data);
   
    
    public PharmacityApiResponse handlerSuccess(Object data, Integer statusCode ,long start);
}
