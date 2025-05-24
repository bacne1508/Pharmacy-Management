SELECT * FROM users 
WHERE Is_Active = 1 AND auth != 0
/*IF userName != NULL && userName != ''*/
	AND username LIKE CONCAT('%', /*userName*/'', '%')
/*END*/
/*IF fullName != NULL && fullName != ''*/
	AND full_name LIKE CONCAT('%', /*fullName*/'', '%')
/*END*/
/*IF phone != NULL && phone != ''*/
	AND phone LIKE CONCAT('%', /*phone*/'', '%')
/*END*/
/*IF email != NULL && email != ''*/
	AND email LIKE CONCAT('%', /*email*/'', '%')
/*END*/