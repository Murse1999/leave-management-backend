package com.example.leave.dto;

import com.example.leave.enums.LeaveStatus;
import com.example.leave.enums.LeaveType;

import java.time.LocalDate;

public record LeaveResponse(
        Long id,
        Long employeeId,
        String employeeName,
        LocalDate startDate,
        LocalDate endDate,
        long leaveDays,
        String reason,
        LeaveType leaveType,
        LeaveStatus status
) {
}