package vn.com.pharmacity.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * UserProfile
 * 
 * @version 01-00
 * @since 01-00
 * @author 
 */
public class UserProfile implements UserDetails {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6950332746466870056L;
    /** accountId */
    private Long accountId;
    /** username */
    private String username;
    /** fullname */
    private String fullname;
    /** password */
    private String password;
    /** email */
    private String email;
    /** enabled */
    private boolean enabled;
    /** birthday */
    private Date birthday;
    /** avatar */
    private String avatar;
    /** branchId */
    private Long branchId;
    /** departmentId */
    private Long departmentId;
    /** teamIds */
    private String teamIds;
    /** authorities */
    private List<GrantedAuthority> authorities;
    /** accountNonExpired */
    private boolean accountNonExpired = true;
    /** accountNonLocked */
    private boolean accountNonLocked = true;
    /** credentialsNonExpired */
    private boolean credentialsNonExpired = true;
    /** default language */
    private String defaultLang;

    private String channel;

    /** create date */

    private Date createdDate;

    private Long positionId;

    private int ldapFlag;

    /**
     * Contructor UserProfile
     * 
     * @param username              type String
     * @param password              password
     * @param accountNonExpired     accountNonExpired
     * @param accountNonLocked      accountNonLocked
     * @param credentialsNonExpired credentialsNonExpired
     * @param enable                enable
     * @param authorities           authorities
     */
    public UserProfile(String username, String password, boolean accountNonExpired, boolean accountNonLocked,
            boolean credentialsNonExpired, boolean enable, List<GrantedAuthority> authorities, Date createdDate,
            Long positionId) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enable;
        this.authorities = authorities;
        this.createdDate = createdDate;
        this.positionId = positionId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Set accountNonExpired
     *
     * @param accountNonExpired type boolean
     * @author 
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * Set accountNonLocked
     *
     * @param accountNonLocked type boolean
     * @author 
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * Set credentialsNonExpired
     *
     * @param credentialsNonExpired type boolean
     * @author 
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    /**
     * Set enabled
     *
     * @param enabled type boolean
     * @author 
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get fullname
     * 
     * @return String
     * @author 
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set fullname
     * 
     * @param fullname type String
     * @return
     * @author 
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Get email
     * 
     * @return String
     * @author 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * 
     * @param email type String
     * @return
     * @author 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get birthday
     * 
     * @return Date
     * @author 
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Set birthday
     * 
     * @param birthday type Date
     * @return
     * @author 
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Get avatar
     * 
     * @return String
     * @author 
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Set avatar
     * 
     * @param avatar type String
     * @return
     * @author 
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Get departmentId
     * 
     * @return Long
     * @author 
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Set departmentId
     * 
     * @param departmentId type Long
     * @return
     * @author 
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Get teamIds
     * 
     * @return String
     * @author 
     */
    public String getTeamIds() {
        return teamIds;
    }

    /**
     * Set teamIds
     * 
     * @param teamIds type String
     * @return
     * @author 
     */
    public void setTeamIds(String teamIds) {
        this.teamIds = teamIds;
    }

    /**
     * Set username
     * 
     * @param username type String
     * @return
     * @author 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set password
     * 
     * @param password type String
     * @return
     * @author 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set authorities
     * 
     * @param authorities type List<GrantedAuthority>
     * @return
     * @author 
     */
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Get defaultLang
     * 
     * @return String
     * @author 
     */
    public String getDefaultLang() {
        return defaultLang;
    }

    /**
     * Set defaultLang
     * 
     * @param defaultLang type String
     * @return
     * @author 
     */
    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    /**
     * Get branchId
     * 
     * @return Long
     * @author 
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Set branchId
     * 
     * @param branchId type Long
     * @return
     * @author 
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Get accountId
     * 
     * @return Long
     * @author 
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * Set accountId
     * 
     * @param accountId type Long
     * @return
     * @author 
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * Valid account has permission
     * 
     * @param permission type String
     * @return boolean
     * @author 
     */
    public boolean hasPermission(String permission) {
        return getAuthorities().contains(new SimpleGrantedAuthority(permission));
    }

    /**
     * Get createdDate
     * 
     * @return Date
     * @author 
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     * 
     * @param createdDate type Date
     * @return
     * @author 
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * Get serialVersionUID
     * 
     * @return long
     * @author 
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
        result = prime * result + (accountNonExpired ? 1231 : 1237);
        result = prime * result + (accountNonLocked ? 1231 : 1237);
        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + (credentialsNonExpired ? 1231 : 1237);
        result = prime * result + ((defaultLang == null) ? 0 : defaultLang.hashCode());
        result = prime * result + ((departmentId == null) ? 0 : departmentId.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        result = prime * result + ((teamIds == null) ? 0 : teamIds.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserProfile other = (UserProfile) obj;
        if (accountId == null) {
            if (other.accountId != null)
                return false;
        } else if (!accountId.equals(other.accountId))
            return false;
        if (accountNonExpired != other.accountNonExpired)
            return false;
        if (accountNonLocked != other.accountNonLocked)
            return false;
        if (authorities == null) {
            if (other.authorities != null)
                return false;
        } else if (!authorities.equals(other.authorities))
            return false;
        if (avatar == null) {
            if (other.avatar != null)
                return false;
        } else if (!avatar.equals(other.avatar))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (branchId == null) {
            if (other.branchId != null)
                return false;
        } else if (!branchId.equals(other.branchId))
            return false;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        } else if (!createdDate.equals(other.createdDate))
            return false;
        if (credentialsNonExpired != other.credentialsNonExpired)
            return false;
        if (defaultLang == null) {
            if (other.defaultLang != null)
                return false;
        } else if (!defaultLang.equals(other.defaultLang))
            return false;
        if (departmentId == null) {
            if (other.departmentId != null)
                return false;
        } else if (!departmentId.equals(other.departmentId))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (enabled != other.enabled)
            return false;
        if (fullname == null) {
            if (other.fullname != null)
                return false;
        } else if (!fullname.equals(other.fullname))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (positionId == null) {
            if (other.positionId != null)
                return false;
        } else if (!positionId.equals(other.positionId))
            return false;
        if (teamIds == null) {
            if (other.teamIds != null)
                return false;
        } else if (!teamIds.equals(other.teamIds))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    /**
     * Get channel
     *
     * @return String
     * @author 
     */
    public String getChannel() {
        return channel;
    }

    /**
     * set channel
     *
     * @author 
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Get ldapFlag
     * 
     * @return int
     * @author 
     */
    public int getLdapFlag() {
        return ldapFlag;
    }

    /**
     * Set ldapFlag
     * 
     * @param ldapFlag type int
     * @return
     * @author 
     */
    public void setLdapFlag(int ldapFlag) {
        this.ldapFlag = ldapFlag;
    }

}
