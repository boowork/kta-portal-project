# Error Responses

Standard error response format for all APIs.

## Standard Error Format

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

### Field Descriptions
- `success`: Always `false`
- `message`: Error summary message (optional)
- `errors`: Error details array
  - `field`: Field name where error occurred (optional)
  - `message`: Error message
  - `code`: Error code
- `timestamp`: Error occurrence time

## HTTP Status Codes

### 400 Bad Request
Request data validation failed

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
Authentication failed

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
Insufficient permissions

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
Resource not found

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
Internal server error

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

## Authentication Related Errors

### Login Failed
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

### User Not Found
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

## Error Code List

| Code | Description |
|------|-------------|
| `VALIDATION_ERROR` | Input data validation failed |
| `UNAUTHORIZED` | Auth token missing or invalid |
| `FORBIDDEN` | Insufficient permissions |
| `NOT_FOUND` | Resource not found |
| `INVALID_CREDENTIALS` | Login credentials error |
| `INVALID_TOKEN` | Refresh token error |
| `INTERNAL_SERVER_ERROR` | Internal server error |

## Client Handling Guide

### JavaScript Example
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
        showError('Access denied.');
        break;
      case 400:
        // Show validation errors
        result.errors.forEach(error => {
          showFieldError(error.field, error.message);
        });
        break;
      default:
        // Show general error
        showError('An error occurred. Please try again.');
    }
    throw new Error(result.errors[0]?.message || 'API Error');
  }
}
```