package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminUser;
import com.example.demo.entity.GeneralUser;
import com.example.demo.repository.AdminUserRepository;
import com.example.demo.repository.GeneralUserRepository;

@Service
public class GeneralUserService {
	   
	   private final GeneralUserRepository generalUserRepository;
	
	    private final PasswordEncoder passwordEncoder;
	
	    private final AdminUserRepository adminUserRepository;

	    // コンストラクタで依存関係を注入
	    public GeneralUserService(GeneralUserRepository generalUserRepository,
	                          
	                              PasswordEncoder passwordEncoder,
	                           
	                              AdminUserRepository adminUserRepository) {
	        this.generalUserRepository = generalUserRepository;
	
	        this.passwordEncoder = passwordEncoder;
	    
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
        Short loginId = generateUniqueLoginId();
        generalUser.setGeneralLoginId(loginId);
        // AdminUser オブジェクトを使用して一般ユーザーに設定
        generalUser.setAdminUser(adminUser);
        // 一般ユーザーを保存
        generalUserRepository.save(generalUser);
    }

    // 4桁のランダムなIDを生成し、既存のログインIDと重複しないようにする
    private Short generateUniqueLoginId() {
        Short loginId;
        do {
            loginId = (short) (1000 + Math.random() * 9000); // 1000 ~ 9999 の範囲でランダムな数字を生成
        } while (generalUserRepository.findByGeneralLoginId(loginId).isPresent());
        return loginId;
    }

    public List<GeneralUser> findAllUsers() {
        return generalUserRepository.findAll();
    }

    

    public Page<GeneralUser> findByAdminUserAdminIdAndDeletedAtIsNull(Integer adminId, PageRequest pageable) {
        return generalUserRepository.findByAdminUser_AdminIdAndDeletedAtIsNull(adminId, pageable);
        }

    public void deleteGeneralUserByLoginId(Short loginId) {
        GeneralUser generalUser = generalUserRepository.findByGeneralLoginId(loginId)
           .orElseThrow(() -> new IllegalArgumentException("Invalid user login ID: " + loginId));
     generalUser.setDeletedAt(1);
      generalUserRepository.save(generalUser);
 }

}