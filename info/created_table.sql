CREATE DATABASE `miss_accountant` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(100) NOT NULL COMMENT 'User id get by line event.',
  `group_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'Group id get by line event.',
  `name` varchar(50) NOT NULL COMMENT '名稱',
  `amount` int(11) DEFAULT '0' COMMENT '帳戶金額',
  `advance` int(11) DEFAULT '0' COMMENT '預支金額',
  `is_advance` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否要被分帳',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `created_time` datetime DEFAULT NULL COMMENT '建立時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(100) NOT NULL COMMENT 'User id get by line event.',
  `group_id` varchar(100) DEFAULT '' COMMENT 'Group id get by line event.',
  `amount` int(11) DEFAULT '0' COMMENT '金額',
  `created_time` datetime DEFAULT NULL COMMENT '建立時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
