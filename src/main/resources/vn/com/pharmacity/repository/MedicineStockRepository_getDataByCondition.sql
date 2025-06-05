SELECT * FROM Stock
WHERE 1=1
/*IF medicineId != NULL && medicineId != ''*/
    AND medicine_id  LIKE CONCAT('%', /*medicineId*/'', '%')
/*END*/
/*IF batchNo != NULL && batchNo != ''*/
    AND batch_no  LIKE CONCAT('%', /*batchNo*/'', '%')
/*END*/
/*IF warehouseId != NULL && warehouseId != ''*/
    AND warehouse_id  LIKE CONCAT('%', /*warehouseId*/'', '%')
/*END*/
