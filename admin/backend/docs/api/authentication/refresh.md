# Refresh Token API

**POST** `/api/refresh`

Issues a new Access Token using the Refresh Token.

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
- `refreshToken` (string, required): Refresh Token issued during login

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
- `accessToken`: Newly issued JWT Access Token (valid for 24 hours)
- `refreshToken`: Newly issued Refresh Token (valid for 30 days)

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

## Token Refresh Logic

1. **Delete Existing Refresh Token**: Remove user's existing Refresh Token from database
2. **Issue New Tokens**: Generate new Access Token and Refresh Token
3. **Database Storage**: Save new Refresh Token to database
4. **Return Response**: Send newly issued tokens to client

## Usage Examples

### cURL
```bash
curl -X POST http://localhost:8080/api/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "your-refresh-token-here"
  }'
```

### JavaScript
```javascript
async function refreshAccessToken() {
  const refreshToken = localStorage.getItem('refreshToken');
  
  const response = await fetch('/api/refresh', {
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