# Logout API

**POST** `/api/logout`

Invalidates the current user's Refresh Token to perform logout.

## Request

### Headers
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

### Body
None (Empty body)

## Response

### Success (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "Logged out successfully"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

### Already Logged Out (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "Already logged out"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

## Logout Logic

1. **Token Validation**: Extract and validate Access Token from Authorization header
2. **User Identification**: Extract user information from JWT token
3. **Delete Refresh Token**: Remove all Refresh Tokens for the user from database
4. **Return Response**: Return logout success message

## Security Considerations

- Access Token cannot be invalidated on server side, must be deleted on client side
- Refresh Token is deleted from server database preventing reuse
- Returns success response without error even when already logged out

## Usage Examples

### cURL
```bash
curl -X POST http://localhost:8080/api/logout \
  -H "Authorization: Bearer your-access-token-here" \
  -H "Content-Type: application/json"
```

### JavaScript
```javascript
async function logout() {
  const accessToken = localStorage.getItem('accessToken');
  
  try {
    const response = await fetch('/api/logout', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json'
      }
    });

    const result = await response.json();
    if (result.success) {
      // Clear stored tokens
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      
      // Redirect to login page
      window.location.href = '/login';
    }
  } catch (error) {
    console.error('Logout failed:', error);
    // Clear tokens anyway
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }
}
```