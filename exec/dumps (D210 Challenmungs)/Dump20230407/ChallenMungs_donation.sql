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
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `donation_id` int NOT NULL AUTO_INCREMENT,
  `donate_date` datetime DEFAULT NULL,
  `money` int DEFAULT NULL,
  `shelter` varchar(255) DEFAULT NULL,
  `total_money` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`donation_id`),
  KEY `FKclws30gn2wr2csppk9yv8ycsw` (`login_id`),
  CONSTRAINT `FKclws30gn2wr2csppk9yv8ycsw` FOREIGN KEY (`login_id`) REFERENCES `user` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (1,'2023-04-06 23:20:35',10,'주토피아보호소',10,2023,'souk0712@naver.com'),(2,'2023-04-07 23:28:40',1,'주토피아보호소',11,2023,'souk0712@naver.com'),(3,'2023-04-08 23:28:48',1,'주토피아보호소',12,2023,'souk0712@naver.com'),(4,'2023-04-06 20:26:21',5,'주토피아보호소',5,2023,'silverline030@kakao.com'),(5,'2023-04-06 20:41:39',7,'주토피아보호소',12,2023,'silverline030@kakao.com'),(6,'2023-04-07 07:01:24',90,'블랑팡 유기견 보호소',102,2023,'souk0712@naver.com'),(7,'2023-04-07 07:01:37',60,'주토피아보호소',162,2023,'souk0712@naver.com'),(8,'2023-04-07 07:03:33',80,'블랑팡 유기견 보호소',242,2023,'souk0712@naver.com'),(9,'2023-04-07 07:05:08',40,'주토피아보호소',282,2023,'souk0712@naver.com'),(10,'2023-04-07 07:05:24',30,'주토피아보호소',312,2023,'souk0712@naver.com'),(11,'2023-04-07 00:24:46',10,'주토피아 유기견 보호소',22,2023,'silverline030@kakao.com');
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 11:04:02
