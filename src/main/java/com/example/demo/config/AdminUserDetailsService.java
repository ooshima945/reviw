package com.example.demo.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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

        // AdminUserDetails を返す
        return new AdminUserDetails(
                adminUser.getAdminId(), 
                adminUser.getAdminEmail(), 
                adminUser.getAdminPassword(), 
                new ArrayList<>()
        );
    }
}


