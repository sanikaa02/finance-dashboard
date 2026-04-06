package com.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Monthly Trend DTO
 * Contains monthly income and expense data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTrendDTO {

    private String month;  // Format: "2024-01"
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal net;
}