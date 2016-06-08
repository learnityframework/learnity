-- MySQL dump 10.13  Distrib 5.6.25, for Win32 (x86)
--
-- Host: localhost    Database: learnityframework
-- ------------------------------------------------------
-- Server version	5.6.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `add_action_param`
--

DROP TABLE IF EXISTS `add_action_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `add_action_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `action_sequence` varchar(10) DEFAULT NULL,
  `action_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `add_action_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `add_action_param`
--

LOCK TABLES `add_action_param` WRITE;
/*!40000 ALTER TABLE `add_action_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `add_action_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `add_param`
--

DROP TABLE IF EXISTS `add_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `add_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `add_param_value` blob,
  `add_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `add_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `add_param`
--

LOCK TABLES `add_param` WRITE;
/*!40000 ALTER TABLE `add_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `add_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `add_validation_param`
--

DROP TABLE IF EXISTS `add_validation_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `add_validation_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `add_param_value` blob,
  `add_parameter` blob,
  `message` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `function_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `add_validation_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `add_validation_param`
--

LOCK TABLES `add_validation_param` WRITE;
/*!40000 ALTER TABLE `add_validation_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `add_validation_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_user`
--

DROP TABLE IF EXISTS `admin_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_user` (
  `user_id` varchar(25) NOT NULL,
  `password` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_user`
--

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;
INSERT INTO `admin_user` VALUES ('superadmin','superadmin');
/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator_creation_details`
--

DROP TABLE IF EXISTS `administrator_creation_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator_creation_details` (
  `user_id` varchar(25) NOT NULL,
  `date_user_created` datetime DEFAULT NULL,
  `user_created_by` varchar(25) DEFAULT NULL,
  `last_modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator_creation_details`
--

LOCK TABLES `administrator_creation_details` WRITE;
/*!40000 ALTER TABLE `administrator_creation_details` DISABLE KEYS */;
INSERT INTO `administrator_creation_details` VALUES ('superadmin',now(),'Install Script',now());
/*!40000 ALTER TABLE `administrator_creation_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator_details`
--

DROP TABLE IF EXISTS `administrator_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator_details` (
  `user_id` varchar(25) NOT NULL,
  `user_first_name` varchar(50) NOT NULL,
  `user_middle_name` varchar(50) DEFAULT NULL,
  `user_last_name` varchar(50) NOT NULL,
  `gender` varchar(8) DEFAULT NULL,
  `email_id` varchar(25) DEFAULT NULL,
  `department` varchar(25) DEFAULT NULL,
  `educational_qualification` varchar(25) DEFAULT NULL,
  `age` varchar(2) DEFAULT NULL,
  `account_status` varchar(25) DEFAULT NULL,
  `experience` varchar(10) DEFAULT NULL,
  `access_priviledge` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator_details`
--

LOCK TABLES `administrator_details` WRITE;
/*!40000 ALTER TABLE `administrator_details` DISABLE KEYS */;
INSERT INTO `administrator_details` VALUES ('superadmin','superadmin',NULL,'superadmin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `administrator_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator_logged_in_at`
--

DROP TABLE IF EXISTS `administrator_logged_in_at`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator_logged_in_at` (
  `user_id` varchar(25) NOT NULL,
  `logged_in_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator_logged_in_at`
--

LOCK TABLES `administrator_logged_in_at` WRITE;
/*!40000 ALTER TABLE `administrator_logged_in_at` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrator_logged_in_at` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator_login_time`
--

DROP TABLE IF EXISTS `administrator_login_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator_login_time` (
  `user_id` varchar(25) NOT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator_login_time`
--

LOCK TABLES `administrator_login_time` WRITE;
/*!40000 ALTER TABLE `administrator_login_time` DISABLE KEYS */;
 INSERT INTO `administrator_login_time` VALUES ('superadmin',NOW());
/*!40000 ALTER TABLE `administrator_login_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator_previlege_management`
--

DROP TABLE IF EXISTS `administrator_previlege_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator_previlege_management` (
  `user_id` varchar(25) NOT NULL,
  `previledge` varchar(35) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator_previlege_management`
--

LOCK TABLES `administrator_previlege_management` WRITE;
/*!40000 ALTER TABLE `administrator_previlege_management` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrator_previlege_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_template_asset`
--

DROP TABLE IF EXISTS `application_template_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_template_asset` (
  `application_template_id` int(11) NOT NULL DEFAULT '0',
  `delivery_mode` varchar(300) DEFAULT NULL,
  `asset_type` varchar(300) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `sequence_no` int(10) NOT NULL DEFAULT '0',
  `file_name` varchar(300) DEFAULT NULL,
  `asset_path` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`sequence_no`,`application_template_id`),
  KEY `application_template_asset1` (`sequence_no`,`application_template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_template_asset`
--

LOCK TABLES `application_template_asset` WRITE;
/*!40000 ALTER TABLE `application_template_asset` DISABLE KEYS */;

/*!40000 ALTER TABLE `application_template_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_template_default`
--

DROP TABLE IF EXISTS `application_template_default`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_template_default` (
  `application_template_id` int(11) NOT NULL DEFAULT '0',
  `section_name` varchar(100) NOT NULL DEFAULT '',
  `class_name` varchar(100) NOT NULL DEFAULT '',
  `attribute_name` varchar(100) NOT NULL DEFAULT '',
  `default_value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`application_template_id`,`section_name`,`class_name`,`attribute_name`),
  KEY `application_template_default1` (`application_template_id`,`section_name`,`class_name`,`attribute_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_template_default`
--

LOCK TABLES `application_template_default` WRITE;
/*!40000 ALTER TABLE `application_template_default` DISABLE KEYS */;

/*!40000 ALTER TABLE `application_template_default` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_template_master`
--

DROP TABLE IF EXISTS `application_template_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_template_master` (
  `application_template_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `application_template_title` varchar(300) DEFAULT NULL,
  `templatecomment` varchar(300) DEFAULT NULL,
  `upload_on` datetime NOT NULL,
  `default_value` varchar(100) DEFAULT NULL,
  `ui_framework` varchar(100) DEFAULT 'jQueryUI' NOT NULL,
  `block_ui_timeout` int(10) DEFAULT 2000 NOT NULL,
  `applivation_xml_value` blob,
  UNIQUE (application_template_title),
  PRIMARY KEY (`application_template_id`),
  KEY `application_template_master1` (`application_template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_template_master`
--

LOCK TABLES `application_template_master` WRITE;
/*!40000 ALTER TABLE `application_template_master` DISABLE KEYS */;

/*!40000 ALTER TABLE `application_template_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `behaviour`
--

DROP TABLE IF EXISTS `behaviour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `behaviour` (
  `event_id` int(10) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `behaviour_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `value` varchar(100) DEFAULT NULL,
  `valuetype` varchar(100) DEFAULT NULL,
  `target` varchar(100) DEFAULT NULL,
  `behaviourevent` varchar(100) DEFAULT NULL,
  `parameter` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `callback` varchar(100) DEFAULT NULL,
  `resourceid` varchar(100) DEFAULT NULL,
  `javaclass` varchar(100) DEFAULT NULL,
  `package` varchar(100) DEFAULT NULL,
  `resource_location` varchar(100) DEFAULT NULL,
  `dom_ready` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`interface_id`,`behaviour_id`,`part_id`),
  KEY `behaviour1` (`interface_id`,`behaviour_id`,`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6593 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `behaviour`
--

LOCK TABLES `behaviour` WRITE;
/*!40000 ALTER TABLE `behaviour` DISABLE KEYS */;
/*!40000 ALTER TABLE `behaviour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cache_definition`
--

DROP TABLE IF EXISTS `cache_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cache_definition` (
  `cache_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `cache_name` varchar(100) NOT NULL DEFAULT '',
  `cache_type` varchar(20) DEFAULT NULL,
  `max_element` int(11) NOT NULL,
  `overflowtodisk` tinyint(1) NOT NULL DEFAULT '1',
  `timetoliveseconds` int(11) NOT NULL DEFAULT '0',
  `timetoidleseconds` int(11) NOT NULL DEFAULT '0',
  `diskpersistent` tinyint(1) NOT NULL DEFAULT '0',
  `eternal` tinyint(1) NOT NULL DEFAULT '0',
  `diskexpirythreadintervalseconds` int(11) NOT NULL DEFAULT '0',
  `memorystoreevictionpolicy` varchar(10) NOT NULL DEFAULT 'LFU',
  `diskstorepath` varchar(250) DEFAULT NULL,
  `default_cache` varchar(10) NOT NULL DEFAULT 'No',
  PRIMARY KEY (`cache_id`),
  UNIQUE KEY `cache_name` (`cache_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cache_definition`
--

LOCK TABLES `cache_definition` WRITE;
/*!40000 ALTER TABLE `cache_definition` DISABLE KEYS */;
/*!40000 ALTER TABLE `cache_definition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `columnmodel`
--

DROP TABLE IF EXISTS `columnmodel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `columnmodel` (
  `interface_id` varchar(100) NOT NULL,
  `part_id` varchar(100) NOT NULL,
  `col_head` varchar(100) DEFAULT NULL,
  `col_name` varchar(100) NOT NULL,
  `col_index` int(11) DEFAULT NULL,
  `col_width` varchar(100) DEFAULT NULL,
  `col_editable` varchar(100) DEFAULT NULL,
  `col_hidden` varchar(100) DEFAULT NULL,
  `key_value` varchar(100) DEFAULT NULL,
  `required` varchar(100) DEFAULT NULL,
  `minval` varchar(100) DEFAULT NULL,
  `maxval` varchar(100) DEFAULT NULL,
  `dbcolumnname` varchar(200) DEFAULT NULL,
  `edithidden` varchar(200) DEFAULT NULL,
  `influence` varchar(100) DEFAULT NULL,
  `email` varchar(300) DEFAULT NULL,
  `number_check` varchar(300) DEFAULT NULL,
  `custom` varchar(300) DEFAULT NULL,
  `custom_func` varchar(300) DEFAULT NULL,
  `default_type` varchar(200) DEFAULT NULL,
  `default_value` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`col_name`),
  KEY `columnmodel1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `columnmodel`
--

LOCK TABLES `columnmodel` WRITE;
/*!40000 ALTER TABLE `columnmodel` DISABLE KEYS */;
/*!40000 ALTER TABLE `columnmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_item`
--

DROP TABLE IF EXISTS `configuration_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_item` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `createsession` varchar(100) DEFAULT NULL,
  `checkrole` varchar(100) DEFAULT NULL,
  `contenttype` varchar(100) DEFAULT NULL,
  `doctypepublic` varchar(100) DEFAULT NULL,
  `doctypesystem` varchar(100) DEFAULT NULL,
  `cachecontrol` varchar(300) DEFAULT NULL,
  `expires` varchar(300) DEFAULT NULL,
  `lastmodify` varchar(300) DEFAULT NULL,
  `template` varchar(300) DEFAULT NULL,
  `themes_id` varchar(200) DEFAULT NULL,
  `enable_caching` varchar(10) NOT NULL DEFAULT ' ',
  `cache_name` varchar(200) DEFAULT NULL,
  `cache_dynamic_js` varchar(10) NOT NULL DEFAULT ' ',
  `cache_dynamic_css` varchar(10) NOT NULL DEFAULT ' ',
  `cache_dynamic_image` varchar(10) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`interface_id`),
  KEY `configuration_item1` (`interface_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_item`
--

LOCK TABLES `configuration_item` WRITE;
/*!40000 ALTER TABLE `configuration_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuration_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content` (
  `menu_item_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `value` longblob,
  `contenttype` varchar(100) DEFAULT NULL,
  `active_value` longblob,
  `contentlocation` varchar(100) DEFAULT NULL,
  `show_choose_one` varchar(100) DEFAULT NULL,
  `choose_one_label` varchar(200) DEFAULT NULL,
  `choose_one_value` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`menu_item_id`,`interface_id`,`content_id`,`part_id`),
  KEY `content1` (`interface_id`,`content_id`,`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6722 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delete_action_param`
--

DROP TABLE IF EXISTS `delete_action_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delete_action_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `action_sequence` varchar(10) DEFAULT NULL,
  `action_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `delete_action_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delete_action_param`
--

LOCK TABLES `delete_action_param` WRITE;
/*!40000 ALTER TABLE `delete_action_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `delete_action_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delete_param`
--

DROP TABLE IF EXISTS `delete_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delete_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `delete_param_value` blob,
  `delete_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `delete_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delete_param`
--

LOCK TABLES `delete_param` WRITE;
/*!40000 ALTER TABLE `delete_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `delete_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delete_validation_param`
--

DROP TABLE IF EXISTS `delete_validation_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delete_validation_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `delete_param_value` blob,
  `delete_parameter` blob,
  `message` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `function_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `delete_validation_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delete_validation_param`
--

LOCK TABLES `delete_validation_param` WRITE;
/*!40000 ALTER TABLE `delete_validation_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `delete_validation_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dropdownmenu`
--

DROP TABLE IF EXISTS `dropdownmenu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dropdownmenu` (
  `menu_item_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) DEFAULT NULL,
  `part_id` varchar(100) NOT NULL,
  `name` varchar(300) DEFAULT NULL,
  `value` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`menu_item_id`),
  KEY `dropdownmenu1` (`interface_id`,`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dropdownmenu`
--

LOCK TABLES `dropdownmenu` WRITE;
/*!40000 ALTER TABLE `dropdownmenu` DISABLE KEYS */;
/*!40000 ALTER TABLE `dropdownmenu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edit_type`
--

DROP TABLE IF EXISTS `edit_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edit_type` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `col_name` varchar(100) NOT NULL DEFAULT '',
  `type` varchar(100) DEFAULT NULL,
  `size` varchar(100) DEFAULT NULL,
  `rows` varchar(100) DEFAULT NULL,
  `cols` varchar(100) DEFAULT NULL,
  `editdomaintype` varchar(100) DEFAULT NULL,
  `value` blob,
  `active_value` blob,
  `multiple` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`col_name`),
  KEY `edit_type1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edit_type`
--

LOCK TABLES `edit_type` WRITE;
/*!40000 ALTER TABLE `edit_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `edit_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flash_parameter`
--

DROP TABLE IF EXISTS `flash_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flash_parameter` (
  `interface_flash_parameter_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `parameter_name` varchar(200) DEFAULT NULL,
  `parameter_value` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`interface_flash_parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flash_parameter`
--

LOCK TABLES `flash_parameter` WRITE;
/*!40000 ALTER TABLE `flash_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `flash_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_add_param`
--

DROP TABLE IF EXISTS `form_add_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_add_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `add_param_value` blob,
  `add_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `form_add_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_add_param`
--

LOCK TABLES `form_add_param` WRITE;
/*!40000 ALTER TABLE `form_add_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_add_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_delete_param`
--

DROP TABLE IF EXISTS `form_delete_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_delete_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `delete_param_value` blob,
  `delete_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `form_delete_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_delete_param`
--

LOCK TABLES `form_delete_param` WRITE;
/*!40000 ALTER TABLE `form_delete_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_delete_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_element`
--

DROP TABLE IF EXISTS `form_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_element` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `element_id` varchar(100) NOT NULL DEFAULT '',
  `element_type` varchar(100) DEFAULT NULL,
  `element_key` varchar(100) DEFAULT NULL,
  `selectindex` int(11) DEFAULT '0',
  `modifyindex` int(11) DEFAULT '0',
  `insertindex` int(11) DEFAULT '0',
  `forlabel` varchar(100) DEFAULT NULL,
  `required` varchar(100) DEFAULT NULL,
  `minlength` varchar(50) DEFAULT NULL,
  `maxlength` varchar(50) DEFAULT NULL,
  `equalto` varchar(50) DEFAULT NULL,
  `numbercheck` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `requiredmess` varchar(200) DEFAULT NULL,
  `minlengthmess` varchar(200) DEFAULT NULL,
  `maxlengthmess` varchar(200) DEFAULT NULL,
  `equaltomess` varchar(200) DEFAULT NULL,
  `numbercheckmess` varchar(200) DEFAULT NULL,
  `emailmess` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`element_id`),
  KEY `form_element1` (`interface_id`,`part_id`,`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_element`
--

LOCK TABLES `form_element` WRITE;
/*!40000 ALTER TABLE `form_element` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_modify_param`
--

DROP TABLE IF EXISTS `form_modify_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_modify_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `modify_param_value` blob,
  `modify_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `form_modify_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_modify_param`
--

LOCK TABLES `form_modify_param` WRITE;
/*!40000 ALTER TABLE `form_modify_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_modify_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_select_param`
--

DROP TABLE IF EXISTS `form_select_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_select_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `select_param_value` blob,
  `select_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `form_select_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_select_param`
--

LOCK TABLES `form_select_param` WRITE;
/*!40000 ALTER TABLE `form_select_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_select_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formelement`
--

DROP TABLE IF EXISTS `formelement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formelement` (
  `interface_id` varchar(100) NOT NULL,
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `formmethod` varchar(100) DEFAULT NULL,
  `formaction` varchar(100) DEFAULT NULL,
  `jscontrol` varchar(100) DEFAULT NULL,
  `success_method` varchar(200) DEFAULT NULL,
  `failure_method` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `formelement1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formelement`
--

LOCK TABLES `formelement` WRITE;
/*!40000 ALTER TABLE `formelement` DISABLE KEYS */;
/*!40000 ALTER TABLE `formelement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `framework_asset`
--

DROP TABLE IF EXISTS `framework_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `framework_asset` (
  `asset_id` int(9) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(200) DEFAULT NULL,
  `upload_on` datetime DEFAULT NULL,
  `asset_type` varchar(300) DEFAULT NULL,
  `value` blob,
  PRIMARY KEY (`asset_id`),
  KEY `framework_asset1` (`asset_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `framework_asset`
--

LOCK TABLES `framework_asset` WRITE;
/*!40000 ALTER TABLE `framework_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `framework_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `framework_file`
--

DROP TABLE IF EXISTS `framework_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `framework_file` (
  `framework_file_id` varchar(100) NOT NULL,
  `framework_file_title` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `filesize` varchar(100) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `inlinecss` varchar(100) DEFAULT NULL,
  `inlinejs` varchar(100) DEFAULT NULL,
  `imagepath` varchar(100) DEFAULT NULL,
  `value` longblob,
  `last_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`framework_file_id`),
  KEY `framework_file1` (`framework_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `framework_file`
--

LOCK TABLES `framework_file` WRITE;
/*!40000 ALTER TABLE `framework_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `framework_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grid_structure`
--

DROP TABLE IF EXISTS `grid_structure`;

CREATE TABLE `grid_structure` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `altRows` varchar(1) DEFAULT NULL,
  `autowidth` varchar(1) DEFAULT NULL,
  `ignoreCase` varchar(1) DEFAULT NULL,
  `rowNumbers` varchar(1) DEFAULT NULL,
  `altClass` varchar(100) DEFAULT NULL,
  `searchonEnter` varchar(1) DEFAULT NULL,
  `columnChooser` varchar(1) DEFAULT NULL,
  `toolbarSearch` varchar(1) DEFAULT NULL,
  `shrinkToFit` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `grid_structure1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 


--
-- Table structure for table `grid_column_snipet`
--

DROP TABLE IF EXISTS `grid_column_snipet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grid_column_snipet` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `column_name` varchar(100) NOT NULL DEFAULT '',
  `grid_snipet` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`column_name`),
  KEY `grid_column_snipet1` (`interface_id`,`part_id`,`column_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grid_column_snipet`
--

LOCK TABLES `grid_column_snipet` WRITE;
/*!40000 ALTER TABLE `grid_column_snipet` DISABLE KEYS */;
/*!40000 ALTER TABLE `grid_column_snipet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gridquery`
--

DROP TABLE IF EXISTS `gridquery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gridquery` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `grid_query` blob,
  `active_query` blob,
  `load_parameter` blob,
  `server_cache_grid` varchar(10) NOT NULL DEFAULT ' ',
  `cachekey_includeuserid` varchar(10) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `gridquery1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gridquery`
--

LOCK TABLES `gridquery` WRITE;
/*!40000 ALTER TABLE `gridquery` DISABLE KEYS */;
/*!40000 ALTER TABLE `gridquery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interface`
--

DROP TABLE IF EXISTS `interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interface` (
  `interface_id` varchar(100) NOT NULL,
  `interface_title` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `filesize` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`),
  KEY `interface1` (`interface_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interface`
--

LOCK TABLES `interface` WRITE;
/*!40000 ALTER TABLE `interface` DISABLE KEYS */;
/*!40000 ALTER TABLE `interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interface_chart_content`
--

DROP TABLE IF EXISTS `interface_chart_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interface_chart_content` (
  `interface_chart_item_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `metric_title` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `X_axis_query` blob,
  `series1_query` blob,
  `series2_query` blob,
  `series3_query` blob,
  `legenddata1` varchar(50) DEFAULT NULL,
  `legenddata2` varchar(50) DEFAULT NULL,
  `legenddata3` varchar(50) DEFAULT NULL,
  `chart_type` varchar(50) DEFAULT NULL,
  `subtype` varchar(50) DEFAULT NULL,
  `width` int(6) NOT NULL DEFAULT '0',
  `height` int(6) NOT NULL DEFAULT '0',
  `yaxis_label` varchar(100) DEFAULT NULL,
  `xaxis_label` varchar(100) DEFAULT NULL,
  `color` int(10) NOT NULL DEFAULT '0',
  `transpose` varchar(5) DEFAULT NULL,
  `stacked` varchar(5) DEFAULT NULL,
  `chart_dimension` varchar(40) NOT NULL DEFAULT 'TwoDimensional',
  PRIMARY KEY (`interface_chart_item_id`,`interface_id`,`content_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interface_chart_content`
--

LOCK TABLES `interface_chart_content` WRITE;
/*!40000 ALTER TABLE `interface_chart_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `interface_chart_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interface_report_content`
--

DROP TABLE IF EXISTS `interface_report_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interface_report_content` (
  `interface_report_item_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `rptdesign_file_name` varchar(100) DEFAULT NULL,
  `viewer_type` varchar(100) DEFAULT NULL,
  `report_chooser` varchar(100) DEFAULT NULL,
  `report_executer` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_report_item_id`,`interface_id`,`content_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interface_report_content`
--

LOCK TABLES `interface_report_content` WRITE;
/*!40000 ALTER TABLE `interface_report_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `interface_report_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interfaceenginecalling`
--

DROP TABLE IF EXISTS `interfaceenginecalling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interfaceenginecalling` (
  `interface_id` varchar(100) NOT NULL,
  `valueblob` longblob,
  `layout_id` varchar(100) NOT NULL DEFAULT '',
  `content_id` varchar(100) NOT NULL DEFAULT '',
  `behaviour_id` varchar(100) NOT NULL DEFAULT '',
  `style_id` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`interface_id`,`layout_id`,`content_id`,`behaviour_id`,`style_id`),
  KEY `interfaceenginecalling1` (`interface_id`,`layout_id`,`content_id`,`behaviour_id`,`style_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interfaceenginecalling`
--

LOCK TABLES `interfaceenginecalling` WRITE;
/*!40000 ALTER TABLE `interfaceenginecalling` DISABLE KEYS */;
/*!40000 ALTER TABLE `interfaceenginecalling` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keycolumn`
--

DROP TABLE IF EXISTS `keycolumn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keycolumn` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `keycolumn_value` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `keycolumn1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keycolumn`
--

LOCK TABLES `keycolumn` WRITE;
/*!40000 ALTER TABLE `keycolumn` DISABLE KEYS */;
/*!40000 ALTER TABLE `keycolumn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layout`
--

DROP TABLE IF EXISTS `layout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layout` (
  `layout_interface_id` int(10) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL,
  `layout_id` varchar(100) DEFAULT NULL,
  `part_id` varchar(100) DEFAULT NULL,
  `height` varchar(100) DEFAULT NULL,
  `width` varchar(100) NOT NULL,
  `x` varchar(100) DEFAULT NULL,
  `y` varchar(100) NOT NULL,
  `position` varchar(100) DEFAULT NULL,
  `parent_id` varchar(100) NOT NULL,
  PRIMARY KEY (`layout_interface_id`),
  KEY `layout1` (`interface_id`,`layout_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9467 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layout`
--

LOCK TABLES `layout` WRITE;
/*!40000 ALTER TABLE `layout` DISABLE KEYS */;
/*!40000 ALTER TABLE `layout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manifestinterfaceassociation`
--

DROP TABLE IF EXISTS `manifestinterfaceassociation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manifestinterfaceassociation` (
  `manifest_id` varchar(100) NOT NULL DEFAULT '',
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`manifest_id`,`interface_id`),
  KEY `manifestinterfaceassociation1` (`interface_id`,`manifest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manifestinterfaceassociation`
--

LOCK TABLES `manifestinterfaceassociation` WRITE;
/*!40000 ALTER TABLE `manifestinterfaceassociation` DISABLE KEYS */;
/*!40000 ALTER TABLE `manifestinterfaceassociation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modify_action_param`
--

DROP TABLE IF EXISTS `modify_action_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modify_action_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `action_sequence` varchar(10) DEFAULT NULL,
  `action_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `modify_action_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modify_action_param`
--

LOCK TABLES `modify_action_param` WRITE;
/*!40000 ALTER TABLE `modify_action_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `modify_action_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modify_param`
--

DROP TABLE IF EXISTS `modify_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modify_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `modify_param_value` blob,
  `modify_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `modify_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modify_param`
--

LOCK TABLES `modify_param` WRITE;
/*!40000 ALTER TABLE `modify_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `modify_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modify_validation_param`
--

DROP TABLE IF EXISTS `modify_validation_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modify_validation_param` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `query_id` varchar(100) NOT NULL DEFAULT '',
  `modify_param_value` blob,
  `modify_parameter` blob,
  `message` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `function_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`query_id`),
  KEY `modify_validation_param1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modify_validation_param`
--

LOCK TABLES `modify_validation_param` WRITE;
/*!40000 ALTER TABLE `modify_validation_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `modify_validation_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optionmenu`
--

DROP TABLE IF EXISTS `optionmenu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optionmenu` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL,
  `option_id` varchar(100) NOT NULL,
  `labelname` varchar(100) DEFAULT NULL,
  `labelvalue` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`,`option_id`),
  KEY `optionmenu1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optionmenu`
--

LOCK TABLES `optionmenu` WRITE;
/*!40000 ALTER TABLE `optionmenu` DISABLE KEYS */;
/*!40000 ALTER TABLE `optionmenu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_parameter`
--

DROP TABLE IF EXISTS `report_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_parameter` (
  `interface_report_parameter_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `interface_id` varchar(100) NOT NULL,
  `content_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `parameter_name` varchar(200) NOT NULL DEFAULT '',
  `parameter_value` varchar(300) DEFAULT NULL,
  `parameter_value_type` varchar(300) DEFAULT NULL,
  `value_source_item_name` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`interface_report_parameter_id`,`interface_id`,`content_id`,`part_id`,`parameter_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_parameter`
--

LOCK TABLES `report_parameter` WRITE;
/*!40000 ALTER TABLE `report_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `interface_id` varchar(100) NOT NULL,
  `resource_id` varchar(100) NOT NULL DEFAULT '',
  `value` longblob,
  `href` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `keyvalue` varchar(100) DEFAULT NULL,
  `resource_location` varchar(100) DEFAULT NULL,
  `size` VARCHAR(100) NULL,
  `date` TIMESTAMP DEFAULT NOW() NOT NULL,
  `uploaded_by` VARCHAR(25) NULL,
  PRIMARY KEY (`interface_id`,`resource_id`),
  KEY `resource1` (`interface_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_type`
--

DROP TABLE IF EXISTS `resource_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_type` (
  `resource_type_id` varchar(100) NOT NULL,
  `resource_type_title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`resource_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_type`
--

LOCK TABLES `resource_type` WRITE;
/*!40000 ALTER TABLE `resource_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_type` ENABLE KEYS */;

INSERT INTO `resource_type` (resource_type_id, resource_type_title) VALUES ('Interfacexml', 'Interfacexml');

INSERT INTO `resource_type` (resource_type_id, resource_type_title) VALUES ('Resources', 'Resources');


UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `role1` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'DEFAULT',NULL),(2,'ADMIN',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roleassociation`
--

DROP TABLE IF EXISTS `roleassociation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roleassociation` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `role_id` varchar(100) NOT NULL,
  `layout_id` varchar(100) DEFAULT NULL,
  `style_id` varchar(100) DEFAULT NULL,
  `content_id` varchar(100) DEFAULT NULL,
  `behaviour_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`,`interface_id`),
  KEY `roleassociation1` (`interface_id`,`role_id`,`layout_id`,`style_id`,`content_id`,`behaviour_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roleassociation`
--

LOCK TABLES `roleassociation` WRITE;
/*!40000 ALTER TABLE `roleassociation` DISABLE KEYS */;
/*!40000 ALTER TABLE `roleassociation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selector`
--

DROP TABLE IF EXISTS `selector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `selector` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `selector_id` varchar(100) NOT NULL DEFAULT '',
  `gridrefresh` varchar(100) DEFAULT NULL,
  `influence` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`selector_id`,`part_id`),
  KEY `selector1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selector`
--

LOCK TABLES `selector` WRITE;
/*!40000 ALTER TABLE `selector` DISABLE KEYS */;
/*!40000 ALTER TABLE `selector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `snippet`
--

DROP TABLE IF EXISTS `snippet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `snippet` (
  `interface_id` varchar(100) NOT NULL,
  `snippet_id` varchar(100) NOT NULL DEFAULT '',
  `value` longblob,
  PRIMARY KEY (`interface_id`,`snippet_id`),
  KEY `snippet1` (`interface_id`,`snippet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snippet`
--

LOCK TABLES `snippet` WRITE;
/*!40000 ALTER TABLE `snippet` DISABLE KEYS */;
/*!40000 ALTER TABLE `snippet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `structure`
--

DROP TABLE IF EXISTS `structure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `structure` (
  `interface_id` varchar(100) NOT NULL,
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `part_class` varchar(100) NOT NULL,
  `resize` varchar(100) NOT NULL DEFAULT ' ',
  `border` varchar(100) NOT NULL DEFAULT ' ',
  `cols` varchar(100) NOT NULL DEFAULT ' ',
  `rows` varchar(100) NOT NULL DEFAULT ' ',
  `scrolling` varchar(100) NOT NULL DEFAULT ' ',
  `spacing` varchar(100) NOT NULL DEFAULT ' ',
  `colspan` varchar(100) NOT NULL DEFAULT ' ',
  `length` varchar(100) NOT NULL DEFAULT ' ',
  `size` varchar(100) NOT NULL DEFAULT ' ',
  `tabindex` varchar(100) NOT NULL DEFAULT ' ',
  `archieve` varchar(100) NOT NULL DEFAULT ' ',
  `codebase` varchar(100) NOT NULL DEFAULT ' ',
  `mayscript` varchar(100) NOT NULL DEFAULT ' ',
  `loadurl` varchar(200) NOT NULL DEFAULT ' ',
  `editurl` varchar(200) NOT NULL DEFAULT ' ',
  `caption` varchar(100) NOT NULL DEFAULT ' ',
  `sortname` varchar(300) NOT NULL DEFAULT ' ',
  `sortorder` varchar(100) NOT NULL DEFAULT ' ',
  `gridhidden` varchar(100) NOT NULL DEFAULT ' ',
  `gridnavbar` varchar(100) NOT NULL DEFAULT ' ',
  `multiselect` varchar(100) DEFAULT NULL,
  `multiboxonly` varchar(100) DEFAULT NULL,
  `gridrowNum` varchar(100) DEFAULT NULL,
  `gridrowList` varchar(100) DEFAULT NULL,
  `dateformat` varchar(200) DEFAULT NULL,
  `resetSearchOnClose` varchar(100) DEFAULT NULL,
  `multiplesearch` varchar(100) DEFAULT NULL,
  `customeditbutton` varchar(100) DEFAULT NULL,
  `griddata` varchar(100) DEFAULT NULL,
  `griddatatype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `structure1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `structure`
--

LOCK TABLES `structure` WRITE;
/*!40000 ALTER TABLE `structure` DISABLE KEYS */;
/*!40000 ALTER TABLE `structure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `style`
--

DROP TABLE IF EXISTS `style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `style` (
  `interface_id` varchar(100) NOT NULL,
  `style_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `value` blob,
  `styletype` varchar(100) NOT NULL,
  `resource_id` varchar(100),
  PRIMARY KEY (`interface_id`,`part_id`,`style_id`,`styletype`),
  KEY `style1` (`interface_id`,`style_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `style`
--

LOCK TABLES `style` WRITE;
/*!40000 ALTER TABLE `style` DISABLE KEYS */;
/*!40000 ALTER TABLE `style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `themes`
--

DROP TABLE IF EXISTS `themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `themes` (
  `themes_id` varchar(200) NOT NULL DEFAULT '',
  `default_value` varchar(100) DEFAULT NULL,
  `xml_value` blob,
  `upload_on` datetime NOT NULL,
  PRIMARY KEY (`themes_id`),
  KEY `themes1` (`themes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `themes`
--

LOCK TABLES `themes` WRITE;
/*!40000 ALTER TABLE `themes` DISABLE KEYS */;
/*!40000 ALTER TABLE `themes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `themes_css`
--

DROP TABLE IF EXISTS `themes_css`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `themes_css` (
  `themes_css_id` int(9) NOT NULL AUTO_INCREMENT,
  `themes_id` varchar(200) DEFAULT NULL,
  `css_value` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`themes_css_id`),
  KEY `themes_css1` (`themes_css_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `themes_css`
--

LOCK TABLES `themes_css` WRITE;
/*!40000 ALTER TABLE `themes_css` DISABLE KEYS */;
/*!40000 ALTER TABLE `themes_css` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `themes_definition`
--

DROP TABLE IF EXISTS `themes_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `themes_definition` (
  `themes_id` varchar(200) DEFAULT NULL,
  `themes_element_id` int(9) NOT NULL AUTO_INCREMENT,
  `class_type` varchar(200) DEFAULT NULL,
  `prop_type` varchar(200) DEFAULT NULL,
  `css_classes` varchar(200) DEFAULT NULL,
  `properties` blob,
  `property_application` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`themes_element_id`),
  KEY `themes_definition1` (`themes_element_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `themes_definition`
--

LOCK TABLES `themes_definition` WRITE;
/*!40000 ALTER TABLE `themes_definition` DISABLE KEYS */;
/*!40000 ALTER TABLE `themes_definition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tree_structure`
--

DROP TABLE IF EXISTS `tree_structure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tree_structure` (
  `interface_id` varchar(100) NOT NULL DEFAULT '',
  `part_id` varchar(100) NOT NULL DEFAULT '',
  `treedataremotefunction` varchar(100) DEFAULT NULL,
  `onselectremotefunction` varchar(100) DEFAULT NULL,
  `onpostinitfunction` varchar(100) DEFAULT NULL,
  `autocollapse` varchar(100) DEFAULT NULL,
  `initialiseonload` varchar(100) DEFAULT NULL,
  `islazynode` varchar(100) DEFAULT NULL,
  `tooltip` varchar(100) DEFAULT NULL,
  `nodestructure` blob,
  `parentsql` blob,
  `childnodesql` blob,
  `tree_parameter` blob,
  PRIMARY KEY (`interface_id`,`part_id`),
  KEY `tree_structure1` (`interface_id`,`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tree_structure`
--

LOCK TABLES `tree_structure` WRITE;
/*!40000 ALTER TABLE `tree_structure` DISABLE KEYS */;
/*!40000 ALTER TABLE `tree_structure` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-29 12:18:15
