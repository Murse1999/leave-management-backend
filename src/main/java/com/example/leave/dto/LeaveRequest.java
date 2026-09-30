package com.example.leave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;
import com.example.leave.enums.LeaveType;

import java.time.LocalDate;

public record LeaveRequest(
        @NotNull(message = "員工編號不可為空")
        Long employeeId,

        @NotNull(message = "開始日期不可為空")
        LocalDate startDate,

        @NotNull(message = "結束日期不可為空")
        LocalDate endDate,

        @NotNull(message = "請假類型不可為空")
        LeaveType leaveType,

        @NotBlank(message = "請假原因不可為空")
        @Size(max = 500, message = "請假原因最多 500 個字")
        String reason

) {
    //@AssertTrue 是驗證註解，意思是：它標記的布林值必須是 true，否則驗證失敗。
    @AssertTrue(message = "結束日期不可早於開始日期")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null
                || !endDate.isBefore(startDate);
    }
}