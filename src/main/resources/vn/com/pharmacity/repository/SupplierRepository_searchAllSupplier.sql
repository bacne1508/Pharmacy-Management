SELECT * FROM Suppliers 
WHERE ISNULL(DELETED_BY, '') = ''
/*IF fullName != NULL && fullName != ''*/
	AND full_name LIKE CONCAT('%', /*fullName*/'', '%')
/*END*/
/*IF phone != NULL && phone != ''*/
	AND phone LIKE CONCAT('%', /*phone*/'', '%')
/*END*/
/*IF email != NULL && email != ''*/
	AND email LIKE CONCAT('%', /*email*/'', '%')
/*END*/