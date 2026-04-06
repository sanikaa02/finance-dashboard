package com.finance.controller;

import com.finance.dto.FinancialRecordDTO;
import com.finance.entity.FinancialRecord;
import com.finance.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * FinancialRecordController
 * Handles financial record operations
 * Access Control:
 * - Create/Update/Delete: ADMIN only
 * - View: ADMIN and ANALYST
 */
@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = "*")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService recordService;

    /**
     * Get all financial records
     * GET /api/records
     * Access: ADMIN, ANALYST
     */
    @GetMapping
    public ResponseEntity<List<FinancialRecordDTO>> getAllRecords() {
        List<FinancialRecordDTO> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    /**
     * Get record by ID
     * GET /api/records/{id}
     * Access: ADMIN, ANALYST
     */
    @GetMapping("/{id}")
    public ResponseEntity<FinancialRecordDTO> getRecordById(@PathVariable Long id) {
        FinancialRecordDTO record = recordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }

    /**
     * Create new financial record
     * POST /api/records
     * Access: ADMIN only
     * 
     * Request Body:
     * {
     *   "amount": 5000.00,
     *   "type": "INCOME",
     *   "category": "Salary",
     *   "date": "2024-01-15",
     *   "description": "Monthly salary"
     * }
     */
    @PostMapping
    public ResponseEntity<FinancialRecordDTO> createRecord(
            @Valid @RequestBody FinancialRecordDTO recordDTO) {
        FinancialRecordDTO createdRecord = recordService.createRecord(recordDTO);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    /**
     * Update financial record
     * PUT /api/records/{id}
     * Access: ADMIN only
     */
    @PutMapping("/{id}")
    public ResponseEntity<FinancialRecordDTO> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody FinancialRecordDTO recordDTO) {
        FinancialRecordDTO updatedRecord = recordService.updateRecord(id, recordDTO);
        return ResponseEntity.ok(updatedRecord);
    }

    /**
     * Delete financial record
     * DELETE /api/records/{id}
     * Access: ADMIN only
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Filter records by type
     * GET /api/records/filter/type?type=INCOME
     * Access: ADMIN, ANALYST
     */
    @GetMapping("/filter/type")
    public ResponseEntity<List<FinancialRecordDTO>> getRecordsByType(
            @RequestParam FinancialRecord.Type type) {
        List<FinancialRecordDTO> records = recordService.getRecordsByType(type);
        return ResponseEntity.ok(records);
    }

    /**
     * Filter records by category
     * GET /api/records/filter/category?category=Salary
     * Access: ADMIN, ANALYST
     */
    @GetMapping("/filter/category")
    public ResponseEntity<List<FinancialRecordDTO>> getRecordsByCategory(
            @RequestParam String category) {
        List<FinancialRecordDTO> records = recordService.getRecordsByCategory(category);
        return ResponseEntity.ok(records);
    }

    /**
     * Filter records by date range
     * GET /api/records/filter/date?startDate=2024-01-01&endDate=2024-12-31
     * Access: ADMIN, ANALYST
     */
    @GetMapping("/filter/date")
    public ResponseEntity<List<FinancialRecordDTO>> getRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<FinancialRecordDTO> records = recordService.getRecordsByDateRange(startDate, endDate);
        return ResponseEntity.ok(records);
    }

    /**
     * Get recent records
     * GET /api/records/recent
     * Access: ADMIN, ANALYST
     */
    @GetMapping("/recent")
    public ResponseEntity<List<FinancialRecordDTO>> getRecentRecords() {
        List<FinancialRecordDTO> records = recordService.getRecentRecords();
        return ResponseEntity.ok(records);
    }
}
