-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2018 m. Geg 14 d. 16:13
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db`
--

-- --------------------------------------------------------

--
-- Sukurta duomenų struktūra lentelei `users`
--

CREATE TABLE `users` (
  `username` varchar(30) CHARACTER SET cp1257 COLLATE cp1257_lithuanian_ci NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `userlevel` int(1) NOT NULL,
  `email` varchar(50) NOT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Sukurta duomenų kopija lentelei `users`
--

INSERT INTO `users` (`username`, `name`, `surname`, `password`, `userlevel`, `email`, `timestamp`) VALUES
('Direktorius', 'Domas', 'Domukas', 'Direkt', 9, 'direktorius@kitm.lt', '0000-00-00 00:00:00'),
('Administratorius', 'Tadas', 'Tadukas', 'admin', 9, 'admin@adminas.lt', '0000-00-00 00:00:00'),
('Vartotojas2', 'Rūta', 'Rūtienė', 'vart2', 1, 'ruta@ruta.com', '2018-02-13 19:36:37'),
('Vartotojas1', 'Petras', 'Petraitis', 'vart1', 1, 'petras@petriukas.com', '2018-02-13 18:39:40'),
('Vartotojas3', 'Rasa', 'Rasienė', 'vart3', 1, 'rasa@rasa.com', '2018-02-13 19:38:03'),
('Vartotojas4', 'Rima', 'Rimienė', 'vart4', 1, 'rima@rima.com', '2018-02-14 23:27:17'),
('Vartotojas5', 'Violeta', 'Violienė', 'vart5', 1, 'violeta@vio.lt', '2018-02-15 00:31:34'),
('Vartotojas6', 'Sandra', 'Sandrienė', 'vart6', 1, 'sandra@sandra.lt', '2018-02-15 00:47:33');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
