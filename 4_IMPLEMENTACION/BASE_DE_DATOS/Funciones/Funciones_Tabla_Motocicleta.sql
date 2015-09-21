--funcion agregar
create or replace function fn_agregarMoto(_idempleado integer, _idmarca integer, _modelo character varying, _motor character varying, _color character
varying, _placa boolean,  _descripcionmotocicleta text) returns void as $$
declare total integer;
declare maximo integer;
begin
select count(idMotocicleta) into total
from Motocicleta;
if total = 0 then
maximo:= 1;
else  
select (max(idMotocicleta)+1) into maximo 
from Motocicleta;
end if;
insert into Motocicleta(idMotocicleta, idEmpleado, idMarca, modelo, motor, color, placa, fechaHoraRegistro, descripcionMotocicleta) values (maximo, _idempleado,
_idmarca, _modelo, _motor, _color, _placa, CURRENT_TIMESTAMP, _descripcionmotocicleta);
end;
$$ language plpgsql;

--funcion eliminar
create or replace function fn_eliminarMoto(_idMotocicleta int)
returns void as
$body$
begin
update Motocicleta set activo = '0' where idMotocicleta = _idMotocicleta;
end
$body$
language plpgsql VOLATILE

--funcion editar
Create or replace function fn_actualizarMoto(_idempleado integer, _idmarca integer, _modelo character varying, _motor character varying, _color character
varying, _placa boolean,  _descripcionmotocicleta text,_idMotocicleta int)
RETURNS void as
$body$
begin
update Motocicleta
set idempleado = _idempleado,
idmarca = _idmarca,
modelo = _modelo,
motor = _motor,
color = _color,
placa = _placa,
fechaHoraRegistro = CURRENT_TIMESTAMP,
descripcionmotocicleta = _descripcionmotocicleta
where idMotocicleta = _idMotocicleta;
end $body$
language plpgsql VOLATILE