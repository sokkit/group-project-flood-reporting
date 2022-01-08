 USE `wrack-report`;
 
  -- Example attack possible logged in as root
USE `wrack-report`;
DROP DATABASE IF EXISTS `wrack_report`;
 
 -- Creation of user before pen testing
 SET GLOBAL default_password_lifetime=0;

DROP USER IF EXISTS 'wrackReport'@'localhost';
CREATE USER 'wrackReport'@'localhost' IDENTIFIED BY 'team3';
GRANT SELECT ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT UPDATE ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT EXECUTE ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT ALTER ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT CREATE VIEW ON `wrack-report`.* TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.* TO 'wrackReport'@'localhost';
SHOW GRANTS FOR 'wrackReport'@'localhost';

-- view users
 use mysql;
 Select * from user;
 
-- Example attack not possible logged in as this user
USE `wrack-report`;
DROP DATABASE IF EXISTS `wrack_report`;

-- Example attack still possible logged in as this user
ALTER TABLE `reports`
DROP COLUMN `description`,
DROP COLUMN `depth_meters`,
DROP COLUMN `lat_long`,
DROP COLUMN `datetime`,
DROP COLUMN `postcode`,
DROP COLUMN `local_authority`;

-- user created after pen testing
DROP USER IF EXISTS 'wrackReport'@'localhost';
CREATE USER 'wrackReport'@'localhost' IDENTIFIED BY 'team3';
GRANT SELECT ON `wrack-report`.`categories` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`depth_categories` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`media` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`metadata` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`reports` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`staff_users` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`users` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`report_overview` TO 'wrackReport'@'localhost';
GRANT SELECT ON `wrack-report`.`detailed_report` TO 'wrackReport'@'localhost';
GRANT UPDATE ON `wrack-report`.`media` TO 'wrackReport'@'localhost';
GRANT UPDATE ON `wrack-report`.`reports` TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.`media` TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.`metadata` TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.`reports` TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.`users` TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.`report_form_errors` TO 'wrackReport'@'localhost';
GRANT INSERT ON `wrack-report`.`depth_categories` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`categories` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`depth_categories` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`media` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`metadata` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`reports` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`staff_users` TO 'wrackReport'@'localhost';
GRANT TRIGGER ON `wrack-report`.`users` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`categories` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`depth_categories` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`media` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`metadata` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`reports` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`staff_users` TO 'wrackReport'@'localhost';
GRANT SHOW VIEW ON `wrack-report`.`users` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`detailed_report` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`report_overview` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`media` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`metadata` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`reports` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`users` TO 'wrackReport'@'localhost';
GRANT CREATE ON `wrack-report`.`report_form_errors` TO 'wrackReport'@'localhost';
GRANT ALTER ON `wrack-report`.`staff_users` TO 'wrackReport'@'localhost';
GRANT ALTER ON `wrack-report`.`report_form_errors` TO 'wrackReport'@'localhost';

-- example attack that will no longer work with this user (may need to log back in for changes to take effect)
ALTER TABLE `reports`
DROP COLUMN `description`,
DROP COLUMN `depth_meters`,
DROP COLUMN `lat_long`,
DROP COLUMN `datetime`,
DROP COLUMN `postcode`,
DROP COLUMN `local_authority`;
