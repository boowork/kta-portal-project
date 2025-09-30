-- Insert test data for portal users table
INSERT INTO portal_users (id, userid, password, name, created_at, updated_at)
VALUES
('0199987d-8798-7a79-be6d-c1aa9449d8aa', 'admin', '{noop}admin', '관리자', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('0199987e-0fa0-748d-af0f-37970e02e326', 'user', '{noop}user', '사용자', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 1. Insert partner (from curl.md headers)
INSERT INTO partners (id, partner_name, is_active) VALUES
('01999880-0000-7000-8000-000000000001', 'AI 디지털교과서 개발사', true);

-- 2. Insert school
INSERT INTO schools (school_id, school_name) VALUES
('V100000030', '천재교과서학교');

-- 3. Insert school users
INSERT INTO school_users (user_id, user_name, user_type, school_id) VALUES
('26af6255-e8af-57c7-8cd3-bd4981ba5ce3', 'aidtpbt10', 'T', 'V100000030'),
('5d267a28-3ce9-5441-bb5a-cb0219de6301', 'aidtpbs10', 'S', 'V100000030');

-- Update student with number
UPDATE school_users SET user_number = '1' WHERE user_id = '5d267a28-3ce9-5441-bb5a-cb0219de6301';

-- 4. Insert lecture
INSERT INTO lectures (lecture_code, subject_name, classroom_name) VALUES
('4V100000030_20251_00001001', '영어 3', '1-1 교실');

-- 5. Insert teacher lecture info (from teacher/all response)
INSERT INTO teacher_lectures (
    user_id, lecture_code, user_division, school_id, school_name,
    user_grade, user_order, user_subject, user_dyng, user_class,
    lecture_room_code, lecture_room_name, subject_name
) VALUES (
    '26af6255-e8af-57c7-8cd3-bd4981ba5ce3',
    '4V100000030_20251_00001001',
    '4', 'V100000030', '천재교과서학교',
    '1', '00', '00000', '0', '001',
    '001', '1-1 교실', '영어 3(김태은)'
);

-- 6. Insert schedule (from teacher/all schedule_info - 7 days)
INSERT INTO schedules (lecture_code, day_week, class_period, subject_name, classroom_name, school_name) VALUES
('4V100000030_20251_00001001', '0', '01', '영어 3', '1-1 교실', '천재교과서학교'),
('4V100000030_20251_00001001', '1', '01', '영어 3', '1-1 교실', '천재교과서학교'),
('4V100000030_20251_00001001', '2', '01', '영어 3', '1-1 교실', '천재교과서학교'),
('4V100000030_20251_00001001', '3', '01', '영어 3', '1-1 교실', '천재교과서학교'),
('4V100000030_20251_00001001', '4', '01', '영어 3', '1-1 교실', '천재교과서학교'),
('4V100000030_20251_00001001', '5', '01', '영어 3', '1-1 교실', '천재교과서학교'),
('4V100000030_20251_00001001', '6', '01', '영어 3', '1-1 교실', '천재교과서학교');

-- 7. Insert class info (from teacher/all class_info)
INSERT INTO classes (class_code, school_name, user_grade, user_class) VALUES
('4V100000030_2025_00000000_1001', '천재교과서학교', '1', '1');

-- 8. Insert class member (from teacher/class_member response)
INSERT INTO class_members (class_code, lecture_code, user_id, user_name, user_number) VALUES
('4V100000030_2025_00000000_1001', '4V100000030_20251_00001001',
 '5d267a28-3ce9-5441-bb5a-cb0219de6301', 'aidtpbs10', '1');

-- 9. Insert user authentication (from /api/v1/at/token request)
-- Note: In production, password should be properly hashed
INSERT INTO user_auth (user_id, login_id, password_hash) VALUES
('26af6255-e8af-57c7-8cd3-bd4981ba5ce3',
 '4V100000030_20251_00001001-26af6255-e8af-57c7-8cd3-bd4981ba5ce3',
 '4V100000030_20251_00001001-26af6255-e8af-57c7-8cd3-bd4981ba5ce3_!@12');