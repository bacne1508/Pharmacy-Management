SELECT MAX(/*$columnName*/)
FROM /*$tableName*/ with(nolock)
WHERE /*$columnName*/ LIKE CONCAT(/*prefix*/'','%');