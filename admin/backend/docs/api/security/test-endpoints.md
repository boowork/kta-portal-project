# Security Test Endpoints

보안 기능 테스트를 위한 엔드포인트들입니다.

## Public Endpoint

**GET** `/api/security-test/public`

인증이 필요하지 않은 공개 엔드포인트입니다.

### Request
- Headers: 없음
- Parameters: 없음

### Response (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "This is a public endpoint"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

## User Endpoint

**GET** `/api/security-test/user`

USER 권한이 필요한 엔드포인트입니다.

### Request
```
Authorization: Bearer {accessToken}
```

### Response (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "This endpoint requires USER role"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

### Error Response (401 Unauthorized)
```json
{
  "success": false,
  "message": "Authentication failed",
  "errors": [
    {
      "field": "Authorization",
      "message": "JWT token is missing or invalid"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

## Admin Endpoint

**GET** `/api/security-test/admin`

ADMIN 권한이 필요한 엔드포인트입니다.

### Request
```
Authorization: Bearer {accessToken}
```

### Response (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "This endpoint requires ADMIN role"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

### Error Response (403 Forbidden)
USER 권한으로 접근시:
```json
{
  "success": false,
  "message": "Access denied",
  "errors": [
    {
      "field": "Authorization",
      "message": "Insufficient privileges to access this resource"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

## 권한 테스트 매트릭스

| Endpoint | No Auth | USER Role | ADMIN Role |
|----------|---------|-----------|------------|
| `/public` | ✅ 200 | ✅ 200 | ✅ 200 |
| `/user` | ❌ 401 | ✅ 200 | ✅ 200 |
| `/admin` | ❌ 401 | ❌ 403 | ✅ 200 |

## 사용 예제

### cURL - Public
```bash
curl -X GET http://localhost:8080/api/security-test/public
```

### cURL - User (with token)
```bash
curl -X GET http://localhost:8080/api/security-test/user \
  -H "Authorization: Bearer your-access-token-here"
```

### cURL - Admin (with token)
```bash
curl -X GET http://localhost:8080/api/security-test/admin \
  -H "Authorization: Bearer your-admin-access-token-here"
```