# GET /users/{id}

## Overview
- **Endpoint**: `GET /api/users/{id}`
- **Function**: Get specific user details

## Request
### Path Parameters
| Name | Type | Required | Description |
|------|------|----------|-------------|
| id | Long | ✓ | User ID |

## Response
### Success Response (200 OK)
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
      "message": "User not found",
      "code": "NOT_FOUND",
      "field": null
    }
  ]
}
```

## cURL Example
```bash
curl -X GET \
  -H "DEV_AUTH: 1:admin:관리자" \
  http://localhost:8080/api/users/1
```