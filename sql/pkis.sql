CREATE DATABASE  IF NOT EXISTS `pkis` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `pkis`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 127.0.0.1    Database: pkis
-- ------------------------------------------------------
-- Server version	5.6.22

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
-- Table structure for table `client_groups`
--

DROP TABLE IF EXISTS `client_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_groups` (
  `id` bigint(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `charge_per_month` decimal(10,2) DEFAULT NULL,
  `day_time_fee` decimal(10,2) NOT NULL,
  `night_time_fee` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_groups`
--

LOCK TABLES `client_groups` WRITE;
/*!40000 ALTER TABLE `client_groups` DISABLE KEYS */;
INSERT INTO `client_groups` VALUES (1,'ADMIN',NULL,0.00,0.00),(2,'REGULAR',NULL,1.50,1.00),(3,'PREMIUM',20.00,1.00,0.75);
/*!40000 ALTER TABLE `client_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_invoices`
--

DROP TABLE IF EXISTS `client_invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_invoices` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `parking_date` int(11) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `parking_id` bigint(11) DEFAULT NULL,
  `parking_month` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_clients_idx` (`client_id`),
  KEY `fk_parkings_idx` (`parking_id`),
  CONSTRAINT `fk_clients` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_parkings` FOREIGN KEY (`parking_id`) REFERENCES `parkings` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_invoices`
--

LOCK TABLES `client_invoices` WRITE;
/*!40000 ALTER TABLE `client_invoices` DISABLE KEYS */;
INSERT INTO `client_invoices` VALUES (40,3,'2016-08-29 02:16:28',20160805,6.00,19,201608),(41,3,'2016-08-29 02:16:32',20160805,6.00,20,201608),(42,2,'2016-08-29 02:22:06',20160802,9.00,16,201608),(43,2,'2016-08-29 02:22:06',20160802,28.50,17,201608),(44,2,'2016-08-29 02:22:06',20160805,32.00,18,201608);
/*!40000 ALTER TABLE `client_invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `id` bigint(11) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `address` varchar(256) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `group_id` bigint(11) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `car_details` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_groups_idx` (`group_id`),
  CONSTRAINT `fk_groups` FOREIGN KEY (`group_id`) REFERENCES `client_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'admin','123','admin','asdf',0.00,1,NULL,NULL),(2,'regular','123','regular','adf',NULL,2,NULL,NULL),(3,'premium','123','premium','adsf',NULL,3,NULL,NULL);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parking_houses`
--

DROP TABLE IF EXISTS `parking_houses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking_houses` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_houses`
--

LOCK TABLES `parking_houses` WRITE;
/*!40000 ALTER TABLE `parking_houses` DISABLE KEYS */;
INSERT INTO `parking_houses` VALUES (1,'Seibert','Bluemenstrasse 12');
/*!40000 ALTER TABLE `parking_houses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkings`
--

DROP TABLE IF EXISTS `parkings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkings` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(11) NOT NULL,
  `entry_date` datetime NOT NULL,
  `departure_date` datetime DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `pkhs_id` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_parkings` (`client_id`,`entry_date`,`departure_date`),
  KEY `fk_pkclients_idx` (`client_id`),
  KEY `fk_parkinghouses_idx` (`pkhs_id`),
  CONSTRAINT `fk_parkinghouses` FOREIGN KEY (`pkhs_id`) REFERENCES `parking_houses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pkclients` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkings`
--

LOCK TABLES `parkings` WRITE;
/*!40000 ALTER TABLE `parkings` DISABLE KEYS */;
INSERT INTO `parkings` VALUES (16,2,'2016-08-02 08:33:00','2016-08-02 11:45:00','2016-08-28 23:05:35',1),(17,2,'2016-08-02 19:55:00','2016-08-03 05:33:00','2016-08-28 23:05:35',1),(18,2,'2016-08-05 08:33:00','2016-08-05 20:11:00','2016-08-28 23:05:35',1),(19,3,'2016-08-05 11:55:00','2016-08-05 15:12:00','2016-08-28 23:05:35',1),(20,3,'2016-08-05 20:22:00','2016-08-05 23:44:00','2016-08-28 23:05:35',1);
/*!40000 ALTER TABLE `parkings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-29  2:45:20
