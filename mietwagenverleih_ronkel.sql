-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 10. Sep 2025 um 19:17
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `mietwagenverleih_ronkel`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `auto`
--

CREATE TABLE `auto` (
  `ID` int(11) NOT NULL,
  `Marke` text NOT NULL,
  `Modell` text NOT NULL,
  `Kategorie` text NOT NULL,
  `Leistung` int(11) NOT NULL,
  `Kennzeichen` text NOT NULL,
  `WirdGemietetVon` int(11) NOT NULL,
  `IstPreisklasse` int(11) NOT NULL,
  `HatBewertung` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `auto`
--

INSERT INTO `auto` (`ID`, `Marke`, `Modell`, `Kategorie`, `Leistung`, `Kennzeichen`, `WirdGemietetVon`, `IstPreisklasse`, `HatBewertung`) VALUES
(1, 'Mercedes', '190E', 'Limousine', 195, 'NR L 2020', 0, 0, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE `benutzer` (
  `ID` int(11) NOT NULL,
  `Benutzername` text NOT NULL,
  `Passwort` text NOT NULL,
  `Vorname` text NOT NULL,
  `Name` text NOT NULL,
  `Geburtsdatum` date NOT NULL,
  `Adresse` text NOT NULL,
  `IstMitarbeiter` tinyint(1) NOT NULL,
  `IstVerifiziert` tinyint(1) NOT NULL,
  `HatBewertungGeschrieben` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`ID`, `Benutzername`, `Passwort`, `Vorname`, `Name`, `Geburtsdatum`, `Adresse`, `IstMitarbeiter`, `IstVerifiziert`, `HatBewertungGeschrieben`) VALUES
(1, 'Flyke', 'Flyke', 'Leo', 'Kaußen', '2007-05-31', 'Sayner-Landstraße 22', 1, 1, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bewertungen`
--

CREATE TABLE `bewertungen` (
  `ID` int(11) NOT NULL,
  `Bewertung` int(11) NOT NULL,
  `Kommentar` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `preisklassen`
--

CREATE TABLE `preisklassen` (
  `ID` int(11) NOT NULL,
  `Preis` int(11) NOT NULL,
  `ZusatzversicherungsPreis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `auto`
--
ALTER TABLE `auto`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `bewertungen`
--
ALTER TABLE `bewertungen`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `preisklassen`
--
ALTER TABLE `preisklassen`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `auto`
--
ALTER TABLE `auto`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT für Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT für Tabelle `bewertungen`
--
ALTER TABLE `bewertungen`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `preisklassen`
--
ALTER TABLE `preisklassen`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
