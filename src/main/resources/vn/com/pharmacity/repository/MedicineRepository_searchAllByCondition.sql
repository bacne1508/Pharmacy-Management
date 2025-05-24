SELECT * FROM Medicine 
WHERE Is_Active = 1
/*IF name != NULL && name != ''*/
	AND name LIKE CONCAT('%', /*name*/'', '%')
/*END*/
/*IF code != NULL && code != ''*/
	AND code LIKE CONCAT('%', /*code*/'', '%')
/*END*/