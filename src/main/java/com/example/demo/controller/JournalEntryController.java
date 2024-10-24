package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.JournalEntryForm;
import com.example.demo.service.JournalEntryService;

@Controller
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }
  @GetMapping("/main")
    public String showMainPage() {
        return "main";  // templates/main.html を表示する
    }
  


  // 会計帳票表示への画面遷移とデータの処理

    @PostMapping("/journalentry/create")
    public String createJournalEntry(@ModelAttribute JournalEntryForm journalEntryForm, BindingResult bindingResult, Model model) {
        System.out.println("Entry Date: " + journalEntryForm.getDate());
        System.out.println("Description: " + journalEntryForm.getDescription());
        System.out.println("Total Debit: " + journalEntryForm.getTotalAmount());

        try {
            journalEntryService.createJournalEntry(journalEntryForm);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to register journal entry.");
        }
        return "redirect:/journal";
    }
    
    @GetMapping("/journals/show")
    public String showJournals(Model model) {
        // 必要に応じてモデルにデータを追加して、ビューに渡します
        return "journals/show"; // journals/show.html を表示
    }
    
}

