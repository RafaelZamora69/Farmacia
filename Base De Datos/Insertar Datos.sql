use Farmacia;
show tables;
/* Insertar en Farmacia.Empleado */
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Gerardo Galván Chavez', '2299025028', 'GerardoGalvan', sha1('Gerardo123'), 'Jefe');
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Rafael Antonio Gonzalez Zamora', '2295242553', 'RafaelZamora', sha1('Rafael123'), 'Encargado de turno');
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Karla Lizbeth Valdes Morales', '2295061936', 'KarVm', sha1('Karla123'), 'Vendedor');
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Alejandro Vega Hernandez', '5540907400', 'AlexVega', sha1('Vega123'), 'Vendedor');
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Lorenzo Hernandez Reyes', '5540907400', 'Lorenzo', sha1('Lorenzo123'), 'Inventario');
select * from Empleado; 	
/* Insertar en Farmacia.Proveedores */
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Pepsico', '2291127509', 'evolucion_adm@hotmail.com', 'Carretera Veracruz Medellin KM 2.3');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Laboratorios PISA', '229921 8654', '', 'Ejército Mexicano, Adalberto Tejada, Veracruz');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('AMSA Laboratorios', '5559982100', '', 'Flores 56, Amp Candelaria, 04380 Ciudad de México');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Novag Infancia', '5556664120', '', 'Calz. de Tlalpan 3417, Sta. Úrsula Coapa, 04650 Ciudad de México');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("LABORATORIOS COLUMBIA, S.A. DE C.V.","525557265584","","Calzada del Hueso No. 160 Col. Ejidos de Santa Ursula Coapa 04850 México, D.F.");
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("BAYER DE MÉXICO, S.A. DE C.V.","8002293727","","Blvd. Miguel de Cervantes Saavedra No.259, Granada, 11520 Ciudad de México, CDMX");
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("Janssen Cilag S. A. de C. V.","2222294400","","Carretera Federal México-Puebla, km 81.5 San Mateo capultitlán Codigo postal: 74060, Huejotzingo, Puebla, México");
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("Solar Medical, S. A. de C. V.","2272760858","","Calle Abasolo 309, cuarto barrio, Huejotzingo, Puebla, código postal:74 160");
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("Laboratorios Liomont, S. A. de C. V","5558141200","","Avenida la esperanza, México, c. p: 74 280");
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("Merck Sharp y Dohme Corp.","5557284000","","Av. 16 de Septiembre no. 301, Col. Xaltocan, Ciudad de México C. P. 16090");
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("Productos Farmacéuticos CHINOIN","4659581804","","Km 4.2 carretera pabellón, Hidalgo, código postal: 20420");
select * from Proveedor;

/* Insertar en Farmacia.Promocion y Detalle_Promocion */
insert into promocion (Descripcion, Activa) values ("Sin promoción", 1); 
insert into Promocion (Descripcion, Activa) values ("2X1 en Pepsi 355ML",1);
insert into Promocion (Descripcion, Activa) values ("Lunes 10% desc. en Analgesicos",1);
insert into Promocion (Descripcion, Activa) values ("3x2 En Agua Natural Epura 600ML",1);
insert into Promocion (Descripcion, Activa) values ("Viernes de ahorcar rucas, anticoncepticos al 20% de desc.",0);
select * from Promocion;

/* Insertar en Farmacia.Productos */
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501031311309', 'Pepsi 355ML', 'Lata', 1, 8.0, 10.0, 0, 0, 'Refrescos / Bebidas');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501031311606', 'Pepsi 2L', 'Botella', 1, 12.0, 15.0, 0, 0, 'Refrescos / Bebidas');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('036731501004', 'Gatorade Naranja 500ML', 'Botella', 1, 10.0, 13.0, 0, 0, 'Refrescos / Bebidas');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501086801121', 'Agua Natural Epura 600ML', 'Botella', 1, 6.00, 8.50, 0, 0, 'Refrescos / Bebidas');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501086801046', 'Agua Natural Epura 1LT', 'Botella', 1, 8.00, 10.00, 0, 0, 'Refrescos / Bebidas');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501031360024', 'Manzanita Sol 600ML', 'Botella', 1, 9.00, 11.50, 0, 0, 'Refrescos / Bebidas');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501349029880', 'Tramadol, Paracetamol 325 mg.', 'Tabletas', 2, 90.0, 125.0, 0, 0, 'Analgelsicos'); 
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7502223703735', 'Ketorolaco 30 Mg.', 'Tabletas', 3, 25.0, 31.0, 0, 1, 'Analgelsicos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501075722253', 'Nineka neomicina, Caolín y Pectina', 'Tabletas', 4, 40.0, 60.0, 0, 0, 'Analgelsicos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501086313204', 'OPTIUM 125/50/1MG', 'Tabletas', 5, 280.00, 402.00, 0, 1, 'Analgelsicos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501086300891', 'Ad-Col Oral Ad', 'Tabletas', 5,78.00,112.00, 0, 0, 'Antialérgicos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501050633666', 'Clarityne Solución 30ML', 'Jarabe', 6,95.00,133.00, 0, 0, 'Antialérgicos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501008491966', 'Aspirina 40 Tabletas', 'Tabletas', 6,15.00,24.00, 0, 0, 'Analgelsicos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7703331157506', 'Yasmin Oral 21 Tabletas', 'Tabletas', 6,200.00,291.00, 0, 0, 'Anticonceptivos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501318601314', 'Glucobay 100MG Oral', 'Tabletas', 6,400.00,496.00, 0, 0, 'Diabeticos');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501318620223', 'Levitra 100MG Oral 1 Tab', 'Tabletas', 6,180.00,229.00, 0, 0, 'disfunción eréctil');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501007532349', 'Dramamine 250mL Oral', 'Jarabe', 7,90.00,124.00, 0, 0,'Infantil');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('637420602004', 'Ferlor A. F. cont. 30, adulto.', 'Tabletas', 8,37.00,48.00, 0, 0,'Infantil');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501035274901', 'Ectaprim, 120mL, pediátrico', 'Jarabe', 9,103.00,138.00, 0, 1,'Suplementos alimenticios');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('75035357700', 'Cedax ceftibuteno, 36 mg/mL', 'Jarabe',10,200.00,399.00, 0, 0, 'Suplementos alimenticios');
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) 
    values ('7501342800158', 'Antiflu-Des 30mL pediátrico', 'Jarabe',10,53.00,67.00, 0, 0, 'Suplementos alimenticios');
select * from Producto;

/* Insertar en Farmacia.Detalle_Promocion */
start transaction;
insert into Detalle_Promocion values (2,1);
rollback;
select * from Detalle_Promocion where idPromocion = 2;
insert into Detalle_Promocion values (3,7);
insert into Detalle_Promocion values (3,8);
insert into Detalle_Promocion values (3,9);
insert into Detalle_Promocion values (3,10);
insert into Detalle_Promocion values (3,13);
insert into Detalle_Promocion values (4,4);
insert into Detalle_Promocion values (5,14);

/* Insertar en Farmacia.Cliente */
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Sin cliente', '', '', '', 0,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Abelardo Hernandez Mota', 'Su casa', '2293684747', '20', 0,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Jorge Antonio Pedroza Rendón', 'Su casa', '229 137 0546', '20', 0,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Miguel Angel Nava Diaz', 'Las vegas 2, rio cotaxtla 657B', '2293317317', '20', 0,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Julia Yahaira Román Escobar', 'Rio medio, romain y rio panuco 22', '2293979247', '20', 0,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Herlinda Robert Chávez', 'Alcatraz 124 Esq. Clavel Fracc. Jardines del Virginia Boca del Río Ver C.P. 94294 ', '2291457888', '67', 0,"ROCH511007940");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('María del Rosario Chávez', 'Río Usumacinta 480B Las Vegas 2 Boca del Río Ver C.P. 94297', '2291729306', '57', 0,"");
select * from Cliente;
/* Insertar en Farmacia.Venta*/
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-23', 725.0, 1, 2);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-23', 124.0, 1, 5);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-23', 462.0, 1, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-24', 35.5, 2, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-24', 255.0, 2, 2);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-26', 41.5, 2, 1);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-26', 415.0, 1, 7);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-29', 415.0, 1, 7);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-8-29', 124.0, 1, 5);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-1', 124.0, 1, 5);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-2', 462.0, 1, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-3', 35.5, 2, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-3', 124.0, 1, 5);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-3', 462.0, 1, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-5', 35.5, 2, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-10', 400.0, 1, 5);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-10', 300.0, 1, 4);
insert into Venta (Fecha, Total, idEmpleado, idCliente) values ('2019-9-10', 355.5, 2, 4);
select * from Venta;
 
/* Insertar en Farmacia.Detalle_Venta*/
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (1, 15, 400, 496, 496, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (1, 16, 180, 229, 229, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (2, 17, 90, 124, 124, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (3, 9, 40, 60, 60, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (3, 10, 280, 402, 402, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (4, 1, 8, 10, 20, 2, 4);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (4, 4, 6, 8.50, 17, 3, 5);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (5, 9, 40, 60, 60, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (5, 8, 25, 31, 31, 2, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (5, 12, 95, 133, 133, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (6, 1, 8, 10, 10, 3, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (6, 6, 9, 11.5, 11.5, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (7, 16, 180, 229, 229, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (7, 13, 15, 24, 24, 2, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (7, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (8, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (9, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (10, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (11, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (12, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (13, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (14, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (15, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (16, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (17, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (18, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (19, 19, 103, 108, 108, 1, 1);
insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (20, 19, 103, 108, 108, 1, 1);

select * from Detalle_Venta;

insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(1,1,1150.00,"2019-09-30");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(1,50,1,8);
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(4,50,1,9);
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(6,50,1,6);
insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(3,2,1250.00,"2019-09-30");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values (8,50,2,25);
insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(4,3,2000.00,"2019-08-15");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values (9,50,3,40);
insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(5,4,14000.00,"2019-07-24");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values (10,50,4,280);
insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(6,5,24500.00,"2019-09-5");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(15,25,5,400);
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(16,50,5,180);
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(13,50,5,15);
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(12,50,5,95);
insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(7,6,4500.00,"2019-09-16");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(17,50,6,90);
insert into Compra(idProveedor, idCompra, Total_Compra, Fecha) values(9,7,5150.00,"2019-05-28");
insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values(19,50,7,103);
select * from Compra;
select * from Detalle_Compra;