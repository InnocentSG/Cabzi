package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Role;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository
        extends JpaRepository<User, Long> {

    // =================================
    // FIND BY EMAIL
    // =================================

    Optional<User> findByEmail(
            String email
    );

    // =================================
    // ROLE-BASED LOGIN
    // =================================

    Optional<User> findByEmailAndRole(

            String email,

            Role role
    );

    // =================================
    // ROLE-BASED REGISTER
    // =================================

    boolean existsByEmailAndRole(

            String email,

            Role role
    );

    boolean existsByPhoneAndRole(

            String phone,

            Role role
    );

    // =================================
    // LEGACY METHODS
    // =================================

    boolean existsByEmail(
            String email
    );

    boolean existsByPhone(
            String phone
    );

    // =================================
    // ROLE FILTER
    // =================================

    List<User> findByRole(
            Role role
    );

    // =================================
    // ACTIVE USERS
    // =================================

    List<User> findByActiveTrue();

    // =================================
    // ACTIVE ROLE USERS
    // =================================

    List<User> findByRoleAndActiveTrue(
            Role role
    );
}