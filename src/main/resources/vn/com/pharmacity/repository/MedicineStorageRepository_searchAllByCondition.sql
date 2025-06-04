SELECT * FROM Warehouses
WHERE Is_Active = 1
/*IF name != NULL && name != ''*/
	AND Warehouse_name LIKE CONCAT('%', /*name*/'', '%')
/*END*/
/*IF code != NULL && code != ''*/
	AND Warehouse_code LIKE CONCAT('%', /*code*/'', '%')
/*END*/