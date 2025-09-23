-- Only admin user for specific tests
INSERT INTO users (userid, password, name, role, created_at, updated_at) 
VALUES 
('admin', '{noop}admin', '관리자', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);