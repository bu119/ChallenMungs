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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `login_id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile` varchar(2500) DEFAULT NULL,
  `total_donate` int DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('dmstjs7047@hanmail.net','블랑팡 유기견 보호소','Qazwsx1!','https://kr.object.ncloudstorage.com/challenmungs-storage/user/9581f4bc-39eb-42b4-81ea-8d8e6d9a8978보호소6.PNG',NULL,'s'),('dogo1030@nate.com','준덕이랑산책할래',NULL,'http://k.kakaocdn.net/dn/c8cDOG/btrKaujOaot/mzjKHT3DrkdN56t3QBTdc1/img_640x640.jpg',0,'n'),('opi6@hanmail.net','동동동하하하',NULL,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/eaf7f044-caa8-44d7-b890-9315913e9872ic_profile.png',0,'n'),('sa01023@naver.com','차돌이너무귀여워',NULL,'http://k.kakaocdn.net/dn/jtc4n/btr0xQXXoTP/139KCXLDgrW7ddfzm7ZLM0/img_640x640.jpg',0,'n'),('silverline030@kakao.com','구름이짠나인데용왜용',NULL,'https://kr.object.ncloudstorage.com/challenmungs-storage/user/eaf7f044-caa8-44d7-b890-9315913e9872ic_profile.png',22,'n'),('souk0712@naver.com','mokacoffee',NULL,'http://k.kakaocdn.net/dn/bIsJbT/btr8lNSssbs/xpph1le2fdvpHSOXUSTr80/img_640x640.jpg',312,'n'),('zootopia@naver.com','주토피아 유기견 보호소','1234','https://kr.object.ncloudstorage.com/challenmungs-storage/user/89a11fd2-f364-4596-924c-90ba29d00a7e보호소1.jpg',NULL,'s');
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

-- Dump completed on 2023-04-07 11:04:08
