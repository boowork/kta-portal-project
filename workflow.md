# Development Workflow

HeadVer 기반 자동화된 개발 사이클과 Single Class All Component 아키텍처.

## 핵심 워크플로우

### 1. 개발 사이클 시작
```bash
./new.sh  # HeadVer 증가 + 워크트리 생성 + 문서 구조 자동 생성
```

### 2. 개발 완료
```bash
# close.md 시퀀스
gh issue create --title "기능명 구현 완료"
git add . && git commit -m "이슈번호: 메시지" && git push origin 브랜치
gh pr create --title "이슈번호: PR 제목" --base dev
gh pr merge PR번호 --merge
cd dev_worktree && git pull origin dev
```

## Single Class All Component 아키텍처

### 구조
```
feature/{domain}/
├── model/                            # 공유 모델
├── {Method}{Path}Controller.java     # Controller + Service + DAO + DTOs
└── {Method}{Path}ControllerTest.java # 1:1 대응 테스트
```

### 패턴
```java
// GetUsersController.java
@RestController
public class GetUsersController { /* Controller 구현 */ }

@Service  
class GetUsersService { /* Service 구현 */ }

@Repository
class GetUsersDao { /* DAO 구현 */ }

// 고유 DTO 명명으로 중복 방지
class GetUsersHttpRequestDto { }
class GetUsersHttpResponseDto { }
class GetUsersDaoRequestDto { }
class GetUsersDaoResponseDto { }
```

## 핵심 개발 규칙

### 테스트 철학
- **테스트 코드는 스펙** - 기존 테스트 수정 절대 금지
- **Mock 사용 금지** - TestContainers 실제 DB 테스트만
- **Controller 테스트 필수** - 모든 Controller 1:1 테스트 대응

### 코드 규칙
- **KISS, DRY, YAGNI** 엄격 준수
- **커밋 메시지에 bot/anthropic 정보 포함 금지**
- **Non-public Classes** - Spring Bean 등록용

## 문서화

### 구조 동기화
```
docs/api/feature/{domain}/
└── {Method}{Path}Controller.md  # Controller당 1개 문서
```

### 응답 표준
```json
{
  "success": boolean,
  "data": any,
  "errors": [{"message": "string", "code": "string", "field": "string?"}]
}
```

## 자동화 스크립트

- **new.sh**: 개발 사이클 초기화 (HeadVer + 워크트리 + 문서)
- **close.md**: 개발 완료 시퀀스 (이슈 + 커밋 + PR + 병합)
- **versions/header.py**: HeadVer 자동 관리

## 핵심 가치

**재현 가능성** + **추적성** + **자동화** = 안정적이고 효율적인 개발