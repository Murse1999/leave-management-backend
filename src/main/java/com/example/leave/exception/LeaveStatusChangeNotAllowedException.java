package com.example.leave.exception;

public class LeaveStatusChangeNotAllowedException extends RuntimeException {

    public LeaveStatusChangeNotAllowedException() {
        super("已處理的請假申請不可再次變更狀態");
    }
}