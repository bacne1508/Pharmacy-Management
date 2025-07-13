SELECT * FROM Bill 
WHERE ISNULL(DELETED_BY, '') = ''
/*IF billCode != NULL && billCode != ''*/
	AND bill_code LIKE CONCAT('%', /*billCode*/'', '%')
/*END*/
/*IF billType != NULL && billType != ''*/
	AND bill_type LIKE CONCAT('%', /*billType*/'', '%')
/*END*/