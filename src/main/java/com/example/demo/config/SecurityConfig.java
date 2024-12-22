package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Autowired
    private GeneralUserDetailsService generalUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    .csrf().disable() 
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/test","/", "/login","/users/login", "/adminsignup","/adminuser/create", "/css/**","/js/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .loginProcessingUrl("/users/login") 
	            .usernameParameter("username")
	            .passwordParameter("password") 
	            .successHandler(customAuthenticationProvider.customSuccessHandler())
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .permitAll()
	        )
	        .authenticationProvider(adminAuthenticationProvider())  // 通常の DaoAuthenticationProvider を使用
	        .authenticationProvider(generalAuthenticationProvider()) // 一般ユーザー用プロバイダ
	        .authenticationProvider(customAuthenticationProvider);  // カスタムプロバイダはログイン時のみ使用
	
	    return http.build();
	}

    // 管理者用 DaoAuthenticationProvider
    @Bean
    DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // 一般ユーザー用のDaoAuthenticationProvider
    @Bean
    DaoAuthenticationProvider generalAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(generalUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
