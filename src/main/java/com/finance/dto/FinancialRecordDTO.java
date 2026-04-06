package com.finance.dto;

import com.finance.entity.FinancialRecord;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * FinancialRecord DTO
 * Used for creating and returning financial records
 */
public class FinancialRecordDTO {

    private Long id;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Type is required")
    private FinancialRecord.Type type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String description;

    private Long createdBy;

    private Long deletedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    // Constructors

    public FinancialRecordDTO() {
    }

    public FinancialRecordDTO(Long id, BigDecimal amount, FinancialRecord.Type type,
                              String category, LocalDate date, String description,
                              Long createdBy, Long deletedBy,
                              LocalDateTime createdAt, LocalDateTime updatedAt,
                              LocalDateTime deletedAt) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
        this.createdBy = createdBy;
        this.deletedBy = deletedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FinancialRecord.Type getType() {
        return type;
    }

    public void setType(FinancialRecord.Type type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * Convert Entity → DTO
     */
    public static FinancialRecordDTO fromEntity(FinancialRecord record) {

        FinancialRecordDTO dto = new FinancialRecordDTO();

        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setDescription(record.getDescription());
        dto.setCreatedBy(record.getCreatedBy());
        dto.setDeletedBy(record.getDeletedBy());
        dto.setCreatedAt(record.getCreatedAt());
        dto.setUpdatedAt(record.getUpdatedAt());
        dto.setDeletedAt(record.getDeletedAt());

        return dto;
    }

    /**
     * Convert DTO → Entity
     */
    public FinancialRecord toEntity() {

        FinancialRecord record = new FinancialRecord();

        record.setId(this.id);
        record.setAmount(this.amount);
        record.setType(this.type);
        record.setCategory(this.category);
        record.setDate(this.date);
        record.setDescription(this.description);
        record.setCreatedBy(this.createdBy);
        record.setDeletedBy(this.deletedBy);

        return record;
    }
}