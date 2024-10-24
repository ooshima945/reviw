package com.example.demo.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.GeneralUserDetailsService;
import com.example.demo.entity.AdminUser;
import com.example.demo.entity.GeneralUser;
import com.example.demo.repository.AdminUserRepository;
import com.example.demo.repository.GeneralUserRepository;

@Service
public class GeneralUserService {
	   
    private final GeneralUserRepository generalUserRepository;
    private final GeneralUserDetailsService generalUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdminUserRepository adminUserRepository;

    // コンストラクタで依存関係を注入
    public GeneralUserService(GeneralUserRepository generalUserRepository,
                              GeneralUserDetailsService generalUserDetailsService,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager,
                              AdminUserRepository adminUserRepository) {
        this.generalUserRepository = generalUserRepository;
        this.generalUserDetailsService = generalUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.adminUserRepository = adminUserRepository;
    }

    public void createGeneralUserByAdminEmail(String adminEmail, String generalPassword) {
        // 管理者をEmailで検索してIDを取得
        AdminUser adminUser = adminUserRepository.findByAdminEmail(adminEmail)
                .orElseThrow(() -> new IllegalArgumentException("管理者が見つかりません: " + adminEmail));

        GeneralUser generalUser = new GeneralUser();

        // パスワードをハッシュ化
        String hashedPassword = passwordEncoder.encode(generalPassword);
        generalUser.setGeneralPassword(hashedPassword);

        // ユニークなログインIDを生成し、short型で設定
        short loginId = generateUniqueLoginId();
        generalUser.setGeneralLoginId(loginId); // loginId は short 型として処理

        // AdminUser オブジェクトを使用して一般ユーザーに設定
        generalUser.setAdminUser(adminUser);

        // コンソールに情報を表示
        System.out.println("New General User Information:");
        System.out.println("Login ID: " + loginId);
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("Admin ID: " + adminUser.getAdminId());

        // 一般ユーザーを保存
        generalUserRepository.save(generalUser);
    }

    // 4桁のランダムなIDを生成し、既存のログインIDと重複しないようにする
    private short generateUniqueLoginId() {
        short loginId;
        do {
            loginId = (short) (Math.random() * 10000); // 4桁のランダムな数字を生成
        } while (generalUserRepository.findByGeneralLoginId(loginId).isPresent());
        return loginId;
    }

    public List<GeneralUser> findAllUsers() {
        return generalUserRepository.findAll();
    }

    // ユーザー削除メソッド
    public void deleteGeneralUserById(short generalId) {
        GeneralUser user = generalUserRepository.findByGeneralLoginId(generalId)
                .orElseThrow(() -> new IllegalArgumentException("指定されたIDのユーザーが存在しません: " + generalId));
        generalUserRepository.delete(user);
    }

    public void showGeneralUsersByAdminId(Integer adminId) {
        // admin_id に一致する general_users のリストを取得
        List<GeneralUser> generalUsers = generalUserRepository.findByAdminUser_AdminId(adminId);

        // 取得したリストをコンソールに表示
        System.out.println("Admin ID: " + adminId + " に関連する General Users:");
        for (GeneralUser generalUser : generalUsers) {
            System.out.println("General User Login ID: " + generalUser.getGeneralLoginId());

            System.out.println("General User Password: " + generalUser.getGeneralPassword());
            System.out.println("--------------------------");
        }
    }
}


