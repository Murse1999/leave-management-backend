package com.example.leave.repository;

import com.example.leave.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.leave.enums.LeaveStatus;
import com.example.leave.enums.LeaveType;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    List<Leave> findByStatus(LeaveStatus status);

    List<Leave> findByLeaveType(LeaveType leaveType);
}