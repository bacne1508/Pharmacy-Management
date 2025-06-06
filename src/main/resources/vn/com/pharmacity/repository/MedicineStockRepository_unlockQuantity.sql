UPDATE stock
SET locked_quantity = locked_quantity - /*quantity*/
WHERE medicine_id = /*medicineId*/
  AND locked_quantity >= /*quantity*/