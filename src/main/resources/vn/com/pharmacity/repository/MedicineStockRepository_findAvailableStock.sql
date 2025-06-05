SELECT * FROM Stock s
    WHERE s.medicine_Id = /*medicineId*/''
    AND (s.quantity - s.locked_Quantity - s.used_Quantity) > 0
    ORDER BY s.expiry_Date ASC