-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 20, 2019 at 06:23 PM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ams`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_owner`
--

CREATE TABLE `tbl_owner` (
  `owner_id` int(11) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_owner`
--

INSERT INTO `tbl_owner` (`owner_id`, `first_name`, `last_name`, `address`, `email`, `telephone`, `isDeleted`) VALUES
(10, 'Farook', 'Fazrin', '412 bulugohotenne, Batugoda', 'mfmfazrin1986@gmail.com', '+947744444444', 1),
(11, 'Jerom', 'Sanjeewan', '123, Kandy Road, Colombo', 'abcd@gmail.com', '0772045555', 1),
(20, 'Azeem', 'Mohamed', '321, Owner Street, Colombo', 'azeemproperties@gmail.com', '0772019321', 1),
(21, 'Suja', 'Ahmed', '321, Matale Road, Kandy', 'abcd@gmail.com', '0772049123', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_payment`
--

CREATE TABLE `tbl_payment` (
  `payment_id` int(11) NOT NULL,
  `property_id` int(11) NOT NULL,
  `paid_by` int(11) NOT NULL,
  `charge` float NOT NULL,
  `payment_month` date NOT NULL,
  `paid_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_property`
--

CREATE TABLE `tbl_property` (
  `property_id` int(11) NOT NULL,
  `property_type` int(11) DEFAULT NULL,
  `address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `suitable_for` smallint(6) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  `rented_by` int(11) DEFAULT NULL,
  `charge` float DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '1',
  `status` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_property`
--

INSERT INTO `tbl_property` (`property_id`, `property_type`, `address`, `suitable_for`, `is_available`, `owner`, `rented_by`, `charge`, `isDeleted`, `status`) VALUES
(1, 1, '123, jaffna', 100, 0, 10, 3, 500, 1, 1),
(2, 2, '321, Colombo', 4, 0, 10, 5, 1000, 1, 1),
(3, 2, '88, Matale ', 15, 1, 10, 0, 5000, 1, 1),
(4, 3, '412 bulugohotenne, Batugoda', 12, 1, 10, 0, 12000, 1, 1),
(5, 1, '123, New Road', 5, 1, 10, 0, 50, 1, 1),
(6, 1, '321, Trincomalee Street', 5, 0, 10, 2, 50, 1, 1),
(9, 6, '33, Matale Road, Kandy', 5, 0, 10, 6, 5000, 1, 1),
(11, 6, '33, Matale Road, Kandy', 8, 1, 10, 0, 5000, 1, 1),
(12, 7, '123, New Road, ', 3, 1, 10, 0, 3000, 1, 1),
(13, 1, '212, new street, kandy', 4, 1, 10, 0, 500, 1, 1),
(14, 4, '123, colombo street', 5, 0, 10, 4, 5000, 1, 1),
(15, 4, '875 Beaver Ridge Ave.  Hinesville, GA 31313', 4, 1, 21, 0, 4000, 1, 1),
(16, 5, '4 Chapel Drive  Port Charlotte, FL 33952', 5, 1, 21, 0, 5000, 1, 1),
(17, 6, '152 Leatherwood Street  Janesville, WI 53546', 12, 1, 21, 0, 12500, 1, 0),
(18, 4, '9692 Wagon St.  Largo, FL 33771', 2, 1, 21, 0, 8000, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_property_types`
--

CREATE TABLE `tbl_property_types` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_property_types`
--

INSERT INTO `tbl_property_types` (`type_id`, `type_name`, `isDeleted`) VALUES
(1, 'Single Room', 1),
(2, 'Double Room', 1),
(3, 'Annex', 1),
(4, 'Flat\r\n', 1),
(5, 'Apartment', 1),
(6, 'Villa', 1),
(7, 'Bungalow', 1),
(8, 'House', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_student`
--

CREATE TABLE `tbl_student` (
  `student_id` int(11) NOT NULL,
  `first_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address` text COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telephone` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_student`
--

INSERT INTO `tbl_student` (`student_id`, `first_name`, `last_name`, `address`, `email`, `telephone`, `isDeleted`) VALUES
(1, 'fazrin', 'farook ', '412, Bulugohotenne, Batugoda, Kandy', 'mfmfazrin1986@gmail.com', '0772049000', 1),
(2, 'jerom', 'sanjeewan', 'hekita, wattala', 'jerom@gmail.com', '0778898893', 1),
(3, 'Zamra', 'Banu', 'kolonnawa, Colombo', 'zamra@gmail.com', '0776543212', 1),
(4, 'Fazrin', 'Nuh', '123, Kandy Road, Matale', 'abcd@gmail.com', '0774321212', 1),
(6, 'Arfath', 'Mohamed', '123, Bambalapitiya', 'abcd@gmail.com', '0773434343', 1),
(7, 'Stuent', 'New', 'abcd Road', 'abcd@gmail.com', '07711111111', 1),
(9, 'Ajith', 'Kumar', '321, Kandy Road', 'ajith@gmail.com', '077654321', 1),
(19, 'Mohamed', 'Azeem', '123, Kandy Road, Colombo', 'azeem@gmail.com', '0777456456', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `user_type` int(2) NOT NULL,
  `status` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`user_id`, `username`, `password`, `user_type`, `status`) VALUES
(3, 'zamra', '1234', 1, 1),
(4, 'fazrin1', '1234', 1, 1),
(6, 'arfath', '1234', 1, 1),
(7, 'student', '123456', 1, 1),
(8, 'Admin', '123456', 3, 1),
(9, 'stu', '123456', 1, 1),
(10, 'fazrin', '1234', 2, 1),
(11, 'jerom', '1234', 2, 1),
(14, 'owner', '1234', 2, 1),
(17, 'nirzaf1', '1234', 2, 1),
(19, 'azeem', '1234', 1, 1),
(20, 'azeem1', 'abcd', 2, 1),
(21, 'suja', '1234', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_view_request`
--

CREATE TABLE `tbl_view_request` (
  `request_id` int(11) NOT NULL,
  `requested_by` int(11) NOT NULL,
  `requested_property` int(11) NOT NULL,
  `requested_date` date NOT NULL,
  `date_of_view` date NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbl_view_request`
--

INSERT INTO `tbl_view_request` (`request_id`, `requested_by`, `requested_property`, `requested_date`, `date_of_view`, `status`, `isDeleted`) VALUES
(1, 1, 5, '2019-08-17', '2019-08-23', 1, 1),
(2, 1, 1, '2019-08-17', '2019-08-24', 1, 1),
(3, 1, 9, '2019-08-18', '2019-08-24', 1, 1),
(4, 1, 9, '2019-08-18', '2019-08-31', 0, 0),
(5, 1, 12, '2019-08-18', '2019-08-24', 0, 0),
(6, 3, 1, '2019-08-18', '2019-08-31', 1, 0),
(7, 3, 6, '2019-08-19', '2019-08-31', 1, 1),
(8, 3, 13, '2019-08-19', '2019-08-31', 1, 0),
(9, 3, 3, '2019-08-19', '2019-09-01', 1, 0),
(10, 3, 1, '2019-08-19', '2019-08-24', 0, 0),
(11, 3, 5, '2019-08-19', '2019-08-31', 0, 0),
(12, 3, 13, '2019-08-19', '2019-09-28', 0, 0),
(13, 3, 5, '2019-08-19', '2019-08-28', 0, 0),
(14, 3, 5, '2019-08-19', '2019-09-01', 0, 0),
(15, 3, 5, '2019-08-19', '2019-09-01', 0, 0),
(16, 3, 5, '2019-08-19', '2019-08-15', 1, 1),
(17, 3, 11, '2019-08-19', '2019-08-25', 1, 1),
(18, 3, 9, '2019-08-19', '2019-08-24', 1, 1),
(19, 4, 4, '2019-08-19', '2019-08-31', 1, 1),
(20, 3, 9, '2019-08-20', '2019-08-30', 1, 0),
(21, 19, 14, '2019-08-20', '2019-08-30', 0, 1),
(22, 3, 12, '2019-08-20', '2019-08-31', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_owner`
--
ALTER TABLE `tbl_owner`
  ADD PRIMARY KEY (`owner_id`);

--
-- Indexes for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `tbl_property`
--
ALTER TABLE `tbl_property`
  ADD PRIMARY KEY (`property_id`);

--
-- Indexes for table `tbl_property_types`
--
ALTER TABLE `tbl_property_types`
  ADD PRIMARY KEY (`type_id`);

--
-- Indexes for table `tbl_student`
--
ALTER TABLE `tbl_student`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `tbl_view_request`
--
ALTER TABLE `tbl_view_request`
  ADD PRIMARY KEY (`request_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_property`
--
ALTER TABLE `tbl_property`
  MODIFY `property_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `tbl_view_request`
--
ALTER TABLE `tbl_view_request`
  MODIFY `request_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
