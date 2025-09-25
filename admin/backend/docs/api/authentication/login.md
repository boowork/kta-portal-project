# Login API

**POST** `/api/login`

Login to get Access Token and Refresh Token.

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
- `userid` (string, required): User login ID
- `password` (string, required): User password

## Response

### Success (200 OK)
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "uuid-string",
    "userid": "admin",
    "name": "Administrator",
    "role": "ADMIN"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

#### Response Fields
- `accessToken`: JWT Access Token (24h valid)
- `refreshToken`: UUID format Refresh Token (30d valid)
- `userid`: User login ID
- `name`: User name
- `role`: User role (ADMIN, USER)

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
      "message": "Invalid ID or password",
      "code": "INVALID_CREDENTIALS"
    }
  ],
  "timestamp": "2025-01-01T00:00:00"
}
```

## Usage Examples

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