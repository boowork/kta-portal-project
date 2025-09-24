# Logout API

**POST** `/api/logout`

현재 사용자의 Refresh Token을 무효화하여 로그아웃을 수행합니다.

## Request

### Headers
```
Authorization: Bearer {accessToken}
Content-Type: application/json
```

### Body
없음 (Empty body)

## Response

### Success (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "Logged out successfully"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

### Already Logged Out (200 OK)
```json
{
  "success": true,
  "data": {
    "message": "Already logged out"
  },
  "timestamp": "2025-01-01T00:00:00"
}
```

## 로그아웃 로직

1. **토큰 검증**: Authorization 헤더에서 Access Token 추출 및 검증
2. **사용자 식별**: JWT 토큰에서 사용자 정보 추출
3. **Refresh Token 삭제**: 해당 사용자의 모든 Refresh Token을 DB에서 삭제
4. **응답 반환**: 로그아웃 성공 메시지 반환

## 보안 고려사항

- Access Token은 서버에서 무효화할 수 없으므로 클라이언트에서 삭제 필요
- Refresh Token은 서버 DB에서 삭제되어 재사용 불가
- 이미 로그아웃된 상태에서도 에러 없이 성공 응답 반환

## 사용 예제

### cURL
```bash
curl -X POST http://localhost:8080/api/logout \
  -H "Authorization: Bearer your-access-token-here" \
  -H "Content-Type: application/json"
```

### JavaScript
```javascript
async function logout() {
  const accessToken = localStorage.getItem('accessToken');
  
  try {
    const response = await fetch('/api/logout', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json'
      }
    });

    const result = await response.json();
    if (result.success) {
      // Clear stored tokens
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      
      // Redirect to login page
      window.location.href = '/login';
    }
  } catch (error) {
    console.error('Logout failed:', error);
    // Clear tokens anyway
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }
}
```