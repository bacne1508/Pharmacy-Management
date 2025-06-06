SELECT po.*, s.Full_Name as supplier_code FROM PurchaseOrders po 
LEFT JOIN Suppliers s 
    ON po.supplier_id = s.ID 
WHERE 1=1
/*IF poCode != NULL && poCode != ''*/
	AND po_Code LIKE CONCAT('%', /*poCode*/'', '%')
/*END*/
/*IF status != NULL && status != ''*/
	AND status= /*status*/''
/*END*/