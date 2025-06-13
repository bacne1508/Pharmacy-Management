SELECT s.*, m.code as medicine_code, w.Warehouse_code  FROM Stock s
LEFT JOIN Medicine m
    ON s.medicine_id = m.id
LEFT JOIN Warehouses w 
    ON s.warehouse_id = w.ID 
WHERE 1=1
	AND s.expiry_date >= GETDATE()
/*IF medicineId != NULL && medicineId != ''*/
    AND s.medicine_id  LIKE CONCAT('%', /*medicineId*/'', '%')
/*END*/
/*IF batchNo != NULL && batchNo != ''*/
    AND s.batch_no  LIKE CONCAT('%', /*batchNo*/'', '%')
/*END*/