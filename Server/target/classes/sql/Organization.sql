DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                `organization_id` VARCHAR(32) NOT NULL UNIQUE,
                                `upper_organization_id` VARCHAR(32),
                                `organization_type` TINYINT(1),
                                `organization_name` VARCHAR(32),
                                `organization_desc` VARCHAR(128),
                                `organization_admin_account` VARCHAR(32),
                                `organization_location` VARCHAR(64),
                                `organization_contact_info` VARCHAR(64),
                                `gmt8_create` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                `gmt8_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                `is_disabled` int(1) unsigned DEFAULT 0,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;