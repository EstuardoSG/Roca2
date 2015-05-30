--Laves primarias mayor a 0
alter table Empleados add check(idEmpleado >0);
alter table Cliente add check(idCliente >0);
alter table MarcaMotocicleta add check(idMarcaMotocicleta >0);
alter table Motocicleta add check(idMotocicleta >0);
alter table Checklist add check(idChecklist >0);
alter table RegistroReparacion add check(idReparacion >0);
alter table Servicios add check(idServicio >0);
alter table ServicioRealizado add check(idServicioRealizado >0);
alter table ReparacionPartes add check(idReparacionPartes >0);
alter table Proveedor add check(idProveedor >0);
alter table Contacto add check(idContacto >0);
alter table ProveedorContacto add check(idProveedorContacto >0);
alter table MarcaRefaccion add check(idMarcaRefaccion >0);
alter table Compra add check(folioCompra >0);
alter table Almacen add check(idRefaccionAlmacen >0);
alter table CompraDetalle add check(folio >0);
alter table AjusteAlmacen add check(idAjuste >0);


-- existencia (cantidad) mayor a 0
alter table CompraDetalle add check(cantidad >0);

--precio 1 y precio 2 no aceptan valores negativos
alter table ServicioRealizado add check(precio1 > '0');
alter table ServicioRealizado add check(precio2 > '0');

alter table ReparacionPartes add check(precio1 > '0');
alter table ReparacionPartes add check(precio2 > '0');

alter table CompraDetalle add check(precioUnitario1 > '0');
alter table CompraDetalle add check(precioUnitario2 > '0');

--precio 1 mayor a precio 2
alter table ServicioRealizado add check(precio1 > precio2);

alter table ReparacionPartes add check(precio1 > precio2);

alter table CompraDetalle add check(precioUnitario1 > precioUnitario1);

--campo nombre, apellido paterno, apellido materno min 3
alter table Empleados add check(length(ltrim(rtrim(nombre1))) >3);
alter table Empleados add check(length(ltrim(rtrim(nombre2))) >3);
alter table Empleados add check(length(ltrim(rtrim(apellidoPaterno))) >3);
alter table Empleados add check(length(ltrim(rtrim(apellidoMaterno))) >3);

alter table Cliente add check(length(ltrim(rtrim(nombre1))) >3);
alter table Cliente add check(length(ltrim(rtrim(nombre2))) >3);
alter table Cliente add check(length(ltrim(rtrim(apellidoPaterno))) >3);
alter table Cliente add check(length(ltrim(rtrim(apellidoMaterno))) >3);

alter table Contacto add check(length(ltrim(rtrim(nombre))) >3);
alter table Contacto add check(length(ltrim(rtrim(apellidoPaterno))) >3);
alter table Contacto add check(length(ltrim(rtrim(apellidoMaterno))) >3);

--campo correo electronico al menos un @
alter table Empleados add check(correo ~* '^[a-z]+@[a-z]+[.][a-z]+$');

alter table Contacto add check(correo ~* '^[a-z]+@[a-z]+[.][a-z]+$');

--campo estado solo acepta valores reales
alter table Empleados add check((estado = 'Aguascalientes') or (estado = 'Baja California')
or (estado = 'Baja California Sur') or (estado = 'Campeche') or (estado = 'Chiapas')
or (estado = 'Chihuahua') or (estado = 'Coahuila') or (estado = 'Colima')
or (estado = 'Distrito Federal') or (estado = 'Durango') or (estado = 'Estado de México')
or (estado = 'Guanajuato') or (estado = 'Guerrero') or (estado = 'Hidalgo')
or (estado = 'Jalisco') or (estado = 'Michoacán') or (estado = 'Morelos')
or (estado = 'Nayarit') or (estado = 'Nuevo León') or (estado = 'Oaxaca')
or (estado = 'Puebla') or (estado = 'Querétaro') or (estado = 'Quintana Roo')
or (estado = 'San Luis Potosí') or (estado = 'Sinaloa') or (estado = 'Sonora')
or (estado = 'Tabasco') or (estado = 'Tamaulipas') or (estado = 'Tlaxcala')
or (estado = 'Veracruz') or (estado = 'Yucatán') or (estado = 'Zacatecas'));

alter table Cliente add check((estado = 'Aguascalientes') or (estado = 'Baja California')
or (estado = 'Baja California Sur') or (estado = 'Campeche') or (estado = 'Chiapas')
or (estado = 'Chihuahua') or (estado = 'Coahuila') or (estado = 'Colima')
or (estado = 'Distrito Federal') or (estado = 'Durango') or (estado = 'Estado de México')
or (estado = 'Guanajuato') or (estado = 'Guerrero') or (estado = 'Hidalgo')
or (estado = 'Jalisco') or (estado = 'Michoacán') or (estado = 'Morelos')
or (estado = 'Nayarit') or (estado = 'Nuevo León') or (estado = 'Oaxaca')
or (estado = 'Puebla') or (estado = 'Querétaro') or (estado = 'Quintana Roo')
or (estado = 'San Luis Potosí') or (estado = 'Sinaloa') or (estado = 'Sonora')
or (estado = 'Tabasco') or (estado = 'Tamaulipas') or (estado = 'Tlaxcala')
or (estado = 'Veracruz') or (estado = 'Yucatán') or (estado = 'Zacatecas'));


--campo localidad min 5 caracteres
alter table Empleados add check(length(ltrim(rtrim(localidad))) >5);

alter table Cliente add check(length(ltrim(rtrim(localidad))) >5);

alter table Proveedor add check(length(ltrim(rtrim(localidadEmpresa))) >5);

--valores unique
alter table Empleados add unique(telefono1);
alter table Empleados add unique(telefono2);
alter table Empleados add unique(celular1);
alter table Empleados add unique(celular2);
alter table Empleados add unique(correo);
alter table Empleados add unique(usuario);
alter table Empleados add unique(contrasenia);

alter table cliente add unique(telefono1);
alter table cliente add unique(telefono2);
alter table cliente add unique(celular1);
alter table cliente add unique(celular2);

alter table MarcaMotocicleta add unique(nombre);

alter table Servicios add unique(nombreServicio);

alter table Proveedor add unique(nombreEmpresa);
alter table Proveedor add unique(telefonoEmpresa);
alter table Proveedor add unique(celularEmpresa);

alter table Contacto add unique(celular);
alter table Contacto add unique(correo);

alter table MarcaRefaccion add unique(nombreMarca);

--sin espacio en blanco.
alter table Empleados add check(length(ltrim(rtrim(telefono1)))>3);
alter table Empleados add check(length(ltrim(rtrim(telefono2)))>3);
alter table Empleados add check(length(ltrim(rtrim(celular1)))>3);
alter table Empleados add check(length(ltrim(rtrim(celular2)))>3);
alter table Empleados add check(length(ltrim(rtrim(domicilio)))>3);
alter table Empleados add check(length(ltrim(rtrim(numeroInterior)))>1);
alter table Empleados add check(length(ltrim(rtrim(numeroExterior)))>1);
alter table Empleados add check(length(ltrim(rtrim(calle)))>3);
alter table Empleados add check(length(ltrim(rtrim(localidad)))>3);
alter table Empleados add check(length(ltrim(rtrim(ciudad)))>3);
alter table Empleados add check(length(ltrim(rtrim(estado)))>3);
alter table Empleados add check(length(ltrim(rtrim(codigoPostal)))>3);
alter table Empleados add check(length(ltrim(rtrim(correo)))>3);
alter table Empleados add check(length(ltrim(rtrim(usuario)))>3);
alter table Empleados add check(length(ltrim(rtrim(contrasenia)))>3);
alter table Empleados add check(length(ltrim(rtrim(privilegio)))>3);

alter table Cliente add check(length(ltrim(rtrim(telefono1)))>3);
alter table Cliente add check(length(ltrim(rtrim(telefono2)))>3);
alter table Cliente add check(length(ltrim(rtrim(celular1)))>3);
alter table Cliente add check(length(ltrim(rtrim(celular2)))>3);
alter table Cliente add check(length(ltrim(rtrim(domicilio)))>3);
alter table Cliente add check(length(ltrim(rtrim(numeroInterior)))>1);
alter table Cliente add check(length(ltrim(rtrim(numeroExterior)))>1);
alter table Cliente add check(length(ltrim(rtrim(calle)))>3);
alter table Cliente add check(length(ltrim(rtrim(localidad)))>3);
alter table Cliente add check(length(ltrim(rtrim(ciudad)))>3);
alter table Cliente add check(length(ltrim(rtrim(estado)))>3);
alter table Cliente add check(length(ltrim(rtrim(codigoPostal)))>3);

alter table MarcaMotocicleta add check(length(ltrim(rtrim(nombre)))>3);

alter table Motocicleta add check(length(ltrim(rtrim(modelo)))>3);
alter table Motocicleta add check(length(ltrim(rtrim(motor)))>3);
alter table Motocicleta add check(length(ltrim(rtrim(color)))>3);

alter table Checklist add check(length(ltrim(rtrim(gasolina)))>3);
alter table Checklist add check(length(ltrim(rtrim(luces)))>3);
alter table Checklist add check(length(ltrim(rtrim(espejos)))>3);
alter table Checklist add check(length(ltrim(rtrim(llantas)))>3);
alter table Checklist add check(length(ltrim(rtrim(fallas)))>3);
alter table Checklist add check(length(ltrim(rtrim(diagnostico)))>3);

alter table Servicios add check(length(ltrim(rtrim(nombreServicio)))>3);

alter table ServicioRealizado add check(length(ltrim(rtrim(cantidad)))>0);

alter table ReparacionPartes add check(length(ltrim(rtrim(cantidad)))>0);

alter table Proveedor add check(length(ltrim(rtrim(nombreEmpresa)))>2);
alter table Proveedor add check(length(ltrim(rtrim(direccionEmpresa)))>2);
alter table Proveedor add check(length(ltrim(rtrim(ciudadEmpresa)))>2);
alter table Proveedor add check(length(ltrim(rtrim(localidadEmpresa)))>2);
alter table Proveedor add check(length(ltrim(rtrim(telefonoEmpresa)))>2);
alter table Proveedor add check(length(ltrim(rtrim(celularEmpresa)))>2);

alter table Contacto add check(length(ltrim(rtrim(nombre)))>2);
alter table Contacto add check(length(ltrim(rtrim(apellidoMaterno)))>2);
alter table Contacto add check(length(ltrim(rtrim(apellidoPaterno)))>2);
alter table Contacto add check(length(ltrim(rtrim(celular)))>2);
alter table Contacto add check(length(ltrim(rtrim(correo)))>2);

alter table MarcaRefaccion add check(length(ltrim(rtrim(nombreMarca)))>0);

alter table Almacen add check(length(ltrim(rtrim(nombre)))>0);
alter table Almacen add check(length(ltrim(rtrim(modelo)))>0);