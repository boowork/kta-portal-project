# Login API

**POST** `/api/login`

로그인하여 Access Token과 Refresh Token을 발급받습니다.

## Request

### Headers
```
Content-Type: application/json
```

### Body
```json
{
  "userid": "string",
  "password": "string"
}
```

#### Parameters
- `userid` (string, required): 사용자 로그인 ID
- `password` (string, required): 사용자 패스워드

## Response

### Success (200 OK)
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "uuid-string",
    "userid": "admin",
    "name": "관리자",
    "role": "ADMIN"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

#### Response Fields
- `accessToken`: JWT Access Token (24시간 유효)
- `refreshToken`: UUID 형태의 Refresh Token (30일 유효)
- `userid`: 사용자 로그인 ID
- `name`: 사용자 이름
- `role`: 사용자 권한 (ADMIN, USER)

### Error Responses

#### 400 Bad Request - Validation Error
```json
{
  "success": false,
  "errors": [
    {
      "field": "userid",
      "message": "User ID is required",
      "code": "VALIDATION_ERROR"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

#### 400 Bad Request - Invalid Credentials
```json
{
  "success": false,
  "errors": [
    {
      "field": "",
      "message": "ID 또는 password가 틀립니다",
      "code": "INVALID_CREDENTIALS"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

## 사용 예제

### cURL
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "userid": "admin",
    "password": "admin"
  }'
```

### JavaScript
```javascript
const response = await fetch('/api/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    userid: 'admin',
    password: 'admin'
  })
});

const result = await response.json();
if (result.success) {
  const { accessToken, refreshToken } = result.data;
  // Store tokens for future requests
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('refreshToken', refreshToken);
}
```