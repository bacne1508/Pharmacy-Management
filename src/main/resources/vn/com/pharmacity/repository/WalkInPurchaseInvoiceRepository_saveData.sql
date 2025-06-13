INSERT WalkInPurchaseInvoice (invoice_code, customer_name, gender,  age,  phone, card_number, address, Diagnosis, total_amount, payment_status
, notes, CREATED_DATE, CREATED_BY)
OUTPUT INSERTED.*
VALUES (/*dto.invoiceCode*/, /*dto.customerName*/, /*dto.gender*/, /*dto.age*/, /*dto.phone*/, /*dto.cardNumber*/, /*dto.address*/, /*dto.diagnosis*/
, /*dto.totalAmount*/, 'PAID', /*dto.notes*/, /*dto.createdDate*/, /*dto.createdBy*/)