-- MySQL initialization script for QuantityMeasurementApp
-- This script runs when the MySQL container starts for the first time

-- Create database if not exists (already handled by docker-compose)
-- CREATE DATABASE IF NOT EXISTS quantitymeasurementdb;

-- Use the database
USE quantitymeasurementdb;

-- Create additional user (optional)
-- CREATE USER IF NOT EXISTS 'qma_user'@'%' IDENTIFIED BY 'qma_password';
-- GRANT ALL PRIVILEGES ON quantitymeasurementdb.* TO 'qma_user'@'%';
-- FLUSH PRIVILEGES;

-- Set timezone
SET time_zone = '+00:00';

-- Optional: Insert sample data (uncomment if needed)
-- Note: Tables will be created automatically by Hibernate

/*
-- Sample users (passwords are BCrypt encoded)
INSERT IGNORE INTO user_entity (id, email, name, password, provider, created_at, updated_at) VALUES
(1, 'admin@qma.com', 'Admin User', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM7lbdxIoNcVv1l.ohKC', 'LOCAL', NOW(), NOW()),
(2, 'user@qma.com', 'Test User', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM7lbdxIoNcVv1l.ohKC', 'LOCAL', NOW(), NOW());

-- Sample quantity measurements
INSERT IGNORE INTO quantity_measurement_entity 
(user_id, this_value, this_unit, this_measurement_type, that_value, that_unit, that_measurement_type, 
 operation, result_value, result_unit, result_measurement_type, is_error, created_at, updated_at) VALUES
(1, 12.0, 'INCHES', 'LENGTH', 1.0, 'FEET', 'LENGTH', 'COMPARE', NULL, NULL, NULL, false, NOW(), NOW()),
(1, 1.0, 'FEET', 'LENGTH', NULL, NULL, NULL, 'CONVERT', 12.0, 'INCHES', 'LENGTH', false, NOW(), NOW());
*/

-- Create indexes for better performance (Hibernate will create these, but we can ensure they exist)
-- These are handled by @Index annotations in the entity class

COMMIT;