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
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content` (
  `content_id` int NOT NULL AUTO_INCREMENT,
  `body` varchar(255) DEFAULT NULL,
  `seq` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `campaign_id` int DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  KEY `FK9ehwq5qrw1mcy2wqes97jjrlt` (`campaign_id`),
  CONSTRAINT `FK9ehwq5qrw1mcy2wqes97jjrlt` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`campaign_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
INSERT INTO `content` VALUES (22,'주토피아 보호소',0,'bold',8),(23,'유가견 보호 모금에 많은 분께서 동참해주신 덕분에 예상보다 더 많은 금액을 모을 수 있었습니다.',1,'normal',8),(24,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/22a2c83c-de93-49ae-9d2f-c39bcaff8d6c보호소1.jpg',2,'img',8),(34,'여러분의 관심이 큰 힘이 됩니다.',0,'bold',12),(35,'유기견 보호 모금에 많은 분께서 동참해주신 덕분에 예상보다 더 많은 금액을 모을 수 있었습니다.',1,'normal',12),(36,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/22a2c83c-de93-49ae-9d2f-c39bcaff8d6c보호소1.jpg',2,'img',12),(37,'블랑팡 유기견 보호소',0,'bold',13),(38,'많은 분들이 유기동물은 어딘가 아프거나 문제가 있어서 버려졌을 거라는 고정관념이 있어요. 특정 매체를 통해 보는 유기견의 모습과는 많이 달라, 뿌리 깊은 편견이 변화되길 바랍니다.',1,'normal',13),(39,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/cd40acfe-6b28-4fbf-984a-2dcee5cfcf47보호소2.jpg',2,'img',13),(40,'끝까지 책임지고 키우는 것만으로도 건강한 입양문화를 만들어가는 시작점',0,'bold',14),(41,'많은 분들이 유기동물은 어딘가 아프거나 문제가 있어서 버려졌을 거라는 고정관념이 있어요. 특정 매체를 통해 보는 유기견의 모습과는 많이 달라, 뿌리 깊은 편견이 변화되길 바랍니다.',1,'normal',14),(42,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/cd40acfe-6b28-4fbf-984a-2dcee5cfcf47보호소2.jpg',2,'img',14);
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 11:04:05
