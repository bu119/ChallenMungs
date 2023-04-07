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
-- Table structure for table `my_challenge`
--

DROP TABLE IF EXISTS `my_challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_challenge` (
  `idx` bigint NOT NULL AUTO_INCREMENT,
  `challenge_id` bigint DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  `success_count` int DEFAULT NULL,
  `success_ratio` int DEFAULT NULL,
  `success_result` int DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_challenge`
--

LOCK TABLES `my_challenge` WRITE;
/*!40000 ALTER TABLE `my_challenge` DISABLE KEYS */;
INSERT INTO `my_challenge` VALUES (1,1,'sa01023@naver.com',0,0,0,NULL),(2,1,'silverline030@kakao.com',0,0,0,NULL),(3,1,'dogo1030@nate.com',0,0,0,NULL),(4,1,'souk0712@naver.com',0,0,0,NULL),(5,2,'sa01023@naver.com',0,NULL,NULL,NULL),(6,3,'sa01023@naver.com',0,NULL,NULL,NULL),(7,2,'souk0712@naver.com',0,NULL,NULL,NULL),(8,3,'souk0712@naver.com',0,NULL,NULL,NULL),(9,3,'silverline030@kakao.com',0,NULL,NULL,NULL),(10,2,'silverline030@kakao.com',0,NULL,NULL,NULL),(11,2,'dogo1030@nate.com',0,NULL,NULL,NULL),(12,3,'dogo1030@nate.com',0,NULL,NULL,NULL),(14,5,'souk0712@naver.com',0,NULL,NULL,NULL),(15,6,'sa01023@naver.com',0,NULL,NULL,NULL),(20,5,'sa01023@naver.com',0,NULL,NULL,NULL),(27,7,'souk0712@naver.com',0,NULL,NULL,NULL),(29,8,'sa01023@naver.com',0,NULL,NULL,NULL),(33,9,'sa01023@naver.com',0,NULL,NULL,NULL),(36,10,'souk0712@naver.com',0,NULL,NULL,NULL),(38,12,'dogo1030@nate.com',0,NULL,NULL,NULL),(39,13,'silverline030@kakao.com',0,NULL,NULL,NULL),(41,13,'opi6@hanmail.net',0,NULL,NULL,NULL),(43,14,'dogo1030@nate.com',0,NULL,NULL,NULL),(44,14,'opi6@hanmail.net',0,NULL,NULL,NULL),(45,13,'souk0712@naver.com',0,NULL,NULL,NULL),(49,14,'sa01023@naver.com',0,NULL,NULL,NULL),(50,15,'souk0712@naver.com',0,NULL,NULL,1),(51,13,'sa01023@naver.com',0,NULL,NULL,NULL),(52,16,'souk0712@naver.com',0,NULL,NULL,1),(53,17,'souk0712@naver.com',0,NULL,NULL,NULL),(54,18,'souk0712@naver.com',0,NULL,NULL,1),(55,10,'sa01023@naver.com',0,NULL,NULL,NULL),(56,16,'sa01023@naver.com',0,NULL,NULL,1),(57,17,'sa01023@naver.com',0,NULL,NULL,NULL),(58,18,'sa01023@naver.com',0,NULL,NULL,2),(59,19,'souk0712@naver.com',0,NULL,NULL,1),(60,10,'dogo1030@nate.com',0,NULL,NULL,NULL),(61,10,'silverline030@kakao.com',0,NULL,NULL,NULL),(63,6,'silverline030@kakao.com',0,NULL,NULL,NULL),(64,7,'silverline030@kakao.com',0,NULL,NULL,NULL),(65,8,'silverline030@kakao.com',0,NULL,NULL,NULL),(66,9,'silverline030@kakao.com',0,NULL,NULL,NULL),(67,12,'silverline030@kakao.com',0,NULL,NULL,NULL),(68,19,'silverline030@kakao.com',0,NULL,NULL,2),(69,18,'silverline030@kakao.com',0,NULL,NULL,1),(70,17,'silverline030@kakao.com',0,NULL,NULL,NULL),(71,16,'silverline030@kakao.com',0,NULL,NULL,2),(72,15,'silverline030@kakao.com',0,NULL,NULL,2),(73,14,'silverline030@kakao.com',0,NULL,NULL,NULL),(74,7,'dogo1030@nate.com',0,NULL,NULL,NULL),(75,16,'opi6@hanmail.net',0,NULL,NULL,2),(76,20,'silverline030@kakao.com',0,NULL,NULL,NULL),(77,19,'opi6@hanmail.net',0,NULL,NULL,1),(78,19,'sa01023@naver.com',0,NULL,NULL,2),(79,21,'dogo1030@nate.com',0,NULL,NULL,NULL),(80,21,'opi6@hanmail.net',0,NULL,NULL,NULL),(81,21,'dogo1030@nate.com',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `my_challenge` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 11:04:04
