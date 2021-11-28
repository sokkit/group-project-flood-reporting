SET FOREIGN_KEY_CHECKS = 0;
USE `wrack-report`;
TRUNCATE TABLE `users`;
-- TRUNCATE TABLE `user_types`;
TRUNCATE TABLE `reports`;
TRUNCATE TABLE `metadata`;
TRUNCATE TABLE `media`;
TRUNCATE TABLE `categories`;
TRUNCATE TABLE `depth_categories`;
SET FOREIGN_KEY_CHECKS = 1;

-- INSERT INTO `user_types`
-- VALUES(1, 'user');
-- INSERT INTO `user_types`
-- VALUES(2, 'admin');

INSERT INTO `users`
VALUES(NULL, 'ROLE_USER', 'john', 'jones', 'john@gmail.com', 01823445668, "$2a$10$g4qzJA3h7.7vTfcxrBtV6eaR9TyhP.C6wbKT8zmAPrbibHp8riw8C", true);
INSERT INTO `users`
VALUES(NULL, 'ROLE_ADMIN', 'admin', 'user', 'admin@gmail.com', null, "$2a$10$J.n9gEYayF9kTfQsqTT5u.KjH7JTpU57jImc1sX0/Em38kzHgTYoK", true);

INSERT INTO `categories`
VALUES(NULL, 'sewer flooding'); 
INSERT INTO `categories`
VALUES(NULL, 'river flooding');
INSERT INTO `categories`
VALUES(NULL, 'coastal flooding');
INSERT INTO `categories`
VALUES(NULL, 'debris');
INSERT INTO `categories`
VALUES(NULL, 'flash flooding');
INSERT INTO `categories`
VALUES(NULL, 'N/A');

INSERT INTO `depth_categories`
VALUES(NULL, 'Up to ground socket');
INSERT INTO `depth_categories`
VALUES(NULL, 'Up to my ankles');
INSERT INTO `depth_categories`
VALUES(NULL, 'Up to my knees');
INSERT INTO `depth_categories`
VALUES(NULL, 'Up to my chest');
INSERT INTO `depth_categories`
VALUES(NULL, 'Up to my light switch');
INSERT INTO `depth_categories`
VALUES(NULL, 'Other');


INSERT INTO `metadata`
VALUES(NULL, NULL, NULL, NULL);