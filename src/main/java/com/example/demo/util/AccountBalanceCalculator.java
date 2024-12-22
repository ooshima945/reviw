package com.example.demo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.JournalSumDTO;
@Component
public class AccountBalanceCalculator {
 

    public Map<String, Object> calculateBalances(List<JournalSumDTO> journalSums) {
        if (journalSums == null || journalSums.isEmpty()) {
            return Map.of();
        }
        Map<String, Object> result = new HashMap<>();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.JAPAN);
        ((DecimalFormat) nf).applyPattern("#,##0");
        result.put("journalSums", journalSums);

        Map<Long, BigDecimal> debitSumMap = journalSums.stream()
                .collect(Collectors.groupingBy(JournalSumDTO::getDebitAccountId))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().map(JournalSumDTO::getDebitSum).reduce(BigDecimal.ZERO, BigDecimal::add)));
        
        Map<Long, BigDecimal> creditSumMap = journalSums.stream()
                .collect(Collectors.groupingBy(JournalSumDTO::getCreditAccountId))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().map(JournalSumDTO::getCreditSum).reduce(BigDecimal.ZERO, BigDecimal::add)));
        
        Map<String, List<Long>> accountRanges = new HashMap<>();
        accountRanges.put("流動資産", List.of(1L, 2L, 3L));
        accountRanges.put("固定資産", List.of(9L, 10L));
        accountRanges.put("負債総額", List.of(4L, 11L));
        accountRanges.put("資本総額", List.of(5L));
        accountRanges.put("現金及び預金", List.of(1L));
        accountRanges.put("売掛金", List.of(2L));
        accountRanges.put("在庫", List.of(3L));
        accountRanges.put("買掛金", List.of(4L));
        accountRanges.put("資本金", List.of(5L));
        accountRanges.put("土地",List.of(9L));
        accountRanges.put("建物", List.of(10L));
        accountRanges.put("短期借入金",List.of(11L));
        

        for (Map.Entry<String, List<Long>> entry : accountRanges.entrySet()) {
            String key = entry.getKey();
            List<Long> accountIds = entry.getValue();
            BigDecimal subtotal = calculateSubtotal(debitSumMap, creditSumMap, accountIds);
            if ("短期借入金".equals(key) || "資本金".equals(key) || "買掛金".equals(key)|| "負債総額".equals(key)|| "資本総額".equals(key)|| "買掛金".equals(key)) {
                subtotal = subtotal.multiply(BigDecimal.valueOf(-1)); // 対象科目の符号を反転
            }
            result.put(key, subtotal);
        }

        BigDecimal totalAssets = calculateTotalAssets(debitSumMap, creditSumMap);
        BigDecimal totalLiabilitiesEquity = calculateTotalLiabilitiesEquity(debitSumMap, creditSumMap);
        result.put("資産合計", nf.format(totalAssets));
        result.put("負債資本合計", nf.format(totalLiabilitiesEquity.multiply(BigDecimal.valueOf(-1))));

        return result;
    }

    private BigDecimal calculateSubtotal(Map<Long, BigDecimal> debitSumMap, Map<Long, BigDecimal> creditSumMap, List<Long> accountIds) {
        return accountIds.stream()
                .map(accountId -> {
                    BigDecimal debit = debitSumMap.getOrDefault(accountId, BigDecimal.ZERO);
                    BigDecimal credit = creditSumMap.getOrDefault(accountId, BigDecimal.ZERO);
                    return debit.subtract(credit);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalAssets(Map<Long, BigDecimal> debitSumMap, Map<Long, BigDecimal> creditSumMap) {
        return calculateSubtotal(debitSumMap, creditSumMap, List.of(1L, 2L, 3L, 9L, 10L));
    }

    private BigDecimal calculateTotalLiabilitiesEquity(Map<Long, BigDecimal> debitSumMap, Map<Long, BigDecimal> creditSumMap) {
        return calculateSubtotal(debitSumMap, creditSumMap, List.of(4L, 5L, 11L));
    }
}