# GET /api/partners/{id}

특정 파트너의 상세 정보를 조회합니다.

## Request

### Path Parameters

| Parameter | Type | Required | Description    |
| --------- | ---- | -------- | -------------- |
| id        | UUID | Yes      | 파트너 고유 ID |

### Headers

```
Authorization: Bearer {accessToken}
```

## Response

### Success (200 OK)

```json
{
  "success": true,
  "data": {
    "id": "01999880-0000-7000-8000-000000000001",
    "partnerName": "AI 디지털교과서 개발사",
    "isActive": true,
    "createdAt": "2025-09-30T12:00:00"
  }
}
```

### Error (404 Not Found)

```json
{
  "success": false,
  "errors": [
    {
      "message": "Partner not found",
      "code": "NOT_FOUND"
    }
  ]
}
```

### Error (401 Unauthorized)

```json
{
  "success": false,
  "errors": [
    {
      "message": "Authentication required",
      "code": "UNAUTHORIZED"
    }
  ]
}
```

## Example

```bash
curl -X GET "http://localhost:8080/api/partners/01999880-0000-7000-8000-000000000001" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```