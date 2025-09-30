-- PostgreSQL 17 DDL for AIDT Portal System
-- Minimal design based ONLY on APIs in curl.md

-- Create schema
CREATE SCHEMA IF NOT EXISTS aidt;
SET search_path TO aidt, public;

-- ============================================
-- 1. Partners Table (연동 인증키 관리)
-- ============================================
CREATE TABLE partners (
    partner_id UUID PRIMARY KEY,  -- Partner-ID from API headers
    partner_name VARCHAR(256) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE partners IS '파트너(AI 디지털교과서 개발사) 정보';
COMMENT ON COLUMN partners.partner_id IS '연동 인증키 (Partner-ID)';

-- ============================================
-- 2. Schools Table
-- ============================================
CREATE TABLE schools (
    school_id VARCHAR(11) PRIMARY KEY,  -- V100000030
    school_name VARCHAR(128) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 3. Users Table
-- ============================================
CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    user_name VARCHAR(128) NOT NULL,
    user_type CHAR(1) NOT NULL,         -- T=Teacher, S=Student
    school_id VARCHAR(11) REFERENCES schools(school_id),
    user_number VARCHAR(3),             -- Student number (for class_member API)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_type ON users(user_type);

-- ============================================
-- 4. Lectures Table
-- ============================================
CREATE TABLE lectures (
    lecture_code VARCHAR(26) PRIMARY KEY,  -- 4V100000030_20251_00001001
    subject_name VARCHAR(128) NOT NULL,
    classroom_name VARCHAR(128) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 5. Teacher Lectures (lecture_info in teacher/all API)
-- ============================================
CREATE TABLE teacher_lectures (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(user_id),
    lecture_code VARCHAR(26) REFERENCES lectures(lecture_code),
    user_division CHAR(1) DEFAULT '4',
    school_id VARCHAR(11),
    school_name VARCHAR(128),
    user_grade VARCHAR(1) DEFAULT '1',
    user_order VARCHAR(2) DEFAULT '00',
    user_subject VARCHAR(5) DEFAULT '00000',
    user_dyng CHAR(1) DEFAULT '0',
    user_class VARCHAR(3) DEFAULT '001',
    lecture_room_code VARCHAR(3) DEFAULT '001',
    lecture_room_name VARCHAR(128),
    subject_name VARCHAR(256)
);

CREATE INDEX idx_teacher_lectures_user ON teacher_lectures(user_id);

-- ============================================
-- 6. Schedules (schedule_info in teacher/all API)
-- ============================================
CREATE TABLE schedules (
    id SERIAL PRIMARY KEY,
    lecture_code VARCHAR(26) REFERENCES lectures(lecture_code),
    day_week CHAR(1) NOT NULL,          -- 0-6 (Sunday to Saturday)
    class_period VARCHAR(2) NOT NULL,   -- 01
    subject_name VARCHAR(128),
    classroom_name VARCHAR(128),
    school_name VARCHAR(128)
);

CREATE INDEX idx_schedules_lecture ON schedules(lecture_code);

-- ============================================
-- 7. Classes (class_info in teacher/all API)
-- ============================================
CREATE TABLE classes (
    class_code VARCHAR(30) PRIMARY KEY,  -- 4V100000030_2025_00000000_1001
    school_name VARCHAR(128),
    user_grade VARCHAR(1),
    user_class VARCHAR(1)
);

-- ============================================
-- 8. Class Members (for teacher/class_member API)
-- ============================================
CREATE TABLE class_members (
    id SERIAL PRIMARY KEY,
    class_code VARCHAR(30) REFERENCES classes(class_code),
    lecture_code VARCHAR(26) REFERENCES lectures(lecture_code),
    user_id UUID REFERENCES users(user_id),
    user_name VARCHAR(128),
    user_number VARCHAR(3)
);

CREATE INDEX idx_class_members_class ON class_members(class_code);
CREATE INDEX idx_class_members_lecture ON class_members(lecture_code);

-- ============================================
-- 9. User Authentication (로그인 인증 정보)
-- ============================================
CREATE TABLE user_auth (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(user_id),
    login_id VARCHAR(256) UNIQUE NOT NULL,  -- lecture_code-user_id format
    password_hash VARCHAR(256) NOT NULL,     -- Encrypted password
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE user_auth IS '사용자 인증 정보';
COMMENT ON COLUMN user_auth.login_id IS '로그인 ID (lecture_code-user_id 형식)';
COMMENT ON COLUMN user_auth.password_hash IS '암호화된 비밀번호';

CREATE INDEX idx_user_auth_user ON user_auth(user_id);
CREATE INDEX idx_user_auth_login ON user_auth(login_id);

-- ============================================
-- 10. API Access Logs (Partner API usage tracking)
-- ============================================
CREATE TABLE api_access_logs (
    id SERIAL PRIMARY KEY,
    partner_id UUID REFERENCES partners(partner_id),
    user_id UUID,
    api_endpoint VARCHAR(256),
    request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response_code VARCHAR(10)
);

CREATE INDEX idx_api_logs_partner ON api_access_logs(partner_id);
CREATE INDEX idx_api_logs_time ON api_access_logs(request_time);

-- ============================================
-- INSERT SAMPLE DATA (from curl.md)
-- ============================================

-- 1. Insert partner (from curl.md headers)
INSERT INTO partners (partner_id, partner_name, is_active) VALUES
('fa1f5e94-7f48-563d-aa6f-a9c975f145f8', 'AI 디지털교과서 개발사', true);

-- 2. Insert school
INSERT INTO schools (school_id, school_name) VALUES
('V100000030', '천재교과서학교');

-- 3. Insert users
INSERT INTO users (user_id, user_name, user_type, school_id) VALUES
('26af6255-e8af-57c7-8cd3-bd4981ba5ce3', 'aidtpbt10', 'T', 'V100000030'),
('5d267a28-3ce9-5441-bb5a-cb0219de6301', 'aidtpbs10', 'S', 'V100000030');

-- Update student with number
UPDATE users SET user_number = '1' WHERE user_id = '5d267a28-3ce9-5441-bb5a-cb0219de6301';

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