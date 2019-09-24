use Farmacia;

/* Insertar en Farmacia.Empleado */
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Gerardo Galván Chavez', '229 902 5028', 'GerardoGalvan', sha1('Gerardo123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Rafael Antonio Gonzalez Zamora', '229 524 2553', 'RafaelZamora'. sha1('Rafael123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('', '', ''. sha1(), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('', '', ''. sha1(), 1);

/* Insertar en Farmacia.Proveedores */
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Pepsico', '229 112 7509', 'evolucion_adm@hotmail.com', 'No se je');

/* Insertar en Farmacia.Categorias */
insert into Categorias (idCategoria, Descripcion) values (1, 'Refrescos / Bebidas');
insert into Categorias (idCategoria, Descripcion) values (2, 'Analgelsicos');
insert into Categorias (idCategoria, Descripcion) values (3, 'Laxante');
insert into Categorias (idCategoria, Descripcion) values (4, 'Antialérgicos');

/* Insertar en Farmacia.Presentación */ 
insert into Presentacion (idPresentacion, Descripcion) values (1, 'Lata');
insert into Presentacion (idPresentacion, Descripcion) values (2, 'Botella');
insert into Presentacion (idPresentacion, Descripcion) values (3, 'Tabletas');

/* Insertar en Farmacia.Productos */
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria) 
    values ('7501031311309', 'Pepsi 355ML', 1, 1, 8.0, 10.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501031311606', 'Pepsi 2L', 2, 1, 12.0, 15.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('036731501004', 'Gatorade Naranja 500ML', 2, 1, 10.0, 13.0, 0, 0, 1);

/* Insertar en Farmacia.Cliente */
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Abelardo Hernandez Mota', 'Su casa', '229 368 4747', '20', 0)
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Jorge Antonio Pedroza Rendón', 'Su casa', '229 137 0546', '20', 0)
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('', 'Su casa', '', '20', 0)
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('', 'Su casa', '', '20', 0)