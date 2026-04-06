package com.finance.service;

import com.finance.dto.CategorySummaryDTO;
import com.finance.dto.DashboardSummaryDTO;
import com.finance.dto.FinancialRecordDTO;
import com.finance.dto.MonthlyTrendDTO;
import com.finance.entity.FinancialRecord;
import com.finance.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DashboardService
 * Provides dashboard analytics and summaries
 * Accessible to all authenticated users (ADMIN, ANALYST, VIEWER)
 */
@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository recordRepository;

    /**
     * Get overall dashboard summary
     * Shows total income, expense, and net balance
     */
    public DashboardSummaryDTO getDashboardSummary() {
        BigDecimal totalIncome = recordRepository.sumByType(FinancialRecord.Type.INCOME);
        BigDecimal totalExpense = recordRepository.sumByType(FinancialRecord.Type.EXPENSE);
        BigDecimal netBalance = totalIncome.subtract(totalExpense);
        Long totalTransactions = recordRepository.count();

        return new DashboardSummaryDTO(totalIncome, totalExpense, netBalance, totalTransactions);
    }

    /**
     * Get category-wise summary
     * Shows total spending/income by category
     */
    public List<CategorySummaryDTO> getCategorySummary() {
        List<CategorySummaryDTO> summary = new ArrayList<>();

        // Get income categories
        List<Object[]> incomeCategories = recordRepository.sumByCategory(FinancialRecord.Type.INCOME);
        for (Object[] row : incomeCategories) {
            String category = (String) row[0];
            BigDecimal total = (BigDecimal) row[1];
            summary.add(new CategorySummaryDTO(category, total, "INCOME"));
        }

        // Get expense categories
        List<Object[]> expenseCategories = recordRepository.sumByCategory(FinancialRecord.Type.EXPENSE);
        for (Object[] row : expenseCategories) {
            String category = (String) row[0];
            BigDecimal total = (BigDecimal) row[1];
            summary.add(new CategorySummaryDTO(category, total, "EXPENSE"));
        }

        return summary;
    }

    /**
     * Get monthly trends (last 6 months)
     * Shows income, expense, and net for each month
     */
    public List<MonthlyTrendDTO> getMonthlyTrends() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        List<Object[]> trends = recordRepository.getMonthlyTrends(sixMonthsAgo);

        List<MonthlyTrendDTO> result = new ArrayList<>();
        for (Object[] row : trends) {
            String month = (String) row[0];
            BigDecimal income = (BigDecimal) row[1];
            BigDecimal expense = (BigDecimal) row[2];
            BigDecimal net = income.subtract(expense);

            result.add(new MonthlyTrendDTO(month, income, expense, net));
        }

        return result;
    }

    /**
     * Get recent activity (last 10 transactions)
     * Available to all users for dashboard display
     */
    public List<FinancialRecordDTO> getRecentActivity() {
        return recordRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(FinancialRecordDTO::fromEntity)
                .toList();
    }
}