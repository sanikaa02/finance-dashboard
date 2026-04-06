package com.finance.repository;

import com.finance.entity.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * FinancialRecordRepository
 * Handles database operations for FinancialRecord entity
 */
@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    /**
     * Find records by type (INCOME or EXPENSE)
     */
    List<FinancialRecord> findByType(FinancialRecord.Type type);

    /**
     * Find records by category
     */
    List<FinancialRecord> findByCategory(String category);

    /**
     * Find records by date range
     */
    List<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find records by type and date range
     */
    List<FinancialRecord> findByTypeAndDateBetween(
            FinancialRecord.Type type,
            LocalDate startDate,
            LocalDate endDate
    );

    /**
     * Calculate total by type
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = :type")
    BigDecimal sumByType(@Param("type") FinancialRecord.Type type);

    /**
     * Get category-wise totals
     */
    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f " +
           "WHERE f.type = :type GROUP BY f.category")
    List<Object[]> sumByCategory(@Param("type") FinancialRecord.Type type);

    /**
     * Get monthly trends (last 6 months)
     */
    @Query("SELECT DATE_FORMAT(f.date, '%Y-%m') as month, " +
           "SUM(CASE WHEN f.type = 'INCOME' THEN f.amount ELSE 0 END) as income, " +
           "SUM(CASE WHEN f.type = 'EXPENSE' THEN f.amount ELSE 0 END) as expense " +
           "FROM FinancialRecord f " +
           "WHERE f.date >= :startDate " +
           "GROUP BY DATE_FORMAT(f.date, '%Y-%m') " +
           "ORDER BY month DESC")
    List<Object[]> getMonthlyTrends(@Param("startDate") LocalDate startDate);

    /**
     * Get recent transactions (last 10)
     */
    List<FinancialRecord> findTop10ByOrderByCreatedAtDesc();
}