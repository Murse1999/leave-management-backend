# Leave Management Backend

使用 Spring Boot 與 MySQL 建置的請假管理系統後端 REST API。系統提供員工資料管理、請假申請、請假狀態審核，以及依員工、狀態與假別查詢請假紀錄的功能。

## Features

- 員工資料新增、查詢、修改、刪除
- 請假申請新增、查詢、刪除
- 請假狀態管理：`PENDING`、`APPROVED`、`REJECTED`
- 請假類型管理：`ANNUAL`、`SICK`、`PERSONAL`
- 依員工、請假狀態與請假類型篩選紀錄
- 請假天數自動計算，起訖日皆包含在內
- 使用 Bean Validation 驗證請求資料
- 統一例外處理，提供一致的錯誤回應格式

## Tech Stack

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL
- Gradle

## Project Structure

```text
src/main/java/com/example/leave
├── controller    # REST API 路由
├── dto           # 請求與回應資料模型
├── entity        # JPA Entity
├── enums         # 請假狀態與假別列舉
├── exception     # 自訂例外與全域錯誤處理
├── repository    # 資料庫存取層
└── service       # 商業邏輯
```

## Prerequisites

- JDK 21
- MySQL 8.0 以上

## Database Setup

先在 MySQL 建立資料庫與應用程式帳號：

```sql
CREATE DATABASE leave_management
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER 'leave_app'@'localhost' IDENTIFIED BY 'your-strong-password';
GRANT ALL PRIVILEGES ON leave_management.* TO 'leave_app'@'localhost';
FLUSH PRIVILEGES;
```

請使用自己的高強度密碼，且不要把密碼提交到 Git。

## Configuration

資料庫連線設定位於 `src/main/resources/application.properties`。帳號與密碼透過環境變數提供：

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

在 IntelliJ Run Configuration 的 Environment variables 設定：

```text
DB_USERNAME=leave_app;DB_PASSWORD=your-strong-password
```

## Run the Application

在專案根目錄執行：

```bash
export DB_USERNAME=leave_app
export DB_PASSWORD=your-strong-password
./gradlew bootRun
```

應用程式啟動後，服務位址為：

```text
http://localhost:8080
```

## API Endpoints

### Employee API

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/employees` | 新增員工 |
| `GET` | `/api/employees` | 取得全部員工 |
| `GET` | `/api/employees/{id}` | 依 ID 取得員工 |
| `PUT` | `/api/employees/{id}` | 修改員工 |
| `DELETE` | `/api/employees/{id}` | 刪除員工 |

#### Create Employee

```http
POST /api/employees
Content-Type: application/json
```

```json
{
  "name": "王小明",
  "email": "ming.wang@example.com",
  "department": "Engineering"
}
```

### Leave API

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/leaves` | 新增請假申請 |
| `GET` | `/api/leaves` | 取得全部請假紀錄 |
| `GET` | `/api/leaves/{leaveId}` | 依 ID 取得請假紀錄 |
| `PATCH` | `/api/leaves/{leaveId}/status` | 更新請假狀態 |
| `DELETE` | `/api/leaves/{leaveId}` | 刪除請假紀錄 |
| `GET` | `/api/leaves/employee/{employeeId}` | 查詢指定員工的請假紀錄 |
| `GET` | `/api/leaves/status/{status}` | 依請假狀態查詢 |
| `GET` | `/api/leaves/type/{leaveType}` | 依請假類型查詢 |

#### Create Leave Request

```http
POST /api/leaves
Content-Type: application/json
```

```json
{
  "employeeId": 1,
  "startDate": "2026-10-01",
  "endDate": "2026-10-03",
  "leaveType": "ANNUAL",
  "reason": "Personal travel"
}
```

成功建立後，系統會回傳 `201 Created`。新申請的預設狀態為 `PENDING`。

#### Update Leave Status

```http
PATCH /api/leaves/1/status
Content-Type: application/json
```

```json
{
  "status": "APPROVED"
}
```

只有狀態為 `PENDING` 的請假申請可以變更狀態。

#### Leave Response Example

```json
{
  "id": 1,
  "employeeId": 1,
  "employeeName": "王小明",
  "startDate": "2026-10-01",
  "endDate": "2026-10-03",
  "leaveDays": 3,
  "reason": "Personal travel",
  "leaveType": "ANNUAL",
  "status": "PENDING"
}
```

## Validation Rules

### Employee

- `name`：必填，最多 50 個字
- `email`：必填，必須符合 Email 格式，最多 100 個字
- `department`：必填，最多 50 個字

### Leave Request

- `employeeId`：必填
- `startDate`：必填
- `endDate`：必填，且不可早於 `startDate`
- `leaveType`：必填，可使用 `ANNUAL`、`SICK`、`PERSONAL`
- `reason`：必填，最多 500 個字

## Error Response

資料驗證失敗時，系統回傳 `400 Bad Request`：

```json
{
  "message": "資料格式錯誤",
  "errors": {
    "email": "Email 格式不正確"
  }
}
```

找不到員工或請假紀錄時，系統回傳 `404 Not Found`：

```json
{
  "message": "找不到員工，ID：1"
}
```
