CREATE DATABASE  IF NOT EXISTS `db_springboot` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `db_springboot`;
-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: db_springboot
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.16.04.1

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
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `authority` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_authority` (`user_id`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES (4,1,'ROLE_USER'),(6,2,'ROLE_ADMIN'),(5,2,'ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partners`
--

DROP TABLE IF EXISTS `partners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partners`
--

LOCK TABLES `partners` WRITE;
/*!40000 ALTER TABLE `partners` DISABLE KEYS */;
INSERT INTO `partners` VALUES (1,'2019-10-11','drohne@gmail.com','drohne','','Enhord'),(2,'2019-10-11','pepone@gmail.com','pepone','','toblerone'),(3,'2019-10-11','rul@gmail.com','Rul','','Rul'),(4,'2019-10-11','BugFixer@gmail.com','BugFixer','','BugFixer'),(5,'2019-10-11','qwerty@gmail.com','qwerty','','qwerty'),(6,'2019-10-11','Enhord@gmail.com','Enhord','','Enhord'),(7,'2019-10-11','drohne@gmail.com','drohne','','Enhord'),(8,'2019-10-11','pepone@gmail.com','pepone','','toblerone'),(9,'2019-10-11','rul@gmail.com','Rul','','Rul'),(10,'2019-10-11','BugFixer@gmail.com','BugFixer','','BugFixer'),(11,'2019-10-11','qwerty@gmail.com','qwerty','','qwerty'),(12,'2019-10-11','Enhord@gmail.com','Enhord','','Enhord'),(13,'2019-10-11','drohne@gmail.com','drohne','','Enhord'),(14,'2019-10-11','pepone@gmail.com','pepone','','toblerone'),(15,'2019-10-11','rul@gmail.com','Rul','','Rul'),(16,'2019-10-11','BugFixer@gmail.com','BugFixer','','BugFixer'),(17,'2019-10-11','qwerty@gmail.com','qwerty','','qwerty'),(18,'2019-10-11','Enhord@gmail.com','Enhord','','Enhord'),(19,'2019-10-11','drohne@gmail.com','drohne','','Enhord'),(20,'2019-10-11','pepone@gmail.com','pepone','','toblerone'),(21,'2019-10-11','rul@gmail.com','Rul','','Rul'),(22,'2019-10-11','BugFixer@gmail.com','BugFixer','','BugFixer'),(23,'2019-10-11','qwerty@gmail.com','qwerty','','qwerty'),(24,'2019-10-11','Enhord@gmail.com','Enhord','','Enhord'),(25,'2019-10-11','drohne@gmail.com','drohne','','Enhord'),(26,'2019-10-11','pepone@gmail.com','pepone','','toblerone'),(27,'2019-10-11','rul@gmail.com','Rul','','Rul'),(28,'2019-10-11','BugFixer@gmail.com','BugFixer','','BugFixer'),(29,'2019-10-11','qwerty@gmail.com','qwerty','','qwerty'),(30,'2019-10-11','Enhord@gmail.com','Enhord','','Enhord'),(31,'2019-10-11','drohne@gmail.com','drohne','','Enhord'),(32,'2019-10-11','pepone@gmail.com','pepone','','toblerone'),(33,'2019-10-11','rul@gmail.com','Rul','','Rul'),(34,'2019-10-11','BugFixer@gmail.com','BugFixer','','BugFixer'),(35,'2019-10-11','qwerty@gmail.com','qwerty','','qwerty'),(36,'2019-10-11','Enhord@gmail.com','Enhord','','Enhord');
/*!40000 ALTER TABLE `partners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2020-02-13','Keyboard',10),(2,'2020-02-13','Mouse',7),(3,'2020-02-13','Monitor',65);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_order_line`
--

DROP TABLE IF EXISTS `sale_order_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sale_order_line` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantiy` int(11) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `sale_order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKob5mnkuiloiv9gd73s6c9rbh7` (`product_id`),
  KEY `FKhe6exd4j4h3q6dfl376c9dpah` (`sale_order_id`),
  CONSTRAINT `FKhe6exd4j4h3q6dfl376c9dpah` FOREIGN KEY (`sale_order_id`) REFERENCES `sales_order` (`id`),
  CONSTRAINT `FKob5mnkuiloiv9gd73s6c9rbh7` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_order_line`
--

LOCK TABLES `sale_order_line` WRITE;
/*!40000 ALTER TABLE `sale_order_line` DISABLE KEYS */;
INSERT INTO `sale_order_line` VALUES (1,3,1,1),(2,3,2,1),(3,1,3,1),(4,2,3,2);
/*!40000 ALTER TABLE `sale_order_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_order`
--

DROP TABLE IF EXISTS `sales_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `obserbation` varchar(255) DEFAULT NULL,
  `partner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtou4dbfvoj7sxwsydik2qvpx5` (`partner_id`),
  CONSTRAINT `FKtou4dbfvoj7sxwsydik2qvpx5` FOREIGN KEY (`partner_id`) REFERENCES `partners` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_order`
--

LOCK TABLES `sales_order` WRITE;
/*!40000 ALTER TABLE `sales_order` DISABLE KEYS */;
INSERT INTO `sales_order` VALUES (1,'2020-02-13','Office material',NULL,1),(2,'2020-02-13','Monitor',NULL,1);
/*!40000 ALTER TABLE `sales_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'drohne','$2a$10$9RXByRBy4ajtYayxP9r6vOeHH4p5TE/2PGsK.aYbxSZrQHEC9FX7W',1),(2,'admin','$2a$10$AWPQMgBxJktzfAIBnQbHL./esGUzjSPjcA..1zb4Pee.MUFkULnVW',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db_springboot'
--

--
-- Dumping routines for database 'db_springboot'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-13 20:42:00
