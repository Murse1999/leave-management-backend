package com.example.leave.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全域錯誤處理器。
 *
 * 白話說：Controller 或 Service 發生例外時，錯誤會一路往外丟到這裡。
 * 這裡負責把 Java 的例外，轉成前端看得懂的 HTTP 回應。
 *
 * 這樣做的好處是：
 * 1. 每個 Controller 不需要重複寫 try-catch。
 * 2. 前端收到的錯誤格式比較一致。
 * 3. 不會把後端的完整錯誤堆疊和程式路徑直接暴露給前端。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 處理「找不到員工」的例外。
     *
     * EmployeeService 找不到資料時會執行：
     * throw new EmployeeNotFoundException(id);
     *
     * 這裡接住例外，回傳 HTTP 404，代表請求的資料不存在。
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFound(
            EmployeeNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }

    /**
     * 處理 DTO 驗證失敗。
     *
     * Controller 使用 @Valid 時，如果 EmployeeRequest 裡的
     * @NotBlank、@Email 或 @Size 驗證失敗，Spring 會拋出
     * MethodArgumentNotValidException。
     *
     * 這裡把錯誤整理成前端容易使用的格式，例如：
     * {
     *   "message": "資料格式錯誤",
     *   "errors": {
     *     "name": "姓名不可為空",
     *     "email": "Email 格式不正確"
     *   }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        // 用欄位名稱當作 key，保存每個欄位的錯誤訊息。
        // LinkedHashMap 會保留插入順序，回傳結果比較容易閱讀。
        Map<String, String> errors = new LinkedHashMap<>();

        // 取得所有驗證失敗的欄位，整理成前端可以使用的格式。
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        // 建立統一的錯誤回應內容。
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "資料格式錯誤");
        body.put("errors", errors);

        // HTTP 400 代表前端送來的資料格式不正確。
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(LeaveNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLeaveNotFound(
            LeaveNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(LeaveStatusChangeNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleLeaveStatusChangeNotAllowed(
            LeaveStatusChangeNotAllowedException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
}
