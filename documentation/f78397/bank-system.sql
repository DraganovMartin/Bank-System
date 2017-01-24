-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 24 яну 2017 в 07:22
-- Версия на сървъра: 10.1.19-MariaDB
-- PHP Version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank-system`
--

-- --------------------------------------------------------

--
-- Структура на таблица `bankaccount`
--

CREATE TABLE `bankaccount` (
  `id` int(11) NOT NULL,
  `amount` decimal(65,2) NOT NULL DEFAULT '0.00',
  `currency_id` varchar(3) NOT NULL,
  `type_id` varchar(30) NOT NULL,
  `userName` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `bankaccount`
--

INSERT INTO `bankaccount` (`id`, `amount`, `currency_id`, `type_id`, `userName`) VALUES
(1, '900.50', 'BGN', 'deposit', 'niksan'),
(2, '1055.27', 'CHF', 'market', 'niksan'),
(3, '999.46', 'USD', 'sevings', 'niksan'),
(6, '900.50', 'BGN', 'deposit', 'marko'),
(7, '1000.00', 'CHF', 'market', 'mizonet'),
(8, '1053.72', 'USD', 'sevings', 'mizonet'),
(11, '900.50', 'BGN', 'deposit', 'niksan'),
(12, '1000.00', 'CHF', 'market', 'niksan'),
(13, '1053.72', 'USD', 'sevings', 'niksan'),
(16, '900.50', 'BGN', 'deposit', 'marko'),
(17, '1000.00', 'CHF', 'market', 'mizonet'),
(18, '1053.72', 'USD', 'sevings', 'mizonet'),
(21, '900.50', 'BGN', 'deposit', 'niksan'),
(22, '1000.00', 'CHF', 'market', 'niksan'),
(23, '1000.00', 'USD', 'sevings', 'niksan'),
(24, '957.30', 'GPB', 'deposit', 'alex'),
(25, '1050.87', 'EUR', 'deposit', 'marko'),
(26, '1000.00', 'BGN', 'deposit', 'marko'),
(27, '1000.00', 'CHF', 'market', 'mizonet'),
(28, '1000.00', 'USD', 'sevings', 'mizonet'),
(29, '1000.00', 'GPB', 'deposit', 'alex'),
(30, '1050.87', 'EUR', 'deposit', 'niksan');

-- --------------------------------------------------------

--
-- Структура на таблица `bankaccounttype`
--

CREATE TABLE `bankaccounttype` (
  `id` varchar(30) NOT NULL,
  `overdraft` decimal(65,2) NOT NULL,
  `intresteRate` decimal(14,2) NOT NULL,
  `tax` decimal(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `bankaccounttype`
--

INSERT INTO `bankaccounttype` (`id`, `overdraft`, `intresteRate`, `tax`) VALUES
('deposit', '100000.00', '1.10', '2.00'),
('market', '1000000.00', '-1.00', '3.00'),
('sevings', '100000.00', '1.40', '0.00');

-- --------------------------------------------------------

--
-- Структура на таблица `currencies`
--

CREATE TABLE `currencies` (
  `id` varchar(3) NOT NULL,
  `value` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `currencies`
--

INSERT INTO `currencies` (`id`, `value`) VALUES
('BGN', 1),
('CHF', 1.81836),
('EUR', 1.95583),
('GPB', 2.33017),
('USD', 1.85229);

-- --------------------------------------------------------

--
-- Структура на таблица `systemprofiles`
--

CREATE TABLE `systemprofiles` (
  `userName` varchar(40) NOT NULL,
  `password` binary(16) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `secondName` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `systemprofiles`
--

INSERT INTO `systemprofiles` (`userName`, `password`, `firstName`, `secondName`) VALUES
('', 0x00000000000000000000000000000000, '', ''),
('alex', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Alex', 'Malex'),
('kake', 0x27e6ac3cde0d45aebfb54968b4356f25, 'Mark', 'Markov'),
('marko', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Markov', 'Marki'),
('Martin', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Martin', 'Martinov'),
('MIKI', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Mark', 'Markov'),
('mizonet', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Mizonetova', 'Markova'),
('Mvroit', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Mavroitov', 'Mvrata'),
('niksan', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Nikolay', 'Nikolov'),
('pesho', 0x827ccb0eea8a706c4c34a16891f84e7b, 'Pesho', 'Peshev'),
('root', 0x00000000000000000000000000000000, 'root', 'root');

-- --------------------------------------------------------

--
-- Структура на таблица `transfers`
--

CREATE TABLE `transfers` (
  `id` int(11) NOT NULL,
  `amount` decimal(65,2) NOT NULL DEFAULT '0.00',
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `fromBankAccount_id` int(11) NOT NULL,
  `toBankAccount_id` int(11) NOT NULL,
  `currency_id` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Схема на данните от таблица `transfers`
--

INSERT INTO `transfers` (`id`, `amount`, `date`, `fromBankAccount_id`, `toBankAccount_id`, `currency_id`) VALUES
(1, '100.50', '2017-01-23 21:20:02', 1, 2, 'BGN'),
(2, '100.50', '2017-01-23 21:20:02', 2, 3, 'BGN'),
(6, '100.50', '2017-01-23 21:20:03', 6, 7, 'BGN'),
(7, '100.50', '2017-01-23 21:20:03', 7, 8, 'BGN'),
(11, '100.50', '2017-01-23 21:20:03', 11, 12, 'BGN'),
(12, '100.50', '2017-01-23 21:20:03', 12, 13, 'BGN'),
(16, '100.50', '2017-01-23 21:20:03', 16, 17, 'BGN'),
(17, '100.50', '2017-01-23 21:20:03', 17, 18, 'BGN'),
(21, '100.50', '2017-01-23 21:20:03', 21, 22, 'BGN'),
(22, '100.50', '2017-01-23 21:20:03', 22, 23, 'BGN'),
(23, '100.50', '2017-01-23 21:20:03', 23, 24, 'BGN'),
(24, '100.50', '2017-01-23 21:20:03', 24, 25, 'BGN'),
(25, '100.50', '2017-01-23 21:20:03', 25, 26, 'BGN'),
(26, '100.50', '2017-01-23 21:20:04', 26, 27, 'BGN'),
(27, '100.50', '2017-01-23 21:20:04', 27, 28, 'BGN'),
(28, '100.50', '2017-01-23 21:20:04', 28, 29, 'BGN'),
(29, '100.50', '2017-01-23 21:20:04', 29, 30, 'BGN'),
(30, '99.50', '2017-01-23 21:41:37', 1, 2, 'BGN'),
(31, '99.50', '2017-01-23 21:41:38', 2, 3, 'BGN'),
(32, '99.50', '2017-01-23 21:41:39', 6, 7, 'BGN'),
(33, '99.50', '2017-01-23 21:41:39', 7, 8, 'BGN'),
(34, '99.50', '2017-01-23 21:41:40', 11, 12, 'BGN'),
(35, '99.50', '2017-01-23 21:41:41', 12, 13, 'BGN'),
(36, '99.50', '2017-01-23 21:41:41', 16, 17, 'BGN'),
(37, '99.50', '2017-01-23 21:41:41', 17, 18, 'BGN'),
(38, '99.50', '2017-01-23 21:41:42', 21, 22, 'BGN'),
(39, '99.50', '2017-01-23 21:41:42', 22, 23, 'BGN'),
(40, '99.50', '2017-01-23 21:41:43', 23, 24, 'BGN'),
(41, '99.50', '2017-01-23 21:41:43', 24, 25, 'BGN'),
(42, '99.50', '2017-01-23 21:41:43', 25, 26, 'BGN'),
(43, '99.50', '2017-01-23 21:41:43', 26, 27, 'BGN'),
(44, '99.50', '2017-01-23 21:41:44', 27, 28, 'BGN'),
(45, '99.50', '2017-01-23 21:41:44', 28, 29, 'BGN'),
(46, '99.50', '2017-01-23 21:41:44', 29, 30, 'BGN'),
(47, '99.50', '2017-01-23 21:43:27', 24, 25, 'BGN'),
(48, '6666.66', '2017-01-23 23:09:22', 1, 1, 'USD'),
(49, '6666.66', '2017-01-23 23:12:27', 1, 1, 'USD'),
(50, '6666.66', '2017-01-23 23:19:01', 1, 1, 'USD'),
(51, '6666.66', '2017-01-23 23:19:32', 1, 1, 'USD'),
(52, '-25597.68', '2017-01-23 23:20:28', 1, 1, 'BGN'),
(53, '-6666.66', '2017-01-23 23:22:02', 1, 1, 'BGN'),
(54, '-6666.66', '2017-01-23 23:22:07', 1, 1, 'BGN');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bankaccount`
--
ALTER TABLE `bankaccount`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bankAccount_currencies1_idx` (`currency_id`),
  ADD KEY `fk_bankAccount_bankAccountType1_idx` (`type_id`),
  ADD KEY `fk_bankAccount_systemProfiles1_idx` (`userName`);

--
-- Indexes for table `bankaccounttype`
--
ALTER TABLE `bankaccounttype`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `currencies`
--
ALTER TABLE `currencies`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `systemprofiles`
--
ALTER TABLE `systemprofiles`
  ADD PRIMARY KEY (`userName`),
  ADD UNIQUE KEY `userName_UNIQUE` (`userName`);

--
-- Indexes for table `transfers`
--
ALTER TABLE `transfers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_transfers_bankAccount1_idx` (`fromBankAccount_id`),
  ADD KEY `fk_transfers_bankAccount2_idx` (`toBankAccount_id`),
  ADD KEY `fk_transfers_currencies1_idx` (`currency_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bankaccount`
--
ALTER TABLE `bankaccount`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `transfers`
--
ALTER TABLE `transfers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;
--
-- Ограничения за дъмпнати таблици
--

--
-- Ограничения за таблица `bankaccount`
--
ALTER TABLE `bankaccount`
  ADD CONSTRAINT `fk_bankAccount_bankAccountType1` FOREIGN KEY (`type_id`) REFERENCES `bankaccounttype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_bankAccount_currencies1` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_bankAccount_systemProfiles1` FOREIGN KEY (`userName`) REFERENCES `systemprofiles` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения за таблица `transfers`
--
ALTER TABLE `transfers`
  ADD CONSTRAINT `fk_transfers_bankAccount1` FOREIGN KEY (`fromBankAccount_id`) REFERENCES `bankaccount` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_transfers_bankAccount2` FOREIGN KEY (`toBankAccount_id`) REFERENCES `bankaccount` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_transfers_currencies1` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
