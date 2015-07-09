--Funcion agregar proveedor
create or replace function fn_agregarproveedor(_nombreempresa character varying, 
_domicilio character varying,
_numerointerior character varying,
_numeroexterior character varying, 
_calle character varying,
_localidad character varying, 
_ciudad character varying, 
_estado character varying, 
_codigopostal character varying, 
_telefonoempresa character varying ) 
RETURNS void as $$
declare maximo integer;
declare total integer;
begin
select count (idproveedor) into total
from proveedor;
if (total =0) then 
maximo:=1;
else 
select max((idproveedor)+1) into maximo
from proveedor;
end if;
if length(_numerointerior)=0 then
_numerointerior:='Ninguno';
end if;
insert into proveedor (idproveedor, nombreempresa, domicilio, numerointerior, numeroexterior, calle, localidad, ciudad, estado, codigopostal, telefonoempresa) 
values 
(maximo, _nombreempresa, _domicilio, _numerointerior, _numeroexterior, _calle, _localidad, _ciudad, _estado, _codigopostal, _telefonoempresa);
end;
$$ language plpgsql; 

---FUNCION PARA ELIMINAR PROVEEDOR

create or replace function fn_eliminarproveedpr (_idproveedor integer )
RETURNS void as 
$$
begin
update proveedor set estatus = '0' where idproveedor = _idproveedor;
end
$$
language plpgsql VOLATILE;

---FUNCION PARA ACTUALIZAR PROVEEDOR

create or replace function fn_actualizarproveedor(_nombreempresa character varying, 
_domicilio character varying,
_numerointerior character varying,
_numeroexterior character varying, 
_calle character varying,
_localidad character varying, 
_ciudad character varying, 
_estado character varying, 
_codigopostal character varying, 
_telefonoempresa character varying,
_idproveedor integer)
RETURNS void AS
$$
begin	
Update proveedor set nombreempresa=_nombreempresa,
domicilio=_domicilio,
numerointerior=_numerointerior,
numeroexterior=_numeroexterior,
calle=_calle,
localidad=_localidad,
ciudad=_cuidad,
estado=_estado,
codigopostal=_codigopostal,
telefonoempresa=_telefonoempresa
where idproveedor = _idproveedor;
end 
$$
LANGUAGE plpgsql VOLATILE;