package com.example.leave.service;

import com.example.leave.dto.LeaveRequest;
import com.example.leave.dto.LeaveResponse;
import com.example.leave.dto.LeaveStatusUpdateRequest;
import com.example.leave.entity.Employee;
import com.example.leave.entity.Leave;
import com.example.leave.enums.LeaveStatus;
import com.example.leave.exception.EmployeeNotFoundException;
import com.example.leave.exception.LeaveNotFoundException;
import com.example.leave.exception.LeaveStatusChangeNotAllowedException;
import com.example.leave.repository.EmployeeRepository;
import com.example.leave.repository.LeaveRepository;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import com.example.leave.enums.LeaveType;

import java.util.ArrayList;
import java.util.List;


@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveService(
             LeaveRepository leaveRepository,
            EmployeeRepository employeeRepository
    ) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    public LeaveResponse addLeave(LeaveRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(request.employeeId())
                );
        Leave leave = new Leave(
                employee,
                request.startDate(),
                request.endDate(),
                request.reason()
        );
        leave.setLeaveType(request.leaveType());
        return toResponse(leaveRepository.save(leave));
    }

    public List<LeaveResponse> getAllLeaves() {
        List<Leave> leaves = leaveRepository.findAll();
        return toResponseList(leaves);
    }

    public LeaveResponse getLeaveById(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException(leaveId));

        return toResponse(leave);
    }

    public LeaveResponse updateLeaveStatus(
            Long leaveId,
            LeaveStatusUpdateRequest request
    ) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException(leaveId));
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new LeaveStatusChangeNotAllowedException();
        }

        leave.setStatus(request.status());
        return toResponse(leaveRepository.save(leave));
    }

    public void deleteLeave(Long leaveId){
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException(leaveId));
        leaveRepository.delete(leave);
    }

    public List<LeaveResponse> getLeavesByEmployeeId(Long employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        List<Leave> leaves = leaveRepository.findByEmployeeId(employeeId);

        return toResponseList(leaves);
    }

    public List<LeaveResponse> getLeavesByStatus(LeaveStatus status) {
        List<Leave> leaves = leaveRepository.findByStatus(status);

        return toResponseList(leaves);
    }

    public List<LeaveResponse> getLeavesByType(LeaveType leaveType) {
        List<Leave> leaves = leaveRepository.findByLeaveType(leaveType);

        return toResponseList(leaves);
    }

    private List<LeaveResponse> toResponseList(List<Leave> leaves) {
        List<LeaveResponse> leaveResponses = new ArrayList<>();

        for (Leave leave : leaves) {
            leaveResponses.add(toResponse(leave));
        }

        return leaveResponses;
    }

    private LeaveResponse toResponse(Leave leave) {

        long leaveDays = ChronoUnit.DAYS.between(
                leave.getStartDate(),
                leave.getEndDate()
        ) + 1;
        return new LeaveResponse(
                leave.getId(),
                leave.getEmployee().getId(),
                leave.getEmployee().getName(),
                leave.getStartDate(),
                leave.getEndDate(),
                leaveDays,
                leave.getReason(),
                leave.getLeaveType(),
                leave.getStatus()
        );
    }
}