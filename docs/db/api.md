# AIDT Portal API Documentation

## Base URLs
- Authentication Service: `https://e3engl0sk3.aitextbook.co.kr`
- API Service: `https://api.aidtbook.kr`

## Common Headers
```
API-version: 2.4
Partner-ID: fa1f5e94-7f48-563d-aa6f-a9c975f145f8
Content-Type: application/json
```

---

## 1. Authentication API

### 1.1 Get Access Token
**Endpoint:** `POST /api/v1/at/token`  
**Description:** Authenticate user and obtain JWT tokens

**Request:**
```json
{
  "loginId": "4V100000030_20251_00001001-26af6255-e8af-57c7-8cd3-bd4981ba5ce3",
  "password": "4V100000030_20251_00001001-26af6255-e8af-57c7-8cd3-bd4981ba5ce3_!@12"
}
```

**Response:**
```json
{
  "timestamp": "2025-09-29T05:16:59.605833",
  "code": 200,
  "status": "OK",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
  }
}
```

---

## 2. Teacher APIs

### 2.1 Get Teacher All Information
**Endpoint:** `POST /aidt_userinfo/teacher/all`  
**Description:** Get teacher's complete information including lectures, schedules, and classes

**Request:**
```json
{
  "access_token": {
    "token": "eyJjdHkiOiJKV1QiLCJlbmMi...",
    "access_id": "47d4de9263e300424d6fb4af8979cd18d52aebbc"
  },
  "user_id": "26af6255-e8af-57c7-8cd3-bd4981ba5ce3",
  "user_id_schdule_yn": "Y"
}
```

**Response:**
```json
{
  "code": "00000",
  "message": "성공",
  "api_version": "2.4",
  "user_name": "aidtpbt10",
  "school_name": "천재교과서학교",
  "school_id": "V100000030",
  "lecture_info": [
    {
      "user_division": "4",
      "school_id": "V100000030",
      "school_name": "천재교과서학교",
      "user_grade": "1",
      "user_order": "00",
      "user_subject": "00000",
      "user_dyng": "0",
      "user_class": "001",
      "lecture_room_code": "001",
      "lecture_room_name": "1-1 교실",
      "subject_name": "영어 3(김태은)",
      "lecture_code": "4V100000030_20251_00001001"
    }
  ],
  "schedule_info": [
    {
      "lecture_code": "4V100000030_20251_00001001",
      "day_week": "0",
      "class_period": "01",
      "subject_name": "영어 3",
      "classroom_name": "1-1 교실",
      "school_name": "천재교과서학교"
    }
  ],
  "class_info": [
    {
      "school_name": "천재교과서학교",
      "user_grade": "1",
      "user_class": "1",
      "class_code": "4V100000030_2025_00000000_1001"
    }
  ]
}
```

### 2.2 Get Class Members
**Endpoint:** `POST /aidt_userinfo/teacher/class_member`  
**Description:** Get list of students in a specific class and lecture

**Request:**
```json
{
  "access_token": {
    "token": "eyJjdHkiOiJKV1QiLCJlbmMi...",
    "access_id": "47d4de9263e300424d6fb4af8979cd18d52aebbc"
  },
  "user_id": "26af6255-e8af-57c7-8cd3-bd4981ba5ce3",
  "class_code": "4V100000030_2025_00000000_1001",
  "lecture_code": "4V100000030_20251_00001001"
}
```

**Response:**
```json
{
  "code": "00000",
  "message": "성공",
  "api_version": "2.4",
  "member_info": [
    {
      "user_id": "5d267a28-3ce9-5441-bb5a-cb0219de6301",
      "user_name": "aidtpbs10",
      "user_number": "1"
    }
  ]
}
```

---

## 3. Student API

### 3.1 Get Student All Information
**Endpoint:** `POST /aidt_userinfo/student/all`  
**Description:** Get student's complete information

**Request:**
```json
{
  "access_token": {
    "token": "eyJjdHkiOiJKV1QiLCJlbmMi...",
    "access_id": "47d4de9263e300424d6fb4af8979cd18d52aebbc"
  },
  "user_id": "5d267a28-3ce9-5441-bb5a-cb0219de6301"
}
```

**Response (Error):**
```json
{
  "code": "40401",
  "message": "존재하지 않은 데이타",
  "api_version": "2.4",
  "user_name": null,
  "user_class": null,
  "user_division": null,
  "user_gender": null,
  "user_grade": null,
  "user_number": null,
  "school_id": null,
  "school_name": null,
  "schedule_info": null,
  "lecture_info": null
}
```

---

## Error Codes

| Code | Message | Description |
|------|---------|-------------|
| 00000 | 성공 | Success |
| 40401 | 존재하지 않은 데이타 | Data not found |

---

## Notes

- Day of week: 0=Sunday, 1=Monday, 2=Tuesday, 3=Wednesday, 4=Thursday, 5=Friday, 6=Saturday
- Class periods: 01=1st period, 02=2nd period, etc.
- Lecture code format: {school_id}_{year}{semester}_{subject}{classroom}
- Class code format: {school_id}_{year}_{department}_{grade}{class}