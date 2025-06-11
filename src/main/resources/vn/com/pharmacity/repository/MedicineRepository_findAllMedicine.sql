SELECT 
    m.ID AS ID,
    m.CODE AS CODE,
    m.NAME AS NAME
FROM 
    Medicine m
JOIN 
    Stock s ON m.ID = s.medicine_id
WHERE 
    m.Is_Active = 1
    AND s.expiry_date >= GETDATE()
    AND (s.quantity - s.locked_quantity - s.used_quantity) > 0
