
-- 导出 my_test 的数据库结构
CREATE DATABASE IF NOT EXISTS `my_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `my_test`;

-- 导出  表 my_test.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `first_name` varchar(100) DEFAULT NULL COMMENT '名字',
  `sur_name` varchar(100) DEFAULT NULL COMMENT '姓式',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `date_joined` date DEFAULT NULL COMMENT '入会日期',
  `spend_to_date` decimal(20,2) DEFAULT NULL COMMENT '消费总额',
  `account_balance` decimal(20,2) DEFAULT NULL COMMENT '会员积分',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `user_type` tinyint(4) unsigned zerofill DEFAULT '0000' COMMENT '用户类型，0：会员，1：员工',
  `latitude` decimal(20,6) DEFAULT NULL,
  `longitude` decimal(20,6) DEFAULT NULL,
  `easting` int(11) DEFAULT NULL,
  `northing` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  my_test.user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `first_name`, `sur_name`, `phone`, `email`, `date_joined`, `spend_to_date`, `account_balance`, `address`, `user_type`, `latitude`, `longitude`, `easting`, `northing`) VALUES
	(1, 'john', 'doe', '88888 888888', 'john.doe@kcoffee.com', '2020-01-01', NULL, NULL, NULL, 0001, NULL, NULL, NULL, NULL),
	(2, 'Zephr', 'Maddox', '07557 684068	', 'ornare.tortor@vitaerisus.edu', '2017-03-09', 553.39, 21.07, 'SE15 4HA', 0000, 51.463757, -0.066800, 534390, 175637);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
