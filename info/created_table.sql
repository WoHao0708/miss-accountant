CREATE DATABASE `miss_accountant` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `account` (
  `user_id` varchar(100) NOT NULL COMMENT 'User id get by line event.',
  `group_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'Group id get by line event.',
  `name` varchar(50) NOT NULL COMMENT '名稱',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `created_time` datetime DEFAULT NULL COMMENT '建立時間',
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `debt` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(100) NOT NULL COMMENT 'User id get by line event.',
  `group_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'Group id get by line event.',
  `creditor` varchar(100) NOT NULL,
  `amount` int(11) DEFAULT '0' COMMENT '金額',
  `note` varchar(100) DEFAULT '',
  `created_time` datetime DEFAULT NULL COMMENT '建立時間',
  `is_delete` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `public_fund` (
  `group_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'Group id get by line event.',
  `amount` int(11) DEFAULT '0' COMMENT '金額',
  `update_time` datetime DEFAULT NULL COMMENT '建立時間',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
