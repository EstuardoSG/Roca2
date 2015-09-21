CREATE OR REPLACE FUNCTION fninsertarempleado(
    _nombre1 character varying,
    _nombre2 character varying,
    _apellidopaterno character varying,
    _apellidomaterno character varying,
    _telefono1 character varying,
    _telefono2 character varying,
    _celular1 character varying,
    _celular2 character varying,
    _domicilio character varying,
    _numerointerior character varying,
    _numeroexterior character varying,
    _calle character varying,
    _localidad character varying,
    _ciudad character varying,
    _estado character varying,
    _codigopostal character varying,
    _correo character varying,
    _usuario character varying,
    _contrasenia character varying,
    _privilegio character varying,
    _fechadesalida character varying)
  RETURNS void AS
$BODY$
declare total integer;
declare maximo integer;
begin
select count(idempleado) into total
from empleados;
if total = 0 then
maximo:= 1;
else  
select (max(idempleado)+1) into maximo 
from empleados;
end if;
if length(_nombre2)=0 then
_nombre2:='ninguno';
end if;
if length(_telefono1)=0 then
_telefono1:='000-00-0-00-00';
end if;
if length(_telefono2)=0 then
_telefono2:='000-00-0-00-00';
end if;
if length(_celular1)=0 then
_celular1:='000-000-00-00';
end if;
if length(_celular2)=0 then
_celular2:='000-000-00-00';
end if;
if length(_numerointerior)=0 then
_numerointerior:='ninguno';
end if;
if length(_fechadesalida)=0 then
_fechadesalida:='0000-00-00';
end if;
insert into empleados(idempleado, nombre1, nombre2, apellidopaterno, apellidomaterno, telefono1, telefono2, celular1, celular2, domicilio, numerointerior, numeroexterior, calle, localidad, ciudad, 
estado, codigopostal, correo, usuario, contrasenia, privilegio, estatus, fechaingreso,fechadesalida)
values (maximo, _nombre1, _nombre2, _apellidopaterno, _apellidomaterno, _telefono1, _telefono2, _celular1, _celular2, _domicilio, _numerointerior, _numeroexterior, _calle, _localidad, _ciudad, _estado, _codigopostal,
	_correo, _usuario, _contrasenia, _privilegio,default,now(),_fechadesalida);
end;
$BODY$
  LANGUAGE plpgsql VOLATILE

 
CREATE OR REPLACE FUNCTION fn_actualizare(
    _idempleado integer,
    _nombre1 character varying,
    _nombre2 character varying,
    _apellidopaterno character varying,
    _apellidomaterno character varying,
    _telefono1 character varying,
    _telefono2 character varying,
    _celular1 character varying,
    _celular2 character varying,
    _domicilio character varying,
    _numerointerior character varying,
    _numeroexterior character varying,
    _calle character varying,
    _localidad character varying,
    _ciudad character varying,
    _estado character varying,
    _codigopostal character varying,
    _correo character varying,
    _usuario character varying,
    _contrasenia character varying,
    _privilegio character varying,
    _fechadesalida character varying)
  RETURNS void AS
$BODY$
begin
update empleados
set nombre1 = _nombre1,
nombre2 = _nombre2,
apellidopaterno = _apellidopaterno,
apellidomaterno = _apellidomaterno,
telefono1 = _telefono1,
telefono2 = _telefono2,
celular1 = _celular1,
celular2 = _celular2,
domicilio = _domicilio,
numerointerior = _numerointerior,
numeroexterior = _numeroexterior,
calle = _calle,
localidad = _localidad,
ciudad = _ciudad,
estado = _estado,
codigopostal = _codigopostal,
correo = _correo,
usuario= _usuario,
contrasenia = _contrasenia,
privilegio = _privilegio,
fechadesalida = _fechadesalida,
fechaingreso = current_date
where idempleado = _idempleado;
end $BODY$
LANGUAGE plpgsql VOLATILE

CREATE OR REPLACE FUNCTION fneliminarempleado(id_empleado integer)
RETURNS void AS
$BODY$
declare status integer;
begin
update empleados set estatus = '0' where idempleado=id_empleado;
end
$BODY$
LANGUAGE plpgsql VOLATILE