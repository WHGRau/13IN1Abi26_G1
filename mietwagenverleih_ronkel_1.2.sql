/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.0.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: mietwagen-2
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
(6,'Markö','D1000','Schwer',12345,'NR-NR-232323',1);
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
  `Adresse` text NOT NULL,
  `IstMitarbeiter` tinyint(1) NOT NULL,
  `IstVerifiziert` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer`
--

LOCK TABLES `benutzer` WRITE;
/*!40000 ALTER TABLE `benutzer` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `benutzer` VALUES
(1,'Flyke','Flyke','Leo','Kaußen','2007-05-31','Sayner-Landstraße 22',1,1),
(2,'sirtobius','sir1395q','Tobias','Ahrens','2007-07-04','Rheinblick-Siedlung Weißenthurm',0,1),
(3,'Xxx_shadowgodslayer69_xxX','supertornado420','Gerhard','Müller','1959-03-12','Deutschlandweg 45 Ostland',0,0),
(4,'Klogang420','stuhlgang69','Franz','Kloger','2001-09-11','Reichstagsschtrasse 4.5 45454 Hausen',0,0);
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
-- Dumping routines for database 'mietwagen-2'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-09-17 18:33:28
