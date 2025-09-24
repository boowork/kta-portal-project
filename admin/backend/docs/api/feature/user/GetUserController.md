# GET /users/{id}

## 개요
- **엔드포인트**: `GET /api/users/{id}`
- **기능**: 특정 사용자 조회

## 요청
### Path Parameters
| 이름 | 타입 | 필수 | 설명 |
|------|------|------|------|
| id | Long | ✓ | 사용자 ID |

## 응답
### 성공 응답 (200 OK)
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userid": "user123",
    "name": "홍길동",
    "role": "USER",
    "createdAt": "2025-09-22T14:30:00",
    "updatedAt": "2025-09-22T14:30:00"
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

## cURL 예시
```bash
curl -X GET http://localhost:8080/api/users/1
```