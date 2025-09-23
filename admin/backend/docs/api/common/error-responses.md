# Error Responses

모든 API의 공통 에러 응답 형식입니다.

## 표준 에러 형식

```json
{
  "success": false,
  "message": "Error description",
  "errors": [
    {
      "field": "field_name",
      "message": "Error message",
      "code": "ERROR_CODE"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### 필드 설명
- `success`: 항상 `false`
- `message`: 에러 요약 메시지 (선택적)
- `errors`: 에러 상세 정보 배열
  - `field`: 에러가 발생한 필드명 (선택적)
  - `message`: 에러 메시지
  - `code`: 에러 코드
- `timestamp`: 에러 발생 시간

## HTTP Status Codes

### 400 Bad Request
요청 데이터 검증 실패

```json
{
  "success": false,
  "errors": [
    {
      "field": "userid",
      "message": "User ID is required",
      "code": "VALIDATION_ERROR"
    },
    {
      "field": "password",
      "message": "Password must be at least 8 characters",
      "code": "VALIDATION_ERROR"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### 401 Unauthorized
인증 실패

```json
{
  "success": false,
  "message": "Authentication failed",
  "errors": [
    {
      "field": "Authorization",
      "message": "JWT token is missing or invalid",
      "code": "UNAUTHORIZED"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### 403 Forbidden
권한 부족

```json
{
  "success": false,
  "message": "Access denied",
  "errors": [
    {
      "field": "Authorization",
      "message": "Insufficient privileges to access this resource",
      "code": "FORBIDDEN"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### 404 Not Found
리소스를 찾을 수 없음

```json
{
  "success": false,
  "errors": [
    {
      "field": "id",
      "message": "User not found",
      "code": "NOT_FOUND"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### 500 Internal Server Error
서버 내부 오류

```json
{
  "success": false,
  "errors": [
    {
      "message": "Internal server error",
      "code": "INTERNAL_SERVER_ERROR"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

## 인증 관련 에러

### 로그인 실패
```json
{
  "success": false,
  "errors": [
    {
      "field": "password",
      "message": "Invalid credentials",
      "code": "INVALID_CREDENTIALS"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### 사용자 없음
```json
{
  "success": false,
  "errors": [
    {
      "field": "userid",
      "message": "Invalid credentials",
      "code": "INVALID_CREDENTIALS"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

### Refresh Token 에러
```json
{
  "success": false,
  "errors": [
    {
      "field": "refreshToken",
      "message": "Invalid or expired refresh token",
      "code": "INVALID_TOKEN"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

## 에러 코드 목록

| Code | Description |
|------|-------------|
| `VALIDATION_ERROR` | 입력 데이터 검증 실패 |
| `UNAUTHORIZED` | 인증 토큰 누락 또는 무효 |
| `FORBIDDEN` | 권한 부족 |
| `NOT_FOUND` | 리소스를 찾을 수 없음 |
| `INVALID_CREDENTIALS` | 로그인 정보 오류 |
| `INVALID_TOKEN` | Refresh Token 오류 |
| `INTERNAL_SERVER_ERROR` | 서버 내부 오류 |

## 클라이언트 처리 가이드

### JavaScript 예제
```javascript
async function handleApiResponse(response) {
  const result = await response.json();
  
  if (result.success) {
    return result.data;
  } else {
    // Handle errors
    switch (response.status) {
      case 401:
        // Redirect to login
        window.location.href = '/login';
        break;
      case 403:
        // Show access denied message
        showError('접근 권한이 없습니다.');
        break;
      case 400:
        // Show validation errors
        result.errors.forEach(error => {
          showFieldError(error.field, error.message);
        });
        break;
      default:
        // Show general error
        showError('오류가 발생했습니다. 다시 시도해주세요.');
    }
    throw new Error(result.errors[0]?.message || 'API Error');
  }
}
```