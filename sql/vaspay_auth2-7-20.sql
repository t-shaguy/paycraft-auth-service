# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.31)
# Database: vaspay_auth
# Generation Time: 2020-02-07 04:20:40 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table clients_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `clients_info`;

CREATE TABLE `clients_info` (
  `TID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IP_ADDRESS` varchar(25) DEFAULT NULL,
  `ENFORCE_IP` bit(1) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  `CR_DT` datetime DEFAULT NULL,
  `CR_BY` bigint(20) NOT NULL,
  `UP_DT` datetime DEFAULT NULL,
  `CODE` varchar(25) DEFAULT NULL,
  `IV` varchar(45) DEFAULT NULL,
  `C_KEY` varchar(45) DEFAULT NULL,
  `CLIENT_NAME` varchar(200) DEFAULT 'NA',
  `UP_BY` bigint(20) NOT NULL,
  `token_lifespan_days` int(11) NOT NULL DEFAULT '1',
  `partner_ID` varchar(20) NOT NULL DEFAULT '',
  `partner_code` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

LOCK TABLES `clients_info` WRITE;
/*!40000 ALTER TABLE `clients_info` DISABLE KEYS */;

INSERT INTO `clients_info` (`TID`, `IP_ADDRESS`, `ENFORCE_IP`, `STATUS`, `CR_DT`, `CR_BY`, `UP_DT`, `CODE`, `IV`, `C_KEY`, `CLIENT_NAME`, `UP_BY`, `token_lifespan_days`, `partner_ID`, `partner_code`)
VALUES
	(1,'127.0.0.1',b'1',1,NULL,0,NULL,'1234567890','nd4kAi~U0eU5ek!Q','%5Lfauz45MU~jkg?','testdrive@vaspay.com',0,30,'8036533882TST','1234567890'),
	(2,'127.0.0.1',b'1',1,NULL,0,NULL,'12345678','H@zx!I!FPSq?&1W@','rU$Z!ZeOqivY0?o)','integrator@vaspay.com',0,30,'8036533882TST','12345678');

/*!40000 ALTER TABLE `clients_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table e_seq
# ------------------------------------------------------------

DROP TABLE IF EXISTS `e_seq`;

CREATE TABLE `e_seq` (
  `TID` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `SEQ_CODE` varchar(20) DEFAULT NULL,
  `LAST_SEQ` bigint(20) DEFAULT NULL,
  `LENGTH` int(11) DEFAULT NULL,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table resource_sts_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `resource_sts_info`;

CREATE TABLE `resource_sts_info` (
  `TID` bigint(11) NOT NULL AUTO_INCREMENT,
  `STS_DESC` varchar(50) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATED_BY` bigint(11) DEFAULT NULL,
  `UPDATED_BY` bigint(11) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

LOCK TABLES `resource_sts_info` WRITE;
/*!40000 ALTER TABLE `resource_sts_info` DISABLE KEYS */;

INSERT INTO `resource_sts_info` (`TID`, `STS_DESC`, `CREATE_DATE`, `CREATED_BY`, `UPDATED_BY`, `UPDATED_DATE`)
VALUES
	(1,'ACTIVE',NULL,1,1,NULL),
	(2,'IN-ACTIVE',NULL,1,1,NULL),
	(3,'DELETED',NULL,1,1,NULL),
	(4,'PENDING-AUTHORIZATION',NULL,1,1,NULL);

/*!40000 ALTER TABLE `resource_sts_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table status
# ------------------------------------------------------------

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(2) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;

INSERT INTO `status` (`tid`, `code`, `description`)
VALUES
	(1,'00','ACTIVE'),
	(2,'01','IN-ACTIVE'),
	(3,'02','PENDIND-AUTH'),
	(4,'03','DORMANT'),
	(5,'04','BLACK-LISTED'),
	(6,'05','DECLINED'),
	(7,'06','SUSPENDED'),
	(8,'07','EXITED');

/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_data`;

CREATE TABLE `sys_data` (
  `TID` bigint(11) NOT NULL AUTO_INCREMENT,
  `PARAM_NAME` varchar(50) DEFAULT NULL,
  `PARAM_VALUE` varchar(100) DEFAULT NULL,
  `STS` bigint(11) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `AUTH_BY` bigint(20) DEFAULT NULL,
  `AUTH_DATE` datetime DEFAULT NULL,
  `OPR_COMMENT` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`TID`),
  KEY `STS` (`STS`),
  CONSTRAINT `sys_data_ibfk_1` FOREIGN KEY (`STS`) REFERENCES `resource_sts_info` (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

LOCK TABLES `sys_data` WRITE;
/*!40000 ALTER TABLE `sys_data` DISABLE KEYS */;

INSERT INTO `sys_data` (`TID`, `PARAM_NAME`, `PARAM_VALUE`, `STS`, `CREATED_BY`, `CREATED_DATE`, `AUTH_BY`, `AUTH_DATE`, `OPR_COMMENT`)
VALUES
	(3,'SYS_AUTHORIZATION','http://127.0.0.1:8080/fsauthservice/api/auth/',1,1,NULL,NULL,NULL,NULL),
	(4,'MAX-FIRST-LEG','20000',1,1,NULL,NULL,NULL,NULL),
	(5,'REQUESTS-DAILY_REQUEST','3',1,1,NULL,NULL,NULL,NULL),
	(7,'DAILY_LIMIT','5000',1,1,NULL,NULL,NULL,NULL),
	(8,'SESSION_MAX_AGE','30',1,1,NULL,NULL,NULL,NULL),
	(9,'CURRENCY','566',1,1,NULL,NULL,NULL,NULL),
	(14,'TRANS_FEE','50',1,1,NULL,NULL,NULL,NULL),
	(15,'PAN','7777778883301150154',1,1,NULL,NULL,NULL,NULL),
	(16,'NE-OP-CODE','5',1,1,NULL,NULL,NULL,NULL),
	(17,'TOKEN_LENGTH','4',1,1,NULL,NULL,NULL,NULL),
	(18,'REQUEST_ID_LENGTH','20',1,1,NULL,NULL,NULL,NULL),
	(19,'ACCOUNT_LENGTH','10',1,1,NULL,NULL,NULL,NULL),
	(20,'SYS_IV','+!rtmoputy*%1234',1,1,NULL,NULL,NULL,NULL),
	(21,'DS_KEY','+!r:moputy*%0000',1,1,NULL,NULL,NULL,NULL),
	(22,'DEFAULT-TOKEN','0',1,1,NULL,NULL,NULL,NULL),
	(23,'DEFAULT-TOKEN-VALUE','1234',1,1,NULL,NULL,NULL,NULL),
	(24,'MAX-TOKEN-AGE-IN-MINUTES','5',1,1,NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `sys_data` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table wallet_audit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `wallet_audit`;

CREATE TABLE `wallet_audit` (
  `tid` bigint(11) NOT NULL AUTO_INCREMENT,
  `walletid` bigint(20) DEFAULT NULL,
  `accountNo` varchar(20) DEFAULT NULL,
  `activity_info` text,
  `activity_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `accountId` (`walletid`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

LOCK TABLES `wallet_audit` WRITE;
/*!40000 ALTER TABLE `wallet_audit` DISABLE KEYS */;

INSERT INTO `wallet_audit` (`tid`, `walletid`, `accountNo`, `activity_info`, `activity_date`)
VALUES
	(58,16,'1234567890','Token verification on 15593217342672019053','2019-05-31 17:56:08');

/*!40000 ALTER TABLE `wallet_audit` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table wallet_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `wallet_info`;

CREATE TABLE `wallet_info` (
  `tid` bigint(11) NOT NULL AUTO_INCREMENT,
  `walletId` varchar(20) NOT NULL DEFAULT '',
  `v_hash` text NOT NULL,
  `v_hash_1` text NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

LOCK TABLES `wallet_info` WRITE;
/*!40000 ALTER TABLE `wallet_info` DISABLE KEYS */;

INSERT INTO `wallet_info` (`tid`, `walletId`, `v_hash`, `v_hash_1`)
VALUES
	(16,'NA','1','');

/*!40000 ALTER TABLE `wallet_info` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
