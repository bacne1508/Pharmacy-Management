INSERT PurchaseOrders (po_code, supplier_id, expected_delivery_date,  status,  created_from, CREATED_DATE, CREATED_BY)
OUTPUT INSERTED.*
VALUES (/*po.poCode*/, /*po.supplierId*/, /*po.expectedDeliveryDate*/, /*po.status*/, /*po.createdFrom*/, /*po.createdDate*/, /*po.createdBy*/)