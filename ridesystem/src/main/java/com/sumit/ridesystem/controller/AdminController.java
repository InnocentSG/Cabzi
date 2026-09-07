package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.model.Booking;
import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.DriverSubscription;
import com.sumit.ridesystem.model.HelpIssue;
import com.sumit.ridesystem.model.StaffLog;
import com.sumit.ridesystem.model.StaffMessage;
import com.sumit.ridesystem.model.Subscription;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.service.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")

@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class AdminController {

    private final AdminService
            adminService;

    // =================================
    // DASHBOARD STATS
    // =================================

    @GetMapping("/stats")

    public ResponseEntity<Map<String, Object>>
    dashboardStats() {

        return ResponseEntity.ok(

                adminService.dashboardStats()
        );
    }

    // =================================
    // TODAY BOOKINGS
    // =================================

    @GetMapping("/bookings/today")

    public ResponseEntity<List<Booking>>
    todayBookings() {

        return ResponseEntity.ok(

                adminService.todayBookings()
        );
    }

    // =================================
    // USERS
    // =================================

    @GetMapping("/users")

    public ResponseEntity<List<User>>
    getAllUsers() {

        return ResponseEntity.ok(

                adminService.getAllUsers()
        );
    }

    @PostMapping("/users")

    public ResponseEntity<User>
    saveUser(

            @RequestBody User user
    ) {

        return ResponseEntity.ok(

                adminService.saveUser(user)
        );
    }

    // =================================
    // DRIVERS
    // =================================

    @GetMapping("/drivers")

    public ResponseEntity<List<Driver>>
    getAllDrivers() {

        return ResponseEntity.ok(

                adminService.getAllDrivers()
        );
    }

    @PostMapping("/drivers")

    public ResponseEntity<Driver>
    saveDriver(

            @RequestBody Driver driver
    ) {

        return ResponseEntity.ok(

                adminService.saveDriver(driver)
        );
    }

    // =================================
    // STAFF USERS
    // =================================

    @GetMapping("/staff")

    public ResponseEntity<List<User>>
    getStaffUsers() {

        return ResponseEntity.ok(

                adminService.getStaffUsers()
        );
    }

    @PostMapping("/staff")

    public ResponseEntity<User>
    saveStaff(

            @RequestBody User user
    ) {

        return ResponseEntity.ok(

                adminService.saveStaff(user)
        );
    }

    // =================================
    // HELP ISSUES
    // =================================

    @GetMapping("/help-issues")

    public ResponseEntity<List<HelpIssue>>
    helpIssues() {

        return ResponseEntity.ok(

                adminService.helpIssues()
        );
    }

    // =================================
    // CLOSE HELP ISSUE
    // =================================

    @PatchMapping("/help-issues/{id}")

    public ResponseEntity<HelpIssue>
    closeHelpIssue(

            @PathVariable Long id
    ) {

        return ResponseEntity.ok(

                adminService.closeHelpIssue(id)
        );
    }

    // =================================
    // SUBSCRIPTIONS
    // =================================

    @GetMapping("/subscriptions")

    public ResponseEntity<List<Subscription>>
    subscriptions() {

        return ResponseEntity.ok(

                adminService.subscriptions()
        );
    }

    @PostMapping("/subscriptions")

    public ResponseEntity<Subscription>
    saveSubscription(

            @RequestBody Subscription subscription
    ) {

        return ResponseEntity.ok(

                adminService.saveSubscription(subscription)
        );
    }

    // =================================
    // DRIVER SUBSCRIPTIONS
    // =================================

    @GetMapping("/driver-subscriptions")

    public ResponseEntity<List<DriverSubscription>>
    driverSubscriptions() {

        return ResponseEntity.ok(

                adminService.driverSubscriptions()
        );
    }

    // =================================
    // STAFF LOGS
    // =================================

    @GetMapping("/staff-logs")

    public ResponseEntity<List<StaffLog>>
    staffLogs() {

        return ResponseEntity.ok(

                adminService.staffLogs()
        );
    }

    // =================================
    // STAFF MESSAGES
    // =================================

    @GetMapping("/staff-messages")

    public ResponseEntity<List<StaffMessage>>
    staffMessages() {

        return ResponseEntity.ok(

                adminService.staffMessages()
        );
    }

    @PostMapping("/staff-messages")

    public ResponseEntity<StaffMessage>
    sendStaffMessage(

            @RequestBody StaffMessage message
    ) {

        return ResponseEntity.ok(

                adminService.sendStaffMessage(message)
        );
    }

    // =================================
    // DELETE USER
    // =================================

    @DeleteMapping("/users/{userId}")

    public ResponseEntity<String>
    deleteUser(

            @PathVariable Long userId
    ) {

        adminService.deleteUser(userId);

        return ResponseEntity.ok(

                "User deleted successfully"
        );
    }

    // =================================
    // DELETE DRIVER
    // =================================

    @DeleteMapping("/drivers/{driverId}")

    public ResponseEntity<String>
    deleteDriver(

            @PathVariable Long driverId
    ) {

        adminService.deleteDriver(driverId);

        return ResponseEntity.ok(

                "Driver deleted successfully"
        );
    }

    // =================================
    // DELETE STAFF
    // =================================

    @DeleteMapping("/staff/{staffId}")

    public ResponseEntity<String>
    deleteStaff(

            @PathVariable Long staffId
    ) {

        adminService.deleteStaff(staffId);

        return ResponseEntity.ok(

                "Staff deleted successfully"
        );
    }
}