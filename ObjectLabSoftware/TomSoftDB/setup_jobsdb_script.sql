-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema jobsDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `jobsDB` ;

-- -----------------------------------------------------
-- Schema jobsDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `jobsDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `jobsDB` ;

-- -----------------------------------------------------
-- Table `jobsDB`.`printers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`printers` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`printers` (
  `printer` VARCHAR(100) NOT NULL,
  `materialType` VARCHAR(20) NULL,
  `materialUnit` VARCHAR(20) NULL,
  `materialCostPerUnit` DECIMAL(10) NULL,
  `materialType2` VARCHAR(20) NULL,
  `materialUnit2` VARCHAR(20) NULL,
  `materialCostPerUnit2` DECIMAL(10) NULL,
  `materialType3` VARCHAR(20) NULL,
  `materialUnit3` VARCHAR(20) NULL,
  `materialCostPerUnit3` DECIMAL(10) NULL,
  `materialType4` VARCHAR(20) NULL,
  `materialUnit4` VARCHAR(20) NULL,
  `materialCostPerUnit4` DECIMAL(10) NULL,
  `materialType5` VARCHAR(20) NULL,
  `materialUnit5` VARCHAR(20) NULL,
  `materialCostPerUnit5` DECIMAL(10) NULL,
  PRIMARY KEY (`printer`))
ENGINE = InnoDB;

INSERT INTO `jobsDB`.`printers` VALUES
(
	'zcorp', 
	'mtype', 'munit', 0, 
	'mtype2', 'munit2', 1, 
	'mtype3', 'munit3', 2,
	'mtype4', 'munit4', 3,
	'mtype5', 'munit5', 4
);

INSERT INTO `jobsDB`.`printers` VALUES
(
	'solidscape', 
	'mtype', 'munit', 0, 
	'mtype2', 'munit2', 1, 
	'mtype3', 'munit3', 2,
	'mtype4', 'munit4', 3,
	'mtype5', 'munit5', 4
);
-- -----------------------------------------------------
-- Table `jobsDB`.`pendingJobs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`pendingJobs` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`pendingJobs` (
  `idJobs` VARCHAR(200) NOT NULL,
  `printer` VARCHAR(100) NOT NULL,
  `firstName` VARCHAR(50) NULL,
  `lastName` VARCHAR(50) NOT NULL,
  `course` VARCHAR(25) NULL,
  `section` VARCHAR(25) NULL,
  `filename` VARCHAR(100) NOT NULL,
  `filePath` VARCHAR(500) NULL,
  `dateStarted` DATETIME NOT NULL DEFAULT NOW(),
  `dateUpdated` DATETIME NOT NULL DEFAULT NOW(),
  `status` VARCHAR(20) NULL,
  `email` VARCHAR(60) NULL,
  `comment` VARCHAR(500) NULL,
  `buildName` VARCHAR(50) NULL,
  `volume` DECIMAL(8,3) NULL,
  `cost` DECIMAL(8,2) NULL,
  INDEX `printer_idx` (`printer` ASC),
  PRIMARY KEY (`idJobs`),
  CONSTRAINT `printer`
    FOREIGN KEY (`printer`)
    REFERENCES `jobsDB`.`printers` (`printer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jobsDB`.`timestamps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`timestamps` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`timestamps` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL);


-- -----------------------------------------------------
-- Table `jobsDB`.`completedJobs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`completedJobs` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`completedJobs` (
  `idCompJobs` VARCHAR(200) NOT NULL,
  `printer` VARCHAR(100) NOT NULL,
  `firstName` VARCHAR(50) NULL,
  `lastName` VARCHAR(50) NULL,
  `course` VARCHAR(25) NULL,
  `section` VARCHAR(25) NULL,
  `filename` VARCHAR(100) NULL,
  `filePath` VARCHAR(500) NULL,
  `dateStarted` DATETIME NULL,
  `dateCompleted` DATETIME NULL,
  `status` VARCHAR(20) NULL,
  `email` VARCHAR(60) NULL,
  `comment` VARCHAR(500) NULL,
  `buildName` VARCHAR(50) NULL,
  `volume` DECIMAL(8,3) NULL,
  `cost` DECIMAL(8,2) NULL,
  PRIMARY KEY (`idCompJobs`),
  INDEX `printer_idx` (`printer` ASC),
  CONSTRAINT `printerComp`
    FOREIGN KEY (`printer`)
    REFERENCES `jobsDB`.`printers` (`printer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jobsDB`.`solidscape`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`solidscape` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`solidscape` (
  `idSolidscape` VARCHAR(200) NOT NULL,
  `buildName` VARCHAR(50) NULL,
  `dateRun` DATETIME NULL DEFAULT now(),
  `noModels` INT NULL,
  `resolution` DECIMAL(8,2) NULL,
  `runTime` TIME NULL,
  `comments` VARCHAR(100) NULL,
  `costOfBuild` DECIMAL(8,2) NULL,
  `status` VARCHAR(50) NULL,
  PRIMARY KEY (`idSolidscape`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jobsDB`.`classes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`classes` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`classes` (
  `idClasses` INT NOT NULL AUTO_INCREMENT,
  `className` VARCHAR(25) NULL,
  `classSection` VARCHAR(25) NULL,
  `current` TINYINT(1) NULL,
  PRIMARY KEY (`idClasses`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jobsDB`.`zcorp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`zcorp` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`zcorp` (
  `idZcorp` VARCHAR(200) NOT NULL,
  `buildName` VARCHAR(50) NULL,
  `dateRun` DATETIME NULL DEFAULT now(),
  `monoBinder` DECIMAL(8,3) NULL,
  `yellowBinder` DECIMAL(8,3) NULL,
  `magentaBinder` DECIMAL(8,3) NULL,
  `cyanBinder` DECIMAL(8,3) NULL,
  `cubicInches` DECIMAL(8,3) NULL,
  `noModels` INT NULL,
  `runTime` TIME NULL,
  `comments` VARCHAR(100) NULL,
  `costOfBuild` DECIMAL(8,2) NULL,
  `status` VARCHAR(50) NULL,
  PRIMARY KEY (`idZcorp`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jobsDB`.`objet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jobsDB`.`objet` ;

CREATE TABLE IF NOT EXISTS `jobsDB`.`objet` (
  `idObjet` VARCHAR(200) NOT NULL,
  `buildName` VARCHAR(50) NULL,
  `dateRun` DATETIME NULL DEFAULT NOW(),
  `buildConsumed` DECIMAL(8,3) NULL,
  `supportConsumed` DECIMAL(8,3) NULL,
  `noModels` INT NULL,
  `buildMaterials` VARCHAR(50) NULL,
  `resolution` DECIMAL(8,2) NULL,
  `runTime` TIME NULL,
  `comments` VARCHAR(100) NULL,
  `costOfBuild` DECIMAL(8,2) NULL,
  `status` VARCHAR(50) NULL,
  PRIMARY KEY (`idObjet`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
