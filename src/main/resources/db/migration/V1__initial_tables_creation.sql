

CREATE TABLE `persons` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `birthday` date,
  `email` varchar(100) NOT NULL,
  `gender` varchar(50),
  `id_number` varchar(50),
  `lastname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL
);

CREATE TABLE `students` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `created_at` timestamp DEFAULT (now()),
  `created_by` integer NOT NULL,
  `id_classroom` integer,
  `id_person` integer NOT NULL,
  `student_status` ENUM ('ACTIVE', 'INACTIVE') NOT NULL
);

CREATE TABLE `users` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `created_at` timestamp DEFAULT (now()),
  `id_person` integer NOT NULL,
  `last_login` timestamp,
  `nickname` varchar(50),
  `password` varchar(250) NOT NULL,
  `roles` varchar(250) NOT NULL,
  `user_status` ENUM ('ACTIVE', 'INACTIVE', 'BLOCKED', 'RESET', 'DELETED') NOT NULL,
  `username` varchar(50) UNIQUE NOT NULL
);

ALTER TABLE `persons` COMMENT = 'Table to store persons general information';

ALTER TABLE `students` COMMENT = 'Table to store students information';

ALTER TABLE `users` COMMENT = 'Table to store users information';

ALTER TABLE `students` ADD FOREIGN KEY (`id_person`) REFERENCES `persons` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id_person`) REFERENCES `persons` (`id`);

ALTER TABLE `students` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);