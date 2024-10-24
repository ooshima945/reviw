package com.example.demo.config;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminUserDetails implements UserDetails {

    private Integer adminId;  // 管理者ID
    private String adminEmail;
    private String adminPassword;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public AdminUserDetails(Integer adminId, String adminEmail, String adminPassword, 
                            Collection<? extends GrantedAuthority> authorities, 
                            boolean accountNonExpired, boolean accountNonLocked, 
                            boolean credentialsNonExpired, boolean enabled) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    // 管理者IDを取得
    public Integer getAdminId() {
        return adminId;
    }

    @Override
    public String getUsername() {
        return adminEmail;
    }

    @Override
    public String getPassword() {
        return adminPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

