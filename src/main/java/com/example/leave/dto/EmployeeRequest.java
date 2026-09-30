package com.example.leave.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(
        @NotBlank(message = "姓名不可為空")
        @Size(max = 50, message = "姓名最多 50 個字")
        String name,

        @NotBlank(message = "Email 不可為空")
        @Email(message = "Email 格式不正確")
        @Size(max = 100, message = "Email 最多 100 個字")
        String email,

        @NotBlank(message = "部門不可為空")
        @Size(max = 50, message = "部門最多 50 個字")
        String department
) {
}
