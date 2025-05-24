SELECT * FROM MedicineUnits
WHERE del_flag = 1
/*IF name != NULL && name != ''*/
	AND name LIKE CONCAT('%', /*name*/'', '%')
/*END*/
/*IF code != NULL && code != ''*/
	AND code LIKE CONCAT('%', /*code*/'', '%')
/*END*/