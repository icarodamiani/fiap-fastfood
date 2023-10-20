-- MySQL Script generated by MySQL Workbench
-- Mon Oct 16 20:11:12 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema fastfood
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fastfood
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fastfood` DEFAULT CHARACTER SET utf8 ;
USE `fastfood` ;

-- -----------------------------------------------------
-- Table `fastfood`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`categoria` (
  `id_categoria` INT NOT NULL,
  `descricao` VARCHAR(45) NULL,
  PRIMARY KEY (`id_categoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`produto` (
  `id_produto` INT NOT NULL,
  `id_categoria` INT NOT NULL,
  `descricao` VARCHAR(200) NOT NULL,
  `preco` DECIMAL(15,2) NOT NULL,
  `imagem` BLOB NOT NULL,
  `quantidade` INT NULL,
  PRIMARY KEY (`id_produto`),
  UNIQUE INDEX `idproduto_UNIQUE` (`id_produto` ASC) VISIBLE,
  INDEX `categoria_idcategoria_idx` (`id_categoria` ASC) VISIBLE,
  CONSTRAINT `categoria_idcategoria`
    FOREIGN KEY (`id_categoria`)
    REFERENCES `fastfood`.`categoria` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`cliente` (
  `id_cliente` INT NOT NULL,
  `nome` VARCHAR(200) NOT NULL,
  `documento` VARCHAR(14) NOT NULL,
  `telefone` VARCHAR(20) NULL,
  `email` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE INDEX `idcliente_UNIQUE` (`id_cliente` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`pagamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`pagamento` (
  `id_pagamento` INT NOT NULL,
  `meio_pagamento` VARCHAR(200) NULL,
  `valor` DECIMAL NULL,
  `data_hora` DATETIME NULL,
  PRIMARY KEY (`id_pagamento`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`ponto_venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`ponto_venda` (
  `id_ponto_venda` INT NOT NULL,
  `descricao` VARCHAR(45) NULL,
  PRIMARY KEY (`id_ponto_venda`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`pedido` (
  `id_pedido` INT NOT NULL,
  `id_cliente` INT NULL,
  `id_pagamento` INT NULL,
  `id_ponto_venda` INT NULL,
  `data_hora` DATETIME NULL,
  `numero_pedido` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_pedido`),
  INDEX `cliente_idcliente_idx` (`id_cliente` ASC) VISIBLE,
  INDEX `forma_pagamento_idforma_pagamento_idx` (`id_pagamento` ASC) VISIBLE,
  INDEX `ponto_venda_idponto_venda_idx` (`id_ponto_venda` ASC) VISIBLE,
  CONSTRAINT `cliente_idcliente`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `fastfood`.`cliente` (`id_cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `forma_pagamento_idforma_pagamento`
    FOREIGN KEY (`id_pagamento`)
    REFERENCES `fastfood`.`pagamento` (`id_pagamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ponto_venda_idponto_venda`
    FOREIGN KEY (`id_ponto_venda`)
    REFERENCES `fastfood`.`ponto_venda` (`id_ponto_venda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`items_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`items_pedido` (
  `id_item_pedido` INT NOT NULL,
  `id_pedido` INT NULL,
  `id_produto` INT NULL,
  `quantidade` INT NULL,
  `observacao` VARCHAR(2000) NULL,
  PRIMARY KEY (`id_item_pedido`),
  INDEX `produto_idproduto_idx` (`id_produto` ASC) VISIBLE,
  INDEX `pedido_idpedido_idx` (`id_pedido` ASC) VISIBLE,
  CONSTRAINT `produto_idproduto`
    FOREIGN KEY (`id_produto`)
    REFERENCES `fastfood`.`produto` (`id_produto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pedido_idpedido`
    FOREIGN KEY (`id_pedido`)
    REFERENCES `fastfood`.`pedido` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`comprovante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`comprovante` (
  `id_comprovante` INT NOT NULL,
  `id_pedido` INT NULL,
  `data_hora_emissao` DATETIME NULL,
  `numero` VARCHAR(500) NULL,
  PRIMARY KEY (`id_comprovante`),
  INDEX `comprovante_idpedido_idx` (`id_pedido` ASC) VISIBLE,
  CONSTRAINT `comprovante_idpedido`
    FOREIGN KEY (`id_pedido`)
    REFERENCES `fastfood`.`pedido` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`status_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`status_pedido` (
  `id_status` INT NOT NULL,
  `nome` VARCHAR(30) NOT NULL,
  `descricao` VARCHAR(100) NULL,
  `habilitado_cliente` TINYINT NOT NULL,
  PRIMARY KEY (`id_status`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`status_has_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`status_has_pedido` (
  `status_id_status` INT NOT NULL,
  `pedido_id_pedido` INT NOT NULL,
  `data_hora` DATETIME NULL,
  PRIMARY KEY (`status_id_status`, `pedido_id_pedido`),
  INDEX `fk_status_has_pedido_pedido1_idx` (`pedido_id_pedido` ASC) VISIBLE,
  INDEX `fk_status_has_pedido_status1_idx` (`status_id_status` ASC) VISIBLE,
  CONSTRAINT `fk_status_has_pedido_status1`
    FOREIGN KEY (`status_id_status`)
    REFERENCES `fastfood`.`status_pedido` (`id_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_status_has_pedido_pedido1`
    FOREIGN KEY (`pedido_id_pedido`)
    REFERENCES `fastfood`.`pedido` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`status_pagamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`status_pagamento` (
  `id_status_pagamento` INT NOT NULL,
  `id_pagamento` INT NOT NULL,
  `descricao` VARCHAR(45) NULL,
  PRIMARY KEY (`id_status_pagamento`),
  INDEX `fk_status_pagamento_pagamento1_idx` (`id_pagamento` ASC) VISIBLE,
  CONSTRAINT `fk_status_pagamento_pagamento1`
    FOREIGN KEY (`id_pagamento`)
    REFERENCES `fastfood`.`pagamento` (`id_pagamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fastfood`.`caixa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastfood`.`caixa` (
  `id_caixa` INT NOT NULL,
  `data_hora_abertura` DATETIME NULL,
  `data_hora_fechamento` DATETIME NULL,
  `id_ponto_venda` INT NOT NULL,
  PRIMARY KEY (`id_caixa`),
  INDEX `fk_fechamento_dia_ponto_venda1_idx` (`id_ponto_venda` ASC) VISIBLE,
  CONSTRAINT `fk_fechamento_dia_ponto_venda1`
    FOREIGN KEY (`id_ponto_venda`)
    REFERENCES `fastfood`.`ponto_venda` (`id_ponto_venda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;