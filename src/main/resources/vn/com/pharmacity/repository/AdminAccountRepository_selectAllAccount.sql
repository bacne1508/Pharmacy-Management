SELECT * FROM users 
WHERE Is_Active = 1
/*IF userName != NULL && userName != ''*/
	AND username LIKE CONCAT('%', /*userName*/'', '%')
/*END*/