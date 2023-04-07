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
-- Table structure for table `general_reject`
--

DROP TABLE IF EXISTS `general_reject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_reject` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `board_id` int DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `FK5vm8364nnhri62usprsomn0ce` (`board_id`),
  KEY `FK951bysdp8kkiiii62s5v3jbm7` (`login_id`),
  CONSTRAINT `FK5vm8364nnhri62usprsomn0ce` FOREIGN KEY (`board_id`) REFERENCES `general_board` (`board_id`) ON DELETE CASCADE,
  CONSTRAINT `FK951bysdp8kkiiii62s5v3jbm7` FOREIGN KEY (`login_id`) REFERENCES `user` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_reject`
--

LOCK TABLES `general_reject` WRITE;
/*!40000 ALTER TABLE `general_reject` DISABLE KEYS */;
INSERT INTO `general_reject` VALUES (2,17,'sa01023@naver.com'),(6,17,'silverline030@kakao.com'),(9,40,'souk0712@naver.com'),(10,40,'sa01023@naver.com'),(22,28,'souk0712@naver.com'),(25,7,'souk0712@naver.com'),(26,7,'sa01023@naver.com'),(36,40,'silverline030@kakao.com'),(39,38,'silverline030@kakao.com'),(40,41,'silverline030@kakao.com');
/*!40000 ALTER TABLE `general_reject` ENABLE KEYS */;
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
