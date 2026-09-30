package com.example.leave.controller;

import com.example.leave.dto.LeaveRequest;
import com.example.leave.dto.LeaveResponse;
import com.example.leave.dto.LeaveStatusUpdateRequest;
import com.example.leave.enums.LeaveStatus;
import com.example.leave.enums.LeaveType;
import com.example.leave.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<LeaveResponse> addLeave(
            @Valid @RequestBody LeaveRequest request
    ) {
        LeaveResponse response = leaveService.addLeave(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public List<LeaveResponse> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/{leaveId}")
    public LeaveResponse getLeave(@PathVariable Long leaveId) {
        return leaveService.getLeaveById(leaveId);
    }

    @PatchMapping("/{leaveId}/status")
    public LeaveResponse updateLeave(@PathVariable Long leaveId,
                                     @Valid @RequestBody LeaveStatusUpdateRequest request
    ){
        return leaveService.updateLeaveStatus(leaveId, request);
    }

    @DeleteMapping("/{leaveId}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long leaveId) {
        leaveService.deleteLeave(leaveId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveResponse> getLeaveByEmployeeId(@PathVariable Long employeeId) {
        return leaveService.getLeavesByEmployeeId(employeeId);
    }

    @GetMapping("/status/{status}")
    public List<LeaveResponse> getLeaveByStatus(@PathVariable LeaveStatus status) {
        return leaveService.getLeavesByStatus(status);
    }

    @GetMapping("/type/{leaveType}")
    public List<LeaveResponse> getLeavesByType(
            @PathVariable LeaveType leaveType
    ) {
        return leaveService.getLeavesByType(leaveType);
    }


}