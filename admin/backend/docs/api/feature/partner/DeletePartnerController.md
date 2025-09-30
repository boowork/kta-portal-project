# DELETE /api/partners/{id}

파트너를 삭제합니다.

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
  "data": null
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
curl -X DELETE "http://localhost:8080/api/partners/01999880-0000-7000-8000-000000000001" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```