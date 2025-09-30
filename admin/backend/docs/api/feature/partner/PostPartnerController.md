# POST /api/partners

새로운 파트너를 생성합니다.

## Request

### Headers

```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

### Body

```json
{
  "partnerId": "fa1f5e94-7f48-563d-aa6f-a9c975f145f8",
  "partnerName": "AI 디지털교과서 개발사",
  "isActive": true
}
```

### Body Parameters

| Parameter   | Type    | Required | Description              |
| ----------- | ------- | -------- | ------------------------ |
| partnerName | string  | Yes      | 파트너명                 |
| isActive    | boolean | No       | 활성 상태 (기본값: true) |

## Response

### Success (201 Created)

```json
{
  "success": true,
  "data": {
    "id": "01999880-0000-7000-8000-000000000001",
    "partnerId": "fa1f5e94-7f48-563d-aa6f-a9c975f145f8",
    "partnerName": "AI 디지털교과서 개발사",
    "isActive": true,
    "createdAt": "2025-09-30T12:00:00"
  }
}
```

### Error (400 Bad Request)

```json
{
  "success": false,
  "errors": [
    {
      "message": "Partner with this partnerId already exists",
      "code": "BAD_REQUEST"
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
curl -X POST "http://localhost:8080/api/partners" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "partnerId": "fa1f5e94-7f48-563d-aa6f-a9c975f145f8",
    "partnerName": "AI 디지털교과서 개발사",
    "isActive": true
  }'
```