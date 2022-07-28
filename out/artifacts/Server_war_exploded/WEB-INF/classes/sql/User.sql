DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           int         NOT NULL AUTO_INCREMENT,
    `username`     VARCHAR(50) NOT NULL unique,
    `password`     VARCHAR(20) DEFAULT 'admin123',
    `email`        VARCHAR(30) DEFAULT NULL,
    `primaryPhone` VARCHAR(20) DEFAULT NULL,
    `memberShip`   VARCHAR(20) DEFAULT 'REGULAR',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;