UPDATE PurchaseOrders
SET status = /*po.status*/,
    UPDATED_BY = /*po.updatedBy*/,
    UPDATED_DATE = /*po.updatedDate*/
WHERE ID = /*po.id*/
