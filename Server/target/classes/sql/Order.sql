DROP TABLE IF EXISTS `orderTable`;
CREATE TABLE `orderTable` (
                                   `id` VARCHAR(255) NOT NULL,
                                   `buyerId` int NOT NULL,
                                   `itemId` int NOT NULL,
                                   `status` VARCHAR(30) DEFAULT 'PROCESSING',
                                   `courier` VARCHAR(30) DEFAULT NULL,
                                   `courierFee` int DEFAULT 0,
                                   `createTime` datetime DEFAULT now(),
                                   `completeTime` datetime DEFAULT NULL,
                                   `comment` VARCHAR(60) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
