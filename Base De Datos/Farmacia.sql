-- MySQL Script generated by MySQL Workbench
-- Sun Oct 27 17:49:57 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Farmacia
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Farmacia` ;

-- -----------------------------------------------------
-- Schema Farmacia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Farmacia` DEFAULT CHARACTER SET utf8 ;
USE `Farmacia` ;

-- -----------------------------------------------------
-- Table `Farmacia`.`Empleado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Empleado` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Empleado` (
  `idEmpleado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(50) NOT NULL,
  `Telefono` VARCHAR(20) NOT NULL,
  `Usuario` VARCHAR(45) NOT NULL,
  `Password` BLOB NOT NULL,
  `Jerarquia` VARCHAR(70) NOT NULL,
  PRIMARY KEY (`idEmpleado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Cliente` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Cliente` (
  `idCliente` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(100) NOT NULL,
  `Direccion` VARCHAR(120) NOT NULL,
  `Telefono` VARCHAR(20) NOT NULL,
  `Edad` VARCHAR(5) NOT NULL,
  `Puntos` INT NOT NULL,
  `Rfc` VARCHAR(45) NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Proveedor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Proveedor` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Proveedor` (
  `idProveedor` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(50) NOT NULL,
  `Telefono` VARCHAR(20) NOT NULL,
  `Correo` VARCHAR(45) NULL,
  `Direccion` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`idProveedor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Categorias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Categorias` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Categorias` (
  `idCategoria` INT NOT NULL AUTO_INCREMENT,
  `Descripcion` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCategoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Presentacion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Presentacion` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Presentacion` (
  `idPresentacion` INT NOT NULL AUTO_INCREMENT,
  `Descripcion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPresentacion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Producto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Producto` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Producto` (
  `idProducto` INT NOT NULL AUTO_INCREMENT,
  `Cod_Barras` VARCHAR(50) NOT NULL,
  `Descripcion` VARCHAR(120) NOT NULL,
  `Presentacion` INT NOT NULL,
  `Proveedor` INT NOT NULL,
  `Precio_Compra` FLOAT NOT NULL,
  `Precio_Venta` FLOAT NOT NULL,
  `Cantidad` INT NOT NULL,
  `Receta` TINYINT NULL,
  `idCategoria` INT NOT NULL,
  PRIMARY KEY (`idProducto`),
  INDEX `Proveedor_idx` (`Proveedor` ASC) VISIBLE,
  INDEX `idCategoria_idx` (`idCategoria` ASC) VISIBLE,
  INDEX `idPresentacion_idx` (`Presentacion` ASC) VISIBLE,
  CONSTRAINT `Proveedor`
    FOREIGN KEY (`Proveedor`)
    REFERENCES `Farmacia`.`Proveedor` (`idProveedor`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idCategoria`
    FOREIGN KEY (`idCategoria`)
    REFERENCES `Farmacia`.`Categorias` (`idCategoria`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idPresentacion`
    FOREIGN KEY (`Presentacion`)
    REFERENCES `Farmacia`.`Presentacion` (`idPresentacion`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Venta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Venta` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Venta` (
  `idVenta` INT NOT NULL AUTO_INCREMENT,
  `Fecha` DATE NOT NULL,
  `Total` FLOAT NOT NULL,
  `idEmpleado` INT NOT NULL,
  `idCliente` INT NULL,
  PRIMARY KEY (`idVenta`),
  INDEX `idEmpleado_idx` (`idEmpleado` ASC) VISIBLE,
  INDEX `idCliente_idx` (`idCliente` ASC) VISIBLE,
  CONSTRAINT `idEmpleado`
    FOREIGN KEY (`idEmpleado`)
    REFERENCES `Farmacia`.`Empleado` (`idEmpleado`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `idCliente`
    FOREIGN KEY (`idCliente`)
    REFERENCES `Farmacia`.`Cliente` (`idCliente`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Detalle_Venta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Detalle_Venta` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Detalle_Venta` (
  `idVenta` INT NOT NULL,
  `idProducto` INT NOT NULL,
  `Precio_Compra` DOUBLE NOT NULL,
  `Precio_Venta` DOUBLE NOT NULL,
  `Total` DOUBLE NOT NULL,
  `Cantidad` INT NOT NULL,
  `Promocion` INT NULL,
  INDEX `idVenta_idx` (`idVenta` ASC) VISIBLE,
  INDEX `idProducto_idx` (`idProducto` ASC) VISIBLE,
  CONSTRAINT `idVenta`
    FOREIGN KEY (`idVenta`)
    REFERENCES `Farmacia`.`Venta` (`idVenta`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idProducto`
    FOREIGN KEY (`idProducto`)
    REFERENCES `Farmacia`.`Producto` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Compra`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Compra` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Compra` (
  `idProveedor` INT NOT NULL,
  `idCompra` INT NOT NULL AUTO_INCREMENT,
  `Total_Compra` FLOAT NOT NULL,
  `Fecha` DATE NOT NULL,
  INDEX `idProveedor_idx` (`idProveedor` ASC) VISIBLE,
  PRIMARY KEY (`idCompra`),
  CONSTRAINT `idProveedor`
    FOREIGN KEY (`idProveedor`)
    REFERENCES `Farmacia`.`Proveedor` (`idProveedor`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Promocion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Promocion` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Promocion` (
  `idPromocion` INT NOT NULL AUTO_INCREMENT,
  `Descripcion` VARCHAR(120) NOT NULL,
  `Activa` TINYINT NOT NULL,
  PRIMARY KEY (`idPromocion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Detalle_Promocion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Detalle_Promocion` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Detalle_Promocion` (
  `idPromocion` INT NOT NULL,
  `idProducto` INT NOT NULL,
  INDEX `idPromocion_idx` (`idPromocion` ASC) VISIBLE,
  INDEX `idProducto_idx` (`idProducto` ASC) VISIBLE,
  CONSTRAINT `idPromocion`
    FOREIGN KEY (`idPromocion`)
    REFERENCES `Farmacia`.`Promocion` (`idPromocion`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Producto`
    FOREIGN KEY (`idProducto`)
    REFERENCES `Farmacia`.`Producto` (`idProducto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Farmacia`.`Detalle_Compra`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Detalle_Compra` ;

CREATE TABLE IF NOT EXISTS `Farmacia`.`Detalle_Compra` (
  `idProducto` INT NOT NULL,
  `Cantidad` INT NOT NULL,
  `idCompra` INT NOT NULL,
  `Precio_Compra` FLOAT NOT NULL,
  INDEX `idCompra_idx` (`idCompra` ASC) VISIBLE,
  CONSTRAINT `idCompra`
    FOREIGN KEY (`idCompra`)
    REFERENCES `Farmacia`.`Compra` (`idCompra`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `Farmacia` ;

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Trabajadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Trabajadores` (`Nombre` INT, `Telefono` INT, `Jerarquia` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Reporte de ventas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Reporte de ventas` (`'Código del producto'` INT, `'Descripción'` INT, `'Costo'` INT, `'Precio'` INT, `'En inventario'` INT, `'Cantidad Vendida'` INT, `'Venta_Total'` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Reporte de proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Reporte de proveedores` (`'Código del producto'` INT, `'Descripción'` INT, `'Precio de compra'` INT, `'Precio de venta'` INT, `'En inventario'` INT, `'Proveedor'` INT, `'Teléfono'` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Reporte Compras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Reporte Compras` (`idProveedor` INT, `Nombre` INT, `idCompra` INT, `'Total'` INT, `Fecha` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Inventario` (`'Código del producto'` INT, `'Descripción'` INT, `'Precio de compra'` INT, `'Precio de venta'` INT, `'En inventario'` INT, `'Proveedor'` INT, `'Teléfono'` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Reporte Promociones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Reporte Promociones` (`'Detalles'` INT, `Activa` INT, `Descripcion` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Reporte Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Reporte Clientes` (`idCliente` INT, `Nombre` INT, `Direccion` INT, `Telefono` INT, `Edad` INT, `Rfc` INT, `Puntos` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`view1` (`id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Farmacia`.`Reporte de utilidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Farmacia`.`Reporte de utilidades` (`idVenta` INT, `idProducto` INT, `Descripcion` INT, `'Precio de compra'` INT, `'Precio de venta'` INT, `Cantidad` INT, `'Total'` INT, `'Promocion'` INT, `'Utilidad'` INT);

-- -----------------------------------------------------
-- View `Farmacia`.`Trabajadores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Trabajadores`;
DROP VIEW IF EXISTS `Farmacia`.`Trabajadores` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Trabajadores` AS
select Nombre, Telefono, Jerarquia
from Empleado;

-- -----------------------------------------------------
-- View `Farmacia`.`Reporte de ventas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Reporte de ventas`;
DROP VIEW IF EXISTS `Farmacia`.`Reporte de ventas` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Reporte de ventas` AS
select Detalle_Venta.idProducto As 'Código del producto',
	Producto.Descripcion As 'Descripción', 
	concat('$', Producto.Precio_Compra) As 'Costo', 
	concat('$', Producto.Precio_Venta) As 'Precio', 
	Producto.Cantidad 'En inventario', 
	count(Detalle_Venta.idProducto) As 'Cantidad Vendida',
    sum(Detalle_Venta.Precio_Venta) As 'Venta_Total'
	from Detalle_Venta inner join Producto 
	on Detalle_Venta.idProducto = Producto.idProducto
	group by Detalle_Venta.idProducto
	order by 'Venta_Total' desc;

-- -----------------------------------------------------
-- View `Farmacia`.`Reporte de proveedores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Reporte de proveedores`;
DROP VIEW IF EXISTS `Farmacia`.`Reporte de proveedores` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Reporte de proveedores` AS
select Producto.idProducto As 'Código del producto', 
    Producto.Descripcion As 'Descripción', 
    concat('$', Producto.Precio_Compra) As 'Precio de compra',
    concat('$', Producto.Precio_Venta) As 'Precio de venta',
    Producto.Cantidad As 'En inventario',
    Proveedor.Nombre As 'Proveedor',
    Proveedor.Telefono As 'Teléfono'
    from Producto inner join Proveedor
    on Producto.Proveedor = Proveedor.idProveedor
    order by Proveedor.Nombre desc;

-- -----------------------------------------------------
-- View `Farmacia`.`Reporte Compras`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Reporte Compras`;
DROP VIEW IF EXISTS `Farmacia`.`Reporte Compras` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Reporte Compras` AS
select Compra.idProveedor, Proveedor.Nombre, Compra.idCompra, concat('$', Compra.Total_Compra) As 'Total', Compra.Fecha
from Compra inner join Proveedor on Compra.idProveedor = Proveedor.idProveedor;

-- -----------------------------------------------------
-- View `Farmacia`.`Inventario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Inventario`;
DROP VIEW IF EXISTS `Farmacia`.`Inventario` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Inventario` AS
select Producto.idProducto As 'Código del producto', 
    Producto.Descripcion As 'Descripción', 
    concat('$', Producto.Precio_Compra) As 'Precio de compra',
    concat('$', Producto.Precio_Venta) As 'Precio de venta',
    Producto.Cantidad As 'En inventario',
    Proveedor.Nombre As 'Proveedor',
    Proveedor.Telefono As 'Teléfono'
    from Producto inner join Proveedor
    on Producto.Proveedor = Proveedor.idProveedor
    order by Producto.Cantidad asc;

-- -----------------------------------------------------
-- View `Farmacia`.`Reporte Promociones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Reporte Promociones`;
DROP VIEW IF EXISTS `Farmacia`.`Reporte Promociones` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Reporte Promociones` AS
select Promocion.Descripcion As 'Detalles', Promocion.Activa,
Producto.Descripcion
from Detalle_Promocion 
inner join Promocion on Promocion.idPromocion = Detalle_Promocion.idPromocion
inner join Producto on Detalle_Promocion.idProducto = Producto.idProducto
order by Activa desc;

-- -----------------------------------------------------
-- View `Farmacia`.`Reporte Clientes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Reporte Clientes`;
DROP VIEW IF EXISTS `Farmacia`.`Reporte Clientes` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Reporte Clientes` AS
select idCliente, Nombre, Direccion, Telefono, Edad, Rfc, Puntos 
    from Cliente
    order by Puntos desc;

-- -----------------------------------------------------
-- View `Farmacia`.`view1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`view1`;
DROP VIEW IF EXISTS `Farmacia`.`view1` ;
USE `Farmacia`;


-- -----------------------------------------------------
-- View `Farmacia`.`Reporte de utilidades`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Farmacia`.`Reporte de utilidades`;
DROP VIEW IF EXISTS `Farmacia`.`Reporte de utilidades` ;
USE `Farmacia`;
CREATE  OR REPLACE VIEW `Reporte de utilidades` AS
select Detalle_Venta.idVenta, Detalle_Venta.idProducto, Producto.Descripcion, 
concat('$', Detalle_Venta.Precio_Compra) As 'Precio de compra',
concat('$', (select Precio_Venta from Producto where Producto.idProducto = Detalle_Venta.idProducto)) As 'Precio de venta', 
Detalle_Venta.Cantidad,
concat('$', Detalle_Venta.Cantidad * Detalle_Venta.Total) As 'Total',
(select Descripcion from Promocion where idPromocion = Detalle_Venta.Promocion) As 'Promocion',
concat('$', ((Detalle_Venta.Total * Detalle_Venta.Cantidad) - (Detalle_Venta.Precio_Compra * Detalle_Venta.Cantidad))) As 'Utilidad'
from Detalle_Venta inner join Producto on Detalle_Venta.idProducto = Producto.idProducto;
USE `Farmacia`;

DELIMITER $$

USE `Farmacia`$$
DROP TRIGGER IF EXISTS `Farmacia`.`Actualizar Puntos` $$
USE `Farmacia`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Actualizar Puntos` AFTER INSERT ON `Venta` FOR EACH ROW
BEGIN
	update Cliente set Cliente.Puntos = Puntos + ((3 * new.Total)/100)
	where new.idCliente = Cliente.idCliente;
END$$


USE `Farmacia`$$
DROP TRIGGER IF EXISTS `Farmacia`.`Disminuir Inventario` $$
USE `Farmacia`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Disminuir Inventario` AFTER INSERT ON `Detalle_Venta` FOR EACH ROW
BEGIN
	UPDATE producto SET cantidad = cantidad - new.Cantidad 
    WHERE idProducto = new.idProducto;
END$$


USE `Farmacia`$$
DROP TRIGGER IF EXISTS `Farmacia`.`Aumentar Inventario` $$
USE `Farmacia`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Aumentar Inventario` AFTER INSERT ON `Detalle_Compra` FOR EACH ROW
BEGIN
	UPDATE producto SET cantidad = cantidad + new.cantidad 
    WHERE idProducto = new.idProducto;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
