UPDATE Branches
SET 
    deleted_by=/*form.deletedBy*/,
    deleted_Date=/*form.deletedDate*/,
    is_active=0
WHERE id=/*form.id*/