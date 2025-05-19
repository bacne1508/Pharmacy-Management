package vn.com.pharmacity.exception;

import org.thymeleaf.util.StringUtils;

import lombok.Getter;
import lombok.Setter;
import vn.com.pharmacity.constant.AppCoreConstant;

@Getter
@Setter
public class ExceptionCode {

    private String text;
    private int value;
 
    public ExceptionCode(String exceptionErrorCode) {
        int indexUnderlinedFirst = StringUtils.indexOf(exceptionErrorCode, AppCoreConstant.UNDERLINED);
        this.value = Integer.parseInt(StringUtils.substring(exceptionErrorCode, 0, indexUnderlinedFirst));
        this.text = StringUtils.substring(exceptionErrorCode, indexUnderlinedFirst + 1, StringUtils.length(exceptionErrorCode));
    }
}
