/*
SQLyog Enterprise - MySQL GUI v6.15
MySQL - 5.7.21-log : Database - learn_english
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `learn_english`;

USE `learn_english`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `analysistask1` */

DROP TABLE IF EXISTS `analysistask1`;

CREATE TABLE `analysistask1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_times` int(11) NOT NULL DEFAULT '0',
  `wrong_times` int(11) NOT NULL DEFAULT '0',
  `sentence_eng_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgfx9pnxixv927r7m5pwk3l1nj` (`sentence_eng_id`),
  CONSTRAINT `FKgfx9pnxixv927r7m5pwk3l1nj` FOREIGN KEY (`sentence_eng_id`) REFERENCES `sentenceeng` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `analysistask1` */

/*Table structure for table `analysistask2` */

DROP TABLE IF EXISTS `analysistask2`;

CREATE TABLE `analysistask2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_times` int(11) NOT NULL DEFAULT '0',
  `wrong_times` int(11) NOT NULL DEFAULT '0',
  `word_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgo78gan45gqmwtpk20t5v8xdg` (`word_id`),
  CONSTRAINT `FKgo78gan45gqmwtpk20t5v8xdg` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `analysistask2` */

/*Table structure for table `lesson` */

DROP TABLE IF EXISTS `lesson`;

CREATE TABLE `lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) DEFAULT NULL,
  `mean` varchar(300) DEFAULT NULL,
  `score` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_lesson` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `lesson` */

/*Table structure for table `sentenceeng` */

DROP TABLE IF EXISTS `sentenceeng`;

CREATE TABLE `sentenceeng` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sentence` varchar(1000) DEFAULT NULL,
  `sentence_vi_id` int(11) DEFAULT NULL,
  `word_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_sentenceeng` (`sentence`),
  KEY `FKa4011n20dbe6y0ej1rnr2qyby` (`word_id`),
  KEY `FKfuimp31mxpgvd0vbpdkry57tu` (`sentence_vi_id`),
  CONSTRAINT `FKa4011n20dbe6y0ej1rnr2qyby` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`),
  CONSTRAINT `FKfuimp31mxpgvd0vbpdkry57tu` FOREIGN KEY (`sentence_vi_id`) REFERENCES `sentencevi` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `sentenceeng` */

/*Table structure for table `sentencevi` */

DROP TABLE IF EXISTS `sentencevi`;

CREATE TABLE `sentencevi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sentence` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_sentencevi` (`sentence`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `sentencevi` */

/*Table structure for table `synonym` */

DROP TABLE IF EXISTS `synonym`;

CREATE TABLE `synonym` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `synonym_name` varchar(255) DEFAULT NULL,
  `word_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_synonym` (`synonym_name`),
  KEY `FKf986xys78du7gi0p88iqdbyy2` (`word_id`),
  CONSTRAINT `FKf986xys78du7gi0p88iqdbyy2` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `synonym` */

/*Table structure for table `word` */

DROP TABLE IF EXISTS `word`;

CREATE TABLE `word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) DEFAULT NULL,
  `lesson_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_word` (`name`),
  KEY `FK4e4gf9reb9d0wxb24a77ny3sp` (`lesson_id`),
  CONSTRAINT `FK4e4gf9reb9d0wxb24a77ny3sp` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `word` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
