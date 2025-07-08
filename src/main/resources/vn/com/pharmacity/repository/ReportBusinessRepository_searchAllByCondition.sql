SELECT * from ReportBusiness
WHERE 1=1
/*IF fileName != NULL && fileName != ''*/
	AND file_name LIKE CONCAT('%', /*fileName*/'', '%')
/*END*/
order by id desc