-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: zzw6776.tk    Database: zzw
-- ------------------------------------------------------
-- Server version	5.1.73

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
-- Current Database: `zzw`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `zzw` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `zzw`;

--
-- Table structure for table `HEntity`
--

DROP TABLE IF EXISTS `HEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HEntity` (
  `id` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HEntity`
--

LOCK TABLES `HEntity` WRITE;
/*!40000 ALTER TABLE `HEntity` DISABLE KEYS */;
/*!40000 ALTER TABLE `HEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diary`
--

DROP TABLE IF EXISTS `diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diary` (
  `id` varchar(255) NOT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `isEncrypt` bit(1) DEFAULT NULL,
  `message` longtext,
  `uAccount` varchar(255) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary`
--

LOCK TABLES `diary` WRITE;
/*!40000 ALTER TABLE `diary` DISABLE KEYS */;
INSERT INTO `diary` VALUES ('6af1ed43-daa5-4ae1-948d-7325c437e5e3',1478574798243,'','99AEF4D0525856BDED3D6143B19A7F6A98A3F855AF97A806A0B41DE9F9F50206','zzw',1478574818498);
/*!40000 ALTER TABLE `diary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diary_history`
--

DROP TABLE IF EXISTS `diary_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diary_history` (
  `id` varchar(255) NOT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `dId` varchar(255) DEFAULT NULL,
  `isEncrypt` bit(1) DEFAULT NULL,
  `message` longtext,
  `uAccount` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary_history`
--

LOCK TABLES `diary_history` WRITE;
/*!40000 ALTER TABLE `diary_history` DISABLE KEYS */;
INSERT INTO `diary_history` VALUES ('437eb4cf-1d57-4626-9d3a-c82df68e4980',1478574798243,'','','F9175140E7D7DBCC99B5C305FADFB45ADFE7A26DD6A230FD465A813D6DA188C9A08956C7E23ACA1CB6E9CC32A9390DA083141829E4C07815287108340D85D1DA2DC7F44247DA6B472E55DA1024F3B44B5DE45597153D3BF4D89130F14F1A7F862EAD826322D62D9E557CB392BC155DEB7B7F4DCC96EAE849BC7E1C357B214888','zzw'),('c20f4923-2630-4dc4-9d46-d8ca15cc112c',1478574816201,'6af1ed43-daa5-4ae1-948d-7325c437e5e3','','2C950281F0CCFC2B0615E7F33F528245750330C15E09E956D02078D12D8185F3','zzw'),('31273062-8191-46d1-9d46-cfc0c9de33c6',1478574818225,'6af1ed43-daa5-4ae1-948d-7325c437e5e3','','EE2D6DFB03BDDD48AA92C743A5247F9801A3881124ECFA9C2CE28BE3712D064B','zzw'),('ec3a905d-17b8-4e0f-b668-e923e3a8b03f',1478574818494,'6af1ed43-daa5-4ae1-948d-7325c437e5e3','','99AEF4D0525856BDED3D6143B19A7F6A98A3F855AF97A806A0B41DE9F9F50206','zzw');
/*!40000 ALTER TABLE `diary_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shiro_resource`
--

DROP TABLE IF EXISTS `shiro_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_resource` (
  `id` varchar(255) NOT NULL,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `parentIds` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shiro_resource`
--

LOCK TABLES `shiro_resource` WRITE;
/*!40000 ALTER TABLE `shiro_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `shiro_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shiro_role`
--

DROP TABLE IF EXISTS `shiro_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_role` (
  `id` varchar(255) NOT NULL,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `resourceIds` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shiro_role`
--

LOCK TABLES `shiro_role` WRITE;
/*!40000 ALTER TABLE `shiro_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `shiro_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roleId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-08 16:26:21
