# Refresh Token API

**POST** `/api/v1/refresh`

Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.

## Request

### Headers
```
Content-Type: application/json
```

### Body
```json
{
  "refreshToken": "uuid-string"
}
```

#### Parameters
- `refreshToken` (string, required): 로그인시 발급받은 Refresh Token

## Response

### Success (200 OK)
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "new-uuid-string"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

#### Response Fields
- `accessToken`: 새로 발급된 JWT Access Token (24시간 유효)
- `refreshToken`: 새로 발급된 Refresh Token (30일 유효)

### Error Responses

#### 400 Bad Request - Validation Error
```json
{
  "success": false,
  "errors": [
    {
      "field": "refreshToken",
      "message": "Refresh token is required",
      "code": "VALIDATION_ERROR"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

#### 200 OK - Invalid or Expired Token
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

## 토큰 갱신 로직

1. **기존 Refresh Token 삭제**: 사용자의 기존 Refresh Token을 DB에서 삭제
2. **새 토큰 발급**: 새로운 Access Token과 Refresh Token 생성
3. **DB 저장**: 새 Refresh Token을 DB에 저장
4. **응답 반환**: 새로 발급된 토큰들을 클라이언트에 전달

## 사용 예제

### cURL
```bash
curl -X POST http://localhost:8080/api/v1/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "your-refresh-token-here"
  }'
```

### JavaScript
```javascript
async function refreshAccessToken() {
  const refreshToken = localStorage.getItem('refreshToken');
  
  const response = await fetch('/api/v1/refresh', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      refreshToken: refreshToken
    })
  });

  const result = await response.json();
  if (result.success) {
    const { accessToken, refreshToken: newRefreshToken } = result.data;
    // Update stored tokens
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', newRefreshToken);
    return accessToken;
  } else {
    // Refresh token expired, redirect to login
    window.location.href = '/login';
  }
}
```