SELECT pod.*, po.po_code as po_code, m.code as medicine_code FROM PurchaseOrderDetails pod 
LEFT JOIN PurchaseOrders po 
    ON pod.purchase_order_id = po.ID 
LEFT JOIN Medicine m
    ON pod.medicine_id = m.id
WHERE pod.purchase_order_id = /*poId*/