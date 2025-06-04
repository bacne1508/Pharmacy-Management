SELECT *, s.Full_Name AS supplier_code FROM Medicine
LEFT JOIN Suppliers s ON
	Medicine.supplier_id = s.id
WHERE Is_Active = 1
/*IF name != NULL && name != ''*/
	AND name LIKE CONCAT('%', /*name*/'', '%')
/*END*/
/*IF code != NULL && code != ''*/
	AND code LIKE CONCAT('%', /*code*/'', '%')
/*END*/