# Development Rules - KTA Portal Admin Backend

## 프로젝트 개요

**패키지 구조**: `com.kta.aidt.admin` → `com.kta.portal.admin` 리팩토링 완료
**아키텍처**: Single Class All Component 패턴
**버전 관리**: HeadVer 기반 자동화 워크플로우

---

## 아키텍처 규칙

### 1. Single Class All Component 패턴

```
src/main/java/com/kta/portal/admin/feature/api/{domain}/
├── {Method}{Path}Controller.java     # Controller + Service + DAO + DTOs (통합)
├── {기능}Service.java                 # 별도 Service (필요시)
├── {기능}Repository.java             # 도메인별 Repository (필요시)
└── model/                            # 도메인 모델 (RefreshToken 등)

src/main/java/com/kta/portal/admin/feature/repository/
├── {Domain}Repository.java           # 공용 Repository 인터페이스 (UserRepository)
└── model/                            # JPA/JDBC 엔티티 (User)
```

### 2. 컴포넌트 구성 원칙

```java
// 1개 파일에 모든 관련 컴포넌트 정의
@RestController                    // 1. HTTP Controller
@RequiredArgsConstructor
public class GetUsersController {
    private final GetUsersService service;
    
    @GetMapping("/api/users")
    public ResponseEntity<ResponseDto<List<GetUsersHttpResponseDto>>> getAllUsers() {
        return ResponseEntity.ok(ResponseDto.success(service.getAllUsers()));
    }
}

@Service                          // 2. Business Service
@RequiredArgsConstructor 
class GetUsersService { }

@Repository                       // 3. Data Access (필요시)
@RequiredArgsConstructor
class GetUsersDao { }

// 4. HTTP DTOs (Controller 전용)
@Data
class GetUsersHttpRequestDto { }

@Data  
class GetUsersHttpResponseDto { }

// 5. Internal DTOs (Service/DAO 간)
@Data
class GetUsersDaoResponseDto { }
```

---

## 데이터 접근 규칙

### Repository vs DAO 선택 기준

```java
// Repository (정적 SQL) - Spring Data JDBC 사용
interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByRole(String role);
    
    @Query("""
        SELECT u.*, r.role_name 
        FROM users u 
        JOIN roles r ON u.role_id = r.id 
        WHERE u.department = :dept
        """)
    List<UserWithRole> findUsersWithRole(@Param("dept") String department);
}

// DAO (동적 SQL) - JdbcTemplate 사용  
@Repository
class GetUsersDao {
    private final JdbcTemplate jdbcTemplate;
    
    public List<UserReportDto> getDynamicReport(FilterDto filter) {
        StringBuilder sql = new StringBuilder("""
            SELECT u.id, u.name, u.role
            FROM users u 
            WHERE 1=1
            """);
            
        List<Object> params = new ArrayList<>();
        
        if (filter.getRole() != null) {
            sql.append(" AND u.role = ?");
            params.add(filter.getRole());
        }
        
        return jdbcTemplate.query(sql.toString(), params.toArray(), rowMapper);
    }
}
```

### SQL 작성 가이드

```sql
-- 권장: Text Blocks + 구조적 포맷팅
String sql = """
    SELECT 
        u.id,
        u.userid,
        u.name,
        r.role_name
    FROM users u
        LEFT JOIN roles r ON u.role_id = r.id  
    WHERE u.status = 'ACTIVE'
        AND u.created_at >= ?
    ORDER BY u.created_at DESC
    """;

-- 금지: 한 줄 SQL
String sql = "SELECT u.id, u.userid FROM users u WHERE u.status = 'ACTIVE'";
```

---

## 테스트 규칙

### 테스트 철학
- **테스트 코드는 스펙** - 기존 테스트 수정 절대 금지
- **Mock 사용 금지** - TestContainers 실제 DB 테스트만
- **Controller 테스트 필수** - 모든 Controller 1:1 테스트 대응

### 테스트 구조 및 데이터 초기화

```java
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetUsersControllerTest extends BaseIntegrationTest {

    @Test
    void getAllUsers_Success() throws Exception {
        // Given: Text blocks로 테스트 데이터 준비
        insertTestData("""
            INSERT INTO users (id, userid, password, name, role, created_at, updated_at) VALUES 
            (1, 'testuser', '$2a$10$...', '테스트 사용자', 'USER', NOW(), NOW()),
            (2, 'testadmin', '$2a$10$...', '테스트 관리자', 'ADMIN', NOW(), NOW());
            """);
        
        // When & Then
        mockMvc.perform(withAdminAuth(get("/api/users")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }
}
```

### 데이터베이스 초기화 규칙

**핵심 원칙**: 모든 테스트는 독립적이고 재현 가능해야 함

#### 1. 자동 초기화 시스템
```java
// BaseIntegrationTest.java - 모든 테스트 실행 전 자동 실행
@BeforeEach
void setUp() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
        // init.sql 실행: DROP + CREATE + 기본 데이터
        ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/init.sql"));
    }
}
```

#### 2. init.sql 구조 예시
```sql
-- 1. 테이블 삭제 (의존성 순서 고려)
DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- 2. 테이블 생성
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    userid VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- 3. 기본 데이터 (모든 테스트에서 공통으로 필요한 최소 데이터)
INSERT INTO users (id, userid, password, name, role) VALUES 
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUwKkmDso6.', '기본 관리자', 'ADMIN');
```

#### 3. 테스트 데이터 준비 방법

**방법 1: 테스트 클래스 공통 데이터** (권장)
```java
@AutoConfigureMockMvc
public class DeleteUserControllerTest extends BaseIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @BeforeEach
    void initInsert() throws SQLException {
        // 클래스 내 모든 테스트에서 공통으로 사용할 데이터
        insertTestData("""
            INSERT INTO users (userid, password, name, role, created_at, updated_at)
            VALUES ('testguest', '{nohup}password', '테스트 게스트', 'GUEST', now(), now());
            """);
    }
    
    @Test
    void testDeleteUser_WithUserRole() throws Exception {
        // Given: @BeforeEach에서 준비된 데이터 사용
        // When & Then
        mockMvc.perform(withUserAuth(delete("/api/users/1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
```

**방법 2: 개별 테스트 특화 데이터**
```java
@Test 
void testSpecificScenario() throws Exception {
    // Given: 특정 테스트에만 필요한 데이터 추가
    insertTestData("""
        INSERT INTO users (userid, password, name, role) VALUES 
        ('testuser1', '$2a$10$...', '테스트사용자1', 'USER'),
        ('testuser2', '$2a$10$...', '테스트사용자2', 'USER');
        
        INSERT INTO orders (user_id, amount, status) VALUES 
        (2, 10000, 'PENDING'),
        (3, 20000, 'COMPLETED');
        """);
    
    // When & Then: 테스트 로직
}
```

#### 4. 테스트 실행 생명주기
```
매 테스트 메서드 실행 시:
1. BaseIntegrationTest.@BeforeEach: init.sql 실행 (DROP → CREATE → 기본 데이터)
2. 테스트 클래스.@BeforeEach: initInsert() 실행 (공통 테스트 데이터 추가)  
3. 개별 @Test 메서드: insertTestData() 실행 (특화 데이터 추가)
4. 테스트 로직 실행
5. 다음 테스트로 이동 시 1번부터 다시 반복
```

#### 5. 데이터 격리 보장
- **완전한 격리**: 매 테스트마다 `init.sql`로 완전 초기화
- **순서 독립성**: 어떤 순서로 실행해도 동일한 결과
- **상태 무공유**: 이전 테스트 결과가 다음 테스트에 영향 없음
- **병렬 실행 안전성**: TestContainers로 격리된 DB 인스턴스

#### 6. 테스트 데이터 작성 규칙
- **Text Blocks 필수**: `"""` 멀티라인 문자열 사용
- **명시적 타임스탬프**: `now()` 또는 구체적 날짜 명시
- **실제 패스워드**: `{nohup}password` 같은 실제 값 사용 (테스트용)
- **의미있는 데이터**: `test`, `테스트` 등 이해하기 쉬운 값

### Controller 테스트 표준 패턴

**표준화된 Controller 테스트 구조 (추상화된 가이드)**

#### 테스트 클래스 구조
- **클래스명**: `{Method}{Path}ControllerTest`
- **상속**: `BaseIntegrationTest` (DB 초기화 + JWT 헬퍼)
- **어노테이션**: `@AutoConfigureMockMvc`
- **MockMvc**: HTTP 요청 테스트용

#### 데이터 준비 패턴
- **@BeforeEach initInsert()**: 클래스 공통 테스트 데이터
- **Text Blocks 사용**: `insertTestData("""...""")`
- **최소 데이터**: 모든 테스트에서 필요한 기본 데이터만

#### 표준 테스트 케이스 4가지 패턴

**① 인증 테스트** - `test{Method}{Path}_WithoutAuthentication_ShouldReturn401()`
- **목적**: 인증 없는 요청 차단 확인  
- **기대값**: 401 Unauthorized

**② 사용자 권한 테스트** - `test{Method}{Path}_WithUserRole()`
- **목적**: 일반 사용자 권한으로 정상 동작 확인
- **기대값**: 200 OK + success: true

**③ 관리자 권한 테스트** - `test{Method}{Path}_WithAdminRole()`  
- **목적**: 관리자 권한으로 정상 동작 확인
- **기대값**: 200 OK + success: true

**④ 실패 케이스 테스트** - `test{Method}{Path}NotFound_WithUserRole()`
- **목적**: 리소스 없음 등 실패 상황 처리 확인
- **기대값**: 400 Bad Request + success: false + error code

### 테스트 패턴 구성 요소

#### 1. 기본 구조
- `@AutoConfigureMockMvc`: MockMvc 자동 설정
- `extends BaseIntegrationTest`: DB 초기화 및 JWT 헬퍼 메서드 상속
- `@Autowired MockMvc mockMvc`: HTTP 요청 테스트용

#### 2. 데이터 준비 패턴
```java
@BeforeEach
void initInsert() throws SQLException {
    // 모든 테스트에서 공통으로 사용할 최소 데이터
    insertTestData("""
        INSERT INTO {table} ({columns})
        VALUES ({values});
        """);
}
```

#### 3. 핵심 테스트 도구
- **MockMvc**: HTTP 요청 시뮬레이션  
- **JWT 헬퍼**: `withUserAuth()`, `withAdminAuth()` - BaseIntegrationTest 제공
- **JSON 검증**: `jsonPath()` - 응답 데이터 검증
- **상태 코드**: `status().isOk()`, `status().isBadRequest()` 등

#### 4. 테스트 네이밍 규칙
- **메서드명**: `test{Method}{Path}_{조건}_{기대결과}()`
- **예시**: `testDeleteUser_WithUserRole()`, `testDeleteUserNotFound_WithUserRole()`
- **조건**: `WithoutAuthentication`, `WithUserRole`, `WithAdminRole`
- **결과**: `ShouldReturn401`, 생략 가능 (성공 케이스), `NotFound` (실패 케이스)

#### 4. 검증 패턴
- **성공 응답**: `status().isOk()` + `jsonPath("$.success").value(true)`
- **실패 응답**: `status().isBadRequest()` + `jsonPath("$.success").value(false)`
- **에러 코드**: `jsonPath("$.errors[0].code").value("ERROR_CODE")`
- **Content-Type**: `content().contentType(MediaType.APPLICATION_JSON)`

#### 5. 실제 적용 예시
**DeleteUserControllerTest 패턴 적용**
- 클래스명: `DeleteUserControllerTest`
- 4가지 표준 테스트: 인증없음(401) + 사용자권한(200) + 관리자권한(200) + NotFound(400)
- 공통 데이터: `@BeforeEach`에서 테스트용 사용자 데이터 삽입
- 검증 패턴: HTTP 상태코드 + JSON 응답 구조 + 에러코드 확인

---

## 응답 표준

### ResponseDto 구조

```java
// 성공 응답
{
  "success": true,
  "data": { /* 실제 데이터 */ },
  "errors": null
}

// 실패 응답  
{
  "success": false,
  "data": null,
  "errors": [
    {
      "message": "User ID is required",
      "code": "INVALID_INPUT", 
      "field": "userid"
    }
  ]
}
```

### 사용 예시

```java
// 성공
return ResponseDto.success(responseData);

// 실패
return ResponseDto.error(Collections.singletonList(
    ErrorDetail.builder()
        .field("userid")
        .message("User not found")
        .code("USER_NOT_FOUND")
        .build()
));
```

---

## 문서화 규칙

### API 문서 구조

```
docs/api/feature/{domain}/
└── {Method}{Path}Controller.md  # Controller당 1개 문서
```

### 문서 템플릿

```markdown
# GET /api/users - GetUsersController

## 개요
전체 사용자 목록을 조회합니다.

## Request
- Method: GET
- Path: /api/users  
- Headers: Authorization: Bearer {token}

## Response
### 성공 (200)
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "userid": "admin",
      "name": "관리자",
      "role": "ADMIN"
    }
  ]
}
```

### 실패 (400)
- 인증 실패, 권한 없음 등
```

---

## 개발 워크플로우

### 1. 개발 시작
```bash
./new.sh  # HeadVer 증가 + 워크트리 생성 + 문서 구조 자동 생성
```

### 2. 개발 완료  
```bash
# close.md 시퀀스 실행
gh issue create --title "기능명 구현 완료"
git add . && git commit -m "이슈번호: 메시지" && git push origin 브랜치
gh pr create --title "이슈번호: PR 제목" --base dev  
gh pr merge PR번호 --merge
cd dev_worktree && git pull origin dev
```

---

## 코딩 규칙

### 1. KISS, DRY, YAGNI 원칙
- **단순함 유지**: 복잡한 패턴 피하기
- **중복 제거**: 공통 로직은 유틸리티로 분리
- **필요한 것만**: 미래를 위한 과도한 추상화 금지

### 2. 네이밍 규칙
```java
// Controller
{Method}{Path}Controller        // GetUsersController, PostLoginController

// Service  
{Method}{Path}Service          // GetUsersService, PostLoginService

// DAO/Repository
{Method}{Path}Dao              // GetUsersDao (동적 SQL)
{Domain}Repository             // UserRepository (정적 SQL)

// DTOs
{Method}{Path}HttpRequestDto   // HTTP 요청 DTO
{Method}{Path}HttpResponseDto  // HTTP 응답 DTO  
{Method}{Path}DaoResponseDto   // DAO 결과 DTO
```

### 3. 접근 제한자 규칙

**기본 원칙**: 최소 권한 원칙 - 필요한 최소한의 접근성만 부여

#### 접근 제한자 매트릭스

| 컴포넌트 유형 | 접근 제한자 | 사용 이유 |
|---|---|---|
| **Controller** | `public` | HTTP 엔드포인트, Spring Bean 등록 |
| **Service** | `package-private` | 내부 구현, 패키지 내부에서만 사용 |
| **DAO** | `package-private` | 데이터 접근 계층, 외부 노출 불필요 |
| **공용 Repository** | `public` | 여러 패키지에서 접근 (UserRepository) |
| **도메인별 Repository** | `package-private` | 특정 패키지 내부에서만 사용 |
| **HTTP DTOs** | `package-private` | Controller 내부 클래스 |
| **Internal DTOs** | `package-private` | Service/DAO 간 데이터 전송 |

#### 결정 기준

**public 사용 조건**
- Spring Bean으로 다른 패키지에서 주입받아야 함
- 외부 API로 노출되어야 함
- 공통 컴포넌트로 여러 도메인에서 사용됨

**package-private 사용 조건** (기본값)
- Single Class All Component 패턴의 내부 구현
- 같은 패키지 내에서만 사용
- 외부에 노출할 필요 없음

**private 사용 조건**
- 클래스 내부 헬퍼 메서드
- 클래스 내부 필드

#### 실제 적용 패턴
- **Controller**: 항상 `public` - 웹 계층 노출 필요
- **Service/DAO**: 기본적으로 `package-private` - 내부 구현
- **Repository**: 위치에 따라 결정
  - `feature/repository/`: `public` (공용)
  - `feature/api/{domain}/`: `package-private` (도메인별)
- **DTOs**: 항상 `package-private` - 내부 데이터 전송


## 패키지 구조

### Production 코드 구조

```
src/main/java/com/kta/portal/admin/
├── Application.java                              # 메인 애플리케이션
├── config/                                       # 설정 클래스
│   ├── {기능}Configuration.java                    # AuthConfiguration, DataConfiguration, etc.
│   └── SecurityConfig.java
├── dto/                                          # 공통 DTO
│   ├── ResponseDto.java
│   └── ErrorDetail.java
├── exception/                                    # 예외 처리
│   ├── GlobalExceptionHandler.java
│   └── {Exception타입}Exception.java                # BadRequestException, etc.
├── feature/api/{domain}/                         # API 기능 (도메인별)
│   ├── {Method}{Path}Controller.java              # PostLoginController, GetUsersController, etc.
│   ├── {기능}Service.java                          # RefreshTokenService (필요시)
│   ├── {기능}Repository.java                       # RefreshTokenRepository (도메인별, 필요시)
│   └── model/{Model}.java                        # RefreshToken (도메인 모델)
├── feature/repository/                           # 공용 Repository
│   ├── {Domain}Repository.java                   # UserRepository (공용)
│   └── model/{Entity}.java                       # User (JPA/JDBC 엔티티)
└── security/                                     # 보안 관련
    ├── Jwt{기능}.java                             # JwtTokenProvider, JwtAuthenticationFilter, etc.
    └── {Security기능}.java
```

### Test 코드 구조

```
src/test/java/com/kta/portal/admin/
├── BaseIntegrationTest.java                      # 테스트 기반 클래스
├── TestContainerConfiguration.java               # TestContainers 설정
├── annotation/                                   # 테스트 어노테이션
│   └── TestDataSet.java
├── support/                                      # 테스트 지원 클래스
│   └── TestDataManager.java
├── feature/api/{domain}/                         # API 테스트 (도메인별)
│   └── {Method}{Path}ControllerTest.java          # DeleteUserControllerTest, GetUsersControllerTest, etc.
└── security/                                     # 보안 테스트
    └── {Security기능}Test.java                     # JwtTokenProviderTest, etc.
```

### Test Resources 구조

```
src/test/resources/
├── application-test.properties                   # 테스트 설정
├── db/
│   ├── init.sql                                  # 테이블 DROP + CREATE + 기본 데이터
│   ├── schema.sql                                # 스키마만 (선택적)
│   └── clean.sql                                 # 정리 스크립트 (선택적)
└── datasets/                                     # 테스트 데이터셋 (사용하지 않음)
    └── {test-name}-dataset.sql
```

### 패키지 네이밍 패턴

#### Production 코드
- **Controller**: `{Method}{Path}Controller` (예: `PostLoginController`, `GetUsersController`)
- **Service**: `{기능}Service` (예: `RefreshTokenService`) - 필요시 도메인별 분리
- **Repository**: 
  - 공용: `{Domain}Repository` (예: `UserRepository`) - `feature/repository/`
  - 도메인별: `{기능}Repository` (예: `RefreshTokenRepository`) - `feature/api/{domain}/`
- **DTO**: `{Method}{Path}Http{Request|Response}Dto` (Controller 내부 클래스)
- **Model**: 
  - 엔티티: `{Entity}` (예: `User`) - `feature/repository/model/`
  - 도메인 모델: `{Model}` (예: `RefreshToken`) - `feature/api/{domain}/model/`
- **Exception**: `{타입}Exception` (예: `BadRequestException`)

#### Test 코드
- **Controller Test**: `{Method}{Path}ControllerTest` (예: `DeleteUserControllerTest`)
- **Service Test**: `{Service명}Test`
- **Integration Test**: `BaseIntegrationTest` 상속
- **Test Method**: `test{Action}_{조건}_{기대결과}()`

### 파일 위치 규칙

#### 1. 도메인별 분리
```
feature/api/auth/     # 인증 관련 (login, logout, refresh + RefreshTokenService/Repository)
feature/api/user/     # 사용자 관리 (CRUD only - Repository는 공용 사용)
feature/api/admin/    # 관리자 전용
```

#### 2. Repository 위치 규칙
```
# 공용 Repository (여러 도메인에서 사용)
feature/repository/UserRepository.java     # User CRUD, 여러 Controller에서 사용
feature/repository/model/User.java         # JPA/JDBC 엔티티

# 도메인별 Repository (특정 도메인에서만 사용)  
feature/api/auth/RefreshTokenRepository.java  # 인증 도메인 전용
feature/api/auth/model/RefreshToken.java       # 도메인 모델
```

#### 3. 기능별 응집 (Single Class All Component)
```
# user 도메인 - 공용 Repository 사용
feature/api/user/
├── GetUsersController.java      # Controller + Service + DAO + DTOs
├── GetUserController.java       # Controller + Service + DAO + DTOs
├── PostUserController.java      # Controller + Service + DAO + DTOs
├── PutUserController.java       # Controller + Service + DAO + DTOs
└── DeleteUserController.java    # Controller + Service + DAO + DTOs

# auth 도메인 - 도메인별 Repository 포함
feature/api/auth/
├── PostLoginController.java     # Controller + Service + DAO + DTOs
├── PostLogoutController.java    # Controller + Service + DAO + DTOs
├── PostRefreshController.java   # Controller + Service + DAO + DTOs
├── RefreshTokenService.java     # 별도 Service (복잡한 로직)
├── RefreshTokenRepository.java  # 도메인 전용 Repository
└── model/RefreshToken.java      # 도메인 모델
```

#### 4. 테스트 미러링
```
# Production 구조와 동일하게 미러링
src/main/.../feature/api/user/DeleteUserController.java
src/test/.../feature/api/user/DeleteUserControllerTest.java
```
