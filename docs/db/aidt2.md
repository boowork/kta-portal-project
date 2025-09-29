## 4.2 학생 인증 API
- AI 디지털교과서 사용자 정보 요청(학생) 세부 정보를 조회할 수 있는 API를 제공합니다.

| 순서 | 설명 |
|------|------|
| 1~2 | 학생 성명 조회 |
| 3~4 | 학생 학교 명 조회 |
| 5~6 | 학생 학교 ID 조회 |
| 7~8 | 학생 학교 구분 조회 |
| 9~10 | 학생 학년 조회 |
| 11~12 | 학생 반 조회 |
| 13~14 | 학생 번호 조회 |
| 15~16 | 학생 시간표 조회 |
| 17~18 | 학생 학급 구성원 조회 |
| 19~20 | 학생 성별 조회 |
| 21~22 | 학생 정보 일괄 조회 |
| 23~24 | 학생 개설과목 정보 조회 |
| 25~26 | 학생 개설과목 목록 조회 |

### 1) 학생 성명 조회

#### ① 기본 정보

| 항목 | 내용 |
|------|------|
| ID | API_AUTH_002 |
| 항목명 | 학생 성명 조회 |
| I/F 방식 및 전송방식 | REST API / GET, POST |
| 주기 | 대화식 처리 |
| 설명 | 사용자 식별 ID를 통한 학생 성명 조회 |
| 송수신시스템 | AIDT → AIDT 플랫폼 |
| 형식 | api_domain + /aidt_userinfo/student/name |

#### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| header | Partner-ID | 연동 인증키 | O | string(36) | |
| | API-version | API 버전 | O | string(10) | |
| | access_token | SSO 토큰 | O | - | |
| | token | 인증토큰 | O | string(가변) | |
| body | access_id | 로그인 사용자 ID | O | string(가변) | |
| | user_id | 조회 대상 사용자 ID | O | string(36) | user_id, user_ids 중 하나는 필수 |
| | user_ids | 조회 대상 사용자 리스트 | △ | list(string) | |

#### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| status | code | HTTP 규약에 따른 상태코드<br>■ 200 : OK, API가 request에 대한 처리를 수행하는 하위<br>API의 수행결과는 body의 code와 message를 반영함<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | O | - | 5. 오류코드 참조 |
| | code | 응답 코드 | O | - | 6. 응답코드 참조 |
| | message | 응답 메시지 | O | - | |
| body | user_name | 사용자 명 | △ | string(128) | 조회 항목에 따라 user_name 또는 user_list 반환 |
| | user_list | 사용자 리스트 | △ | list(1...n) | |
| | user_id | 사용자 ID | △ | string(36) | user_ids 작성 시 반환 |
| | user_name | 사용자 명 | △ | string(128) | |
| | api_version | API 버전 | O | string(10) | |

#### ④ 호출 예시(Request)

**header**
```json
{
  "Partner-ID": "550e8400-e29b-41d4-a716-446655440FFFF",
  "API-version": "2.4"
}
```

**GET method는 파라미터를 URL에 추가하여 호출**

단건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/name?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_id=550e8400-e29b-41d4-a716-446655440FFF
```

다건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/name?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_id=550e8400-e29b-41d4-a716-446655440FFF&user_ids=550e8400-e29b-41d4-a716-446655440FA&user_ids=550e8400-e29b-41d4-a716-446655440FB&user_ids=550e8400-e29b-41d4-a716-446655440FC
```

**POST method는 파라미터를 body에 저장하여 호출**

```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/name
```

단건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_id": "550e8400-e29b-41d4-a716-446655440FFF"
}
```

다건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_ids": [
    "550e8400-e29b-41d4-a716-446655440FA",
    "550e8400-e29b-41d4-a716-446655440FB",
    "550e8400-e29b-41d4-a716-446655440FC"
  ]
}
```

#### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**

단건 조회:
```json
{"code": "00000", "message": "성공", "user_name": "김학생", "api_version": "2.4"}
{"code": "40401", "message": "존재하지 않은 데이터", "user_name": "", "api_version": "2.4"}
{"code": "50001", "message": "시스템 오류", "user_name": "", "api_version": "2.4"}
```

다건 조회:
```json
{
  "code": "00000",
  "message": "성공",
  "user_list": [
    {"user_id": "550e8400-e29b-41d4-a716-446655440FA", "user_name": "김학생"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FB", "user_name": "이학생"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FC", "user_name": "박학생"}
  ],
  "api_version": "2.4"
}
```

### 2) 학생 학교 명 조회

#### ① 기본 정보

| 항목 | 내용 |
|------|------|
| ID | API_AUTH_003 |
| 항목명 | 학생 학교 명 조회 |
| I/F 방식 및 전송방식 | REST API / GET, POST |
| 주기 | 대화식 처리 |
| 설명 | 사용자 식별 ID를 통한 학생 학교 명 조회 |
| 송수신시스템 | AIDT → AIDT 플랫폼 |
| 형식 | api_domain + /aidt_userinfo/student/school_name |

#### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| header | Partner-ID | 연동 인증키 | O | string(36) | |
| | API-version | API 버전 | O | string(10) | |
| | access_token | SSO 토큰 | O | - | |
| | token | 인증토큰 | O | string(가변) | |
| body | access_id | 로그인 사용자 ID | O | string(가변) | |
| | user_id | 조회 대상 사용자 ID | △ | string(36) | user_id, user_ids 중 하나는 필수 |
| | user_ids | 조회 대상 사용자 리스트 | △ | list(string) | |

#### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| status | code | HTTP 규약에 따른 상태코드<br>■ 200 : OK, API가 request에 대한 처리를 수행하는 하위<br>API의 수행결과는 body의 code와 message를 반영함<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | O | - | 5. 오류코드 참조 |
| | code | 응답 코드 | O | - | 6. 응답코드 참조 |
| | message | 응답 메시지 | O | - | |
| body | school_name | 학교 명 | △ | string(128) | 조회 항목에 따라 school_name 또는 user_list 반환 |
| | user_list | 사용자 리스트 | △ | list(1...n) | |
| | user_id | 사용자 ID | △ | string(36) | user_ids 작성 시 반환 |
| | school_name | 학교 명 | △ | string(128) | |
| | api_version | API 버전 | O | string(10) | |

#### ④ 호출 예시(Request)

**header**
```json
{
  "Partner-ID": "550e8400-e29b-41d4-a716-446655440FFFF",
  "API-version": "2.4"
}
```

**GET method는 파라미터를 URL에 추가하여 호출**

단건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/school_name?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_id=550e8400-e29b-41d4-a716-446655440FFF
```

다건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/school_name?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_ids=550e8400-e29b-41d4-a716-446655440FA&user_ids=550e8400-e29b-41d4-a716-446655440FB&user_ids=550e8400-e29b-41d4-a716-446655440FC
```

**POST method는 파라미터를 body에 저장하여 호출**

```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/school_name
```

단건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_id": "550e8400-e29b-41d4-a716-446655440FFF"
}
```

다건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_ids": [
    "550e8400-e29b-41d4-a716-446655440FA",
    "550e8400-e29b-41d4-a716-446655440FB",
    "550e8400-e29b-41d4-a716-446655440FC"
  ]
}
```

#### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**

단건 조회:
```json
{"code": "00000", "message": "성공", "school_name": "하늘초등학교", "api_version": "2.4"}
{"code": "40401", "message": "존재하지 않은 데이터", "school_name": "", "api_version": "2.4"}
{"code": "50001", "message": "시스템 오류", "school_name": "", "api_version": "2.4"}
```

다건 조회:
```json
{
  "code": "00000",
  "message": "성공",
  "user_list": [
    {"user_id": "550e8400-e29b-41d4-a716-446655440FA", "school_name": "하늘초등학교"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FB", "school_name": "하늘초등학교"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FC", "school_name": "하늘초등학교"}
  ],
  "api_version": "2.4"
}
```

### 3) 학생 학교 ID 조회

#### ① 기본 정보

| 항목 | 내용 |
|------|------|
| ID | API_AUTH_016 |
| 항목명 | 학생 학교 ID 조회 |
| I/F 방식 및 전송방식 | REST API / GET, POST |
| 주기 | 대화식 처리 |
| 설명 | 사용자 식별 ID를 통한 학생 학교 ID 조회 |
| 송수신시스템 | AIDT → AIDT 플랫폼 |
| 형식 | api_domain + /aidt_userinfo/student/school_id |

#### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| header | Partner-ID | 연동 인증키 | O | string(36) | |
| | API-version | API 버전 | O | string(10) | |
| | access_token | SSO 토큰 | O | - | |
| | token | 인증토큰 | O | string(가변) | |
| body | access_id | 로그인 사용자 ID | O | string(가변) | |
| | user_id | 조회 대상 사용자 ID | △ | string(36) | user_id, user_ids 중 하나는 필수 |
| | user_ids | 조회 대상 사용자 리스트 | △ | list(string) | |

#### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| status | code | HTTP 규약에 따른 상태코드<br>■ 200 : OK, API가 request에 대한 처리를 수행하는 하위<br>API의 수행결과는 body의 code와 message를 반영함<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | O | - | 5. 오류코드 참조 |
| | code | 응답 코드 | O | - | 6. 응답코드 참조 |
| | message | 응답 메시지 | O | - | |
| body | school_id | 사용자 명 | △ | string(128) | 조회 항목에 따라 school_id 또는 user_list 반환 |
| | user_list | 사용자 리스트 | △ | list(1...n) | |
| | user_id | 사용자 ID | △ | string(36) | user_ids 작성 시 반환 |
| | school_id | 학교 ID | △ | string(10) | |
| | api_version | API 버전 | O | string(10) | |

#### ④ 호출 예시(Request)

**header**
```json
{
  "Partner-ID": "550e8400-e29b-41d4-a716-446655544FFFF",
  "API-version": "2.4"
}
```

**GET method는 파라미터를 URL에 추가하여 호출**

단건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/school_id?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_id=550e8400-e29b-41d4-a716-446655440FFF
```

다건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/school_id?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_ids=550e8400-e29b-41d4-a716-446655440FA&user_ids=550e8400-e29b-41d4-a716-446655440FB&user_ids=550e8400-e29b-41d4-a716-446655440FC
```

**POST method는 파라미터를 body에 저장하여 호출**

```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/school_id
```

단건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_id": "550e8400-e29b-41d4-a716-446655440FFF"
}
```

다건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_ids": [
    "550e8400-e29b-41d4-a716-446655440FA",
    "550e8400-e29b-41d4-a716-446655440FB",
    "550e8400-e29b-41d4-a716-446655440FC"
  ]
}
```

#### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**

단건 조회:
```json
{"code": "00000", "message": "성공", "school_id": "K100000383", "api_version": "2.4"}
{"code": "40401", "message": "존재하지 않은 데이터", "school_id": "", "api_version": "2.4"}
{"code": "50001", "message": "시스템 오류", "school_id": "", "api_version": "2.4"}
```

다건 조회:
```json
{
  "code": "00000",
  "message": "성공",
  "user_list": [
    {"user_id": "550e8400-e29b-41d4-a716-446655440FA", "school_id": "K100000383"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FB", "school_id": "K100000383"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FC", "school_id": "K100000383"}
  ],
  "api_version": "2.4"
}
```

### 4) 학생 학교 구분 조회

#### ① 기본 정보

| 항목 | 내용 |
|------|------|
| ID | API_AUTH_004 |
| 항목명 | 학생 학교 구분 조회 |
| I/F 방식 및 전송방식 | REST API / GET, POST |
| 주기 | 대화식 처리 |
| 설명 | 사용자 식별 ID를 통한 학생 학교 구분 조회 |
| 송수신시스템 | AIDT → AIDT 플랫폼 |
| 형식 | api_domain + /aidt_userinfo/student/division |

#### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| header | Partner-ID | 연동 인증키 | O | string(36) | |
| | API-version | API 버전 | O | string(10) | |
| | access_token | SSO 토큰 | O | - | |
| | └─token | 인증토큰 | O | string(가변) | |
| body | └─access_id | 로그인 사용자 ID | O | string(가변) | |
| | user_id | 조회 대상 사용자 ID | △ | string(36) | user_id, user_ids 중 하나는 필수 |
| | user_ids | 조회 대상 사용자 리스트 | △ | list(string) | |

#### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| status | code | HTTP 규약에 따른 상태코드<br>■ 200 : OK, API가 request에 대한 처리를 수행하는 하위<br>API의 수행결과는 body의 code와 message를 반영함<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | O | - | 5. 오류코드 참조 |
| | code | 응답 코드 | O | - | 6. 응답코드 참조 |
| | message | 응답 메시지 | O | - | |
| body | user_division | 학교 구분 코드 | △ | string(128) | 조회 항목에 따라 user_division 또는 user_list 반환 |
| | user_list | 사용자 리스트 | △ | list(1...n) | |
| | └─user_id | 사용자 ID | △ | string(36) | user_ids 작성 시 반환 |
| | └─user_division | 학교 구분 코드 | △ | string(1) | |
| | api_version | API 버전 | O | string(10) | |

#### ④ 호출 예시(Request)

**header**
```json
{
  "Partner-ID": "550e8400-e29b-41d4-a716-446655544FFFF",
  "API-version": "2.4"
}
```

**GET method는 파라미터를 URL에 추가하여 호출**

단건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/division?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_id=550e8400-e29b-41d4-a716-446655440FFF
```

다건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/division?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_ids=550e8400-e29b-41d4-a716-446655440FA&user_ids=550e8400-e29b-41d4-a716-446655440FB&user_ids=550e8400-e29b-41d4-a716-446655440FC
```

**POST method는 파라미터를 body에 저장하여 호출**

```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/division
```

단건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_id": "550e8400-e29b-41d4-a716-446655440FFF"
}
```

다건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_ids": [
    "550e8400-e29b-41d4-a716-446655440FA",
    "550e8400-e29b-41d4-a716-446655440FB",
    "550e8400-e29b-41d4-a716-446655440FC"
  ]
}
```

#### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**

단건 조회:
```json
{"code": "00000", "message": "성공", "user_division": "2", "api_version": "2.4"}
{"code": "40401", "message": "존재하지 않은 데이터", "user_division": "", "api_version": "2.4"}
{"code": "50001", "message": "시스템 오류", "user_division": "", "api_version": "2.4"}
```

다건 조회:
```json
{
  "code": "00000", 
  "message": "성공",
  "user_list": [
    {"user_id": "550e8400-e29b-41d4-a716-446655440FA", "user_division": "2"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FB", "user_division": "2"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FC", "user_division": "2"}
  ],
  "api_version": "2.4"
}
```

### 5) 학생 학년 조회

#### ① 기본 정보

| 항목 | 내용 |
|------|------|
| ID | API_AUTH_005 |
| 항목명 | 학생 학년 조회 |
| I/F 방식 및 전송방식 | REST API / GET, POST |
| 주기 | 대화식 처리 |
| 설명 | 사용자 식별 ID를 통한 학생 학년 조회 |
| 송수신시스템 | AIDT → AIDT 플랫폼 |
| 형식 | api_domain + /aidt_userinfo/student/grade |

#### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| header | Partner-ID | 연동 인증키 | O | string(36) | |
| | API-version | API 버전 | O | string(10) | |
| | access_token | SSO 토큰 | O | - | |
| | └─token | 인증토큰 | O | string(가변) | |
| body | └─access_id | 로그인 사용자 ID | O | string(가변) | |
| | user_id | 조회 대상 사용자 ID | △ | string(36) | user_id, user_ids 중 하나는 필수 |
| | user_ids | 조회 대상 사용자 리스트 | △ | list(string) | |

#### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| status | code | HTTP 규약에 따른 상태코드<br>■ 200 : OK, API가 request에 대한 처리를 수행하는 하위<br>API의 수행결과는 body의 code와 message를 반영함<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | O | - | 5. 오류코드 참조 |
| | code | 응답 코드 | O | - | 6. 응답코드 참조 |
| | message | 응답 메시지 | O | - | |
| body | user_grade | 학년 | △ | string(128) | 조회 항목에 따라 user_grade 또는 user_list 반환 |
| | user_list | 사용자 리스트 | △ | list(1...n) | |
| | └─user_id | 사용자 ID | △ | string(36) | user_ids 작성 시 반환 |
| | └─user_grade | 학년 | △ | string(1) | |
| | api_version | API 버전 | O | string(10) | |

#### ④ 호출 예시(Request)

**header**
```json
{
  "Partner-ID": "550e8400-e29b-41d4-a716-446655544FFFF",
  "API-version": "2.4"
}
```

**GET method는 파라미터를 URL에 추가하여 호출**

단건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/grade?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_id=550e8400-e29b-41d4-a716-446655440FFF
```

다건 조회:
```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/grade?access_token.token=F58D9956AB92798F51B7F9D8330DC42B&access_token.access_id=550e8400-e29b-41d4-a716-446655440000&user_ids=550e8400-e29b-41d4-a716-446655440FA&user_ids=550e8400-e29b-41d4-a716-446655440FB&user_ids=550e8400-e29b-41d4-a716-446655440FC
```

**POST method는 파라미터를 body에 저장하여 호출**

```
URL: https://xxx.aidtbook.kr/aidt_userinfo/student/grade
```

단건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_id": "550e8400-e29b-41d4-a716-446655440FFF"
}
```

다건 조회 body:
```json
{
  "access_token": {
    "token": "F58D9956AB92798F51B7F9D8330DC42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "user_ids": [
    "550e8400-e29b-41d4-a716-446655440FA",
    "550e8400-e29b-41d4-a716-446655440FB",
    "550e8400-e29b-41d4-a716-446655440FC"
  ]
}
```

#### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**

단건 조회:
```json
{"code": "00000", "message": "성공", "user_grade": "3", "api_version": "2.4"}
{"code": "40401", "message": "존재하지 않은 데이터", "user_grade": "", "api_version": "2.4"}
{"code": "50001", "message": "시스템 오류", "user_grade": "", "api_version": "2.4"}
```

다건 조회:
```json
{
  "code": "00000", 
  "message": "성공",
  "user_list": [
    {"user_id": "550e8400-e29b-41d4-a716-446655440FA", "user_grade": "3"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FB", "user_grade": "3"},
    {"user_id": "550e8400-e29b-41d4-a716-446655440FC", "user_grade": "3"}
  ],
  "api_version": "2.4"
}
```

