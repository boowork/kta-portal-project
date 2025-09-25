# POST /users

## Overview
- **Endpoint**: `POST /api/users`
- **Function**: Create new user

## Request
### Request Body
```json
{
  "userid": "user123",
  "password": "password123",
  "name": "John Doe"
}
```

### Request Fields
| Name | Type | Required | Constraints | Description |
|------|------|----------|-------------|-------------|
| userid | String | ✓ | NotBlank | User ID |
| password | String | ✓ | NotBlank | Password |
| name | String | ✓ | NotBlank | User name |

## Response
### Success Response (201 Created)
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userid": "user123",
    "name": "John Doe",
    "createdAt": "2025-09-22T14:30:00",
    "updatedAt": "2025-09-22T14:30:00"
  },
  "errors": null
}
```

### Error Response (400 Bad Request)
```json
{
  "success": false,
  "data": null,
  "errors": [
    {
      "message": "User with this userid already exists",
      "code": "BAD_REQUEST",
      "field": null
    }
  ]
}
```

## cURL Example
```bash
curl -X POST http://localhost:8080/api/users \
  -H "DEV_AUTH: 1:admin:관리자" \
  -H "Content-Type: application/json" \
  -d '{
    "userid": "testuser",
    "password": "password123",
    "name": "Test User"
  }'
```