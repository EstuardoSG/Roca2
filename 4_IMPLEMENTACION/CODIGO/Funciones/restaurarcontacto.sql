create or replace function fn_restaurarC(_idproveedorC integer )
RETURNS void as 
$$
begin
update ProveedorContacto set estatus = '1' where idProveedorContacto = _idproveedorC;
end
$$
language plpgsql VOLATILE;