-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema caso_de_estudio
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema caso_de_estudio
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `caso_de_estudio` DEFAULT CHARACTER SET utf8mb4 ;
USE `caso_de_estudio` ;

-- -----------------------------------------------------
-- Table `caso_de_estudio`.`concesionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `caso_de_estudio`.`concesionario` (
  `id_concecionario` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  `dirreccion` VARCHAR(50) NULL DEFAULT NULL,
  `telefono` INT(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id_concecionario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `caso_de_estudio`.`marca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `caso_de_estudio`.`marca` (
  `id_marca` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id_marca`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `caso_de_estudio`.`tipovehiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `caso_de_estudio`.`tipovehiculo` (
  `id_tipo` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id_tipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `caso_de_estudio`.`vehiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `caso_de_estudio`.`vehiculo` (
  `id_vehiculo` INT(11) NOT NULL AUTO_INCREMENT,
  `placa` VARCHAR(6) NULL DEFAULT NULL,
  `modelo` INT(6) NULL DEFAULT NULL,
  `precio` DOUBLE NULL DEFAULT NULL,
  `id_tipo_fk` INT(11) NULL DEFAULT NULL,
  `id_marca_fk` INT(11) NULL DEFAULT NULL,
  `id_concesionario_fk` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id_vehiculo`),
  INDEX `id_tipo_fk` (`id_tipo_fk` ASC) ,
  INDEX `id_marca_fk` (`id_marca_fk` ASC) ,
  INDEX `id_concesionario_fk` (`id_concesionario_fk` ASC) ,
  CONSTRAINT `vehiculo_ibfk_1`
    FOREIGN KEY (`id_tipo_fk`)
    REFERENCES `caso_de_estudio`.`tipovehiculo` (`id_tipo`),
  CONSTRAINT `vehiculo_ibfk_2`
    FOREIGN KEY (`id_marca_fk`)
    REFERENCES `caso_de_estudio`.`marca` (`id_marca`),
  CONSTRAINT `vehiculo_ibfk_3`
    FOREIGN KEY (`id_concesionario_fk`)
    REFERENCES `caso_de_estudio`.`concesionario` (`id_concecionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
