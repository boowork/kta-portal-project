# PUT /users/{id}

## Overview
- **Endpoint**: `PUT /api/users/{id}`
- **Function**: Update user information

## Request
### Path Parameters
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | Long | ✓ | User ID |

### Request Body
```json
{
  "name": "Updated Name",
  "password": "newpassword123"
}
```

### Request Fields
| Name | Type | Required | Constraints | Description |
|------|------|----------|-------------|-------------|
| name | String | ✗ | - | User name |
| password | String | ✗ | - | New password |

**Note**: Only name and password can be modified. userid cannot be changed.

## Response
### Success Response (200 OK)
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userid": "user123",
    "name": "Updated Name",
    "createdAt": "2025-09-22T14:30:00",
    "updatedAt": "2025-09-22T14:35:00"
  },
  "errors": null
}
```

### 에러 응답 (400 Bad Request)
```json
{
  "success": false,
  "data": null,
  "errors": [
    {
      "message": "User not found",
      "code": "NOT_FOUND",
      "field": null
    }
  ]
}
```

## cURL Example
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "DEV_AUTH: 1:admin:관리자" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Name",
    "password": "newpassword123"
  }'
```