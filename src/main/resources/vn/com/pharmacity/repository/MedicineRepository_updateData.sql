UPDATE medicine 
SET 
code = /*form.code*/
,
name = /*form.name*/
,
medicine_Images = /*form.medicineImages*/
,
description = /*form.description*/
,
medicine_Groups_Code = /*form.medicineGroupsCode*/
,
medicine_Units_Code = /*form.medicineUnitsCode*/
,
medicine_Types_Code = /*form.medicineTypesCode*/
,
ingredient = /*form.ingredient*/
,
strength = /*form.strength*/
,
manufacturer = /*form.manufacturer*/
,
origin_Country = /*form.originCountry*/
,
is_Active = 1
,
updated_Date = /*form.updatedDate*/
,
updated_By = /*form.updatedBy*/
WHERE
id = /*form.id*/;