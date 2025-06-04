SELECT mor.*, u.Username , m.code AS medicine_code, m.name AS medicine_name
FROM PurchaseOrderRequest mor
LEFT JOIN Users u 
    ON mor.user_id = u.id
LEFT JOIN Medicine m 
    ON mor.medicine_id = m.id 
WHERE 1=1
/*IF username != NULL && username != ''*/
    AND u.Username LIKE CONCAT('%', /*username*/'', '%')
/*END*/
/*IF status != NULL && status != ''*/
    AND status LIKE CONCAT('%', /*status*/'', '%')
/*END*/