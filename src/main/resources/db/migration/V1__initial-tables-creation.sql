DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `last_update` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `client` WRITE;

INSERT INTO `client` VALUES (1,'2022-11-07','erik@email.com','2022-11-07','Erik Lima','5511941054345');

UNLOCK TABLES;


DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `creation_date` date NOT NULL,
  `client_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `last_update` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoewar6f18rkn4iptr6da4oysv` (`client_id`),
  KEY `FKm8xumi0g23038cw32oiva2ymw` (`room_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `reservation` WRITE;

INSERT INTO `reservation` VALUES (1,'2022-11-20','2022-11-21','2022-11-07',1,1,'2022-11-07');

UNLOCK TABLES;


DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `creation_date` date NOT NULL,
  `last_update` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `room` WRITE;

INSERT INTO `room` VALUES (1,7,'2022-11-07','2022-11-07');

UNLOCK TABLES;