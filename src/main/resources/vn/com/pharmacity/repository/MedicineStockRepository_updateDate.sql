UPDATE Stock
SET deleted_by = /*form.deletedBy*/, 
	deleted_date = /*form.deletedDate*/
WHERE id = /*form.id*/;