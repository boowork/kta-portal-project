# Admin API Documentation

KTA Portal Admin Backend API documentation.

## Authentication
- [JWT Token Structure](authentication/jwt-structure.md) - JWT token structure and service identification
- [Login API](authentication/login.md) - Login and token issuance
- [Refresh Token API](authentication/refresh.md) - Token refresh
- [Logout API](authentication/logout.md) - Logout and token invalidation
- [DEV_AUTH Header](#dev-auth-development-authentication) - Development environment authentication

## Security
- [Test Endpoints](security/test-endpoints.md) - Security feature test endpoints

## User Management
All User APIs require authentication via JWT token (`Authorization: Bearer {accessToken}`) or DEV_AUTH header in development environments.

- [GetUsersController](feature/user/GetUsersController.md) - List users with pagination
- [GetUserController](feature/user/GetUserController.md) - Get user details
- [PostUserController](feature/user/PostUserController.md) - Create user
- [PutUserController](feature/user/PutUserController.md) - Update user
- [DeleteUserController](feature/user/DeleteUserController.md) - Delete user

## Common
- [Error Responses](common/error-responses.md) - Standard error response format

## API Overview

### Base URL
```
http://localhost:8080
```

### Authentication
- **Production**: JWT Bearer Token authentication
  - Access Token (24h valid) + Refresh Token (30d valid)
  - Issuer-based service identification (`kta-portal-admin`)
- **Development**: DEV_AUTH header support (test/dev/local profiles only)
  - Header format: `DEV_AUTH: {id}:{userid}:{name}`
  - No token required for development convenience

### Response Format
All APIs use standard response format:

```json
{
  "success": true|false,
  "message": "string (optional)",
  "data": {}, 
  "errors": [],
  "timestamp": "2025-01-01T00:00:00"
}
```

## DEV_AUTH (Development Authentication)

### Overview
DEV_AUTH is a development-only authentication mechanism that bypasses JWT token validation for convenience during development and testing.

### Activation Conditions
DEV_AUTH is only active when the following Spring profiles are active:
- `test` - Test environment
- `dev` - Development environment
- `local` - Local development environment
- `default` - Default profile (when no profile is specified)

**Important**: DEV_AUTH is automatically disabled in production environments for security.

### Header Format
```
DEV_AUTH: {id}:{userid}:{name}
```

### Examples
```bash
# Admin user authentication
DEV_AUTH: 1:admin:관리자

# Regular user authentication  
DEV_AUTH: 2:user:사용자

# Custom user authentication
DEV_AUTH: 3:testuser:Test User
```

### Usage in cURL
```bash
# Using DEV_AUTH for API calls
curl -X GET \
  -H "DEV_AUTH: 1:admin:관리자" \
  http://localhost:8080/api/users

# Combined with other headers
curl -X POST \
  -H "DEV_AUTH: 1:admin:관리자" \
  -H "Content-Type: application/json" \
  -d '{"userid":"newuser","password":"password","name":"New User"}' \
  http://localhost:8080/api/users
```

### Security Notes
1. **Never use DEV_AUTH in production environments**
2. DEV_AUTH bypasses all JWT token validation
3. Authentication is processed before JWT token validation
4. Invalid DEV_AUTH format is silently ignored and falls back to JWT authentication

### Production Authentication
In production environments (when DEV_AUTH is not available), use JWT tokens:

```bash
# First, login to get access token
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"userid":"admin","password":"admin"}'

# Use the returned access token in subsequent requests
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer {your_access_token_here}"
```

**Note**: All cURL examples in this documentation use DEV_AUTH for development convenience. In production, replace the DEV_AUTH header with the Authorization header containing your JWT token.