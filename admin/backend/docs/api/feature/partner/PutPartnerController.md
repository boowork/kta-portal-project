# PUT /api/partners/{id}

파트너 정보를 수정합니다.

## Request

### Path Parameters

| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| id        | UUID   | Yes      | 파트너 고유 ID |

### Headers

```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

### Body

```json
{
  "partnerName": "Updated Partner Name",
  "isActive": false
}
```

### Body Parameters

| Parameter    | Type    | Required | Description |
|-------------|---------|----------|-------------|
| partnerName | string  | No       | 파트너명 |
| isActive    | boolean | No       | 활성 상태 |

## Response

### Success (200 OK)

```json
{
  "success": true,
  "data": {
    "id": "01999880-0000-7000-8000-000000000001",
    "partnerId": "fa1f5e94-7f48-563d-aa6f-a9c975f145f8",
    "partnerName": "Updated Partner Name",
    "isActive": false,
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

### Error (403 Forbidden)

```json
{
  "success": false,
  "errors": [
    {
      "message": "Admin role required",
      "code": "FORBIDDEN"
    }
  ]
}
```

## Example

```bash
curl -X PUT "http://localhost:8080/api/partners/01999880-0000-7000-8000-000000000001" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "partnerName": "Updated Partner Name",
    "isActive": false
  }'
```