-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2018 at 02:20 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ticket system`
--

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `ID` int(10) NOT NULL,
  `Name` varchar(25) NOT NULL,
  `Password` varchar(25) NOT NULL,
  `User` int(1) NOT NULL,
  `Engineer` int(1) NOT NULL,
  `Manager` int(1) NOT NULL,
  `System Admin` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `Title` varchar(30) NOT NULL,
  `TID` int(4) NOT NULL,
  `description` varchar(50) NOT NULL,
  `created_by` int(3) NOT NULL,
  `status` varchar(1) NOT NULL,
  `Location` varchar(50) NOT NULL,
  `Contact` int(15) NOT NULL,
  `assigned_to` int(3) NOT NULL,
  `Resolution` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `Name` varchar(25) NOT NULL,
  `Password` varchar(6) NOT NULL,
  `Code` char(1) NOT NULL,
  `Location` varchar(50) NOT NULL,
  `Contact` int(15) NOT NULL,
  `Approved` int(1) NOT NULL,
  `Fcm` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Name`, `Password`, `Code`, `Location`, `Contact`, `Approved`, `Fcm`) VALUES
(1, 'User', '1234', 'U', 'Changi Business Park', 12345678, 1, 'up'),
(2, 'Engineer', '1234', 'E', 'Changi Business Park', 12345, 1, 'up'),
(3, 'Manager', '1234', 'M', 'Changi Business Park', 1234, 1, 'up'),
(4, 'Admin', '1234', 'A', 'Changi Business Park', 1234, 1, 'up');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`TID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `TID` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
