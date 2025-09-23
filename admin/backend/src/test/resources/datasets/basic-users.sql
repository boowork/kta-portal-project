-- Basic test users (admin and user)
INSERT INTO users (userid, password, name, role, created_at, updated_at) 
VALUES 
('admin', '{noop}admin', '관리자', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('user', '{noop}user', '사용자', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);