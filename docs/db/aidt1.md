# 1.4 용어 정의

본 문서에서 다루는 가이드의 주요 용어를 설명합니다.

| 용어명 | 설명 |
|--------|------|
| 교육디지털플랫폼 | - 교직원 및 학생이 하나의 아이디로 교육디지털플랫폼과 연계된 교육 관련 사이트를 이용할 수 있는 다양한 인증 수단을 제공<br>- 간편 로그인 기능을 제공하여 교육 관련 사이트 회원가입의 편리성과 온라인 학생 신분 확인 및 정확한 학생 정보(학교, 학년 등)를 제공 |
| 디지털원패스 | - 하나의 아이디로 본인이 선택한 인증수단을 사용하여 안전하고 편리하게 여러 전자정부 서비스를 이용할 수 있는 인증 서비스<br>- 전자정부 서비스 이용 시 웹사이트마다 아이디를 일일이 기억할 필요 없이 하나의 디지털원패스 아이디를 통해 여러 전자정부 서비스들을 이용<br>- 디지털원패스에서는 편리하게 전자정부 서비스를 이용할 수 있도록 모바일 인증(지문, 안면, 패턴, PIN, 공동인증서(지원)), 공동인증서(PC), SMS 등 다양한 간편 인증수단을 제공 |
| 연동 인증키 | - AI 디지털교과서 API 호출 시 파트너를 식별하는 기준이 되는 키<br>- 파트너가 등록되면 텍스트용 연동 인증키와 운영용 연동 인증키 발급 |
| UUID | - W3C에서 정의하고 있는 범용 고유 식별자(Universally Unique Identifier) |

# 3. API 연계 기본 구조

## 3.1 파트너란?

- AI 디지털교과서 포털을 통해 서비스를 개발하는 AI 디지털교과서 개발사를 의미합니다.
- 파트너는 1:N(개발사 : 교과서)의 관계로 연결될 수 있으며, 연결된 파트너는 연동 인증키로 API를 사용할 수 있습니다.

## 3.2 연동 인증키란?

- AI 디지털교과서 포털에 세 파트너가 등록되면 테스트용 연동 인증키와 운영용 연동 인증키가 자동으로 발급됩니다.
- 연동 인증키는 AI 디지털교과서 포털 API 호출 시 파라미터에 사용되며, 파트너를 식별하는 기준으로 활용됩니다.
- 사용 권한 : AI 디지털교과서의 모든 API는 파트너를 식별하기 위해, 첫번째 파라미터에 파트너의 연동 인증키를 입력하도록 설계되어 있습니다.

## 3.3 개인 식별코드(User-ID:UUID)란?

- AI 디지털교과서의 ID 체계는 범용 고유 식별자(Universally Unique Identifier, UUID) 방식을 채택하여 사용합니다.

※ 340,282,366,920,938,463,463,374,607,431,768,211,456개의 서로 가능한 UUID가 있어 중복이 생길 가능성이 없습니다.

- AI 디지털교과서 개인식별번호 체계는 나이스 ID + 개발사 ID + Time을 기준으로 한 버전 5를 채택하여 생성합니다.

• UUID의 버전 버전 1(MAC 주소, 버전 2(DEC 보안), 버전 3(MD5 해시), 버전 4(랜덤), 버전 5(SHA-1해시))가 있습니다.

- UUID는 128비트의 수입니다. 표준 형식에서 UUID는 32개의 십육진수로 표현되며 총 36개 문자(32개 문자와 4개의 하이픈)로 된 8-4-4-4-12라는 5개의 그룹을 하이픈으로 구분합니다. 형식은 다음과 같습니다.

※ (형식) 550e8400-e29b-41d4-a716-446655440000

## 3.4 대화식 처리, 일괄 처리 방식이란?

- AI 디지털교과서의 통신 방식은 데이터의 특성과 성격에 따라 대화식 처리 방식(Interactive processing)과 일괄 처리 방식(Batch processing)을 사용합니다.
- 대화식 처리 방식은 AIDT 개발사의 요청에 따라 AIDT 플랫폼에서 작업을 즉시 처리하는 방식을 말합니다.
- 일괄 처리 방식은 AI 디지털교과서에서 발생하는 대량의 데이터 수집을 위하여 AIDT 개발사에서 지체적으로 수집·정제한 데이터를 정해진 있는 일정에 따라 수집하는 방식을 말합니다.

## 3.5 AI 디지털교과서 통합인증 API URL

- AI 디지털교과서 API는 각 항목별로 별도의 URL이 존재합니다.
- 최초 접달 시작(Launch) 메시지에 포함된 api_domain + 항목 경로(아래 정의된) 형식으로 호출하게 됩니다.
- api_domain은 프로토콜(http:// 또는 https://)과 도메인명이 결합된 형태로 제공됩니다.
  (테스트 환경의 경우 경로가 포함될 수도 있음)
  예) http://210.210.210.210, https://xxx.aidtbook.kr, https://xxx.aidtbookr.kr/test

## 3.6 API version 정보

- API 버전 불일치 해소를 위해 API version 파라미터가 추가 되었습니다.
- 파라미터는 header 또는 body에 위치하며, 위치에 따른 명칭은 다음과 같습니다.

※ (header) API-version, (body) api_version

- API_AUTH_001은 Request와 Response 모두 body에 위치합니다.

※ Request body의 api_version "2.3"을 설정하여 호출하는 경우, API의 버전이 "2.3"임을 의미

※ 단, 접촉일 시에는 api_version을 전송하지 않음

- API_AUTH_002~022은 Request는 header, Response는 body에 위치합니다.

※ 통합인증 API(API_AUTH_002~021)는 Request header에 API-version 값을 "2.3"으로 설정되어 요청해야 합니다.

| 운영 환경 |
| 항목 | 경로 |
|------|------|
| 학생 성명 조회 | /aidt_userinfo/student/name |
| 학생 학교 명 조회 | /aidt_userinfo/student/school_name |
| 학생 학교 ID 조회 | /aidt_userinfo/student/school_id |
| 학생 학교 구분 조회 | /aidt_userinfo/student/division |
| 학생 학년 조회 | /aidt_userinfo/student/grade |
| 학생 반 조회 | /aidt_userinfo/student/class |
| 학생 번호 조회 | /aidt_userinfo/student/number |
| 학생 시간표 조회 | /aidt_userinfo/student/schedule |
| 학생 학급 구성원 조회 | /aidt_userinfo/student/class_member |
| 학생 성별 조회 | /aidt_userinfo/student/gender |
| 학생 정보 일괄 조회 | /aidt_userinfo/student/all |
| 학생 개설과목 정보 조회 | /aidt_userinfo/student/open_subject_info |
| 학생 개설과목 목록 조회 | /aidt_userinfo/student/open_subject_list |
| 교사 성명 조회 | /aidt_userinfo/teacher/name |
| 교사 학교 명 조회 | /aidt_userinfo/teacher/school_name |
| 교사 학교 ID 조회 | /aidt_userinfo/teacher/school_id |
| 교사 시간표 조회 | /aidt_userinfo/teacher/schedule |
| 교사 학급 목록 조회 | /aidt_userinfo/teacher/class_list |
| 교사 학급 구성원 조회 | /aidt_userinfo/teacher/class_member |
| 교사 정보 일괄 조회 | /aidt_userinfo/teacher/all |
| 교사 개설과목 정보 조회 | /aidt_userinfo/teacher/open_subject_info |
| 교사 강의실 정보 조회 | /aidt_userinfo/teacher/lecture_room_info |
| 교사 개설과목 목록 조회 | /aidt_userinfo/teacher/open_subject_list |

# 4. 인증 API 연계 구성

## 4.1 AI 디지털교과서 호출(시작)

- AI 디지털교과서 포털의 시간표나 책장 선택 시 학생의 상태 정보(진학 등) 및 교과서 시작 정보를 AI 디지털교과서 개발사에 전달합니다.

| 순서 | 설명 |
|------|------|
| 1~3 | 개발사 정보 확인 |
| 4 | 개발사 변경 여부 확인(학생 상태 변경 확인) |
| 5 | 사용자 변경 정보 구성 |
| 6 | 기존 AI 디지털교과서 이용 |
| 7 | 현재 AI 디지털교과서로부터 학습이력 데이터 수신<br>- 전달 학생의 경우 Request의 학습 상태 정보에 전달 정보를 포함하여 호출<br>- AIDT 개발사에서 학생 전출 정보 확인 후 학습이력 데이터를 AI 디지털교과서 포털로 전송<br>(API ID : API_AUTH_001 참조) |
| 8 | 변경 AI 디지털교과서로 학습이력 데이터 전송 |

## 1) 시작정보 전달

### ① 기본 정보

| | | | |
|---|---|---|---|
| **ID** | API_AUTH_001 | **항목명** | 교과서 시작 정보 |
| **I/F 방식 및 전송방식** | REST API / POST | **주기** | 대화식 처리 |
| **설명** | AI 디지털교과서 시작 정보 전달(사용자 상태가 기존 인 경우)<br>※ 사용자 상태가 전학(전출/전입) 인 경우 데이터수집 API 연계 가이드 참조 |
| **송수신시스템** | AIDT 플랫폼 → AIDT |
| **형식** | AIDT 개발사 등록 URL(AIDT 개발사의 교과서별 URL) |

* 인가 되지 않은 호출자면을 위하여 개발사는 AI 디지털교과서 포털에서 별도 경로를 통해 제공하는 도메인(또는 아이피주소)의 시작 정보를 유효한 호출로 인식하여 응답하여야 함.

### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| **header** | | | | | |
| | access_token | SSO 토큰 | ○ | - | |
| | └─token | 인증토큰 | ○ | string(가변) | |
| | └─access_id | 로그인 사용자 ID | ○ | string(가변) | |
| | api_domain | 통합인증 API 도메인 또는 IP | ○ | string(128) | |
| | user_type | 사용자 종류 | ○ | string(1) | 주1) |
| | user_status | 사용자 상태 | ○ | string(1) | 주2) |
| | user_id | 사용자 ID | ○ | string(36) | |
| | lecture_code | 강의코드 | ○ | string(26) | 주3) |
| | teacher_lecture_code | 교사별 강의 코드 | ○ | string(가변) | 주4) |
| **body** | textbook_id | 교과서 ID | ○ | string(3) | 주5) |
| | class_code | 학급코드 | △ | string(30) | 주6) |
| | class_period | 교시 | ○ | string(2) | 주7) |
| | entrusted_info | 등의정보 | △ | | |
| | └─use_terms_agree_yn | 이용약관동의여부 | △ | string(1) | |
| | └─use_terms_agree_dt | 이용약관동의일시 | △ | string(14) | |
| | └─collect_use_pi_agree_yn | 개인정보 수집 및 이용 동의 여부 | △ | string(1) | 주8) |
| | └─collect_use_pi_agree_dt | 개인정보 수집 및 이용 동의 일시 | △ | string(14) | |
| | └─provide_pi_3rdp_agree_yn | 개인정보 제3자 제공 동의 여부 | △ | string(1) | |
| | └─provide_pi_3rdp_agree_dt | 개인정보 제3자 제공 동의 일시 | △ | string(14) | |
| | api_version | API 버전 | ○ | string(10) | |

| 주호 | Request 주의 상세 설명 |
|------|----------------------|
| 주1) | (학생) S, (교사) T, (학부모) P |
| 주2) | (기존, default 값) E, (전출) O, (전입) I |
| | ※ 강의코드(lecture_code)는 다음 교사의 강의코드 동일한 값을 가질 수 있음 |
| 주3) | - (학생) 시간표 선택 시, 이외(책장 진입)의 경우 모두 해당 강의코드 전송, |
| | - (교사) 시간표 선택 시, 이외(책장 진입)의 경우 모두 해당 강의코드 전송 |
| 주4) | (학생) 소속 학급코드<br>(교사) 학급담임교사/학급담임+교과교사 : 담당 학급코드 전송, 교과전담교사 : null값 전송 |
| 주5) | - (AIDT 포털 시간표 진입 시) AIDT포털의 시간표 진입 시 시간표 상의 교시(예, 01(1교시), 02(2교시),…)<br>- (AIDT 포털 책장 진입 시) 지기주도학습 등을 위해 책장 진입 시 "00"(0교시) 표기 |
| 주6) | - 사용자(교사, 학생)가 AIDT 포털 로그인 시, 개인정보이용약관 동의하는 경우에만 결과를 전송<br>- 사용자(학생)가 14세 미만인 경우, 법정대리인이 개인정보이용약관 동의 후 사용자(학생)가 AIDT 최초 접속 시 전송<br>- (예시) 이용약관동의일시(14) : 년도(4) + 월(2) + 일(2) + 시(2) + 분(2) + 초(2) (예시 : 20241205133059) |
| | 강의코드(lecture_code)는 1명 이상의 교사가 공유하는 개념이며,<br>교사 강의코드(teacher_lecture_code)는 교사마다 고유함(교사+강의코드) 값을 전달함<br>- (학생) 시간표, 책장 진입 시 teacher_lecture_code null값 전송<br>- (교사) 시간표, 책장 진입 시 teacher_lecture_code 값 전송<br>(예시)<br>중킹동 교사 {<br>"lecture_code" : " 4T100000157_20241_00036001 " ,<br>"teacher_lecture_code" : " 100002477 "<br>}<br>이순신 교사 {<br>"lecture_code" : " 4T100000157_20241_00036001 " ,<br>"teacher_lecture_code" : " 100002488 "<br>} |
| 주7) | |
| 주8) | Partner-ID와 매핑되는 교과서별 유일한 ID값 |

### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| | | "HTTP 규약에 의거한 응답코드",<br>■ 200 : OK, API가 request에 대한 처리를 수행했다는 의미.<br>API의 수행결과는 body의 code와 message로 전달함.<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | | | |
| status | code |  | ○ | - | 5. 오류코드 참조 |
| body | code | 응답 코드 | ○ | - |  |
| | message | 응답 메시지 | ○ | - | 6. 응답코드 참조 |

### ④ 호출 예시(Request)

**URL**
```
https://www.sample.co.kr/element/3/english/3-1
```

**header**
```
-
```

**body**
```json
{
  "access_token": {
    "token": "F58D995-6AB92798F51B7F9D833D0C42B",
    "access_id": "550e8400-e29b-41d4-a716-446655400AA"
  },
  "api_domain": "https://xxx.aidtbook.kr",
  "user_type": "S",
  "user_status": "E",
  "user_id": "550e8400-e29b-41d4-a716-446655400000",
  "lecture_code": "4T100000157_20241",
  "teacher_lecture_code": "100002554",
  "textbook_id": "001",
  "class_code": "4T100000157_2024_10609013_1003",
  "class_period": "02",
  "entrusted_info": {
    "use_terms_agree_yn": "Y",
    "use_terms_agree_dt": "20241205123030",
    "collect_use_pi_agree_yn": "Y",
    "collect_use_pi_agree_dt": "20241205123030",
    "provide_pi_3rdp_agree_yn": "Y",
    "provide_pi_3rdp_agree_dt": "20241205123030"
  },
  "api_version": "2.4"
}
```

### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**
```json
{ "code": "00000", "message": "성공", "api_version": "2.4" }
```

## 2) 미리보기정보 전달

### ① 기본 정보

| | | | |
|---|---|---|---|
| **ID** | API_AUTH_100 | **항목명** | 미리보기정보 전달 |
| **I/F 방식 및 전송방식** | REST API / POST | **주기** | 대화식 처리 |
| **설명** | AI 디지털교과서 미리보기 정보 전달(교사)<br>※ 학기 초 개설과목, 시간표 정보가 없고 채택정보만 존재하는 경우, 교사가 미리 수업설계를 해볼 수 있는 정보를 전달한다. |
| **송수신시스템** | AIDT 플랫폼 → AIDT |
| **형식** | AIDT 개발사 등록 URL(AIDT 개발사의 교과서별 URL) |

* 인가 되지 않은 호출자면을 위하여 개발사는 AI 디지털교과서 포털에서 별도 경로를 통해 제공하는 도메인(또는 아이피주소)의 시작 정보를 유효한 호출로 인식하여 응답하여야 함.

| 미리보기 사용 시기 | 신학기 개학 후 정상 사용 시기 |
|---|---|
| (방학) 학기 시작 전 | (1학기) 3월, (2학기) 7~9월 |
| | 신학기 개학 후 사용자(user_id)를 식별할 수 있는, 미리보기는 설계원 *수업 대응 불완전 수강 클래스로 데이터 lecture_code |
| AI 디지털교과서 채택정보만 존재하는 상황에서 학기 시작 전 교육세 수업 설계 보완 | |

* 미리보기정보 전달 이후 교사의 해당 과목에 대해 개설한 시정부터는 통일한 user_id로 시작정보전달 lecture_code값과 함께 전달되어 교사가 이전시기 시 수업 설계한 내용 불러오기 등 데이터의 연속성을 보장해야 함.

### ② Request 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| **header** | | | | | |
| | access_token | SSO 토큰 | ○ | - | |
| | └─token | 인증토큰 | ○ | string(가변) | |
| | └─access_id | 로그인 사용자 ID | ○ | string(가변) | |
| | api_domain | 통합인증 API 도메인 또는 IP | ○ | string(128) | |
| | user_id | 사용자 ID | ○ | string(36) | |
| | schl_crs_se_cd | 학교과정구분코드 | ○ | string(1) | |
| | schl_cd | 학교코드 | ○ | string(10) | |
| | scyr | 학년도 | ○ | string(4) | |
| **body** | smstr | 학기 | ○ | string(1) | 다음 학기 정보 |
| | entrusted_info | 등의정보 | △ | | |
| | └─use_terms_agree_yn | 이용약관동의여부 | △ | string(1) | |
| | └─use_terms_agree_dt | 이용약관동의일시 | △ | string(14) | |
| | └─collect_use_pi_agree_yn | 개인정보 수집 및 이용 동의 여부 | △ | string(1) | |
| | └─collect_use_pi_agree_dt | 개인정보 수집 및 이용 동의 일시 | △ | string(14) | |
| | └─provide_pi_3rdp_agree_yn | 개인정보 제3자 제공 동의 여부 | △ | string(1) | |
| | └─provide_pi_3rdp_agree_dt | 개인정보 제3자 제공 동의 일시 | △ | string(14) | |
| | api_version | API 버전 | ○ | string(10) | |

### ③ Response 메시지 명세

| 구분 | 항목명 | 항목설명 | 필수 | 타입(길이) | 설명(비고) |
|------|--------|----------|------|------------|------------|
| | | "HTTP 규약에 따른 상태코드",<br>■ 200 : OK, API가 request에 대한 처리를 수행했다는 의미.<br>API의 수행결과는 body의 code와 message로 전달함.<br>■ 3xx : 리다이렉션 메시지<br>■ 4xx : 클라이언트 오류 메시지<br>■ 5xx : 서버 오류 메시지 | | | |
| status | code |  | ○ | - | 5. 오류코드 참조 |
| body | code | 응답 코드 | ○ | - | |
| | message | 응답 메시지 | ○ | - | 6. 응답코드 참조 |

### ④ 호출 예시(Request)

**URL**
```
https://www.sample.co.kr/element/3/english/3-11
```

**header**
```
-
```

**body**
```json
{
  "access_token": {
    "token": "F58D995-6AB92798F51B7F9D833D0C42B",
    "access_id": "550e8400-e29b-41d4-a716-446655440AAA"
  },
  "api_domain": "https://xxx.aidtbook.kr",
  "user_id": "550e8400-e29b-41d4-a716-446655440000",
  "schl_crs_se_cd": "3",
  "schl_cd": "V100000004",
  "scyr": "2025",
  "smstr": "2",
  "entrusted_info": {
    "use_terms_agree_yn": "Y",
    "use_terms_agree_dt": "20241205123030",
    "collect_use_pi_agree_yn": "Y",
    "collect_use_pi_agree_dt": "20241205123030",
    "provide_pi_3rdp_agree_yn": "Y",
    "provide_pi_3rdp_agree_dt": "20241205123030"
  },
  "api_version": "2.4"
}
```

### ⑤ 응답 예시(Response)

**http status code**
```
200 : OK
```

**header**
```
-
```

**body**
```json
{ "code": "00000", "message": "성공", "api_version": "2.4" }
```

