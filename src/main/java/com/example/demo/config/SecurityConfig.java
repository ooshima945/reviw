package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/test", "/", "/login", "/users/login", "/adminsignup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/users/login") // カスタムログイン処理のURL
                .usernameParameter("username") // ユーザー名を受け取るパラメータ名を指定
                .passwordParameter("password")  // パスワードを受け取るパラメータ名を指定
                .successHandler(customAuthenticationProvider.customSuccessHandler()) // 認証成功時のハンドラー
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            )
            .authenticationProvider(customAuthenticationProvider); // カスタム認証プロバイダーを使用

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}


