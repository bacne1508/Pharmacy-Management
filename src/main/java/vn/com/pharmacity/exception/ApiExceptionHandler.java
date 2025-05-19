package vn.com.pharmacity.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;
import vn.com.pharmacity.constant.AppCoreConstant;
import vn.com.pharmacity.constant.MovieExceptionCodeConstant;
import vn.com.pharmacity.webapp.PharmacityApiResponse;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	private void logMessage(String uri, String message) {
		log.error("Call API {} with error {} ", uri, message);
	}

	// Custom Error
	@ExceptionHandler(DuplicateException.class)
	public PharmacityApiResponse duplicateException(DuplicateException ex, WebRequest request) {
		logMessage(((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
		ExceptionCode expCode = new ExceptionCode(MovieExceptionCodeConstant.E500_ERROR_INTERNAL);
		String hiddenDesc = StringUtils.EMPTY;
		return new PharmacityApiResponse(expCode.getValue(), AppCoreConstant.ERROR, ex.getMessage(), hiddenDesc, 0);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	PharmacityApiResponse handleConstraintViolationException(ConstraintViolationException e) {

		int errorCode = 500;
		List<String> errorMessages = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
			String messageTemplate = constraintViolation.getMessageTemplate();
			ExceptionCode expCode = new ExceptionCode(messageTemplate);
			errorCode = expCode.getValue();
			String messageCode = expCode.getText();
			PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
			String[] params = new String[] { path.getLeafNode().asString() };
			String message = this.messageSource.getMessage(messageCode, params,
					new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
			errorMessages.add(message);
		}

		return new PharmacityApiResponse(errorCode, AppCoreConstant.ERROR, String.join("; ", errorMessages), AppCoreConstant.EMPTY, 0);
	}
}
