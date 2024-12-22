package com.example.demo.config;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.GeneralUser;
import com.example.demo.repository.GeneralUserRepository;

@Service
public class GeneralUserDetailsService implements UserDetailsService {

    private final GeneralUserRepository generalUserRepository;

    public GeneralUserDetailsService(GeneralUserRepository generalUserRepository) {
        this.generalUserRepository = generalUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Short loginIdShort;
        try {
            loginIdShort = Short.parseShort(loginId);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("無効なログインID形式: " + loginId);
        }

        // generalLoginIdをもとに一般ユーザーを検索
        GeneralUser generalUser = generalUserRepository.findByGeneralLoginId(loginIdShort)
            .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + loginId));

        // ユーザーの情報をもとにUserDetailsを返す
        return new GeneralUserDetails(
                generalUser.getGeneralId(),
                String.valueOf(generalUser.getGeneralLoginId()),
                generalUser.getGeneralPassword(),
                Collections.emptyList()
        );
    }
}


