//package vn.com.pharmacity.validator;
//
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//import vn.com.pharmacity.dto.AuthenUserInfoResDto;
//import vn.com.pharmacity.service.AuthenUserInfoService;
//
//
//@Component
//public class UserValidator implements Validator {
//
//    @Autowired
//    private AuthenUserInfoService authenUserInfoService;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return AuthenUserInfoResDto.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//        AuthenUserInfoResDto user = (AuthenUserInfoResDto) obj;
//        Pattern emailPattern = Pattern.compile(
//                "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]++)*+)|(\".+\"))@((\\[[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}])|(([a-zA-Z\\-0-9]++\\.)++[a-zA-Z]{2,}))");
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
////        if (authenUserInfoService.findByUsername(user.getEmail()) != null) {
////            errors.rejectValue("email", "Duplicate.userForm.username");
////        }
//        
//        if (StringUtils.isNotEmpty(user.getEmail()) && !emailPattern.matcher(user.getEmail()).matches())
//            errors.rejectValue("email", "email.format.wrong", null);
//        
//        //duplicate sdt
////        if (authenUserInfoService.isExistPhone(user.getSdt())) {
////            errors.rejectValue("email", "Duplicate.userForm.username");
////        }
//    }
//
//}
