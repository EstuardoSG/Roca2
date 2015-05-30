Create table Empleados(
idEmpleado int primary key not null,
nombre1 varchar(15) not null,
nombre2 varchar(15) not null,
apellidoPaterno varchar(15) not null,
apellidoMaterno varchar(15) not null,
telefono1 varchar(14) not null default '000-00-0-00-00',
telefono2 varchar(14) not null default '000-00-0-00-00',
celular1 varchar(14) not null default '000-00-0-00-00',
celular2 varchar(14) not null default '000-00-0-00-00',
domicilio varchar(50) not null,
numeroInterior varchar(3) not null,
numeroExterior varchar(3) not null,
calle varchar(25) not null,
localidad varchar(35) not null,
ciudad varchar(25) not null,
estado varchar(25) not null,
codigoPostal varchar(10) not null,
correo varchar(25) not null,
usuario varchar(15) not null,
contrasenia varchar(20) not null,
privilegio varchar(13) not null,
estatus boolean not null,
fechaIngreso date not null,
fechaDeSalida date not null
);




Create table Cliente(
idCliente int primary key not null,
nombre1 varchar(15) not null,
nombre2 varchar(15) not null,
apellidoPaterno varchar(15) not null,
apellidoMaterno varchar(15) not null,
telefono1 varchar(14) not null default '000-00-0-00-00',
telefono2 varchar(14) not null default '000-00-0-00-00',
celular1 varchar(14) not null default '000-00-0-00-00',
celular2 varchar(14) not null default '000-00-0-00-00',
domicilio varchar(50) not null,
numeroInterior varchar(3) not null,
numeroExterior varchar(3) not null,
calle varchar(25) not null,
localidad varchar(35) not null,
ciudad varchar(25) not null,
estado varchar(25) not null,
codigoPostal varchar(10) not null,
numeroCliente int not null,
clienteFrecuente boolean not null,
activo boolean not null
);




Create table MarcaMotocicleta(
idMarcaMotocicleta int primary key not null,
nombre varchar(25) not null
);

Create table Motocicleta(
idMotocicleta int primary key not null,
idMarcaMotocicleta int references MarcaMotocicleta not null,
idEmpleado int references Empleados not null,
modelo varchar(15) not null,
motor varchar(8) not null,
color varchar(10) not null,
placa boolean not null,
fechaHoraRegistro timestamp not null,
descripcionMotocicleta text not null,
activo boolean
);

Create table Checklist(
idChecklist int primary key not null,
idCliente int references Cliente not null,
idMotocicleta int references Motocicleta not null,
gasolina varchar(25) not null,
luces varchar(25) not null,
espejos varchar(25) not null,
llantas varchar(25) not null,
fallas varchar(25) not null,
diagnostico varchar(25) not null
);

Create table RegistroReparacion(
idReparacion int primary key not null,
idRefaccionAlmacen int references Almacen not null,
idChecklist int references Checklist not null,
dejaRefaccion boolean not null,
descripcionRefaccion text not null,
anticipo money not null default 0,
fechaHoraRegistro timestamp not null,
dejaLlaves boolean not null,
descripcionLlaves text not null
);

Create table Servicios(
idServicio int primary key not null,
nombreServicio varchar(50) not null,
activo boolean not null
);

Create table ServicioRealizado(
idServicioRealizado int primary key not null,
idServicio int references Servicios not null,
idChecklist int references Checklist not null,
descripcion text not null,
precio1 money not null,
precio2 money not null default 0,
cantidad varchar(5) not null
);

Create table ReparacionPartes(
idReparacionPartes int primary key not null,
idRefaccionAlmacen int references Almacen not null,
precio1 money not null,
precio2 money not null default 0,
cantidad varchar(5) not null
);






Create table Proveedor(
idProveedor int primary key not null,
nombreEmpresa varchar(20) not null,
direccionEmpresa varchar(50) not null,
ciudadEmpresa varchar(15) not null,
localidadEmpresa varchar(15) not null,
telefonoEmpresa varchar(10) not null default '000-00-0-00-00',
celularEmpresa varchar(13) not null default '000-00-0-00-00'
);

Create table Contacto(
idContacto int primary key not null,
nombre varchar(20) not null,
apellidoMaterno varchar(15) not null,
apellidoPaterno varchar(15) not null,
celular varchar(14) not null default '000-00-0-00-00',
correo varchar(25) not null
);

Create table ProveedorContacto(
idProveedorContacto int primary key not null,
idProveedor int references Proveedor not null,
idContacto int references Contacto not null,
FechaInicial date not null,
FechaFinal date not null
);






Create table MarcaRefaccion(
idMarcaRefaccion int primary key not null,
nombreMarca varchar(25) not null
);

Create table Compra(
folioCompra int primary key not null,
idEmpleado int references Empleados not null,
idProveedor int references Proveedor not null,
fechaCompra date not null,
fechaHoraRegistro timestamp not null
);

Create table Almacen(
idRefaccionAlmacen int primary key not null,
idMarcaRefaccion int references MarcaRefaccion not null,
nombre varchar(20) not null,
modelo varchar(20) not null,
precioVenta money not null,
ivaVenta money not null
);

Create table CompraDetalle(
folio int primary key not null,
folioCompra int references Compra not null,
idRefaccionAlmacen int references Almacen not null,
costoUnitario money not null,
ivaUnitario money not null,
cantidad int not null,
precioUnitario1 money not null,
precioUnitario2 money not null default 0
);

Create table AjusteAlmacen(
idAjuste int primary key not null,
idRefaccionAlmacen int references Almacen not null,
idEmpleado int references Empleados not null,
iva money not null,
refaccionActiva boolean not null
);