DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `sellerId`    int NOT NULL,
    `name`        VARCHAR(50)  DEFAULT 'NO_NAME_ITEM',
    `price`       int          DEFAULT 0,
    `tag`         VARCHAR(30)  DEFAULT 'General',
    `status`      VARCHAR(20)  DEFAULT 'PENDING',
    `description` VARCHAR(1024) DEFAULT 'No description',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;