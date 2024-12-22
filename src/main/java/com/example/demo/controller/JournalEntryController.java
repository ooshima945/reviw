package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.config.GeneralUserDetails;
import com.example.demo.form.JournalEntryForm;
import com.example.demo.repository.GeneralUserRepository;
import com.example.demo.service.AccountBalanceService;
import com.example.demo.service.JournalEntryService;

@Controller
public class JournalEntryController {

    private final GeneralUserRepository generalUserRepository;
    private final JournalEntryService journalEntryService;
    private final AccountBalanceService accountBalanceService;
   

  
    public JournalEntryController(GeneralUserRepository generalUserRepository, JournalEntryService journalEntryService, AccountBalanceService accountBalanceService) {
        this.generalUserRepository = generalUserRepository;
        this.journalEntryService = journalEntryService;
        this.accountBalanceService = accountBalanceService;
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "main";  // templates/main.html を表示する
    }


    @GetMapping("/journalentry/form")
    public String showJournalEntryForm(Model model) {
        // Retrieve the current generalId from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GeneralUserDetails userDetails = (GeneralUserDetails) authentication.getPrincipal();
        Integer generalId = userDetails.getGeneralId();

        // Retrieve entries based on the logged-in user's generalId
        List<Object[]> journalEntries = journalEntryService.getEntriesWithDetailsByGeneralId(generalId);


        // Add entries to the model for rendering
        model.addAttribute("journalEntries", journalEntries);

        // 新しい JournalEntryForm オブジェクトを作成してモデルに追加
        model.addAttribute("journalEntryForm", new JournalEntryForm());

        return "journals";
    }

    

    // 仕訳データの作成処理
    @PostMapping("/journalentry/create")
    public String createJournalEntry(@ModelAttribute JournalEntryForm journalEntryForm,  Model model) {

        // ログインしている一般ユーザーの generalId を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GeneralUserDetails userDetails = (GeneralUserDetails) authentication.getPrincipal();
        Integer generalId = userDetails.getGeneralId(); // generalIdを取得
      

      
            journalEntryService.createJournalEntry(generalId, journalEntryForm);
         
  
          
   
        List<Object[]> journalEntries = journalEntryService.getEntriesWithDetailsByGeneralId(generalId);
        model.addAttribute("journalEntries", journalEntries);
        return "redirect:/journalentry/form";
    }


    @GetMapping("/journals/show")
    public String showJournals(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GeneralUserDetails userDetails = (GeneralUserDetails) authentication.getPrincipal();
        Integer generalId = userDetails.getGeneralId();


        Integer adminId = generalUserRepository.findAdminIdByGeneralId(generalId);


        Map<String, Object> calculatedBalances = accountBalanceService.getAccountBalances(adminId);

        model.addAttribute("calculatedBalances", calculatedBalances);

        return "balance_sheet";
    }
    
    @PostMapping("/journals/update/{entryId}")
    public ResponseEntity<String> deleteJournalEntry(@PathVariable("entryId") Integer entryId) {
       
            // サービスを呼び出して削除処理を実行
            journalEntryService.deleteJournalEntry(entryId);
          
            return ResponseEntity.ok("Entry deleted successfully.");
        }
    }

