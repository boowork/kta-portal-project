# DELETE /users/{id}

## 개요
- **엔드포인트**: `DELETE /api/users/{id}`
- **기능**: 사용자 삭제

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
  "data": null,
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
curl -X DELETE http://localhost:8080/api/users/1
```