use Farmacia;
show tables;
/* Insertar en Farmacia.Empleado */
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Gerardo Galván Chavez', '2299025028', 'GerardoGalvan', sha1('Gerardo123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Rafael Antonio Gonzalez Zamora', '2295242553', 'RafaelZamora', sha1('Rafael123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Karla Lizbeth Valdes Morales', '2295061936', 'KarVm', sha1('Karla123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Alejandro Vega Hernandez', '5540907400', 'AlexVega', sha1('Vega123'), 1);
select * from Empleado; delete from Empleado;
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
/* Insertar en Farmacia.Categorias */
insert into Categorias (idCategoria, Descripcion) values (1, 'Refrescos / Bebidas');
insert into Categorias (idCategoria, Descripcion) values (2, 'Analgelsicos');
insert into Categorias (idCategoria, Descripcion) values (3, 'Laxante');
insert into Categorias (idCategoria, Descripcion) values (4, 'Antialérgicos');
insert into Categorias (idCategoria, Descripcion) values (5, 'Anticonceptivos');
insert into Categorias (idCategoria, Descripcion) values (6, 'Diabeticos');
insert into Categorias (idCategoria, Descripcion) values (7, 'disfunción eréctil');
insert into Categorias (idCategoria, Descripcion) values (8, 'Infantil');
insert into Categorias (idCategoria, Descripcion) values (9, 'Suplementos alimenticios');
select * from Categorias;

/* Insertar en Farmacia.Presentación */ 
insert into Presentacion (idPresentacion, Descripcion) values (1, 'Lata');
insert into Presentacion (idPresentacion, Descripcion) values (2, 'Botella');
insert into Presentacion (idPresentacion, Descripcion) values (3, 'Tabletas');
insert into Presentacion (idPresentacion, Descripcion) values (4, 'Capsulas');
insert into Presentacion (idPresentacion, Descripcion) values (5, 'Jarabe');
select * from Presentacion;

/* Insertar en Farmacia.Productos */
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria) 
    values ('7501031311309', 'Pepsi 355ML', 1, 1, 8.0, 10.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501031311606', 'Pepsi 2L', 2, 1, 12.0, 15.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('036731501004', 'Gatorade Naranja 500ML', 2, 1, 10.0, 13.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086801121', 'Agua Natural Epura 600ML', 2, 1, 6.00, 8.50, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086801046', 'Agua Natural Epura 1LT', 2, 1, 8.00, 10.00, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501031360024', 'Manzanita Sol 600ML', 2, 1, 9.00, 11.50, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501349029880', 'Tramadol, Paracetamol 325 mg.', 3, 2, 90.0, 125.0, 0, 0, 2); 
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7502223703735', 'Ketorolaco 30 Mg.', 3, 3, 25.0, 31.0, 0, 1, 2);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501075722253', 'Nineka neomicina, Caolín y Pectina', 3, 4, 40.0, 60.0, 0, 0, 2);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086313204', 'OPTIUM 125/50/1MG', 3, 5, 280.00, 402.00, 0, 1, 2);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086300891', 'Ad-Col Oral Ad', 3, 5,78.00,112.00, 0, 0, 4);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501050633666', 'Clarityne Solución 30ML', 5, 6,95.00,133.00, 0, 0, 4);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501008491966', 'Aspirina 40 Tabletas', 3, 6,15.00,24.00, 0, 0, 2);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7703331157506', 'Yasmin Oral 21 Tabletas', 5, 6,200.00,291.00, 0, 0, 5);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501318601314', 'Glucobay 100MG Oral', 3, 6,400.00,496.00, 0, 0, 6);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501318620223', 'Levitra 100MG Oral 1 Tab', 3, 6,180.00,229.00, 0, 0, 7);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501007532349', 'Dramamine 250mL Oral', 5, 7,90.00,124.00, 0, 0,8 );
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('637420602004', 'Ferlor A. F. cont. 30, adulto.', 3, 8,37.00,48.00, 0, 0,8 );
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501035274901', 'Ectaprim, 120mL, pediátrico', 5, 9,103.00,138.00, 0, 1,9 );
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('75035357700', 'Cedax ceftibuteno, 36 mg/mL', 5,10,200.00,399.00, 0, 0,10);
insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501342800158', 'Antiflu-Des 30mL pediátrico', 5,11,53.00,67.00, 0, 0,11);
select * from producto;

/* Insertar en Farmacia.Cliente */
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Abelardo Hernandez Mota', 'Su casa', '2293684747', '20', 0,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Jorge Antonio Pedroza Rendón', 'Su casa', '229 137 0546', '20', 10,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Miguel Angel Nava Diaz', 'Las vegas 2, rio cotaxtla 657B', '2293317317', '20', 50,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Julia Yahaira Román Escobar', 'Rio medio, romain y rio panuco 22', '2293979247', '20', 25,"");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Herlinda Robert Chávez', 'Alcatraz 124 Esq. Clavel Fracc. Jardines del Virginia Boca del Río Ver C.P. 94294 ', '2291457888', '67', 33,"ROCH511007940");
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('María del Rosario Chávez', 'Río Usumacinta 480B Las Vegas 2 Boca del Río Ver C.P. 94297', '2291729306', '57', 13,"");
select * from Cliente;

/* Insertar en Farmacia.Venta*/
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 725.0, 1);
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 180.0, 1);
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 91.0, 1);
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 28.5, 2);
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 255.0, 2);
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 41.5, 3);
insert into Venta (Fecha, Total, idEmpleado) values (curdate(), 415.0, 4);
select * from Venta;
 
/* Insertar en Farmacia.Detalle_Venta*/
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (1, 15, 1, 496, 400);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (1, 16, 1, 229, 180);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (2, 17, 4, 124, 90);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (3, 9, 3, 60, 40);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (3, 10, 3, 402, 280);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (4, 1, 3, 10, 8);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (4, 1, 3, 10, 8);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (4, 4, 3, 8.5, 6);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (5, 9, 1, 60, 40);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (5, 8, 1, 31, 25);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (5, 8, 1, 31, 25);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (5, 12, 1, 133, 95);
insert into Detalle_Venta(idVenta, idProducto, Precio_Venta, Precio_Compra) values (6, 1, 10, 8);
insert into Detalle_Venta(idVenta, idProducto, Precio_Venta, Precio_Compra) values (6, 1, 10, 8);
insert into Detalle_Venta(idVenta, idProducto, Precio_Venta, Precio_Compra) values (6, 1, 10, 8);
insert into Detalle_Venta(idVenta, idProducto, Precio_Venta, Precio_Compra) values (6, 6, 11.5, 9);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (7, 16, 6, 229, 180);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (7, 13, 6, 24, 15);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (7, 13, 6, 24, 15);
insert into Detalle_Venta(idVenta, idProducto, idCliente, Precio_Venta, Precio_Compra) values (7, 19, 6, 138, 103);
select * from Detalle_Venta;

delete from Empleado where idEmpleado = 1;

