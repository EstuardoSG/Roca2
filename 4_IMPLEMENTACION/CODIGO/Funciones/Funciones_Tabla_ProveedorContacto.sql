--Funcion agregar proveedorContacto
create or replace function fn_agregarpContacto(_idProv integer,
_nombre character varying, 
_apellidoPaterno character varying,
_apellidoMaterno character varying,
_celular character varying, 
_correo character varying) 
RETURNS void as $$
declare maximo integer;
declare total integer;
begin
select count (idProveedorContacto) into total
from ProveedorContacto;
if (total =0) then 
maximo:=1;
else 
select max((idProveedorContacto)+1) into maximo
from ProveedorContacto;
end if;
if length(_correo)=0 then
_correo:='Ninguno';
end if;
insert into ProveedorContacto (idProveedorContacto, idProveedor, nombre, apellidoPaterno, apellidoMaterno, celular, correo, FechaInicial, FechaFinal) 
values 
(maximo, _idProv, _nombre, _apellidoPaterno, _apellidoMaterno, _celular, _correo, current_date, '0000-00-00');
end;
$$ language plpgsql; 


---FUNCION PARA ELIMINAR PROVEEDORCONTACTO

create or replace function fn_eliminarpContacto(_idproveedorC integer )
RETURNS void as 
$$
begin
update ProveedorContacto set estatus = '0' where idProveedorContacto = _idproveedorC;
end
$$
language plpgsql VOLATILE;

---FUNCION PARA ACTUALIZAR PROVEEDORCONTACTO

create or replace function fn_actualizarpContacto(_idProv integer,
_nombre character varying, 
_apellidoPaterno character varying,
_apellidoMaterno character varying,
_celular character varying, 
_correo character varying,
_idproveedorC integer)
RETURNS void AS
$$
begin	
Update ProveedorContacto set idProveedor=_idProv,
nombre=_nombre,
apellidoPaterno=_apellidoPaterno,
apellidoMaterno=_apellidoMaterno,
celular=_celular,
correo=_correo,
fechainicial = now(),
fechafinal = '0000-00-00'
where idProveedorContacto = _idproveedorC;
end 
$$
LANGUAGE plpgsql VOLATILE;

