package com.finance.service;

import com.finance.dto.UserDTO;
import com.finance.entity.User;
import com.finance.exception.AccessDeniedException;
import com.finance.exception.ResourceNotFoundException;
import com.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 * Handles user-related business logic
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get current logged-in user
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Check if current user is Admin
     */
    public boolean isAdmin() {
        User user = getCurrentUser();
        return user.getRole() == User.Role.ADMIN;
    }

    /**
     * Get all users (Admin only)
     */
    public List<UserDTO> getAllUsers() {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can view all users");
        }

        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Get user by ID
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserDTO.fromEntity(user);
    }

    /**
     * Create new user (Admin only)
     */
    public UserDTO createUser(UserDTO userDTO) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can create users");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : User.Status.ACTIVE);

        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    /**
     * Update user (Admin only)
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can update users");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());

        // Update password only if provided
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return UserDTO.fromEntity(updatedUser);
    }

    /**
     * Delete user (Admin only)
     */
    public void deleteUser(Long id) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can delete users");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    /**
     * Toggle user status (Admin only)
     */
    public UserDTO toggleUserStatus(Long id) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can change user status");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setStatus(user.getStatus() == User.Status.ACTIVE 
                ? User.Status.INACTIVE 
                : User.Status.ACTIVE);

        User updatedUser = userRepository.save(user);
        return UserDTO.fromEntity(updatedUser);
    }

    /**
     * Search users by name or email (Admin and Analyst)
     */
    public List<UserDTO> searchUsers(String searchTerm) {
        User user = getCurrentUser();
        if (user.getRole() == User.Role.VIEWER) {
            throw new AccessDeniedException("Viewers cannot search users");
        }

        return userRepository.searchUsers(searchTerm).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}