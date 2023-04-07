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
-- Table structure for table `general_board`
--

DROP TABLE IF EXISTS `general_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `picture_url` varchar(2500) DEFAULT NULL,
  `register_day` date DEFAULT NULL,
  `reject_count` int DEFAULT NULL,
  `challenge_id` bigint DEFAULT NULL,
  `login_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  KEY `FK4dparnfo5xtbldfsgkgfbp6r7` (`challenge_id`),
  KEY `FK9lgfea8ibe6smc4sf71186875` (`login_id`),
  CONSTRAINT `FK4dparnfo5xtbldfsgkgfbp6r7` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`challenge_id`) ON DELETE CASCADE,
  CONSTRAINT `FK9lgfea8ibe6smc4sf71186875` FOREIGN KEY (`login_id`) REFERENCES `user` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_board`
--

LOCK TABLES `general_board` WRITE;
/*!40000 ALTER TABLE `general_board` DISABLE KEYS */;
INSERT INTO `general_board` VALUES (1,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/a7c167c0-de1a-43cb-bc96-d42dfcf0ace1KakaoTalk_20230405_172838839.jpg','2023-04-01',0,1,'sa01023@naver.com'),(2,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/687dd87d-5f8f-4c96-a145-c151082c8d61IMG_6632.jpeg','2023-04-01',0,1,'silverline030@kakao.com'),(3,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/cdc537b2-a413-4442-b61a-f8c969067a01KakaoTalk_20230405_094939339.jpg','2023-04-02',0,1,'sa01023@naver.com'),(4,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/0566d891-99dc-46c6-ad72-1f0354e0f37666894286712__89333B20-F944-4424-8673-DACDE0B706DE.jpeg','2023-04-02',0,1,'silverline030@kakao.com'),(5,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1ae84250-3eed-436e-a6f2-e201f442e696IMG_6655.jpeg','2023-04-03',0,1,'silverline030@kakao.com'),(6,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/ddc78001-ca08-4322-af9c-99f144f3ef72KakaoTalk_20230405_094910738.jpg','2023-04-03',0,1,'sa01023@naver.com'),(7,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/f69f810b-f730-497c-833e-6d213d0c0963젤다꾸.jpg','2023-04-03',2,1,'dogo1030@nate.com'),(8,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/fe394085-f0a2-44ab-bbfb-1235fefed954KakaoTalk_20230405_094433962_01.jpg','2023-04-04',0,1,'sa01023@naver.com'),(9,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/e60278f9-9322-46c5-8fcb-951c678c7298IMG_0261.jpeg','2023-04-04',0,1,'silverline030@kakao.com'),(10,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/17a31c6c-b8ef-4517-bd71-6cd6a317f0cf준덕1.jpg','2023-04-04',0,1,'dogo1030@nate.com'),(11,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/c0795ff7-bb24-49ce-9196-7d0e39f6fe8aKakaoTalk_20230405_094433962.jpg','2023-04-05',0,1,'sa01023@naver.com'),(12,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1b671f09-96aa-488d-bb55-cc281ce7903aIMG_5452.jpeg','2023-04-05',0,1,'silverline030@kakao.com'),(13,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2b323fb7-276b-43b0-a3dd-82feed4bd0c1준덕2.jpg','2023-04-05',0,1,'dogo1030@nate.com'),(14,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/12946beb-df94-4dc4-8320-8248036e4ad6준덕5.jpg','2023-04-06',0,1,'dogo1030@nate.com'),(15,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/57a2889c-5254-4c5d-aa46-4f10b5aa7e0cKakaoTalk_20230405_092027111.jpg','2023-04-06',0,1,'sa01023@naver.com'),(16,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/0f661aff-9fec-4827-be5d-7569df006c20IMG_2677.jpeg','2023-04-06',0,1,'silverline030@kakao.com'),(17,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/576be402-4fc7-42b5-b857-142fc688da91KakaoTalk_20230406_222240245.jpg','2023-04-06',2,1,'souk0712@naver.com'),(18,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/39f12fc6-c3f9-43f7-a249-5ce6d7a10cc1KakaoTalk_20230405_091955479.jpg','2023-04-01',0,2,'sa01023@naver.com'),(19,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/4a256eb5-503b-41c7-9911-5374f92a9a85IMG_2677.jpeg','2023-04-01',0,2,'silverline030@kakao.com'),(20,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/f9559661-5105-4bbb-9034-0e8f3b11b77fKakaoTalk_20230403_135115188_01.jpg','2023-04-01',0,2,'souk0712@naver.com'),(21,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/06811709-6fc8-4385-ba42-2c708443c5a5준덕4.jpg','2023-04-01',0,2,'dogo1030@nate.com'),(22,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/f5ec8758-de36-4e73-9d25-1a6396ee14c1KakaoTalk_20230405_092027111.jpg','2023-04-02',0,2,'sa01023@naver.com'),(23,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/9b0e1c9d-8a7d-49d7-a3d2-1ba93166844aIMG_5452.jpeg','2023-04-02',0,2,'silverline030@kakao.com'),(24,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/261fabab-1aa0-4af4-9245-1a7463f42bcf준덕3.jpg','2023-04-02',0,2,'dogo1030@nate.com'),(25,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/6d9037d8-8314-4f84-8876-5d37f2eae8d5KakaoTalk_20230406_222240245.jpg','2023-04-02',0,2,'souk0712@naver.com'),(26,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/d0d5a531-63bc-491a-a655-2828ed1ec096KakaoTalk_20230405_092216157.jpg','2023-04-03',0,2,'sa01023@naver.com'),(27,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/e633663d-0741-4c52-80eb-b39e6108cc6cIMG_6655.jpeg','2023-04-03',0,2,'silverline030@kakao.com'),(28,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/b5e92a5d-a56f-4469-80b0-e4e0c6dce1c4준덕3.jpg','2023-04-03',1,2,'dogo1030@nate.com'),(29,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/630ac8cb-afff-4214-909c-993b073d007b토리.jpg','2023-04-03',0,2,'souk0712@naver.com'),(30,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/3eb76c25-0ca6-4128-b112-602935061bceKakaoTalk_20230405_092125710.jpg','2023-04-04',0,2,'sa01023@naver.com'),(31,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/98c868ec-fbfc-4a5c-b5ab-f8d835a65893준덕5.jpg','2023-04-04',0,2,'dogo1030@nate.com'),(32,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2dc69327-56d4-45c8-8f5b-b7105ada9751IMG_0261.jpeg','2023-04-04',0,2,'silverline030@kakao.com'),(33,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/32af74f6-12a6-4f13-a93a-e7454542ece1KakaoTalk_20230405_094928451.jpg','2023-04-05',0,2,'sa01023@naver.com'),(34,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/ba8af1a0-5e09-4f54-956d-b8dc2f60983166894286712__89333B20-F944-4424-8673-DACDE0B706DE.jpeg','2023-04-05',0,2,'silverline030@kakao.com'),(35,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/16c8bf54-8984-4070-b485-bf1a7744eb20준덕1.jpg','2023-04-05',0,2,'dogo1030@nate.com'),(36,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/09317343-ae79-40d0-90b8-745866ad2655KakaoTalk_20230405_172838839.jpg','2023-04-06',0,2,'sa01023@naver.com'),(37,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/54dce819-6cc1-4699-86c7-4e4a086a6899IMG_0261.jpeg','2023-04-06',0,2,'silverline030@kakao.com'),(38,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/a49509f0-9f15-46a4-a9ab-b609172a3e32KakaoTalk_20230327_155852745.jpg','2023-04-07',1,2,'sa01023@naver.com'),(39,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/708e9f8e-5cc2-4b01-b4e7-a0309a94508f66894286712__89333B20-F944-4424-8673-DACDE0B706DE.jpeg','2023-04-07',0,2,'silverline030@kakao.com'),(40,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/25b44718-ba3a-4b77-a86a-5e6b9d1e80a4주토피아.png','2023-04-07',3,2,'dogo1030@nate.com'),(41,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/98a06c8a-22e0-484e-8f74-8df562bd2cb6KakaoTalk_20230407_022448205.jpg','2023-04-07',1,1,'sa01023@naver.com'),(42,'https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/22adc400-f386-4476-866d-824f41d92b94IMG_3178.jpeg','2023-04-07',0,1,'silverline030@kakao.com');
/*!40000 ALTER TABLE `general_board` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 11:04:03
