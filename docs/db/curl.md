
curl 'https://e3engl0sk3.aitextbook.co.kr/api/v1/at/token' \
  -H 'Referer: https://e3engl0sk3.aitextbook.co.kr/sso?access_token.token=eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.Q_TNVvmNKMrBru2spUcH9cEfiz-UV0GYn7hUbeTJYz5Wkt_rx3Sncl0P49-8aGp-1SyyjQnE2Q555yX0y3a5lUyWEmldGhuoMNb4Vus9klKfBwBv4TSOUAQuWyNlWuAoy57ZVTMUyS9v6REDFwzMq-riKrcxj8CZzGuJ69Dxh6Yiu-Sn8l0ZR9XU9er00cMgRKpi_v_pAqwLd0EKhJlJFul_aGQ4tIKASLmg3vwcAdD4gP7sD39lQVALa07Rv4RcvWMxtpkVpoOTTg-CLcs2U4aJGORk-dZRmFQgnFexTEU4jI3x9aFh73s47_WEhBt6XAAtPx8jdjLUlo5bAb0tIQ.ZVwZzHuOVTk0-8ZD.TbOQvsPxFp82Ihlt0-n_ZPlcdy7ngubb9o2Hq79xCVBf9VJzebM2HYisC7QANJ0vE1SzscWgYZCiAfZ23MofnEJ42lGxdclOlelEAy6kTgQg3F7A18oqry_spDagL9OI5xz6OOn9nHvknpJaVOF0YXncq7bg2jW_6Q6v2pV-IzLhrlhKH8uPSaU4AcEoICAzdv-axJOGu61yK6RUZJwBn7-jaameVCuJ7spZgJ4m4fqCku7DRCKHxCJkH9TfzjLYwF50LpvtsC-Q8vXpcjPh1y3KK5V7-VhnXjEzM9T7QD0DMXpbdywJL3-lazfq17q7JTcxcrdjVw9ovOrcJlqMOpm9zFjSoolvD7H86rqDTezhC7fGazMTOIs7gLHawsa2GHOzEVFYlIEwg9c5TdpA9WZH4uiX8D9Vd_XeThwgauofEGuYWQAqSEkKdjNcnr98FR1TQgYGQ6nGZt339M3mq1kL8I-9YpTVBpuVwjHO_IO3WeeMYxnlCwit2AJC71JgDkqFo8Jh1-gmL5RWK9FA0lutdE7e6j59gvRFbu_pqeqAq4wD-BcoosVv8kePf5jOgIbuAiNQC2NewNwD9dA2YTAd_DwBVQBB9Mtu4Z0evTwPW19cGEwVdwP9mvBDnx464p0mPUN5RBKJeIiolhZ7ZXtMyvZ3oe8Vb7NG0s_EnsHpLFwFJnUl7BxASWMJKVGdEuUGxDlgnaZSnOicme1jTtwf492QlBI49mbw3U0zUyOx7fxvqQgFTb7qUkSarhoUf0gF7slTH6DY2u2MGxVU6A2ngXMCSDTEj-HnHaL9CxO-HBp6EYTpd_8lo4QnE5t2T1iDvjtqmnUCO3IqAh7fB6fLYU0JiPKUHEZLKhGfvOzc8Zkxrr1Qp1hCKBviXaFilFUuK1rWm6X3MTh3FZE.MRKHP0VrqIIDL-FU98jiEA&access_token.access_id=47d4de9263e300424d6fb4af8979cd18d52aebbc&user_id=26af6255-e8af-57c7-8cd3-bd4981ba5ce3&user_status=E&user_type=T&api_domain=https://api.aidtbook.kr&class_code=4V100000030_2025_00000000_1001&lecture_code=4V100000030_20251_00001001&ssoCheck=null&partnerId=fa1f5e94-7f48-563d-aa6f-a9c975f145f8&entrusted_info.use_terms_agree_yn=&entrusted_info.use_terms_agree_dt=&class_period=01&cla_sel_yn=N&orgl_lecture_code=4V100000030_20251_00001001' \
  --data-raw $'{"loginId":"4V100000030_20251_00001001-26af6255-e8af-57c7-8cd3-bd4981ba5ce3","password":"4V100000030_20251_00001001-26af6255-e8af-57c7-8cd3-bd4981ba5ce3_\u0021@12"}'

```json
{
    "timestamp": "2025-09-29T05:16:59.605833",
    "code": 200,
    "status": "OK",
    "data": {
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMDI1MDMwMzIwMjE1NzU1NDc1MS1iN2YzMjFhMGY4MjExMWUiLCJyb2xlcyI6WyJ0Y3IiXSwibWV0YSI6InFhMForL3RFSjRaVGpiRXUyU2duV09wbjNlV3IxbG1GQW1jUU0yTDRRY3lkdkk5KzFxWkNSeEIxVmpJaXJhTHV5SC8zMmlDQ1BDSC95WW16UU94aHlyUy9YdW9DZVl1WVYwV3ZKT283YTRvclRiS1BObjhZVlp4VEg0bDV2VHAvS2tUTmtmZjcvMjUvcGdGK2Y3QnF6K3pLaDZET3ZlZU1BelI4cERFY2UwMGJ6NGo4R1MxOW9OdVd3RktYaXh0bFB4K3hSQklMdjlUeW9Gby9Jd2JGSFQ0bUViNW1IM1Btczk1dEtaU0wwc0plYkZoclhZUW52SStzYzlENHNRY0E0S0gvVTNLRjk2MFg5TTlGVmM2T3lRTUJhTytOTWlZWlpLSHM0TE85Y0I3cVJvS01POXBnSGVENnM0ZVljcTBmZ21kRDl3VXlxVmIvNzdldlRkcGMvUT09IiwiaWF0IjoxNzU5MTIzMDE5LCJleHAiOjE3NTkxMzc0MTl9.UAeJyFLzOprkCI4WerWNekBbJ-u_ojH-fG_2kw_ZgxeHxYIKKG-tj3TNJmFHXYfm_agXPsDo8Plpq70pxWP4Pg",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJ2YWx1ZSI6IjEyMjgwNjg2NzU0ZjQzOThhOTM1M2U5M2VhMzBhMjkyIiwiaWF0IjoxNzU5MTIzMDE5LCJleHAiOjE3NTkxNjYyMTl9.TR-T_wxBFHg4OhCWH-e66YHOA5Inz5mHejr_F1jupwigYTjZQhgcBis6zk6H2DciSk9rqfXYI8SZ3s197uR-Jg"
    }
}
```

---

curl 'https://api.aidtbook.kr/aidt_userinfo/teacher/all' \
  -H 'API-version: 2.4' \
  -H 'Content-Type: application/json' \
  -H 'Partner-ID: fa1f5e94-7f48-563d-aa6f-a9c975f145f8' \
  --data-raw '{"access_token":{"token":"eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.Q_TNVvmNKMrBru2spUcH9cEfiz-UV0GYn7hUbeTJYz5Wkt_rx3Sncl0P49-8aGp-1SyyjQnE2Q555yX0y3a5lUyWEmldGhuoMNb4Vus9klKfBwBv4TSOUAQuWyNlWuAoy57ZVTMUyS9v6REDFwzMq-riKrcxj8CZzGuJ69Dxh6Yiu-Sn8l0ZR9XU9er00cMgRKpi_v_pAqwLd0EKhJlJFul_aGQ4tIKASLmg3vwcAdD4gP7sD39lQVALa07Rv4RcvWMxtpkVpoOTTg-CLcs2U4aJGORk-dZRmFQgnFexTEU4jI3x9aFh73s47_WEhBt6XAAtPx8jdjLUlo5bAb0tIQ.ZVwZzHuOVTk0-8ZD.TbOQvsPxFp82Ihlt0-n_ZPlcdy7ngubb9o2Hq79xCVBf9VJzebM2HYisC7QANJ0vE1SzscWgYZCiAfZ23MofnEJ42lGxdclOlelEAy6kTgQg3F7A18oqry_spDagL9OI5xz6OOn9nHvknpJaVOF0YXncq7bg2jW_6Q6v2pV-IzLhrlhKH8uPSaU4AcEoICAzdv-axJOGu61yK6RUZJwBn7-jaameVCuJ7spZgJ4m4fqCku7DRCKHxCJkH9TfzjLYwF50LpvtsC-Q8vXpcjPh1y3KK5V7-VhnXjEzM9T7QD0DMXpbdywJL3-lazfq17q7JTcxcrdjVw9ovOrcJlqMOpm9zFjSoolvD7H86rqDTezhC7fGazMTOIs7gLHawsa2GHOzEVFYlIEwg9c5TdpA9WZH4uiX8D9Vd_XeThwgauofEGuYWQAqSEkKdjNcnr98FR1TQgYGQ6nGZt339M3mq1kL8I-9YpTVBpuVwjHO_IO3WeeMYxnlCwit2AJC71JgDkqFo8Jh1-gmL5RWK9FA0lutdE7e6j59gvRFbu_pqeqAq4wD-BcoosVv8kePf5jOgIbuAiNQC2NewNwD9dA2YTAd_DwBVQBB9Mtu4Z0evTwPW19cGEwVdwP9mvBDnx464p0mPUN5RBKJeIiolhZ7ZXtMyvZ3oe8Vb7NG0s_EnsHpLFwFJnUl7BxASWMJKVGdEuUGxDlgnaZSnOicme1jTtwf492QlBI49mbw3U0zUyOx7fxvqQgFTb7qUkSarhoUf0gF7slTH6DY2u2MGxVU6A2ngXMCSDTEj-HnHaL9CxO-HBp6EYTpd_8lo4QnE5t2T1iDvjtqmnUCO3IqAh7fB6fLYU0JiPKUHEZLKhGfvOzc8Zkxrr1Qp1hCKBviXaFilFUuK1rWm6X3MTh3FZE.MRKHP0VrqIIDL-FU98jiEA","access_id":"47d4de9263e300424d6fb4af8979cd18d52aebbc"},"user_id":"26af6255-e8af-57c7-8cd3-bd4981ba5ce3","user_id_schdule_yn":"Y"}'

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
        },
        {
            "lecture_code": "4V100000030_20251_00001001",
            "day_week": "1",
            "class_period": "01",
            "subject_name": "영어 3",
            "classroom_name": "1-1 교실",
            "school_name": "천재교과서학교"
        },
        {
            "lecture_code": "4V100000030_20251_00001001",
            "day_week": "2",
            "class_period": "01",
            "subject_name": "영어 3",
            "classroom_name": "1-1 교실",
            "school_name": "천재교과서학교"
        },
        {
            "lecture_code": "4V100000030_20251_00001001",
            "day_week": "3",
            "class_period": "01",
            "subject_name": "영어 3",
            "classroom_name": "1-1 교실",
            "school_name": "천재교과서학교"
        },
        {
            "lecture_code": "4V100000030_20251_00001001",
            "day_week": "4",
            "class_period": "01",
            "subject_name": "영어 3",
            "classroom_name": "1-1 교실",
            "school_name": "천재교과서학교"
        },
        {
            "lecture_code": "4V100000030_20251_00001001",
            "day_week": "5",
            "class_period": "01",
            "subject_name": "영어 3",
            "classroom_name": "1-1 교실",
            "school_name": "천재교과서학교"
        },
        {
            "lecture_code": "4V100000030_20251_00001001",
            "day_week": "6",
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

---

curl 'https://api.aidtbook.kr/aidt_userinfo/teacher/class_member' \
  -H 'API-version: 2.4' \
  -H 'Partner-ID: fa1f5e94-7f48-563d-aa6f-a9c975f145f8' \
  --data-raw '{"access_token":{"token":"eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.Q_TNVvmNKMrBru2spUcH9cEfiz-UV0GYn7hUbeTJYz5Wkt_rx3Sncl0P49-8aGp-1SyyjQnE2Q555yX0y3a5lUyWEmldGhuoMNb4Vus9klKfBwBv4TSOUAQuWyNlWuAoy57ZVTMUyS9v6REDFwzMq-riKrcxj8CZzGuJ69Dxh6Yiu-Sn8l0ZR9XU9er00cMgRKpi_v_pAqwLd0EKhJlJFul_aGQ4tIKASLmg3vwcAdD4gP7sD39lQVALa07Rv4RcvWMxtpkVpoOTTg-CLcs2U4aJGORk-dZRmFQgnFexTEU4jI3x9aFh73s47_WEhBt6XAAtPx8jdjLUlo5bAb0tIQ.ZVwZzHuOVTk0-8ZD.TbOQvsPxFp82Ihlt0-n_ZPlcdy7ngubb9o2Hq79xCVBf9VJzebM2HYisC7QANJ0vE1SzscWgYZCiAfZ23MofnEJ42lGxdclOlelEAy6kTgQg3F7A18oqry_spDagL9OI5xz6OOn9nHvknpJaVOF0YXncq7bg2jW_6Q6v2pV-IzLhrlhKH8uPSaU4AcEoICAzdv-axJOGu61yK6RUZJwBn7-jaameVCuJ7spZgJ4m4fqCku7DRCKHxCJkH9TfzjLYwF50LpvtsC-Q8vXpcjPh1y3KK5V7-VhnXjEzM9T7QD0DMXpbdywJL3-lazfq17q7JTcxcrdjVw9ovOrcJlqMOpm9zFjSoolvD7H86rqDTezhC7fGazMTOIs7gLHawsa2GHOzEVFYlIEwg9c5TdpA9WZH4uiX8D9Vd_XeThwgauofEGuYWQAqSEkKdjNcnr98FR1TQgYGQ6nGZt339M3mq1kL8I-9YpTVBpuVwjHO_IO3WeeMYxnlCwit2AJC71JgDkqFo8Jh1-gmL5RWK9FA0lutdE7e6j59gvRFbu_pqeqAq4wD-BcoosVv8kePf5jOgIbuAiNQC2NewNwD9dA2YTAd_DwBVQBB9Mtu4Z0evTwPW19cGEwVdwP9mvBDnx464p0mPUN5RBKJeIiolhZ7ZXtMyvZ3oe8Vb7NG0s_EnsHpLFwFJnUl7BxASWMJKVGdEuUGxDlgnaZSnOicme1jTtwf492QlBI49mbw3U0zUyOx7fxvqQgFTb7qUkSarhoUf0gF7slTH6DY2u2MGxVU6A2ngXMCSDTEj-HnHaL9CxO-HBp6EYTpd_8lo4QnE5t2T1iDvjtqmnUCO3IqAh7fB6fLYU0JiPKUHEZLKhGfvOzc8Zkxrr1Qp1hCKBviXaFilFUuK1rWm6X3MTh3FZE.MRKHP0VrqIIDL-FU98jiEA","access_id":"47d4de9263e300424d6fb4af8979cd18d52aebbc"},"user_id":"26af6255-e8af-57c7-8cd3-bd4981ba5ce3","class_code":"4V100000030_2025_00000000_1001","lecture_code":"4V100000030_20251_00001001"}'

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

curl 'https://api.aidtbook.kr/aidt_userinfo/student/all' \
  -H 'API-version: 2.4' \
  -H 'Partner-ID: fa1f5e94-7f48-563d-aa6f-a9c975f145f8' \
  --data-raw '{"access_token":{"token":"eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.Q_TNVvmNKMrBru2spUcH9cEfiz-UV0GYn7hUbeTJYz5Wkt_rx3Sncl0P49-8aGp-1SyyjQnE2Q555yX0y3a5lUyWEmldGhuoMNb4Vus9klKfBwBv4TSOUAQuWyNlWuAoy57ZVTMUyS9v6REDFwzMq-riKrcxj8CZzGuJ69Dxh6Yiu-Sn8l0ZR9XU9er00cMgRKpi_v_pAqwLd0EKhJlJFul_aGQ4tIKASLmg3vwcAdD4gP7sD39lQVALa07Rv4RcvWMxtpkVpoOTTg-CLcs2U4aJGORk-dZRmFQgnFexTEU4jI3x9aFh73s47_WEhBt6XAAtPx8jdjLUlo5bAb0tIQ.ZVwZzHuOVTk0-8ZD.TbOQvsPxFp82Ihlt0-n_ZPlcdy7ngubb9o2Hq79xCVBf9VJzebM2HYisC7QANJ0vE1SzscWgYZCiAfZ23MofnEJ42lGxdclOlelEAy6kTgQg3F7A18oqry_spDagL9OI5xz6OOn9nHvknpJaVOF0YXncq7bg2jW_6Q6v2pV-IzLhrlhKH8uPSaU4AcEoICAzdv-axJOGu61yK6RUZJwBn7-jaameVCuJ7spZgJ4m4fqCku7DRCKHxCJkH9TfzjLYwF50LpvtsC-Q8vXpcjPh1y3KK5V7-VhnXjEzM9T7QD0DMXpbdywJL3-lazfq17q7JTcxcrdjVw9ovOrcJlqMOpm9zFjSoolvD7H86rqDTezhC7fGazMTOIs7gLHawsa2GHOzEVFYlIEwg9c5TdpA9WZH4uiX8D9Vd_XeThwgauofEGuYWQAqSEkKdjNcnr98FR1TQgYGQ6nGZt339M3mq1kL8I-9YpTVBpuVwjHO_IO3WeeMYxnlCwit2AJC71JgDkqFo8Jh1-gmL5RWK9FA0lutdE7e6j59gvRFbu_pqeqAq4wD-BcoosVv8kePf5jOgIbuAiNQC2NewNwD9dA2YTAd_DwBVQBB9Mtu4Z0evTwPW19cGEwVdwP9mvBDnx464p0mPUN5RBKJeIiolhZ7ZXtMyvZ3oe8Vb7NG0s_EnsHpLFwFJnUl7BxASWMJKVGdEuUGxDlgnaZSnOicme1jTtwf492QlBI49mbw3U0zUyOx7fxvqQgFTb7qUkSarhoUf0gF7slTH6DY2u2MGxVU6A2ngXMCSDTEj-HnHaL9CxO-HBp6EYTpd_8lo4QnE5t2T1iDvjtqmnUCO3IqAh7fB6fLYU0JiPKUHEZLKhGfvOzc8Zkxrr1Qp1hCKBviXaFilFUuK1rWm6X3MTh3FZE.MRKHP0VrqIIDL-FU98jiEA","access_id":"47d4de9263e300424d6fb4af8979cd18d52aebbc"},"user_id":"26af6255-e8af-57c7-8cd3-bd4981ba5ce3"}'

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


