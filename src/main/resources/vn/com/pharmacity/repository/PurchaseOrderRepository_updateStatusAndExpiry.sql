UPDATE PurchaseOrders
SET status = /*po.status*/,
    UPDATED_BY = /*po.updatedBy*/,
    UPDATED_DATE = /*po.updatedDate*/,
    expected_delivery_date = /*po.expectedDeliveryDate*/
WHERE ID = /*po.id*/
