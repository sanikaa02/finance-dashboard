package com.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Category Summary DTO
 * Contains spending/income by category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDTO {

    private String category;
    private BigDecimal total;
    private String type;  // INCOME or EXPENSE
}