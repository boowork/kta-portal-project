# GET /users

## 개요
- **엔드포인트**: `GET /api/users`
- **기능**: 모든 사용자 목록 조회

## 요청
### Parameters
없음

## 응답
### 성공 응답 (200 OK)
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "userid": "user123",
      "name": "홍길동",
      "role": "USER",
      "createdAt": "2025-09-22T14:30:00",
      "updatedAt": "2025-09-22T14:30:00"
    }
  ],
  "errors": null
}
```

## cURL 예시
```bash
curl -X GET http://localhost:8080/api/users
```