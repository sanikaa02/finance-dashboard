package com.finance.service;

import com.finance.dto.FinancialRecordDTO;
import com.finance.entity.FinancialRecord;
import com.finance.entity.User;
import com.finance.exception.AccessDeniedException;
import com.finance.exception.ResourceNotFoundException;
import com.finance.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FinancialRecordService
 * Handles financial record business logic with role-based access control
 */
@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository recordRepository;

    @Autowired
    private UserService userService;

    /**
     * Check if user can manage records (Admin only)
     */
    private void checkManagePermission() {
        User user = userService.getCurrentUser();
        if (user.getRole() != User.Role.ADMIN) {
            throw new AccessDeniedException("Only admins can create/update/delete records");
        }
    }

    /**
     * Check if user can view records (Admin and Analyst)
     */
    private void checkViewPermission() {
        User user = userService.getCurrentUser();
        if (user.getRole() == User.Role.VIEWER) {
            throw new AccessDeniedException("Viewers cannot access financial records directly");
        }
    }

    /**
     * Create new financial record (Admin only)
     */
    public FinancialRecordDTO createRecord(FinancialRecordDTO recordDTO) {
        checkManagePermission();

        User currentUser = userService.getCurrentUser();
        FinancialRecord record = recordDTO.toEntity();
        record.setCreatedBy(currentUser.getId());

        FinancialRecord savedRecord = recordRepository.save(record);
        return FinancialRecordDTO.fromEntity(savedRecord);
    }

    /**
     * Get all financial records (Admin and Analyst)
     */
    public List<FinancialRecordDTO> getAllRecords() {
        checkViewPermission();

        return recordRepository.findAll().stream()
                .map(FinancialRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get record by ID (Admin and Analyst)
     */
    public FinancialRecordDTO getRecordById(Long id) {
        checkViewPermission();

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));
        return FinancialRecordDTO.fromEntity(record);
    }

    /**
     * Update financial record (Admin only)
     */
    public FinancialRecordDTO updateRecord(Long id, FinancialRecordDTO recordDTO) {
        checkManagePermission();

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        record.setAmount(recordDTO.getAmount());
        record.setType(recordDTO.getType());
        record.setCategory(recordDTO.getCategory());
        record.setDate(recordDTO.getDate());
        record.setDescription(recordDTO.getDescription());

        FinancialRecord updatedRecord = recordRepository.save(record);
        return FinancialRecordDTO.fromEntity(updatedRecord);
    }

    /**
     * Delete financial record (Admin only)
     */
    public void deleteRecord(Long id) {
        checkManagePermission();

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        recordRepository.delete(record);
    }

    /**
     * Filter records by type (Admin and Analyst)
     */
    public List<FinancialRecordDTO> getRecordsByType(FinancialRecord.Type type) {
        checkViewPermission();

        return recordRepository.findByType(type).stream()
                .map(FinancialRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter records by category (Admin and Analyst)
     */
    public List<FinancialRecordDTO> getRecordsByCategory(String category) {
        checkViewPermission();

        return recordRepository.findByCategory(category).stream()
                .map(FinancialRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Filter records by date range (Admin and Analyst)
     */
    public List<FinancialRecordDTO> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        checkViewPermission();

        return recordRepository.findByDateBetween(startDate, endDate).stream()
                .map(FinancialRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get recent records (Admin and Analyst)
     */
    public List<FinancialRecordDTO> getRecentRecords() {
        checkViewPermission();

        return recordRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(FinancialRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }
}