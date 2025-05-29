UPDATE medicine
SET 
    deleted_by=/*form.deletedBy*/,
    deleted_Date=/*form.deletedDate*/,
    is_Active=0
WHERE id=/*form.id*/