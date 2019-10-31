start transaction;
insert into Venta (idVenta, Fecha, Total, idEmpleado, idCliente) values (8, '2019-10-30', 725.0, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad) values (8, 15, 400, 496, 496, 1);
select * from Venta where idVenta = 8;
rollback;

start transaction;
insert into Venta (idVenta, Fecha, Total, idEmpleado, idCliente) values (9, '2019-10-30', 725.0, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad) values (8, 15, 400, 496, 496, 1);
select * from Venta where idVenta = 9;
rollback;