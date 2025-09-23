# JWT Token Structure

## Token Issuer 구분
JWT 토큰의 `iss` (issuer) 클레임을 통해 4개 서비스를 구분합니다:
- `kta-portal-admin`: 관리자 포털에서 발급된 토큰
- `kta-portal-school`: 학교 포털에서 발급된 토큰  
- `kta-portal-front`: 일반 사용자 포털에서 발급된 토큰
- `kta-portal-api`: API 서비스에서 발급된 토큰

## Token Claims
```json
{
  "id": 1,
  "userid": "admin",
  "name": "관리자",
  "role": "ADMIN",
  "iss": "kta-portal-admin",
  "iat": 1737626400,
  "exp": 1737712800
}
```

### Claims 설명
- `id`: 사용자 고유 ID (Long)
- `userid`: 로그인 ID (String)
- `name`: 사용자 이름 (String)
- `role`: 사용자 권한 (ADMIN, USER)
- `iss`: 발급자 - 서비스 구분용
- `iat`: 발급 시간 (Unix timestamp)
- `exp`: 만료 시간 (Unix timestamp)

## Token Types
- **Access Token**: 24시간 유효 (86400000ms)
- **Refresh Token**: 30일 유효 (2592000000ms)

## Token Security

### Issuer Validation
- 모든 토큰은 `iss` 클레임으로 발급 서비스를 확인
- `kta-portal-admin` 이외의 issuer 토큰은 거부
- 다른 서비스(`kta-portal-school`, `kta-portal-front`, `kta-portal-api`) 토큰 차단

### Token Validation Process
1. JWT 서명 검증
2. 토큰 만료 시간 확인
3. Issuer 클레임 검증 (`kta-portal-admin`)
4. 사용자 권한 확인