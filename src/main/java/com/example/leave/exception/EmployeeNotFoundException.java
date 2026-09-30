package com.example.leave.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("找不到員工，ID：" + id);
    }
}
