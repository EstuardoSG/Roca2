---FUNCION PARA AGREGAR MARCA
CREATE OR REPLACE FUNCTION fn_agregarmarca(_nombre character varying (25))
returns void as $$
declare maximo integer;
declare total integer;
begin
select count (idmarca) into total from marca;
if (total=0) then maximo:=1;
else select max((idmarca)+1) into maximo from marca;
end if;
insert into marca (idmarca, nombre)
values
(maximo, _nombre);
end;
$$ 
language plpgsql;

---FUNCION PARA ELIMINAR MARCA
CREATE OR REPLACE FUNCTION fn_eliminarmarca (_idmarca integer)
RETURNS void as
$$
begin
update marca set activo ='0' where idmarca=_idmarca;
end
$$
language plpgsql VOLATILE;

---FUNCION PARA ACTUALIZAR MARCA 
CREATE OR REPLACE FUNCTION fn_actualizarmarca (_nombre character varying (25),
_idmarca integer)
RETURNS void as
$$
begin
Update marca set nombre=_nombre
where idmarca=_idmarca;
end
$$
language plpgsql VOLATILE;
