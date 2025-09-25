# API Documentation

## Admin API

- [Complete API Documentation](../admin/backend/docs/api/index.md)

### Key Features (v0.2539.11)

#### Authentication System
- **Production**: JWT Bearer Token authentication with service-based identification
- **Development**: DEV_AUTH header support for simplified development workflow
- **Security**: Spring Security integration with role-free authorization

#### User Management API
- **Full CRUD Operations**: Create, read, update, delete users
- **Pagination Support**: Spring Data JDBC with configurable page size and sorting
- **Response Format**: Standardized JSON responses with success/error states

#### Development Features
- **DEV_AUTH Header**: `{id}:{userid}:{name}` format for development convenience
- **Profile-based Activation**: Automatic enablement in test/dev/local environments
- **Test Integration**: Comprehensive TestUtils with common authentication helpers

### API Base URL
```
http://localhost:8080
```

### Quick Start
```bash
# Development authentication (DEV_AUTH)
curl -H "DEV_AUTH: 1:admin:관리자" http://localhost:8080/api/users

# Production authentication (JWT)
curl -H "Authorization: Bearer {token}" http://localhost:8080/api/users
```
