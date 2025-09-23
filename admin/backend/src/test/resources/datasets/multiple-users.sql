-- Multiple test users for complex scenarios
INSERT INTO users (userid, password, name, role, created_at, updated_at) 
VALUES 
('admin', '{noop}admin', '관리자', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('user', '{noop}user', '사용자', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser1', '{noop}password123', 'Test User 1', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser2', '{noop}password123', 'Test User 2', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('testuser3', '{noop}password123', 'Test User 3', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);