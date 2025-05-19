package vn.com.pharmacity.exception.impl;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.constant.MovieExceptionCodeConstant;
import vn.com.pharmacity.exception.DetailException;
import vn.com.pharmacity.exception.ErrorHandler;
import vn.com.pharmacity.exception.ExceptionCode;
import vn.com.pharmacity.exception.GlobalException;
import vn.com.pharmacity.exception.MessageError;
import vn.com.pharmacity.utils.MovieCollectionUtil;
import vn.com.pharmacity.webapp.PharmacityApiResponse;

/**
 * ErrorHandlerApiImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author BacLV
 */
@Component
public class ErrorHandlerApiImpl implements ErrorHandler  {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    public PharmacityApiResponse handlerException(Exception ex, long start) {
        // Default message
        long took = System.currentTimeMillis() - start;
        ExceptionCode expCode = new ExceptionCode(MovieExceptionCodeConstant.E500_ERROR_INTERNAL);
        boolean isTranslate = false;
        String message = ex.getMessage();
        String hiddenDesc = StringUtils.EMPTY;
        Object[] paramater = null;

        if (ex instanceof NullPointerException) {
            hiddenDesc = "NPE";
        } else if (ex instanceof GlobalException) {
            GlobalException globalException = (GlobalException) ex;
            expCode = globalException.getExceptionCode();
            if (ex instanceof DetailException) {
                DetailException detailException = (DetailException) globalException;
                isTranslate = detailException.isTranslate();
                message = detailException.getSpecificMsg();
                hiddenDesc = detailException.getSpecificMsg();
                paramater = detailException.getParamater();
            }
        }

        String code = expCode.getText();

        if (isTranslate) {
            message = this.messageSource.getMessage(code, paramater,
                    new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
        }

        if (StringUtils.isBlank(message)) {
            message = code;
        }
        return new PharmacityApiResponse(expCode.getValue(), AppCoreConstant.ERROR, message, hiddenDesc, took);
    }

    public PharmacityApiResponse handlerException(int codeStatus, String message) {
        // Default message

        boolean isTranslate = false;

        if (isTranslate) {
            message = this.messageSource.getMessage(MessageError.ERROR_COMMON, null,
                    new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
        }

        return new PharmacityApiResponse(codeStatus, message, null, null);
    }

    @Override
    public PharmacityApiResponse handlerBindingResult(BindingResult bindingResult, long start) {
        // Default message
        String code = MovieExceptionCodeConstant.E500_ERROR_INTERNAL;
        int codeStatus = 500;
        String message = null;
        Object data = null;
        if (null != bindingResult && MovieCollectionUtil.isNotEmpty(bindingResult.getAllErrors())) {
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            code = bindingResult.getAllErrors().get(0).getCode();
            Object[] args = bindingResult.getAllErrors().get(0).getArguments();
            data = bindingResult.getAllErrors();

            if (StringUtils.isBlank(code)) {
                code = MovieExceptionCodeConstant.E500_ERROR_INTERNAL;
            }

            ExceptionCode expCode = new ExceptionCode(code);
            codeStatus = expCode.getValue();
            message = this.messageSource.getMessage(expCode.getText(), args,
                    new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
            
            if (StringUtils.isNotBlank(defaultMessage)) {
                message = message.concat(System.lineSeparator()).concat(defaultMessage);
            }
        }

        return new PharmacityApiResponse(codeStatus, AppCoreConstant.ERROR, null, message, data, AppCoreConstant.RESULT_CODE_SYSTEM_ERROR);

    }
}
