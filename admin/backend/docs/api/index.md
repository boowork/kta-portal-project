# Admin API Documentation

KTA Portal Admin Backend API 문서입니다.

## Authentication
- [JWT Token Structure](authentication/jwt-structure.md) - JWT 토큰 구조 및 서비스 구분
- [Login API](authentication/login.md) - 로그인 및 토큰 발급
- [Refresh Token API](authentication/refresh.md) - 토큰 갱신
- [Logout API](authentication/logout.md) - 로그아웃 및 토큰 무효화

## Security
- [Test Endpoints](security/test-endpoints.md) - 보안 기능 테스트 엔드포인트

## User Management
모든 User API는 USER 권한이 필요하며, `Authorization: Bearer {accessToken}` 헤더가 필요합니다.

- [GetUsersController](feature/user/GetUsersController.md) - 사용자 목록 조회
- [GetUserController](feature/user/GetUserController.md) - 사용자 상세 조회
- [PostUserController](feature/user/PostUserController.md) - 사용자 생성
- [PutUserController](feature/user/PutUserController.md) - 사용자 수정
- [DeleteUserController](feature/user/DeleteUserController.md) - 사용자 삭제

## Common
- [Error Responses](common/error-responses.md) - 공통 에러 응답 형식

## API 개요

### Base URL
```
http://localhost:8080
```

### 인증 방식
- JWT Bearer Token 인증
- Access Token (24시간 유효) + Refresh Token (30일 유효)
- Issuer 기반 서비스 구분 (`kta-portal-admin`)

### 응답 형식
모든 API는 다음과 같은 표준 응답 형식을 사용합니다:

```json
{
  "success": true|false,
  "message": "string (optional)",
  "data": {}, 
  "errors": [],
  "timestamp": "2025-01-01T00:00:00"
}
```