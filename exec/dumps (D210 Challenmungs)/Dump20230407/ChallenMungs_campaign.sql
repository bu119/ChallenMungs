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
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `campaign_id` int NOT NULL AUTO_INCREMENT,
  `collect_amount` int DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `end_unix` bigint DEFAULT NULL,
  `is_end` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `regist_date` date DEFAULT NULL,
  `regist_unix` bigint DEFAULT NULL,
  `target_amount` int DEFAULT NULL,
  `thumbnail` varchar(2500) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `wallet_address` varchar(255) DEFAULT NULL,
  `withdraw_amount` int DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`campaign_id`),
  KEY `FK78bpeuj9ptebgf7ewyls17vb` (`login_id`),
  CONSTRAINT `FK78bpeuj9ptebgf7ewyls17vb` FOREIGN KEY (`login_id`) REFERENCES `user` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (8,81,NULL,0,_binary '\0','주토피아 유기견 보호소','2023-04-01',1680789406,100,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/d76c31c7-28e7-4952-ab96-dda435b776e3보호소3.PNG','여러분의 따뜻한 손길을 기다립니다.','0xba1663bc97c13bb6F9ffF51144d14b7c542f25e6',0,'zootopia@naver.com'),(12,82,NULL,0,_binary '\0','주토피아 유기견 보호소','2023-04-07',1680795376,100,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/22a2c83c-de93-49ae-9d2f-c39bcaff8d6c보호소1.jpg','주토피아 보호소와 함께해주세요.','0x2bbf154f477a36f5ed66a638ac340844b2b566a6',0,'zootopia@naver.com'),(13,90,NULL,0,_binary '\0','블랑팡 유기견 보호소','2023-04-06',1680814668,100,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/bef4b988-6e04-4a83-a4a5-a3236bab1b03보호소4.png','유기동물이 반려동물로 되는 순간, 모두의행동이 여러분의 올바른 선택을 도울게요.','0xde28e1e5f5ec8870173e8080827ff62297cb6d40',0,'dmstjs7047@hanmail.net'),(14,80,NULL,0,_binary '\0','블랑팡 유기견 보호소','2023-04-06',1680814854,100,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/9581f4bc-39eb-42b4-81ea-8d8e6d9a8978보호소6.PNG','따뜻한 머뭄이 있는 반려동물 보호소, 블랑팡 보호소','0xa4482f0f48585646d67c2c5c421545a1e4f5a10a',0,'dmstjs7047@hanmail.net');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 11:04:06
