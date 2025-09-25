# JWT Token Structure

## Token Issuer Identification
Four services are distinguished through the `iss` (issuer) claim of JWT tokens:
- `kta-portal-admin`: Token issued by admin portal
- `kta-portal-school`: Token issued by school portal
- `kta-portal-front`: Token issued by general user portal
- `kta-portal-api`: Token issued by API service

## Token Claims
```json
{
  "id": 1,
  "userid": "admin",
  "name": "관리자",
  "iss": "kta-portal-admin",
  "iat": 1737626400,
  "exp": 1737712800
}
```

### Claims Description
- `id`: User unique ID (Long)
- `userid`: Login ID (String)
- `name`: User name (String)
- `iss`: Issuer - for service identification
- `iat`: Issue time (Unix timestamp)
- `exp`: Expiration time (Unix timestamp)

## Token Types
- **Access Token**: Valid for 24 hours (86400000ms)
- **Refresh Token**: Valid for 30 days (2592000000ms)

## Token Security

### Issuer Validation
- All tokens verify issuing service through `iss` claim
- Tokens with issuer other than `kta-portal-admin` are rejected
- Tokens from other services (`kta-portal-school`, `kta-portal-front`, `kta-portal-api`) are blocked

### Token Validation Process
1. JWT signature verification
2. Token expiration time check
3. Issuer claim validation (`kta-portal-admin`)

## Development Authentication

### DEV_AUTH Header
For development convenience, you can use the `DEV_AUTH` header instead of JWT tokens:

**Header Format**: `{id}:{user_id}:{name}`

**Example**:
```
DEV_AUTH: 1:admin:관리자
```

**Usage**:
- Add `DEV_AUTH` header to your requests
- Format: `id:userid:name` separated by colons
- Takes precedence over JWT authentication when present
- Only processes if JWT authentication is not already set