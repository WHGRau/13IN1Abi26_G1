/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.0.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: mietwagenverleih_ronkel
-- ------------------------------------------------------
-- Server version	12.0.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `auto`
--

DROP TABLE IF EXISTS `auto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `auto` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Marke` text NOT NULL,
  `Modell` text NOT NULL,
  `Kategorie` text NOT NULL,
  `Leistung` int(11) NOT NULL,
  `Kennzeichen` text NOT NULL,
  `PreisklasseID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto`
--

LOCK TABLES `auto` WRITE;
/*!40000 ALTER TABLE `auto` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `auto` VALUES
(1,'Mercedes','190E','Limousine',195,'NR L 2020',2),
(2,'Audi','80','Limousine',89,'NR R 80',1),
(3,'Dodge','Challenger Hellcat','Sportwagen',717,'NR IE 717',4),
(4,'Honda','Accord','Kombi',140,'NR G 420',3),
(5,'Lada','Niva','Geländewagen',81,'SE XY 123',2),
(6,'Markö','D1000','Schwer',12345,'NR-NR-232323',1),
(7,'g','r','r',4,'RRRRRRRRRR',7777);
/*!40000 ALTER TABLE `auto` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `benutzer`
--

DROP TABLE IF EXISTS `benutzer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `benutzer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Benutzername` text NOT NULL,
  `Passwort` text NOT NULL,
  `Vorname` text NOT NULL,
  `Name` text NOT NULL,
  `Geburtsdatum` date NOT NULL,
  `IstMitarbeiter` tinyint(1) NOT NULL,
  `IstVerifiziert` tinyint(1) NOT NULL,
  `AdresseID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer`
--

LOCK TABLES `benutzer` WRITE;
/*!40000 ALTER TABLE `benutzer` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `benutzer` VALUES
(1,'Flyke','6ab36254a905fde4c92752eecf08ea43a0d7be995772c2c9a730dc7cfd4664f3','Leo','Kaußen','2007-05-31',1,1,1),
(2,'sirtobius','d5699d48cee8484d89f253b93c3b46d97886f7f3d6e8e9f2dc9e1715a8110991','Tobias','Ahrens','2007-07-04',0,1,2),
(3,'Xxx_shadowgodslayer69_xxX','ed963d0fe6ed0ae7abf2afa069e50f9b5da9b95f1fa215569e50c0d5e42afc47','Gerhard','Müller','1959-03-12',0,0,3),
(4,'Klogang420','ff9540b6e66986499d255cd318a281e457c5970eab899e5d98abaad2da6d36d8','Franz','Kloger','2001-09-11',0,0,4),
(5,'Ich lol','b89cdab4acf087d19b54bc290a6b281bc50a0cac810688aeaab5c9e6bd628254','davis','marcell','2025-09-03',0,0,5),
(7,'e','42538602949f370aa331d2c07a1ee7ff26caac9cc676288f94b82eb2188b8465','e','e','2025-10-10',0,0,12);
/*!40000 ALTER TABLE `benutzer` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `bewertungen`
--

DROP TABLE IF EXISTS `bewertungen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bewertungen` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Bewertung` int(11) NOT NULL,
  `Kommentar` text NOT NULL,
  `BenutzerID` int(11) NOT NULL,
  `AutoID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bewertungen`
--

LOCK TABLES `bewertungen` WRITE;
/*!40000 ALTER TABLE `bewertungen` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `bewertungen` VALUES
(1,3,'Dieser Wagen war ein Erlebnis für mich und meine Frau Gertrude, wir haben es auf der Autobahn mächtig getrieben. Durch die starke Beschleunigung verlor Gertrude beinahe ihr Gebiss!\r\n\r\nJedoch ist der Komfort nicht sehr hoch, wir kamen nur noch mit Hilfe aus diesem Gefährt. ',3,3),
(2,5,'Kuhl!!',1,1);
/*!40000 ALTER TABLE `bewertungen` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `mietet`
--

DROP TABLE IF EXISTS `mietet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `mietet` (
  `UserID` int(11) NOT NULL,
  `AutoID` int(11) NOT NULL,
  `AusgeliehenAm` datetime NOT NULL,
  `RückgabeAm` datetime NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mietet`
--

LOCK TABLES `mietet` WRITE;
/*!40000 ALTER TABLE `mietet` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `mietet` VALUES
(2,6,'2025-10-06 19:51:43','2025-10-23 18:56:16',1),
(4,4,'2025-10-06 19:03:38','2025-10-06 19:04:01',2),
(5,3,'2001-09-11 00:00:00','2027-05-24 00:00:00',3),
(4,2,'2025-10-23 15:38:50','2025-10-25 19:09:19',4),
(2,5,'2025-10-23 15:40:04','2025-10-01 00:00:00',5),
(2,1,'2025-10-23 16:08:29','2025-10-13 00:00:00',6),
(2,1,'2025-10-23 16:08:36','2025-10-13 00:00:00',7),
(2,1,'2025-10-23 16:08:38','2025-10-13 00:00:00',8),
(2,1,'2025-10-23 16:08:39','2025-10-13 00:00:00',9),
(2,1,'2025-10-23 16:08:40','2025-10-13 00:00:00',10),
(2,4,'2025-10-23 16:12:09','2025-10-23 18:56:09',11),
(2,5,'2025-10-23 17:12:31','2025-10-25 19:08:34',12),
(2,4,'2025-10-25 18:11:42','2025-10-25 00:00:00',13);
/*!40000 ALTER TABLE `mietet` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `preisklassen`
--

DROP TABLE IF EXISTS `preisklassen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `preisklassen` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Preis` int(11) NOT NULL,
  `ZusatzversicherungsPreis` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preisklassen`
--

LOCK TABLES `preisklassen` WRITE;
/*!40000 ALTER TABLE `preisklassen` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `preisklassen` VALUES
(1,30,50),
(2,65,110),
(3,175,300),
(4,350,500);
/*!40000 ALTER TABLE `preisklassen` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `standort`
--

DROP TABLE IF EXISTS `standort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `standort` (
  `Ort` text NOT NULL,
  `Postleitzahl` int(11) NOT NULL,
  `Straße` text NOT NULL,
  `Hausnummer` int(11) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `standort`
--

LOCK TABLES `standort` WRITE;
/*!40000 ALTER TABLE `standort` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `standort` VALUES
('Ängärs',27624,'Sayner-Landstraße',22,1),
('Weißenthurm',27624,'Rheinblick-Siedlung',781,2),
('Ostland',27624,'Deutschlandweg',45,3),
('Hausen',45454,'Reichstagsschtrasse',54,4),
('Hashinghausene',12345,'DieStraße',2,5),
('w',22222,'www',2,6),
('z',6,'z',6,7),
('q',8,'q',8,8),
('q',6,'q',6,9),
('t',6,'t',6,10),
('e',3,'e',3,12);
/*!40000 ALTER TABLE `standort` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `wunschliste`
--

DROP TABLE IF EXISTS `wunschliste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `wunschliste` (
  `AutoID` int(11) NOT NULL,
  `BenutzerID` int(11) NOT NULL,
  PRIMARY KEY (`AutoID`,`BenutzerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wunschliste`
--

LOCK TABLES `wunschliste` WRITE;
/*!40000 ALTER TABLE `wunschliste` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `wunschliste` VALUES
(1,1),
(1,2);
/*!40000 ALTER TABLE `wunschliste` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping routines for database 'mietwagenverleih_ronkel'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-10-25 19:16:23
