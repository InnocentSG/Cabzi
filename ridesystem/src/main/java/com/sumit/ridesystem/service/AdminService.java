package com.sumit.ridesystem.service;

import com.sumit.ridesystem.model.*;

import com.sumit.ridesystem.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class AdminService {

    private final UserRepository
            userRepository;

    private final DriverRepository
            driverRepository;

    private final RideRepository
            rideRepository;

    private final HelpIssueRepository
            helpIssueRepository;

    private final SubscriptionRepository
            subscriptionRepository;

    private final DriverSubscriptionRepository
            driverSubscriptionRepository;

    private final StaffLogRepository
            staffLogRepository;

    private final BookingRepository
            bookingRepository;

    private final StaffMessageRepository
            staffMessageRepository;

    private final PasswordEncoder
            passwordEncoder;

    // =================================
    // ALL USERS
    // =================================

    public List<User>
    getAllUsers() {

        return userRepository.findAll();
    }

    // =================================
    // SAVE USER
    // =================================

    public User saveUser(
            User user
    ) {

        user.setRole(Role.USER);

        user.setPassword(

                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }

    // =================================
    // ALL DRIVERS
    // =================================

    public List<Driver>
    getAllDrivers() {

        return driverRepository.findAll();
    }

    // =================================
    // SAVE DRIVER
    // =================================

    public Driver saveDriver(
            Driver driver
    ) {

        return driverRepository.save(driver);
    }

    // =================================
    // STAFF USERS
    // =================================

    public List<User>
    getStaffUsers() {

        return userRepository.findAll()

                .stream()

                .filter(user ->

                        user.getRole()
                                == Role.STAFF
                )

                .toList();
    }

    // =================================
    // SAVE STAFF
    // =================================

    public User saveStaff(
            User user
    ) {

        user.setRole(Role.STAFF);

        user.setPassword(

                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }

    // =================================
    // DASHBOARD STATS
    // =================================

    public Map<String, Object>
    dashboardStats() {

        Map<String, Object> stats =
                new HashMap<>();

        stats.put(
                "totalUsers",
                userRepository.count()
        );

        stats.put(
                "totalDrivers",
                driverRepository.count()
        );

        stats.put(
                "totalRides",
                rideRepository.count()
        );

        stats.put(
                "totalSubscriptions",
                subscriptionRepository.count()
        );

        return stats;
    }

    // =================================
    // TODAY BOOKINGS
    // =================================

    public List<Booking>
    todayBookings() {

        return bookingRepository.findAll();
    }

    // =================================
    // HELP ISSUES
    // =================================

    public List<HelpIssue>
    helpIssues() {

        return helpIssueRepository.findAll();
    }

    // =================================
    // CLOSE HELP ISSUE
    // =================================

    public HelpIssue closeHelpIssue(
            Long id
    ) {

        HelpIssue issue =

                helpIssueRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Issue not found"
                                )
                        );

        issue.setStatus("Closed");

        return helpIssueRepository.save(issue);
    }

    // =================================
    // SUBSCRIPTIONS
    // =================================

    public List<Subscription>
    subscriptions() {

        return subscriptionRepository.findAll();
    }

    // =================================
    // SAVE SUBSCRIPTION
    // =================================

    public Subscription saveSubscription(
            Subscription subscription
    ) {

        return subscriptionRepository
                .save(subscription);
    }

    // =================================
    // DRIVER SUBSCRIPTIONS
    // =================================

    public List<DriverSubscription>
    driverSubscriptions() {

        return driverSubscriptionRepository
                .findAll();
    }

    // =================================
    // STAFF LOGS
    // =================================

    public List<StaffLog>
    staffLogs() {

        return staffLogRepository.findAll();
    }

    // =================================
    // STAFF MESSAGES
    // =================================

    public List<StaffMessage>
    staffMessages() {

        return staffMessageRepository.findAll();
    }

    // =================================
    // SEND STAFF MESSAGE
    // =================================

    public StaffMessage sendStaffMessage(
            StaffMessage message
    ) {

        return staffMessageRepository
                .save(message);
    }

    // =================================
    // DELETE USER
    // =================================

    public void deleteUser(
            Long userId
    ) {

        userRepository.deleteById(userId);
    }

    // =================================
    // DELETE DRIVER
    // =================================

    public void deleteDriver(
            Long driverId
    ) {

        driverRepository.deleteById(driverId);
    }

    // =================================
    // DELETE STAFF
    // =================================

    public void deleteStaff(
            Long staffId
    ) {

        userRepository.deleteById(staffId);
    }
}