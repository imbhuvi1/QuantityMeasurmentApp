-- SQL Script to make a user admin
-- Replace 'your-email@example.com' with the actual email

-- Method 1: Update existing user to admin
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';

-- Method 2: Insert a new admin user (if needed)
-- INSERT INTO users (email, name, password, role, provider) 
-- VALUES ('admin@example.com', 'Admin User', '$2a$10$encrypted-password', 'ADMIN', 'LOCAL');

-- Check admin users
SELECT id, email, name, role FROM users WHERE role = 'ADMIN';