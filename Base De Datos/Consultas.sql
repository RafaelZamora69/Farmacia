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
        /* Eliminar el producto */
        delete from Producto where idProducto = ?;
    /* Insertar un nuevo producto */
    insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria) 
        values (?, ?, ?, ?, ?, ?, ?, ?, ?);
    
/* Extras */
    /* Ingresar nuevo usuario */
    insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values (?, ?, ?, sha1(?), ?);
    /* Mostrar Empleados */
    Select * from Empleado;
        /* Borrar un empleado */
        delete from Empleado where idEmpleado = ?;
    /* Ingresar nuevo proveedor */
    insert into Proveedor (Nombre, Telefono, Correo, Direccion) values (?, ?, ?, ?);
        /* Borrar proveedor */
        delete from Proveedor where idProveedor = ?;
    /* Mostrar proveedores */
    select * from Proveedor;
    /* Reporte de ventas */
    select Venta.idVenta, Venta.Fecha, (select Nombre from Empleado where idEmpleado = Detalle_Venta.idEmpleado) as 'Empleado', Venta.Total, 
    (select Nombre from Cliente where Cliente.idCliente = Detalle_Venta = idCliente)
    from Venta inner join Detalle_Venta on Venta.idVenta = Detalle_Venta.idVenta
    where Venta.fecha between ? and ?;
        /* Mostrar productos del reporte de ventas */
        select (select Descripcion from Producto where Producto.idProducto = Detalle_Venta.idProducto) as 'Descripcion', Detalle_Venta.Cantidad
        from Detalle_Venta
        where idVenta = ?;
    /* Insertar nueva compra de producto */
    insert into Compra (id_proveedor, Total_Compra, Fecha) values (?, ?, ?);
        /* Insertar detalle de la compra */
        insert into Detalle_Compra (idProducto, Cantidad, idCompra, Precio_Compra) values (?, ?, ?, ?);
    /* Ver promociones */
    select Descripcion, Activa
    from promocion;
        /* Mostrar detalles de la promocion */
        select Producto.Descripcion
        from Producto 
        inner join Detalle_Promocion on Detalle_Promocion.idProducto = Producto.idProducto;
        /* Crear una nueva Promoción */
        insert into Promocion (Descripcion, Activa) values (?, ?);
            /* Insertar detalles de la promoción */
            insert into Detalle_Promocion (idPromocion, idProducto) values (?, ?);
        /* Borrar alguna promoción */
        delete from Promocion where idPromocion = ?;
        delete from Detalle_Promocion where idPromocion = ?;
        
/* Consultas */
