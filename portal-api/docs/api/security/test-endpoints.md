# Security Test Endpoints

Endpoints for testing security features.

## Public Endpoint

**GET** `/api/security-test/public`

A public endpoint that does not require authentication.

### Request
- Headers: None
- Parameters: None

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

Endpoint that requires USER role.

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

Endpoint that requires ADMIN role.

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
When accessed with USER role:
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

## Permission Test Matrix

| Endpoint | No Auth | USER Role | ADMIN Role |
|----------|---------|-----------|------------|
| `/public` | ✅ 200 | ✅ 200 | ✅ 200 |
| `/user` | ❌ 401 | ✅ 200 | ✅ 200 |
| `/admin` | ❌ 401 | ❌ 403 | ✅ 200 |

## Usage Examples

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