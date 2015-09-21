
---FUNCION PARA AGREGAR DE LA TABLA ALMACEN
create or replace function fn_agregaralmacen(_idmarca integer, 
_nombre character varying(20),
_modelo character varying(20),
_precio1 numeric, 
_precio2 numeric,
_iva character varying(5), 
_existencia integer, 
_stockminimo integer, 
_stockmaximo integer)

RETURNS void as $$

declare maximo integer;

declare total integer;

begin

select count (idrefaccionalmacen) into total from almacen;

if (total =0) then 
maximo:=1;

else 
select max((idrefaccionalmacen)+1) into maximo from almacen;

end if;

insert into almacen (idrefaccionalmacen, idmarca, nombre, modelo, precio1, precio2, iva, existencia, stockminimo, stockmaximo) 
values 
(maximo, _idmarca, _nombre, _modelo, _precio1, _precio2, _iva, _existencia, _stockminimo, _stockmaximo);

end; 
$$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_agregaralmacen(integer, character varying, character varying, numeric, numeric, character varying, integer, integer, integer)
  OWNER TO postgres;

---FUNCION PARA ELIMINAR 

create or replace function fn_eliminaralmacen (_idrefaccionalmacen integer )
 
RETURNS void as 

$$

begin

update almacen set activo = '0' where idrefaccionalmacen=_idrefaccionalmacen;

end

$$

language plpgsql VOLATILE;

---FUNCION PARA ACTUALIZAR 

create or replace function fn_actualizaralmacen (_idrefaccionalmacen integer,
_idmarca integer, 
_nombre character varying(20),
_modelo character varying(20),
_precio1 numeric, 
_precio2 numeric,
_iva character varying(5), 
_existencia integer, 
_stockminimo integer, 
_stockmaximo integer)

RETURNS void AS

$$

begin
	
Update almacen set idmarca=_idmarca,
nombre=_nombre,
modelo=_modelo,
precio1=_precio1,
precio2=_precio2,
iva=_iva,
existencia=_existencia,
stockminimo=_stockminimo,
stockmaximo=_stockmaximo

where idrefaccionalmacen=_idrefaccionalmacen;

end 
$$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_actualizaralmacen(integer, integer, character varying, character varying, numeric, numeric, character varying, integer, integer, integer)
  OWNER TO postgres;