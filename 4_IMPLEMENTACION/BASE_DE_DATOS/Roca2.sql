Create table Empleados(
idEmpleado integer not null,
nombre1 varchar(15) not null,
nombre2 varchar(15) not null,
apellidoPaterno varchar(15) not null,
apellidoMaterno varchar(15) not null,
telefono1 varchar(14) not null default '000-00-0-00-00',
telefono2 varchar(14) not null default '000-00-0-00-00',
celular1 varchar(13) not null default '000-000-00-00',
celular2 varchar(13) not null default '000-000-00-00',
domicilio varchar(50) not null,
numeroInterior varchar(15) not null,
numeroExterior varchar(15) not null,
calle varchar(25) not null,
localidad varchar(35) not null,
ciudad varchar(25) not null,
estado varchar(25) not null,
codigoPostal varchar(10) not null,
correo varchar(25) not null,
usuario varchar(15) not null,
contrasenia varchar(20) not null,
privilegio varchar(13) not null,
estatus bit not null default '1',
fechaIngreso date not null,
fechaDeSalida varchar(10) not null,
PRIMARY KEY (idEmpleado)
);




Create table Cliente(
idCliente integer not null,
nombre1 varchar(15) not null,
nombre2 varchar(15) not null,
apellidoPaterno varchar(15) not null,
apellidoMaterno varchar(15) not null,
telefono1 varchar(14) not null default '000-00-0-00-00',
telefono2 varchar(14) not null default '000-00-0-00-00',
celular1 varchar(13) not null default '000-000-00-00',
celular2 varchar(13) not null default '000-000-00-00',
domicilio varchar(50) not null,
numeroInterior varchar(15) not null,
numeroExterior varchar(15) not null,
calle varchar(25) not null,
localidad varchar(35) not null,
ciudad varchar(25) not null,
estado varchar(25) not null,
codigoPostal varchar(10) not null,
numeroCliente integer not null,
clienteFrecuente boolean not null,
activo bit not null default '1',
PRIMARY KEY (idCliente)
);



Create table Motocicleta(
idMotocicleta integer not null,
idEmpleado integer not null,
idMarca integer not null,
modelo varchar(15) not null,
motor varchar(15) not null,
color varchar(30) not null,
placa boolean not null,
fechaHoraRegistro timestamp not null,
descripcionMotocicleta text not null,
activo bit default '1',
PRIMARY KEY (idMotocicleta)
);

Create table Checklist(
idChecklist integer not null,
idCliente integer not null,
idMotocicleta integer not null,
gasolina varchar(25) not null,
luces varchar(25) not null,
espejoIzquierdo varchar(25) not null,
espejoDerecho varchar(25) not null,
llantaDelantera varchar(25) not null,
llantaTrasera varchar(25) not null,
fallas text not null,
diagnostico text not null,
fechaHoraRegistro timestamp not null,
activo bit not null default '1',
PRIMARY KEY (idChecklist)
);

Create table RegistroReparacion(
idReparacion integer not null,
idRefaccionAlmacen integer not null,
idChecklist integer not null,
dejaRefaccion boolean not null,
descripcionRefaccion text not null,
anticipo money not null default 0,
dejaLlaves boolean not null,
descripcionLlaves text not null,
fechaDeEntrega date not null,
activo bit not null default '1',
PRIMARY KEY (idReparacion)
);

Create table Servicios(
idServicio integer not null,
nombreServicio varchar(50) not null,
precio1 money not null,
precio2 money not null default 0,
activo bit not null default '1',
PRIMARY KEY (idServicio)
);

Create table ServicioRealizado(
idServicioRealizado integer not null,
idServicio integer not null,
idReparacion integer not null,
precio money not null,
cantidad varchar(5) not null,
PRIMARY KEY (idServicioRealizado)
);

Create table ReparacionPartes(
idReparacionPartes integer not null,
idRefaccionAlmacen integer not null,
idReparacion int not null,
precioVenta money not null,
ivaVenta money not null,
cantidad varchar(5) not null,
PRIMARY KEY (idReparacionPartes)
);






Create table Proveedor(
idProveedor integer not null,
nombreEmpresa varchar(20) not null,
domicilio varchar(20) not null,
numeroInterior varchar(15) not null,
numeroExterior varchar(15) not null,
calle varchar(25) not null,
localidad varchar(35) not null,
ciudad varchar(25) not null,
estado varchar(25) not null,
codigoPostal varchar(10) not null,
telefonoEmpresa varchar(14) not null default '000-00-0-00-00',
estatus bit(1) not null default '1',
PRIMARY KEY (idProveedor)
);


Create table ProveedorContacto(
idProveedorContacto integer not null,
idProveedor integer not null,
nombre varchar(20) not null,
apellidoPaterno varchar(15) not null,
apellidoMaterno varchar(15) not null,
celular varchar(13) not null default '000-000-00-00',
correo varchar(25) not null,
estatus bit not null default '1',
FechaInicial date not null,
FechaFinal varchar(10) not null,
PRIMARY KEY (idProveedorContacto)
);






Create table Marca(
idMarca integer not null,
nombre varchar(25) not null,
activo bit not null default '1',
PRIMARY KEY (idMarca)
);

Create table Compra(
folioCompra integer not null,
idEmpleado integer not null,
idProveedor integer not null,
fechaCompra date not null,
fechaHoraRegistro timestamp not null,
activo bit not null default '1',
PRIMARY KEY (folioCompra)
);

Create table Almacen(
idRefaccionAlmacen integer not null,
idMarca integer not null,
nombre varchar(20) not null,
modelo varchar(20) not null,
precio1 money not null,
precio2 money not null default 0,
iva varchar(5) not null default '16%',
existencia int not null,
stockMinimo int not null,
stockMaximo int not null,
activo bit not null default '1',
PRIMARY KEY (idRefaccionAlmacen)
);

Create table CompraDetalle(
folio integer not null,
folioCompra integer not null,
idRefaccionAlmacen integer not null,
costoUnitario money not null,
ivaUnitario money not null,
cantidad int not null,
precioUnitario1 money not null,
precioUnitario2 money not null default 0,
activo bit not null default '1',
PRIMARY KEY (folio)
);

Create table AjusteAlmacen(
idAjuste integer not null,
idRefaccionAlmacen integer not null,
idEmpleado integer not null,
iva money not null,
Activo bit not null default '1',
PRIMARY KEY (idAjuste)
);


ALTER TABLE Motocicleta  ADD  CONSTRAINT FK_Motocicleta_Empleados FOREIGN KEY(idEmpleado)
REFERENCES Empleados (idEmpleado);

ALTER TABLE Motocicleta  ADD  CONSTRAINT FK_Motocicleta_Marca FOREIGN KEY(idMarca)
REFERENCES Marca (idMarca);

ALTER TABLE Checklist  ADD  CONSTRAINT FK_Checklist_Cliente FOREIGN KEY(idCliente)
REFERENCES Cliente (idCliente);

ALTER TABLE Checklist  ADD  CONSTRAINT FK_Checklist_Motocicleta FOREIGN KEY(idMotocicleta)
REFERENCES Motocicleta (idMotocicleta);

ALTER TABLE RegistroReparacion  ADD  CONSTRAINT FK_RegistroReparacion_Almacen FOREIGN KEY(idRefaccionAlmacen)
REFERENCES Almacen (idRefaccionAlmacen);

ALTER TABLE RegistroReparacion  ADD  CONSTRAINT FK_RegistroReparacion_Checklist FOREIGN KEY(idChecklist)
REFERENCES Checklist (idChecklist);

ALTER TABLE ServicioRealizado ADD  CONSTRAINT FK_ServicioRealizado_Servicios FOREIGN KEY(idServicio)
REFERENCES Servicios (idServicio);

ALTER TABLE ServicioRealizado ADD  CONSTRAINT FK_ServicioRealizado_RegistroReparacion FOREIGN KEY(idReparacion)
REFERENCES RegistroReparacion (idReparacion);

ALTER TABLE ReparacionPartes ADD  CONSTRAINT FK_ReparacionPartes_Almacen FOREIGN KEY(idRefaccionAlmacen)
REFERENCES Almacen (idRefaccionAlmacen);

ALTER TABLE ReparacionPartes ADD  CONSTRAINT FK_ReparacionPartes_RegistroReparacion FOREIGN KEY(idReparacion)
REFERENCES RegistroReparacion (idReparacion);

ALTER TABLE ProveedorContacto ADD  CONSTRAINT FK_ProveedorContacto_Proveedor FOREIGN KEY(idProveedor)
REFERENCES Proveedor (idProveedor);

ALTER TABLE Compra ADD  CONSTRAINT FK_Compra_Empleados FOREIGN KEY(idEmpleado)
REFERENCES Empleados (idEmpleado);

ALTER TABLE Compra ADD  CONSTRAINT FK_Compra_Proveedor FOREIGN KEY(idProveedor)
REFERENCES Proveedor (idProveedor);

ALTER TABLE Almacen ADD  CONSTRAINT FK_Almacen_Marca FOREIGN KEY(idMarca)
REFERENCES Marca (idMarca);

ALTER TABLE CompraDetalle ADD  CONSTRAINT FK_CompraDetalle_Compra FOREIGN KEY(folioCompra)
REFERENCES Compra (folioCompra);

ALTER TABLE CompraDetalle ADD  CONSTRAINT FK_CompraDetalle_Almacen FOREIGN KEY(idRefaccionAlmacen)
REFERENCES Almacen (idRefaccionAlmacen);

ALTER TABLE AjusteAlmacen ADD  CONSTRAINT FK_AjusteAlmacen_Almacen FOREIGN KEY(idRefaccionAlmacen)
REFERENCES Almacen (idRefaccionAlmacen);

ALTER TABLE AjusteAlmacen ADD  CONSTRAINT FK_AjusteAlmacen_Empleados FOREIGN KEY(idEmpleado)
REFERENCES Empleados (idEmpleado);