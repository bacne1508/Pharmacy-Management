SELECT * FROM Branches 
WHERE Is_active = 1
/*IF branchCode != NULL && branchCode != ''*/
	AND Branches_Code LIKE CONCAT('%', /*branchCode*/'', '%')
/*END*/
/*IF branchName != NULL && branchName != ''*/
	AND Branches_Name LIKE CONCAT('%', /*branchName*/'', '%')
/*END*/
/*IF phone != NULL && phone != ''*/
	AND phone LIKE CONCAT('%', /*phone*/'', '%')
/*END*/
/*IF manager != NULL && manager != ''*/
	AND manager LIKE CONCAT('%', /*manager*/'', '%')
/*END*/