use farmacia;
create user 'GerardoGalvan'@'%' identified by 'Gerardo123';
create user 'RafaelZamora' @'%' identified by 'Rafael123';
create user 'KarVm' @'%' identified by 'Karla123';
create user 'AlexVega' @'%' identified by 'Vega123';
create user 'LorenzoHR' @'%' identified by 'Lorenzo123';

/*Jefe*/
grant all privileges on  Farmacia.* to 'GerardoGalvan';

/*Encargado de turno*/
Grant select,insert,update,delete on farmacia.promocion to 'RafaelZamora';
Grant select,insert,update,delete on farmacia.detalle_promocion to 'RafaelZamora';
Grant all privileges on `farmacia`.`reporte de ventas` to 'RafaelZamora';
Grant all privileges on `farmacia`.`reporte de proveedores` to 'RafaelZamora';

/*Inventario*/
grant select,insert,update on `farmacia`.`producto` to 'LorenzoHR';
grant select,insert on `farmacia`.`compra` to 'LorenzoHR';
grant select,insert on `farmacia`.`detalle_compra` to 'LorenzoHR';
grant all privileges on `farmacia`.`reporte compras` to 'LorenzoHR';
grant all privileges on `farmacia`.`inventario` to 'LorenzoHR';

/*Vendedor*/
grant select,insert on `farmacia`.`venta` to 'KarVm';
grant select,insert on `farmacia`.`detalle_venta` to 'KarVm';
grant all privileges on `farmacia`.`reporte promociones` to 'KarVm';
grant all privileges on `farmacia`.`clientes por puntos` to 'KarVm';

/*Vendedor*/
grant select,insert on `farmacia`.`venta` to 'AlexVega';
grant select,insert on `farmacia`.`detalle_venta` to 'AlexVega';
grant all privileges on `farmacia`.`reporte promociones` to 'AlexVega';
grant all privileges on `farmacia`.`clientes por puntos` to 'AlexVega';

flush privileges;
