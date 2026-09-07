package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "users",

        uniqueConstraints = {

                @UniqueConstraint(
                        columnNames = {
                                "email",
                                "role"
                        }
                ),

                @UniqueConstraint(
                        columnNames = {
                                "phone",
                                "role"
                        }
                )
        }
)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long userId;

    @Column(
            nullable = false
    )

    private String name;

    @Column(
            nullable = false
    )

    private String email;

    @Column(
            nullable = false
    )

    private String phone;

    @Column(
            nullable = false
    )

    private String password;

    @Column(length = 1000)

    private String fcmToken;

    @Enumerated(
            EnumType.STRING
    )

    private Role role = Role.USER;

    // =================================
    // ACCOUNT STATUS
    // =================================

    @Builder.Default

    private Boolean active = true;

    // =================================
    // CREATED TIME
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();

    // =================================
    // AUTHORITIES
    // =================================

    @Override
    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return List.of(

                new SimpleGrantedAuthority(
                        role.name()
                )
        );
    }

    // =================================
    // USERNAME
    // =================================

    @Override
    public String getUsername() {

        return email;
    }

    // =================================
    // ACCOUNT EXPIRED
    // =================================

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    // =================================
    // ACCOUNT LOCKED
    // =================================

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    // =================================
    // CREDENTIALS EXPIRED
    // =================================

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    // =================================
    // ENABLED
    // =================================

    @Override
    public boolean isEnabled() {

        return active;
    }
}
