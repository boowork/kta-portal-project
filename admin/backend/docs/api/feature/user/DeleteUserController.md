# DELETE /users/{id}

## Overview
- **Endpoint**: `DELETE /api/users/{id}`
- **Function**: Delete user

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
  "data": null,
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
curl -X DELETE http://localhost:8080/api/users/1 \
  -H "DEV_AUTH: 1:admin:관리자"
```