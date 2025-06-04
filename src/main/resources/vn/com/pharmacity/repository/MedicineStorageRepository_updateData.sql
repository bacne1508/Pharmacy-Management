UPDATE Warehouses
SET 
warehouse_Name = /*form.warehouseName*/,
warehouse_Type = /*form.warehouseType*/,
Branches_code = /*form.branchesCode*/,
Manager_name = /*form.managerName*/,
phone = /*form.phone*/,
updated_by = /*form.updatedBy*/,
updated_date = /*form.updatedDate*/
WHERE
id = /*form.id*/;