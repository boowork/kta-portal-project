# PUT /users/{id}

## 개요
- **엔드포인트**: `PUT /api/users/{id}`
- **기능**: 사용자 정보 수정

## 요청
### Path Parameters
| 이름 | 타입 | 필수 | 설명 |
|------|------|------|------|
| id | Long | ✓ | 사용자 ID |

### Request Body
```json
{
  "name": "수정된 이름",
  "role": "ADMIN"
}
```

### Request Fields
| 이름 | 타입 | 필수 | 제약사항 | 설명 |
|------|------|------|----------|------|
| name | String | ✗ | - | 사용자 이름 |
| role | String | ✗ | USER \| ADMIN | 사용자 역할 |

**참고**: userid와 password는 수정할 수 없습니다.

## 응답
### 성공 응답 (200 OK)
```json
{
  "success": true,
  "data": {
    "id": 1,
    "userid": "user123",
    "name": "수정된 이름",
    "role": "ADMIN",
    "createdAt": "2025-09-22T14:30:00",
    "updatedAt": "2025-09-22T14:35:00"
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
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "수정된 이름",
    "role": "ADMIN"
  }'
```