CREATE OR REPLACE FUNCTION fnactualizarservicio(
    id_s integer,
    nombre_s character varying,
    precio1_s numeric,
    precio2_s numeric)
  RETURNS void AS
$BODY$
begin

		update servicios set nombreservicio=nombre_s,precio1=precio1_s,precio2=precio2_s where idservicio = id_s;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fnactualizarservicio(integer, character varying, numeric, numeric)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION fneliminaservicio(id_servicio integer)
  RETURNS void AS
$BODY$
declare status integer;
begin
	select activo into status from servicios where idservicio=id_servicio;
	if status='1' then
		update servicios set activo = '0' where idservicio=id_servicio;
	else
		update servicios set activo = '1' where idservicio=id_servicio;
	end if;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fneliminaservicio(integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION fninsertarservicio(
    nombre_s character varying,
    precio1_s numeric,
    precio2_s numeric)
  RETURNS void AS
$BODY$
declare id_contador integer;
declare contador integer;
begin
	select count (idservicio) into contador from servicios;
	if contador =0 then 
		id_contador =1;
	else 
		select max((idservicio)+1) into id_contador from servicios;
	end if;	
		insert into servicios (idservicio,nombreservicio,precio1,precio2,activo)
		values (id_contador,nombre_s,precio1_s,precio2_s,default);

end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fninsertarservicio(character varying, numeric, numeric)
  OWNER TO postgres;
