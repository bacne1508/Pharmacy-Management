package vn.com.pharmacity.exception;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;

import vn.com.pharmacity.webapp.PharmacityApiResponse;

@Qualifier("errorHandler")
public interface ErrorHandler {

	public PharmacityApiResponse handlerException(Exception ex, long start);

    public PharmacityApiResponse handlerException(int codeStatus,String message);
    
    public PharmacityApiResponse handlerBindingResult(BindingResult bindingResult, long start);
}
