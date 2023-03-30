insert into codigos_postales (codigo_postal, localidad, provincia) values ('14920', 'Aguilar', 'Cordoba');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('14500', 'Montilla', 'Cordoba');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('14800', 'Lucena', 'Cordoba');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('23567', 'Carmona', 'Sevilla');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('23561', 'Lora del Rio', 'Sevilla');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('32123', 'Antequera', 'Malaga');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('33001', 'Campillos', 'Malaga');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('32668', 'Teba', 'Malaga');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('44321', 'Martos', 'Jaen');
insert into codigos_postales (codigo_postal, localidad, provincia) values ('41222', 'Andujar', 'Jaen');

insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio,codigo_postal_id) values ('Cristobal', 'Rosa', 'Arjona', '34019852R', 'Pasaje San Vicente, 2', 1);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Antonio', 'Garcia', 'Arjona', '35016852R', 'Membrilla, 23', 2);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Miguel Angel', 'Perez', 'Arjona', '34009952R', 'Saladilla, 5', 1);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Jose Antonio', 'Carrasco', 'Arjona', '34519852Y', 'Empedrada, 76', 3);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Alberto', 'Lopez', 'Arjona', '34519152G', 'Carreras, 8', 2);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Rafael', 'Sanchez', 'Arjona', '34045862F', 'Almirante, 6', 1);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Juan', 'Lucena', 'Arjona', '34219855T', 'Gallos, 34', 7);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Pedro', 'Barranco', 'Arjona', '24039852W', 'Estudiantes, 65', 1);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Pablo', 'Varo', 'Arjona', '31239852O', 'Torito, 7', 1);
insert into propietarios (nombre, primer_apellido, segundo_apellido, dni, domicilio, codigo_postal_id) values ('Mario', 'Perez', 'Arjona', '12319852P', 'Llano, 89', 1);

insert into proveedores (nombre, dni_cif, domicilio, codigo_postal_id) values ('GRUPO PEÑA SL', 'B14995673', 'Peña Blanca, 2', 3);
insert into proveedores (nombre, dni_cif, domicilio, codigo_postal_id) values ('TORNILLOS LA LAGUNA SL', 'B14445676', 'Calle Blanca Palima, 3', 5);
insert into proveedores (nombre, dni_cif, domicilio, codigo_postal_id) values ('PLACAS DE PIEZAS, SL', 'B14665765', 'Las Albarizas, 5', 6);
insert into proveedores (nombre, dni_cif, domicilio, codigo_postal_id) values ('MOTORES ORDOÑEZ', 'B14436571', 'Calle Baena, 28', 3);
insert into proveedores (nombre, dni_cif, domicilio, codigo_postal_id) values ('COMPONENTES ELOY', 'B14005600', 'Calle las cimas, 80', 2);

insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('2345DFG', 'RENAULT', 'CLIO', 'Blanco', 1);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('3214FVC', 'FORD', 'FIESTA', 'Azul', 3);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('0090VVC', 'CITROEN', 'BERLINGO', 'Negro', 1);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('8870FZZ', 'SEAT', 'IBIZA', 'Blanco', 5);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('0011GGA', 'PEUGEOT', '407', 'Rojo', 1);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('2200VVM', 'RENAULT', 'LAGUNA', 'Negro', 3);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('7000QQQ', 'PEUGEOT', '508', 'Naranja', 4);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('5111UUU', 'SEAT', 'TOLEDO', 'Azul', 1);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('2110YTY', 'RENAULT', 'CMAX', 'Amarillo', 6);
insert into vehiculos (matricula, marca, modelo, color, propietario_id) values ('0499FCK', 'FORD', 'FOCUS', 'Blanco', 5);

insert into piezas (referencia, nombre_pieza, precio_venta) values ('VA23356', 'Arandela de cobre', 0.50);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('VA40001', 'polea distribucion', 25.30);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('TE23126', 'filtro aceite', 4.20);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('TE11111', 'filtro de combustible', 10.78);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('TE34000', 'filtro de aire', 9.76);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('TE32111', 'filtro de polen', 12.60);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('VA00000', 'volante motor', 119.25);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('R333300', 'radiador', 150.87);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('VA55555', 'culata', 300.45);
insert into piezas (referencia, nombre_pieza, precio_venta) values ('VA44099', 'caja de cambios', 300.34);

insert into factura_proveedor (proveedor_id, fecha_factura, numero_factura, tipo_iva, contabilizada) values (1, '2022/11/30', 'A0002', 21, false);
insert into factura_proveedor (proveedor_id, fecha_factura, numero_factura, tipo_iva, contabilizada) values (2, '2022/11/03', 'BR0002', 21, false);
insert into factura_proveedor (proveedor_id, fecha_factura, numero_factura, tipo_iva,contabilizada) values (1, '2022/12/20', 'A00022', 21, false);
insert into factura_proveedor (proveedor_id, fecha_factura, numero_factura, tipo_iva, contabilizada) values (2, '2022/12/10', 'BR00025', 21, false);
insert into factura_proveedor (proveedor_id, fecha_factura, numero_factura, tipo_iva, contabilizada) values (3, '2022/11/12', 'TR4567', 21, false);

insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (3, '2022/11/19', 'AOO3445', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (2, '2022/11/19', 'DEFFT55', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (3, '2022/11/19', '3EED45', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (1, '2022/11/19', 'RTTF4442', true, 1);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (3, '2022/11/19', '4400OPL', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (2, '2022/11/19', 'TTTY4SS', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (4, '2022/11/19', 'EEEERR', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (3, '2022/11/19', 'RT667FDD', false, null);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (2, '2022/11/19', '1234REE', true, 2);
insert into albaran_proveedor (proveedor_id, fecha_albaran, numero_albaran, facturado, factura_proveedor_id) values (1, '2022/11/19', '5TGBNHY6', false, null);

insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (1, 2, 5.34, 1);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (4, 3, 3.54, 1);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (2, 1, 23.45, 2);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (3, 2, 4.56, 2);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (8, 1, 43.32, 1);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (1, 2, 5.34, 3);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (4, 3, 3.54, 4);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (2, 1, 23.45, 3);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (3, 2, 4.56, 5);
insert into entrada_pieza (pieza_id, cantidad, precio_entrada, albaran_proveedor_id) values (8, 1, 43.32, 5);

insert into mano_de_obra (fecha_nuevo_precio, precio_hora, precio_hora_actual) values ('2022/11/19', 20.5, false);
insert into mano_de_obra (fecha_nuevo_precio, precio_hora, precio_hora_actual) values ('2023/01/01', 32.0, true);

insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (1, 1, '2022/11/19', '2022/11/21', 'REVISAR ARRANQUE', 120000, 4.5, true, true);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (2, null, '2022/11/20', null, 'REVISAR RUIDO MOTOR', 23987, null, false, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (3, null, '2022/11/19', null, 'RUIDO EMBRAGUE', 165776, null, false, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (1, 1, '2022/11/22', '2022/11/23', 'CAMBIO ACEITE', 13455, 1.5, true, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (4, null, '2022/11/23', null, 'REVISAR GASES', 23987, null, false, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (2, null, '2022/11/22', null, 'FUGA DE ACEITE', 32443, null, false, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (5, null, '2022/11/25', null, 'REVISAR TERMOSTATO', 2332, null, false, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (2, null, '2022/11/19', null, 'COMPROBAR ARRANQUE', 123321, null, false, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (1, 2, '2022/11/20', '2022/11/25', 'CAMBIAR FILTRO POLEN', 54345, 2.5, true, false);
insert into orden_reparacion (vehiculo_id, mano_de_obra_id, fecha_apertura, fecha_cierre, descripcion, kilometros, horas, cerrada, facturada) values (2, 2, '2022/11/21', '2022/11/21', 'REVISAR BUJIAS', 22345, 2, true, false);

insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 1, 1);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 1, 2);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (2, 1, 3);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 2, 4);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 2, 1);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 4, 4);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 4, 6);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 6, 1);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (1, 6, 2);
insert into piezas_reparacion (cantidad, orden_reparacion_id, pieza_id) values (2, 6, 4);

insert into factura_cliente (fecha_factura, numero_factura, tipo_iva, orden_reparacion_id, propietario_id) values ('2022/11/19', 1, 21, 1, 1);
