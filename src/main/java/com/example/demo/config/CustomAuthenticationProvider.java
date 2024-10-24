package com.example.demo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService adminUserDetailsService;

    @Autowired
    @Qualifier("generalUserDetailsService")
    private UserDetailsService generalUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 成功時のリダイレクトを行うSuccessHandler
    private final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails;

        // 管理者か一般ユーザーかを判別してUserDetailsを取得
        if (username.contains("@")) {
            userDetails = adminUserDetailsService.loadUserByUsername(username);
        } else {
            userDetails = generalUserDetailsService.loadUserByUsername(username);
        }

        // パスワードの一致確認
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    // 認証成功後の処理 (リダイレクトのハンドリング)
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            if (username.contains("@")) {
                successHandler.setDefaultTargetUrl("/dashboard");
            } else {
                successHandler.setDefaultTargetUrl("/main");
            }
            successHandler.onAuthenticationSuccess(request, response, authentication);
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}



