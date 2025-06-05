UPDATE Stock
    SET locked_Quantity = locked_Quantity + /*quantity*/
    WHERE medicine_Id = /*medicineId*/'3'
    AND warehouse_Id = /*warehouseId*/'1'
    AND batch_No = /*batchNo*/'KS_01'
    AND (quantity - locked_Quantity - used_Quantity) >= /*quantity*/