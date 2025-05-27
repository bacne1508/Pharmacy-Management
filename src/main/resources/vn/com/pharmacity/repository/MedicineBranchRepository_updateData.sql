UPDATE Branches
SET 
Branches_Name = /*form.branchesName*/
,
Address = /*form.address*/
,
Province_code = /*form.provinceCode*/
,
Province = /*form.province*/
,
District_code = /*form.districtCode*/
,
District = /*form.district*/
,
Ward_code = /*form.wardCode*/
,
Ward = /*form.ward*/
,
Phone = /*form.phone*/
,
Manager = /*form.manager*/
,
Email = /*form.email*/
,
Is_Active = 1
,
Updated_Date = /*form.updatedDate*/
,
Updated_By = /*form.updatedBy*/
WHERE
id = /*form.id*/;