SET FOREIGN_KEY_CHECKS = 0;
USE `wrack-report`;
TRUNCATE TABLE `users`;
TRUNCATE TABLE `user_types`;
TRUNCATE TABLE `reports`;
TRUNCATE TABLE `metadata`;
TRUNCATE TABLE `media`;
TRUNCATE TABLE `categories`;
TRUNCATE TABLE `depth_categories`;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `user_types`
VALUES(1, 'user');
INSERT INTO `user_types`
VALUES(2, 'admin');

INSERT INTO `users`
VALUES(NULL, 1, 'john', 'jones', 'john@gmail.com', 01823445668);
INSERT INTO `users`
VALUES(NULL, 2, 'admin', 'user', 'admin@gmail.com', null);

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

INSERT INTO `reports`
VALUES (NULL, 1, 2, "Overflowing river", 6, 0.5512, "53.0685, -4.0763", "2021-10-25 23:11:59", NULL);
INSERT INTO `reports`
VALUES (NULL, 1, 4, "A lot of debris", 6, 2.5464, "53.2801, -3.8256", "2021-5-15 20:01:09", NULL);
INSERT INTO `reports`
VALUES (NULL, 1, 6, "Car filled with water caused by flood", 2, 6.5464, "51.4822, -3.1812", "2021-08-25 18:23:43", NULL);
INSERT INTO `reports`
VALUES (NULL, 1, 5, "First floor on my house destroyed by flood", 5, 4.0544, "51.4846, -3.1853", "2021-01-05 17:30:11", NULL);
INSERT INTO `reports`
VALUES (NULL, 1, 1, "Sewer clogged. Flooding on street", 2, 0.3456, "51.4857, -3.1768", "2021-06-15 07:40:10", NULL);
