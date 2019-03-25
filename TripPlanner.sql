CREATE DATABASE  IF NOT EXISTS `itwillbs8` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `itwillbs8`;
-- MySQL dump 10.13  Distrib 5.6.39, for Win64 (x86_64)
--
-- Host: localhost    Database: itwillbs8
-- ------------------------------------------------------
-- Server version	5.6.39

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `userID` varchar(20) DEFAULT NULL,
  `boardID` int(11) NOT NULL,
  `boardTitle` varchar(50) DEFAULT NULL,
  `boardContent` longtext,
  `boardDate` datetime DEFAULT NULL,
  `boardHit` int(11) DEFAULT NULL,
  `boardFile` varchar(100) DEFAULT NULL,
  `boardRealFile` varchar(100) DEFAULT NULL,
  `boardGroup` int(11) DEFAULT NULL,
  `boardSequence` int(11) DEFAULT NULL,
  `boardLevel` int(11) DEFAULT NULL,
  `boardAvailable` int(11) DEFAULT NULL,
  `likeCount` int(11) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `tourist` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`boardID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat` (
  `chatID` int(11) NOT NULL AUTO_INCREMENT,
  `fromID` varchar(20) DEFAULT NULL,
  `toID` varchar(20) DEFAULT NULL,
  `chatContent` varchar(200) DEFAULT NULL,
  `chatTime` datetime DEFAULT NULL,
  `chatRead` int(11) DEFAULT NULL,
  PRIMARY KEY (`chatID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `cityNo` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(30) NOT NULL,
  `cityEgname` varchar(30) DEFAULT NULL,
  `cityCode` varchar(10) DEFAULT NULL,
  `cityCountry` varchar(30) DEFAULT NULL,
  `citycount` int(11) DEFAULT NULL,
  PRIMARY KEY (`cityNo`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'오사카','OSAKA','ITM','일본',0),(2,'후쿠오카','Fukuoka','FUK','일본',0),(3,'나고야','Nagoya','NGO','일본',0),(4,'삿포로','Sapporo','CTS','일본',0),(5,'교토','Kyoto','ITM','일본',0),(6,'신주쿠','Shinjuku','HND','일본',0),(7,'시부야','Shibuya','HND','일본',0),(8,'방콕','Bangkok','BKK','방콕',0),(9,'싱가포르','Singapore','SIN','싱가포르',0),(10,'하노이','Hanoi','HAN','베트남',0),(11,'호치민','HoChiMinh','SGN','베트남',0),(12,'홍콩','HongKong','HKG','홍콩',0),(13,'타이페이','Taipei','TPE','대만',0),(14,'상하이','Shanghai','PVG','중국',0),(15,'베이징','Beijing','PEK','중국',0),(16,'광저우','Guangzhou','CAN','중국',0),(17,'부산','Busan','PUS','한국',0),(18,'서울','Seoul','SEL','한국',0),(19,'대구','Daegu','TAE','한국',0),(20,'런던','London','LON','영국',0),(21,'파리','Paris','PAR','프랑스',0),(22,'바르셀로나','Barcelona','BCN','스페인',0),(23,'로마','Rome','FCO','이탈리아',0),(24,'베니스','Venice','VCE','이탈리아',0),(25,'피렌체','Florence','FLR','이탈리아',0),(26,'취리히','Zurich','ZRH','스위스',0),(27,'니스','Nice','NCE','프랑스',0),(28,'베를린','Berlin','TXL','독일',0),(29,'뮌헨','Munich','MUC','독일',4),(30,'프랑크푸르트','Frankfurt','FRA','독일',0),(31,'마드리드','Madrid','MAD','스페인',0),(32,'암스테르담','Amsterdam','AMS','네덜란드',0),(33,'아테네','Athens','ATH','그리스',0),(34,'리스본','Lisbon','LIS','포르투갈',0),(35,'스톡홀름','Stockholm','ARN','스웨덴',0),(36,'마이애미','Miami','MIA','미국',0),(37,'로스앤젤레스','LosAngeles','LAX','미국',0),(38,'라스베이거스','LasVegas','LAS','미국',0),(39,'시카고','Chicago','ORD','미국',0),(40,'괌','Guam','GUM','미국',0);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `commentsNo` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(20) DEFAULT NULL,
  `boardID` int(11) DEFAULT NULL,
  `bcomment` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`commentsNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datatable`
--

DROP TABLE IF EXISTS `datatable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datatable` (
  `nov` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(20) DEFAULT NULL,
  `keyvalue` int(11) DEFAULT NULL,
  PRIMARY KEY (`nov`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datatable`
--

LOCK TABLES `datatable` WRITE;
/*!40000 ALTER TABLE `datatable` DISABLE KEYS */;
/*!40000 ALTER TABLE `datatable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gowith`
--

DROP TABLE IF EXISTS `gowith`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gowith` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `date1` date NOT NULL,
  `date2` date NOT NULL,
  `mem` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `destination` varchar(20) NOT NULL,
  `profilephoto` varchar(100) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gowith`
--

LOCK TABLES `gowith` WRITE;
/*!40000 ALTER TABLE `gowith` DISABLE KEYS */;
/*!40000 ALTER TABLE `gowith` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gowithlike`
--

DROP TABLE IF EXISTS `gowithlike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gowithlike` (
  `boardno` int(11) NOT NULL,
  `user` varchar(45) NOT NULL,
  `likecheck` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gowithlike`
--

LOCK TABLES `gowithlike` WRITE;
/*!40000 ALTER TABLE `gowithlike` DISABLE KEYS */;
/*!40000 ALTER TABLE `gowithlike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myboard`
--

DROP TABLE IF EXISTS `myboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myboard` (
  `userID` varchar(20) DEFAULT NULL,
  `myboardID` int(11) NOT NULL,
  `myboardTitle` varchar(50) DEFAULT NULL,
  `myboardContent` longtext,
  `myboardDate` datetime DEFAULT NULL,
  `myboardHit` int(11) DEFAULT NULL,
  `myboardFile` varchar(100) DEFAULT NULL,
  `myboardRealFile` varchar(100) DEFAULT NULL,
  `myboardGroup` int(11) DEFAULT NULL,
  `myboardSequence` int(11) DEFAULT NULL,
  `myboardLevel` int(11) DEFAULT NULL,
  `myboardAvailable` int(11) DEFAULT NULL,
  `mylikeCount` int(11) DEFAULT NULL,
  `mycategory` varchar(10) DEFAULT NULL,
  `myvisibility` varchar(10) DEFAULT NULL,
  `myplan` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`myboardID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myboard`
--

LOCK TABLES `myboard` WRITE;
/*!40000 ALTER TABLE `myboard` DISABLE KEYS */;
INSERT INTO `myboard` VALUES ('yester31',1,'베니스 갈꺼야','<p><span style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\">(서울=연합뉴스) 임순현 기자 = 사망하거나 노동력을 잃은 피해자에 대한 손해배상액을 계산할 때 기준이 되는 육체노동자의 \'노동가동연한\'(노동에 종사해 수익을 얻을 것으로 예상되는 연령의 상한)을 기존 60세에서 65세로 상향해야 한다는 취지의 대법원 판결이 나왔다.</span><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"><span style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\">이번 판결에 따라 보험금 지급액이 늘어나 보험료 동반 상승이 예상되는 등 보험업계에 상당한 파장이 일 것으로 전망된다. 또 \'60세 이상\'으로 규정된 현행 정년 규정도 상향해야 한다는 논의로 이어질 가능성이 커 노동계와 산업계도 잔뜩 긴장하는 모양새다.</span><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"></p>','2019-02-21 16:26:56',0,'','',0,0,0,1,0,'여행계획','o','0'),('yester31',2,'베니스 갈꺼야','<p><span style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\">대법원 전원합의체는 21일 박동현씨 부부와 딸이 수영장 운영업체를 상대로 낸 손해배상소송 상고심에서 \"총 2억5천416만원을 배상하라\"는 원심 판결을 깨고 \'노동가동연한을 65세로 상향해 손배배상액을 다시 계산하라\'는 취지로 사건을 서울고법에 돌려보냈다.</span><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"><br style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\"><span style=\"font-family: 굴림, gulim, sans-serif; font-size: 17px; letter-spacing: -0.3px;\">재판부는 \"육체노동의 경험칙상 가동연한을 만 60세로 보아온 견해는 더 이상 유지하기 어렵고, 이제는 특별한 사정이 없는 한 만 60세를 넘어 만 65세까지도 가동할 수 있다고 보는 것이 경험칙에 합당하다\"고 판단했다.sdfsadf</span></p>','2019-02-21 16:27:02',1,'','',1,0,0,1,0,'여행계획','o','일정 없음'),('yester31',3,'dd','<p>dd</p>','2019-02-21 16:51:31',0,'','',2,0,0,1,0,'여행계획','o','0'),('yester31',4,'ddaa','<p><img src=\"data:image/jpeg;base64,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\" data-filename=\"pic3.jpg\" style=\"width: 200px;\">ddsdafaa</p>','2019-02-21 16:51:44',6,'','',3,0,0,1,0,'여행계획','o','0');
/*!40000 ALTER TABLE `myboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mycomments`
--

DROP TABLE IF EXISTS `mycomments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mycomments` (
  `mycommentsNo` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(20) DEFAULT NULL,
  `myboardID` int(11) DEFAULT NULL,
  `mybcomment` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`mycommentsNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mycomments`
--

LOCK TABLES `mycomments` WRITE;
/*!40000 ALTER TABLE `mycomments` DISABLE KEYS */;
/*!40000 ALTER TABLE `mycomments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `myrecommendation`
--

DROP TABLE IF EXISTS `myrecommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `myrecommendation` (
  `myboardID` int(11) DEFAULT NULL,
  `userID` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `myrecommendation`
--

LOCK TABLES `myrecommendation` WRITE;
/*!40000 ALTER TABLE `myrecommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `myrecommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
  `planNo` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(20) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `departDay` date DEFAULT NULL,
  `returnDay` date DEFAULT NULL,
  `nofday` int(11) DEFAULT NULL,
  PRIMARY KEY (`planNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendation`
--

DROP TABLE IF EXISTS `recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recommendation` (
  `boardID` int(11) DEFAULT NULL,
  `userID` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendation`
--

LOCK TABLES `recommendation` WRITE;
/*!40000 ALTER TABLE `recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `planNo` int(11) DEFAULT NULL,
  `dayNo` varchar(20) DEFAULT NULL,
  `thingNo` int(11) DEFAULT NULL,
  `touristName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userID` varchar(20) NOT NULL,
  `userPassword` varchar(70) DEFAULT NULL,
  `userName` varchar(20) DEFAULT NULL,
  `userAge` int(11) DEFAULT NULL,
  `userGender` varchar(20) DEFAULT NULL,
  `userEmail` varchar(50) DEFAULT NULL,
  `userProfile` varchar(50) DEFAULT NULL,
  `userMotto` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('yester31','oucCaGF6MqPHDuKjK7Wh9A==','박영호',30,'남자','yes@yes','','ddd');
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

-- Dump completed on 2019-02-21 19:30:18
