# GET /api/partners

파트너 목록을 페이징하여 조회합니다.

## Request

### Query Parameters

| Parameter | Type   | Default | Description |
|-----------|--------|---------|-------------|
| page      | int    | 0       | 페이지 번호 (0부터 시작) |
| size      | int    | 20      | 페이지 크기 |
| sortBy    | string | id      | 정렬 필드 |
| sortDir   | string | asc     | 정렬 방향 (asc/desc) |

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
    "content": [
      {
        "id": "01999880-0000-7000-8000-000000000001",
        "partnerId": "fa1f5e94-7f48-563d-aa6f-a9c975f145f8",
        "partnerName": "AI 디지털교과서 개발사",
        "isActive": true,
        "createdAt": "2025-09-30T12:00:00"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 1,
    "totalPages": 1,
    "first": true,
    "last": true
  }
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
curl -X GET "http://localhost:8080/api/partners?page=0&size=10" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```