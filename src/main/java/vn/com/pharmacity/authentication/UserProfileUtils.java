package vn.com.pharmacity.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class UserProfileUtils {

    /**
     * Get Authentication
     * 
     * @return Authentication
     * @author 
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Get UserProfile
     * 
     * @return UserProfile
     * @author 
     */
    public static UserProfile getUserProfile() {
        UserProfile userProfile = null;

        if (getAuthentication() != null && getAuthentication().getPrincipal() instanceof UserProfile) {
            userProfile = (UserProfile) getAuthentication().getPrincipal();
        }
        return userProfile;
    }

    /**
     * Find only list function code
     * 
     * @return listRole : List<String>
     */
    public static List<String> findOnlyFunctionCode() {
        List<String> listFunctionCode = new ArrayList<String>();
        if (getAuthentication() != null && null != getAuthentication().getAuthorities()) {
            @SuppressWarnings("unchecked")
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) getAuthentication().getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority() != null && !"".equals(grantedAuthority.getAuthority())
                        && !"ROLE_AUTHED".equals(grantedAuthority.getAuthority())) {
                    String[] splitList = grantedAuthority.getAuthority().split(":");
                    if (splitList.length > 0) {
                        // System.out.println("FUNCTION CODE : " +
                        // splitList[0]);
                        listFunctionCode.add(splitList[0]);
                    }
                }
            }
        }
        return listFunctionCode;
    }

    /**
     * valid role of account login
     * 
     * @return boolean
     */
    public static boolean hasRole(String role) {
        boolean hasRole = false;
        UserProfile userProfile = getUserProfile();
        if (userProfile != null) {
            hasRole = userProfile.hasPermission(role);
        }
        return hasRole;
    }

    /**
     * Get UserName login
     * 
     * @return String
     * @author 
     */
    public static String getUserNameLogin() {
    	UserProfile userProfile = getUserProfile(); // fix SonarQube
    	if(null != userProfile) {
    		return userProfile.getUsername();
    	}
        return null;
    }

    public static String getFullName() {
    	UserProfile userProfile = getUserProfile(); // fix SonarQube
    	if(null != userProfile) {
    		return userProfile.getFullname();
    	}
        return null;
    }

}