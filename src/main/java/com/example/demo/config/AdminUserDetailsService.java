package com.example.demo.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminUser;
import com.example.demo.repository.AdminUserRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByAdminEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

        // AdminUserDetails を返す際にROLE_ADMINと状態を付与
        return new AdminUserDetails(
                adminUser.getAdminId(), 
                adminUser.getAdminEmail(), 
                adminUser.getAdminPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")),
                true,  // accountNonExpired
                true,  // accountNonLocked
                true,  // credentialsNonExpired
                true   // enabled
        );
    }
}


