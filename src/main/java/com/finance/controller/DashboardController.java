package com.finance.controller;

import com.finance.dto.CategorySummaryDTO;
import com.finance.dto.DashboardSummaryDTO;
import com.finance.dto.FinancialRecordDTO;
import com.finance.dto.MonthlyTrendDTO;
import com.finance.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DashboardController
 * Provides dashboard analytics and summaries
 * Access: All authenticated users (ADMIN, ANALYST, VIEWER)
 * 
 * These are the IMPORTANT endpoints - not just CRUD!
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Get overall dashboard summary
     * GET /api/dashboard/summary
     * 
     * Response:
     * {
     *   "totalIncome": 50000.00,
     *   "totalExpense": 30000.00,
     *   "netBalance": 20000.00,
     *   "totalTransactions": 150
     * }
     * 
     * Access: All authenticated users
     */
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
        DashboardSummaryDTO summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    /**
     * Get category-wise summary
     * GET /api/dashboard/category-summary
     * 
     * Response:
     * [
     *   {
     *     "category": "Salary",
     *     "total": 50000.00,
     *     "type": "INCOME"
     *   },
     *   {
     *     "category": "Groceries",
     *     "total": 5000.00,
     *     "type": "EXPENSE"
     *   }
     * ]
     * 
     * Access: All authenticated users
     */
    @GetMapping("/category-summary")
    public ResponseEntity<List<CategorySummaryDTO>> getCategorySummary() {
        List<CategorySummaryDTO> summary = dashboardService.getCategorySummary();
        return ResponseEntity.ok(summary);
    }

    /**
     * Get monthly trends (last 6 months)
     * GET /api/dashboard/monthly-trends
     * 
     * Response:
     * [
     *   {
     *     "month": "2024-01",
     *     "income": 50000.00,
     *     "expense": 30000.00,
     *     "net": 20000.00
     *   },
     *   {
     *     "month": "2023-12",
     *     "income": 48000.00,
     *     "expense": 28000.00,
     *     "net": 20000.00
     *   }
     * ]
     * 
     * Access: All authenticated users
     */
    @GetMapping("/monthly-trends")
    public ResponseEntity<List<MonthlyTrendDTO>> getMonthlyTrends() {
        List<MonthlyTrendDTO> trends = dashboardService.getMonthlyTrends();
        return ResponseEntity.ok(trends);
    }

    /**
     * Get recent activity (last 10 transactions)
     * GET /api/dashboard/recent
     * 
     * Response: List of recent transactions
     * 
     * Access: All authenticated users
     */
    @GetMapping("/recent")
    public ResponseEntity<List<FinancialRecordDTO>> getRecentActivity() {
        List<FinancialRecordDTO> recent = dashboardService.getRecentActivity();
        return ResponseEntity.ok(recent);
    }
}