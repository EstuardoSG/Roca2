--funcion agregar
create or replace function fn_agregarCheck(_idcliente integer, _idmotocicleta integer, _gasolina character varying, _luces character varying, _espejoizquierdo character
varying, _espejoderecho character varying, _llantadelantera character varying, _llantatrasera character varying, _fallas text, _diagnostico text) returns void as $$
declare total integer;
declare maximo integer;
begin
select count(idChecklist) into total
from Checklist;
if total = 0 then
maximo:= 1;
else  
select (max(idChecklist)+1) into maximo 
from Checklist;
end if;
if length(_fallas)=0 then
_diagnostico:='ninguna';
end if;
if length(_diagnostico)=0 then
_diagnostico:='En buen estado';
end if;
insert into Checklist(idChecklist, idCliente, idMotocicleta, gasolina, luces, espejoIzquierdo, espejoDerecho, llantaDelantera,llantaTrasera, fallas, diagnostico,
fechaHoraRegistro) values (maximo, _idcliente, _idmotocicleta, _gasolina,
_luces, _espejoizquierdo, _espejoderecho, _llantadelantera,_llantatrasera, _fallas, _diagnostico, CURRENT_TIMESTAMP);
end;
$$ language plpgsql;

--funcion eliminar
create or replace function fn_eliminarCheck(_idChecklist int)
returns void as
$body$
begin
update Checklist set activo = '0' where idChecklist = _idChecklist;
end
$body$
language plpgsql VOLATILE

--funcion editar
Create or replace function fn_actualizarCheck(_idcliente integer, _idmotocicleta integer, _gasolina character varying, _luces character varying, _espejoizquierdo character
varying, _espejoderecho character varying, _llantadelantera character varying, _llantatrasera character varying, _fallas text, _diagnostico text,_idChecklist int)
RETURNS void as
$body$
begin
update Checklist
set idCliente = _idcliente,
idMotocicleta = _idmotocicleta,
gasolina = _gasolina,
luces = _luces,
espejoIzquierdo = _espejoizquierdo,
espejoDerecho = _espejoderecho,
llantaDelantera = _llantadelantera,
llantaTrasera = _llantatrasera,
fallas = _fallas,
diagnostico = _diagnostico,
fechaHoraRegistro = CURRENT_TIMESTAMP
where idChecklist = _idChecklist;
end $body$
language plpgsql VOLATILE
