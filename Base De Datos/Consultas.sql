use farmacia;

/* Consulta para el login */
select Nombre from Empleado where Usuario = ? and Password = sha1(?);

/* Caja registradora */
	/* Información del producto*/
    select Descripcion, Precio_Venta, Receta from Producto where Cod_Barras = ?;
    /* Si no tiene codigo de barras, por Codigo */
    select Descripcion, Precio, Venta, Receta from Producto where idProducto = ?;
    /* Insertar una venta cuando se complete la compra */
    insert into Venta (Fecha, Total) values (curdate(), ?);
		/* Insertar el detalle de la venta cuando se haya insertado la venta */
		insert into Detalle_Venta (idVenta, idProducto, idEmpleado, Cantidad, idCliente) values (?, ?, ?, ?, ?);
	/* Actualizar puntos del cliente cuando complete una compra (si es que está registrado) */
    update Cliente set Puntos = ? where idCliente = ?;

/* Inventario */
	/* Infromación del inventario */
    select Descripcion, Precio_Venta, Cantidad, Categoria from Producto;
    /* Mostrar información para modificar el producto */
    select * from Producto where idProducto = ?;