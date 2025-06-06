UPDATE Stock
SET locked_quantity = /*form.lockedQuantity*/, 
	used_quantity = /*form.usedQuantity*/
WHERE id = /*form.id*/;