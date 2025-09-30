-- Initialize database schema for testing
-- PostgreSQL 18+ with UUIDv7 support for time-ordered primary keys

-- Drop all tables in reverse dependency order (child tables first)
DROP TABLE IF EXISTS api_access_logs;
DROP TABLE IF EXISTS user_auth;
DROP TABLE IF EXISTS class_members;
DROP TABLE IF EXISTS classes;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS teacher_lectures;
DROP TABLE IF EXISTS lectures;
DROP TABLE IF EXISTS school_users;
DROP TABLE IF EXISTS schools;
DROP TABLE IF EXISTS partners_users;
DROP TABLE IF EXISTS partners;
DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS portal_users;

-- Create portal users table
CREATE TABLE portal_users (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    userid VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Create unique index on userid
CREATE UNIQUE INDEX idx_portal_users_userid ON portal_users(userid);

-- Create refresh tokens table
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    user_id UUID NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES portal_users(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_token ON refresh_tokens(token);
CREATE INDEX idx_refresh_tokens_expires_at ON refresh_tokens(expires_at);



-- ============================================
-- 1. Partners Table (연동 인증키 관리)
-- ============================================
CREATE TABLE partners (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    partner_id UUID UNIQUE NOT NULL,  -- Partner-ID from API headers
    partner_name VARCHAR(256) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE partners IS '파트너(AI 디지털교과서 개발사) 정보';
COMMENT ON COLUMN partners.partner_id IS '연동 인증키 (Partner-ID)';

-- ============================================
-- 2. Partners Users Table
-- ============================================
CREATE TABLE partners_users (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    partner_id UUID REFERENCES partners(partner_id),
    user_id UUID UNIQUE NOT NULL,
    user_name VARCHAR(128) NOT NULL,
    email VARCHAR(256),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_partners_users_partner ON partners_users(partner_id);

-- ============================================
-- 3. Schools Table
-- ============================================
CREATE TABLE schools (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    school_id VARCHAR(11) UNIQUE NOT NULL,  -- V100000030
    school_name VARCHAR(128) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 4. School Users Table
-- ============================================
CREATE TABLE school_users (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    user_id UUID UNIQUE NOT NULL,
    user_name VARCHAR(128) NOT NULL,
    user_type CHAR(1) NOT NULL,         -- T=Teacher, S=Student
    school_id VARCHAR(11) REFERENCES schools(school_id),
    user_number VARCHAR(3),             -- Student number (for class_member API)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_school_users_type ON school_users(user_type);

-- ============================================
-- 5. Lectures Table
-- ============================================
CREATE TABLE lectures (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    lecture_code VARCHAR(26) UNIQUE NOT NULL,  -- 4V100000030_20251_00001001
    subject_name VARCHAR(128) NOT NULL,
    classroom_name VARCHAR(128) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 6. Teacher Lectures (lecture_info in teacher/all API)
-- ============================================
CREATE TABLE teacher_lectures (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    user_id UUID REFERENCES school_users(user_id),
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
-- 7. Schedules (schedule_info in teacher/all API)
-- ============================================
CREATE TABLE schedules (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    lecture_code VARCHAR(26) REFERENCES lectures(lecture_code),
    day_week CHAR(1) NOT NULL,          -- 0-6 (Sunday to Saturday)
    class_period VARCHAR(2) NOT NULL,   -- 01
    subject_name VARCHAR(128),
    classroom_name VARCHAR(128),
    school_name VARCHAR(128)
);

CREATE INDEX idx_schedules_lecture ON schedules(lecture_code);

-- ============================================
-- 8. Classes (class_info in teacher/all API)
-- ============================================
CREATE TABLE classes (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    class_code VARCHAR(30) UNIQUE NOT NULL,  -- 4V100000030_2025_00000000_1001
    school_name VARCHAR(128),
    user_grade VARCHAR(1),
    user_class VARCHAR(1)
);

-- ============================================
-- 9. Class Members (for teacher/class_member API)
-- ============================================
CREATE TABLE class_members (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    class_code VARCHAR(30) REFERENCES classes(class_code),
    lecture_code VARCHAR(26) REFERENCES lectures(lecture_code),
    user_id UUID REFERENCES school_users(user_id),
    user_name VARCHAR(128),
    user_number VARCHAR(3)
);

CREATE INDEX idx_class_members_class ON class_members(class_code);
CREATE INDEX idx_class_members_lecture ON class_members(lecture_code);

-- ============================================
-- 10. User Authentication (로그인 인증 정보)
-- ============================================
CREATE TABLE user_auth (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    user_id UUID REFERENCES school_users(user_id),
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
-- 11. API Access Logs (Partner API usage tracking)
-- ============================================
CREATE TABLE api_access_logs (
    id UUID PRIMARY KEY DEFAULT uuidv7(),
    partner_id UUID REFERENCES partners(partner_id),
    user_id UUID,
    api_endpoint VARCHAR(256),
    request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response_code VARCHAR(10)
);

CREATE INDEX idx_api_logs_partner ON api_access_logs(partner_id);
CREATE INDEX idx_api_logs_time ON api_access_logs(request_time);
