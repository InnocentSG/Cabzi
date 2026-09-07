package com.sumit.ridesystem.config;

import com.sumit.ridesystem.model.Role;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DataSeeder
        implements CommandLineRunner {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    @Value("${seed.admin.email}")
    private String adminEmail;

    @Value("${seed.admin.password}")
    private String adminPassword;

    @Value("${seed.staff.email}")
    private String staffEmail;

    @Value("${seed.staff.password}")
    private String staffPassword;

    @Override
    public void run(String... args)
            throws Exception {

        // =================================
        // ADMIN
        // =================================

        if (
                !userRepository.existsByEmail(
                        adminEmail
                )
        ) {

            User admin =
                    User.builder()

                            .name("Sumit Kumar")

                            .email(adminEmail)

                            .phone("9334761659")

                            .password(
                                    passwordEncoder.encode(
                                            adminPassword
                                    )
                            )

                            .role(Role.ADMIN)

                            .active(true)

                            .build();

            userRepository.save(admin);

            System.out.println(
                    "Admin created"
            );
        }

        // =================================
        // STAFF
        // =================================

        if (
                !userRepository.existsByEmail(
                        staffEmail
                )
        ) {

            User staff =
                    User.builder()

                            .name("Aryan Kumar")

                            .email(staffEmail)

                            .phone("7903667329")

                            .password(
                                    passwordEncoder.encode(
                                            staffPassword
                                    )
                            )

                            .role(Role.STAFF)

                            .active(true)

                            .build();

            userRepository.save(staff);

            System.out.println(
                    "Staff created"
            );
        }
    }
}
