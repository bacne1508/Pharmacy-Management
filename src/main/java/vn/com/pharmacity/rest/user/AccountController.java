
package vn.com.pharmacity.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.dto.UserForm;
import vn.com.pharmacity.exception.SystemException;
import vn.com.pharmacity.service.user.AccountService;
import vn.com.pharmacity.webapp.ResponseVO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AccountController {
    private static final String ACCOUNT_INFO_ERROR = "Wrong username or password";

    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private AccountService accountService;
    
//    @PostConstruct
//    public void init() {
//        System.out.println("AccountController @PostConstruct: Injected AuthenticationManager: " + authenticationManager);
//        if (authenticationManager != null) {
//            System.out.println("AccountController @PostConstruct: Injected AuthenticationManager class: " + authenticationManager.getClass().getName());
//            if (authenticationManager instanceof org.springframework.security.authentication.ProviderManager) {
//                org.springframework.security.authentication.ProviderManager pm = (org.springframework.security.authentication.ProviderManager) authenticationManager;
//                System.out.println("AccountController @PostConstruct: ProviderManager providers: " + pm.getProviders());
//            }
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<ResponseVO> login(@RequestBody UserForm userForm) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userForm.getUsername(), userForm.getPassword()));

            // Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve authenticated user details
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ResponseVO.buildSuccess(principal));
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseVO.buildSuccess(principal));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVO.buildFailure(ACCOUNT_INFO_ERROR));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVO.buildFailure("Login failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<ResponseVO> signUp(@RequestBody UserForm userForm) {
        try {
            accountService.registerAccount(userForm); 
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ResponseVO.buildSuccess("User registered successfully!"));
        } catch (SystemException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVO.buildFailure(e.getMessage()));
        } catch (Exception e) {
            System.err.println("Unexpected error during signUp endpoint for " + userForm.getUsername() + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseVO.buildFailure("An error occurred during registration. Please try again later."));
        }
    }
}
