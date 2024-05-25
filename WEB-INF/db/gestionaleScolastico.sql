-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 25, 2024 at 07:10 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionaleScolastico`
--

-- --------------------------------------------------------

--
-- Table structure for table `Classi`
--

CREATE TABLE `Classi` (
  `id_classe` int(11) NOT NULL,
  `nome_classe` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Classi`
--

INSERT INTO `Classi` (`id_classe`, `nome_classe`) VALUES
(5, '1ACA'),
(6, '1AEC'),
(7, '1AEN'),
(8, '1AET'),
(9, '1AIA'),
(10, '1AMM'),
(11, '1BIA'),
(12, '1BMM'),
(13, '2ACA'),
(14, '2AEC'),
(15, '2AEN'),
(16, '2AET'),
(17, '2AIA'),
(18, '2AMM'),
(19, '2BIA'),
(20, '2BMM'),
(21, '3ACA'),
(22, '3AEC'),
(23, '3AEN'),
(24, '3AET'),
(25, '3AIA'),
(26, '3AMM'),
(27, '3BIA'),
(28, '3BMM'),
(29, '4ACA'),
(30, '4AEC'),
(31, '4AEN'),
(32, '4AET'),
(33, '4AIA'),
(34, '4AMM'),
(35, '4BIA'),
(36, '4BMM'),
(37, '5ACA'),
(38, '5AEC'),
(39, '5AEN'),
(40, '5AET'),
(41, '5AIA'),
(42, '5AMM'),
(43, '5BIA'),
(44, '5BMM');

-- --------------------------------------------------------

--
-- Table structure for table `Insegnanti`
--

CREATE TABLE `Insegnanti` (
  `id_insegnante` int(11) NOT NULL,
  `id_utente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Insegnanti`
--

INSERT INTO `Insegnanti` (`id_insegnante`, `id_utente`) VALUES
(28, 23),
(30, 23),
(31, 149),
(33, 157),
(35, 159),
(36, 160);

-- --------------------------------------------------------

--
-- Table structure for table `Insegnanti_Classi_Materie`
--

CREATE TABLE `Insegnanti_Classi_Materie` (
  `id_insegnante` int(11) NOT NULL,
  `id_materia` int(11) NOT NULL,
  `id_classe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Insegnanti_Classi_Materie`
--

INSERT INTO `Insegnanti_Classi_Materie` (`id_insegnante`, `id_materia`, `id_classe`) VALUES
(33, 77, 43),
(35, 80, 6),
(36, 87, 43);

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `id` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `creation_log` timestamp NOT NULL DEFAULT current_timestamp(),
  `action` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`id`, `id_utente`, `creation_log`, `action`) VALUES
(9, 23, '2024-05-23 15:06:16', 'com.mysql.cj.jdbc.ClientPreparedStatement: INSERT INTO Valutazioni (id_studente, id_materia, id_classe, data_valutazione, voto, tipo_valutazione) VALUES (89, 87, 43, \'2024-05-08\', 9.9, \'scritto\')'),
(33, 23, '2024-05-25 08:13:02', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE user SET username=\'andream\', first_name=\'andrea\', last_name=\'marco1\', email=\'andream@gma\', cellphone=\'23993923\', birth_date=\'2024-05-02\', id_role=4 WHERE id=156'),
(34, 23, '2024-05-25 08:13:02', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE studenti SET id_classe=6 WHERE id_utente=156'),
(35, 23, '2024-05-25 08:13:02', 'com.mysql.cj.jdbc.ClientPreparedStatement: DELETE FROM insegnanti_classi_materie WHERE id_insegnante=32'),
(36, 23, '2024-05-25 08:13:02', 'com.mysql.cj.jdbc.ClientPreparedStatement: DELETE FROM insegnanti WHERE id_insegnante=32'),
(37, 23, '2024-05-25 08:24:10', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE classi SET nome_classe=\'1AECa\' WHERE id_classe=6'),
(45, 23, '2024-05-25 08:51:45', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE user SET username=\'andream\', first_name=\'andrea\', last_name=\'marco\', email=\'andream@gma\', cellphone=\'23993923\', birth_date=\'2024-05-02\', id_role=4 WHERE id=156'),
(46, 23, '2024-05-25 08:51:45', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE studenti SET id_classe=6 WHERE id_utente=156'),
(47, 23, '2024-05-25 08:51:45', 'com.mysql.cj.jdbc.ClientPreparedStatement: DELETE FROM insegnanti_classi_materie WHERE id_insegnante=0'),
(48, 23, '2024-05-25 08:51:45', 'com.mysql.cj.jdbc.ClientPreparedStatement: DELETE FROM insegnanti WHERE id_insegnante=0'),
(140, 0, '2024-05-25 17:08:20', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE user SET date_access=\'2024-05-25\' WHERE id=23'),
(141, 23, '2024-05-25 17:09:08', 'com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE Valutazioni SET id_studente=88, id_materia=77, id_classe=43, data_valutazione=\'2024-05-04\', voto=4.66, tipo_valutazione=\'scritto\' WHERE id_valutazione=20'),
(142, 23, '2024-05-25 17:09:37', 'com.mysql.cj.jdbc.ClientPreparedStatement: INSERT INTO Valutazioni (id_studente, id_materia, id_classe, data_valutazione, voto, tipo_valutazione) VALUES (90, 77, 43, \'2024-05-02\', 3.0, \'scritto\')');

-- --------------------------------------------------------

--
-- Table structure for table `Materie`
--

CREATE TABLE `Materie` (
  `id_materia` int(11) NOT NULL,
  `nome_materia` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Materie`
--

INSERT INTO `Materie` (`id_materia`, `nome_materia`) VALUES
(77, 'Architetture dei calcolatori'),
(78, 'Basi di dati'),
(79, 'Chimica applicata'),
(80, 'Disegno tecnico meccanico'),
(81, 'Educazione fisica'),
(82, 'Elettronica digitale'),
(83, 'Fisica applicata'),
(84, 'Fondamenti di informatica'),
(85, 'Geometria descrittiva ed applicata'),
(86, 'Gestione progetti informatici'),
(87, 'Geografia'),
(88, 'Impianti tecnici edili'),
(89, 'Inglese tecnico'),
(90, 'Italiano'),
(91, 'Matematica applicata'),
(92, 'Materiali e strutture edili'),
(93, 'Programmazione C++'),
(94, 'Programmazione Java'),
(95, 'Religione'),
(96, 'Sicurezza nei cantieri'),
(97, 'Sistemi e reti'),
(98, 'Storia'),
(99, 'Tecnologie e sistemi costruttivi edili'),
(100, 'Tecnologie meccaniche');

-- --------------------------------------------------------

--
-- Table structure for table `Studenti`
--

CREATE TABLE `Studenti` (
  `id_studente` int(11) NOT NULL,
  `id_classe` int(11) DEFAULT NULL,
  `id_utente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Studenti`
--

INSERT INTO `Studenti` (`id_studente`, `id_classe`, `id_utente`) VALUES
(87, 5, 150),
(88, 43, 151),
(89, 43, 152),
(90, 43, 153),
(91, 43, 154),
(92, 43, 155),
(93, 6, 156);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Id` int(11) NOT NULL,
  `username` varchar(25) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `email` varchar(100) NOT NULL,
  `cellphone` varchar(25) NOT NULL,
  `birth_date` date NOT NULL,
  `date_access` datetime DEFAULT NULL,
  `date_insert` datetime NOT NULL,
  `password` varchar(100) NOT NULL,
  `id_role` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Id`, `username`, `first_name`, `last_name`, `email`, `cellphone`, `birth_date`, `date_access`, `date_insert`, `password`, `id_role`) VALUES
(23, 'admin', 'admin', 'admin', 'admin@gmail', '12324', '2024-04-29', '2024-05-25 00:00:00', '2024-05-18 00:00:00', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1),
(149, 'InsegnanteProva1', 'InsegnanteProva1', 'InsegnanteProva1', 'InsegnanteProva1@gmail', '23425235', '2024-05-03', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 2),
(150, 'studenteProva1', 'studenteProva1', 'studenteProva1', 'studenteProva1@g', '2325', '2024-05-15', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(151, 'corsoDaSosa', 'Davide', 'Corso', 'corsoDaSosa@gmail.com', '1224252', '2024-04-30', '2024-05-25 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(152, 'filippo.topinelli', 'Filippo', 'Topinelli', 'ftopin@g', '2323532332', '2024-05-11', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(153, 'candeagod', 'davide', 'candeago', 'candeagod@gm', '124559222', '2024-05-02', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(154, 'angeloderiva', 'angelo', 'deriva', 'angeloderiva@gg', '23523532', '2024-04-10', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(155, 'stefanone', 'simone', 'giolai', 'stefanone@yahoo.com', '124959595', '2024-05-02', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(156, 'andream', 'andrea', 'marco', 'andream@gma', '23993923', '2024-05-02', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 4),
(157, 'ZanettiS', 'Stefano', 'Zanetti', 'stefanoz@gmail.com', '2385923', '2024-05-01', '2024-05-25 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 2),
(159, 'Stevano.darchivio', 'Stefano', 'D\'archivio', 'stefanoda@gmail.com', '1281491', '2024-05-01', '2024-05-20 00:00:00', '2024-05-20 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 2),
(160, 'PintoIgnazio', 'Ignazio', 'Pinto', 'ignaziopinto@gmail.com', '12414991', '2024-05-14', '2024-05-23 00:00:00', '2024-05-23 00:00:00', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 2);

-- --------------------------------------------------------

--
-- Table structure for table `Valutazioni`
--

CREATE TABLE `Valutazioni` (
  `id_valutazione` int(11) NOT NULL,
  `id_studente` int(11) DEFAULT NULL,
  `id_classe` int(11) DEFAULT NULL,
  `id_materia` int(11) DEFAULT NULL,
  `data_valutazione` date DEFAULT NULL,
  `voto` double(4,2) DEFAULT NULL,
  `tipo_valutazione` enum('scritto','orale') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Valutazioni`
--

INSERT INTO `Valutazioni` (`id_valutazione`, `id_studente`, `id_classe`, `id_materia`, `data_valutazione`, `voto`, `tipo_valutazione`) VALUES
(4, 88, 43, 77, '2024-05-13', 7.00, 'scritto'),
(5, 88, 43, 77, '2024-05-09', 3.00, 'orale'),
(6, 88, 43, 77, '2024-05-11', 8.00, 'scritto'),
(7, 89, 43, 77, '2024-05-22', 10.00, 'orale'),
(8, 88, 43, 77, '2024-05-06', 9.49, 'orale'),
(9, 89, 43, 77, '2024-05-01', 9.40, 'orale'),
(10, 89, 43, 77, '2024-05-11', 8.90, 'orale'),
(11, 90, 43, 77, '2024-05-09', 5.00, 'orale'),
(12, 90, 43, 77, '2024-05-04', 6.00, 'scritto'),
(13, 90, 43, 77, '2024-05-02', 7.80, 'scritto'),
(14, 88, 43, 77, '2024-05-03', 7.40, 'orale'),
(17, 89, 43, 77, '2024-05-09', 9.26, 'scritto'),
(18, 88, 43, 77, '2024-05-03', 8.80, 'orale'),
(19, 88, 43, 77, '2024-04-29', 5.00, 'orale'),
(20, 88, 43, 77, '2024-05-04', 4.66, 'scritto'),
(21, 88, 43, 77, '2024-05-11', 9.00, 'orale'),
(22, 88, 43, 77, '2024-02-29', 9.76, 'scritto'),
(23, 88, 43, 87, '2024-05-07', 7.00, 'orale'),
(24, 88, 43, 87, '2024-05-07', 8.00, 'orale'),
(25, 88, 43, 87, '2024-05-02', 5.00, 'orale'),
(26, 88, 43, 87, '2024-05-20', 8.30, 'scritto'),
(27, 88, 43, 87, '2024-05-07', 9.79, 'scritto'),
(28, 88, 43, 87, '2024-04-30', 8.33, 'scritto'),
(29, 88, 43, 87, '2024-05-09', 8.30, 'orale'),
(30, 89, 43, 87, '2024-05-08', 9.90, 'scritto'),
(31, 90, 43, 77, '2024-05-02', 3.00, 'scritto');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Classi`
--
ALTER TABLE `Classi`
  ADD PRIMARY KEY (`id_classe`);

--
-- Indexes for table `Insegnanti`
--
ALTER TABLE `Insegnanti`
  ADD PRIMARY KEY (`id_insegnante`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indexes for table `Insegnanti_Classi_Materie`
--
ALTER TABLE `Insegnanti_Classi_Materie`
  ADD KEY `fk_materia` (`id_materia`),
  ADD KEY `fk_classe` (`id_classe`),
  ADD KEY `id_insegnante` (`id_insegnante`,`id_materia`,`id_classe`) USING BTREE;

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Materie`
--
ALTER TABLE `Materie`
  ADD PRIMARY KEY (`id_materia`);

--
-- Indexes for table `Studenti`
--
ALTER TABLE `Studenti`
  ADD PRIMARY KEY (`id_studente`),
  ADD KEY `id_classe` (`id_classe`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `id_role` (`id_role`);

--
-- Indexes for table `Valutazioni`
--
ALTER TABLE `Valutazioni`
  ADD PRIMARY KEY (`id_valutazione`),
  ADD KEY `id_studente` (`id_studente`),
  ADD KEY `id_classe` (`id_classe`),
  ADD KEY `id_materia` (`id_materia`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Classi`
--
ALTER TABLE `Classi`
  MODIFY `id_classe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `Insegnanti`
--
ALTER TABLE `Insegnanti`
  MODIFY `id_insegnante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=143;

--
-- AUTO_INCREMENT for table `Materie`
--
ALTER TABLE `Materie`
  MODIFY `id_materia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- AUTO_INCREMENT for table `Studenti`
--
ALTER TABLE `Studenti`
  MODIFY `id_studente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=167;

--
-- AUTO_INCREMENT for table `Valutazioni`
--
ALTER TABLE `Valutazioni`
  MODIFY `id_valutazione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Insegnanti`
--
ALTER TABLE `Insegnanti`
  ADD CONSTRAINT `insegnanti_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `user` (`Id`);

--
-- Constraints for table `Insegnanti_Classi_Materie`
--
ALTER TABLE `Insegnanti_Classi_Materie`
  ADD CONSTRAINT `fk_classe` FOREIGN KEY (`id_classe`) REFERENCES `Classi` (`id_classe`),
  ADD CONSTRAINT `fk_insegnante` FOREIGN KEY (`id_insegnante`) REFERENCES `Insegnanti` (`id_insegnante`),
  ADD CONSTRAINT `fk_materia` FOREIGN KEY (`id_materia`) REFERENCES `Materie` (`id_materia`);

--
-- Constraints for table `Studenti`
--
ALTER TABLE `Studenti`
  ADD CONSTRAINT `studenti_ibfk_1` FOREIGN KEY (`id_classe`) REFERENCES `Classi` (`id_classe`),
  ADD CONSTRAINT `studenti_ibfk_2` FOREIGN KEY (`id_utente`) REFERENCES `user` (`Id`);

--
-- Constraints for table `Valutazioni`
--
ALTER TABLE `Valutazioni`
  ADD CONSTRAINT `valutazioni_ibfk_1` FOREIGN KEY (`id_studente`) REFERENCES `Studenti` (`id_studente`),
  ADD CONSTRAINT `valutazioni_ibfk_2` FOREIGN KEY (`id_classe`) REFERENCES `Classi` (`id_classe`),
  ADD CONSTRAINT `valutazioni_ibfk_3` FOREIGN KEY (`id_materia`) REFERENCES `Materie` (`id_materia`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
