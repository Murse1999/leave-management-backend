package com.example.leave.dto;

import com.example.leave.enums.LeaveStatus;
import jakarta.validation.constraints.NotNull;

public record LeaveStatusUpdateRequest (

        @NotNull(message = "請假狀態不可為空")
        LeaveStatus status
){}
