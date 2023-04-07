-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: j8d2101.p.ssafy.io    Database: ChallenMungs
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `wallet_id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`wallet_id`),
  KEY `FK29jqp9rqjyfqexslggb2he1u2` (`login_id`),
  CONSTRAINT `FK29jqp9rqjyfqexslggb2he1u2` FOREIGN KEY (`login_id`) REFERENCES `user` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES (1,'0xe0dade152eda55959c77377f39f398bf97712be8','p','dogo1030@nate.com'),(2,'0x9f47f64260268e93ab449abed66e45dc8668933e','w','dogo1030@nate.com'),(3,'0xba1663bc97c13bb6F9ffF51144d14b7c542f25e6','1','zootopia@naver.com'),(4,'0x2bbf154f477a36f5ed66a638ac340844b2b566a6','2','zootopia@naver.com'),(5,'0x6f992EFCf7b02Bd1CDDA569dC5444e52f713A57F','3','zootopia@naver.com'),(6,'0xc35a786644105fe3dd12ecbf7317e96b4c02571e','p','sa01023@naver.com'),(7,'0x5f917530b7d4cd8aa8fab2c2e8a957d5a1403397','w','sa01023@naver.com'),(10,'0xa9638793fd792187a0b6d675a53fcf2918a1dc91','p','souk0712@naver.com'),(11,'0x793c959a62721ecdf595690ee1b48059b3c034c9','w','souk0712@naver.com'),(16,'0x24cec110461a53397745d75aa684063e4c5f60c9','p','silverline030@kakao.com'),(17,'0x91b5cb799a780b4a833755f61593b3b3e69f8239','w','silverline030@kakao.com'),(18,'0xde28e1e5f5ec8870173e8080827ff62297cb6d40','1','dmstjs7047@hanmail.net'),(19,'0xa4482f0f48585646d67c2c5c421545a1e4f5a10a','2','dmstjs7047@hanmail.net'),(20,'0xeDd7b5422977D1deecbC5f651E535D6e06D91840','3','dmstjs7047@hanmail.net'),(21,'0xa8a9ef6c5b62a196cc9d4bee10f31e2c517119d5','p','opi6@hanmail.net'),(22,'0xe5e0127096879fd85810063fb3bce1272731ac79','w','opi6@hanmail.net');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 11:04:08
