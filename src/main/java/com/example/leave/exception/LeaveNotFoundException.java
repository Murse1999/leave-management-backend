package com.example.leave.exception;

/**
 * 當程式依照請假 ID 查詢資料，但資料庫中沒有該筆請假時使用的例外。
 *
 * Service 丟出這個例外後，GlobalExceptionHandler 會接住它，
 * 並回傳 HTTP 404，讓前端知道「請假資料不存在」。
 */
public class LeaveNotFoundException extends RuntimeException {

    /**
     * 建立例外物件時接收找不到的請假 ID。
     *
     * @param leaveId 前端或程式要查詢的請假資料 ID
     */
    public LeaveNotFoundException(Long leaveId) {
        // super(...) 是呼叫 RuntimeException 的建構子，把錯誤訊息存進例外物件。
        // 之後可透過 exception.getMessage() 取得這段文字。
        super("找不到請假資料，ID：" + leaveId);
    }

}
