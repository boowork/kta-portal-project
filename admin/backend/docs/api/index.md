# KTA Admin API Documentation

KTA 포털 관리자 백엔드 API 문서입니다.

## 기본 정보

- **Base URL**: `http://localhost:8080`
- **API Version**: v1
- **Content-Type**: `application/json`
- **Framework**: Spring Boot 4.0.0-SNAPSHOT
- **Database**: PostgreSQL
- **Authentication**: Spring Security (기본 설정)

## 공통 응답 형식

모든 API는 다음과 같은 표준화된 응답 형식을 사용합니다:

### 성공 응답
```json
{
  "success": true,
  "data": {
    // 실제 응답 데이터
  },
  "errors": null
}
```

### 에러 응답
```json
{
  "success": false,
  "data": null,
  "errors": [
    {
      "message": "에러 메시지",
      "code": "ERROR_CODE",
      "field": "필드명 (validation 에러시)"
    }
  ]
}
```

## User API

사용자 관리를 위한 CRUD API입니다.

### 1. 모든 사용자 조회

모든 사용자 목록을 조회합니다.

```http
GET /api/v1/users
```

#### 응답 예시
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

### 2. 특정 사용자 조회

ID로 특정 사용자를 조회합니다.

```http
GET /api/v1/users/{id}
```

#### Path Parameters
| 이름 | 타입 | 필수 | 설명 |
|------|------|------|------|
| id | Long | ✓ | 사용자 ID |

#### 응답 예시 (성공)
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

#### 응답 예시 (사용자 없음)
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

### 3. 사용자 생성

새로운 사용자를 생성합니다.

```http
POST /api/v1/users
Content-Type: application/json
```

#### Request Body
```json
{
  "userid": "user123",
  "password": "password123",
  "name": "홍길동",
  "role": "USER"
}
```

#### Request Fields
| 이름 | 타입 | 필수 | 제약사항 | 설명 |
|------|------|------|----------|------|
| userid | String | ✓ | NotBlank | 사용자 ID |
| password | String | ✓ | NotBlank | 비밀번호 |
| name | String | ✓ | NotBlank | 사용자 이름 |
| role | String | ✓ | USER \| ADMIN | 사용자 역할 |

#### 응답 예시 (성공)
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

#### 응답 예시 (중복 사용자 ID)
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

#### 응답 예시 (Validation 에러)
```json
{
  "success": false,
  "data": null,
  "errors": [
    {
      "message": "사용자 ID는 필수입니다",
      "code": "VALIDATION_ERROR",
      "field": "userid"
    },
    {
      "message": "사용자 역할은 USER 또는 ADMIN이어야 합니다",
      "code": "VALIDATION_ERROR",
      "field": "role"
    }
  ]
}
```

### 4. 사용자 수정

기존 사용자 정보를 수정합니다.

```http
PUT /api/v1/users/{id}
Content-Type: application/json
```

#### Path Parameters
| 이름 | 타입 | 필수 | 설명 |
|------|------|------|------|
| id | Long | ✓ | 사용자 ID |

#### Request Body
```json
{
  "name": "수정된 이름",
  "role": "ADMIN"
}
```

#### Request Fields
| 이름 | 타입 | 필수 | 제약사항 | 설명 |
|------|------|------|----------|------|
| name | String | ✗ | - | 사용자 이름 |
| role | String | ✗ | USER \| ADMIN | 사용자 역할 |

**참고**: userid와 password는 수정할 수 없습니다.

#### 응답 예시 (성공)
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

### 5. 사용자 삭제

특정 사용자를 삭제합니다.

```http
DELETE /api/v1/users/{id}
```

#### Path Parameters
| 이름 | 타입 | 필수 | 설명 |
|------|------|------|------|
| id | Long | ✓ | 사용자 ID |

#### 응답 예시 (성공)
```json
{
  "success": true,
  "data": null,
  "errors": null
}
```

## HTTP 상태 코드

| 상태 코드 | 설명 | 사용 케이스 |
|-----------|------|-------------|
| 200 OK | 성공 | 조회, 수정, 삭제 성공 |
| 201 Created | 생성됨 | 사용자 생성 성공 |
| 400 Bad Request | 잘못된 요청 | Validation 에러, 중복 데이터, 리소스 없음 |
| 500 Internal Server Error | 서버 오류 | 예상치 못한 서버 에러 |

## 에러 코드

| 코드 | 설명 | 발생 상황 |
|------|------|-----------|
| VALIDATION_ERROR | 입력 검증 오류 | 필수 필드 누락, 형식 오류 |
| BAD_REQUEST | 잘못된 요청 | 중복 데이터, 비즈니스 로직 오류 |
| NOT_FOUND | 리소스 없음 | 존재하지 않는 사용자 조회/수정/삭제 |
| INTERNAL_SERVER_ERROR | 서버 오류 | 예상치 못한 서버 에러 |

## cURL 예시

### 사용자 생성
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "userid": "testuser",
    "password": "password123",
    "name": "테스트 사용자",
    "role": "USER"
  }'
```

### 모든 사용자 조회
```bash
curl -X GET http://localhost:8080/api/v1/users
```

### 특정 사용자 조회
```bash
curl -X GET http://localhost:8080/api/v1/users/1
```

### 사용자 수정
```bash
curl -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "수정된 이름",
    "role": "ADMIN"
  }'
```

### 사용자 삭제
```bash
curl -X DELETE http://localhost:8080/api/v1/users/1
```


## 버전 정보
- **Version**: 0.2539.8
- **Last Updated**: 2025-09-22
- **Test Coverage**: 100% (10/10 tests passing)