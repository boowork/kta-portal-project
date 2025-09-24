# POST /users

## 개요
- **엔드포인트**: `POST /api/users`
- **기능**: 새 사용자 생성

## 요청
### Request Body
```json
{
  "userid": "user123",
  "password": "password123",
  "name": "홍길동",
  "role": "USER"
}
```

### Request Fields
| 이름 | 타입 | 필수 | 제약사항 | 설명 |
|------|------|------|----------|------|
| userid | String | ✓ | NotBlank | 사용자 ID |
| password | String | ✓ | NotBlank | 비밀번호 |
| name | String | ✓ | NotBlank | 사용자 이름 |
| role | String | ✓ | USER \| ADMIN | 사용자 역할 |

## 응답
### 성공 응답 (201 Created)
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
      "message": "User with this userid already exists",
      "code": "BAD_REQUEST",
      "field": null
    }
  ]
}
```

## cURL 예시
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "userid": "testuser",
    "password": "password123",
    "name": "테스트 사용자",
    "role": "USER"
  }'
```