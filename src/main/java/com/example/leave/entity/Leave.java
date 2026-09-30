package com.example.leave.entity;

import com.example.leave.enums.LeaveStatus;
import jakarta.persistence.*;
import com.example.leave.enums.LeaveType;

import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 500)
    private String reason;

    @Enumerated(EnumType.STRING) //存文字
    @Column(nullable = false, length = 20)
    private LeaveStatus status = LeaveStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType = LeaveType.ANNUAL;

    public Leave() {
    }

    public Leave(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate,
            String reason
    ) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        // status 不用設定，會使用預設值 PENDING
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
}