DROP TABLE if exists dbo.Roles;
CREATE TABLE Roles (
    ID INT PRIMARY KEY IDENTITY,
    Role_Id INT,
    Role_Name NVARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE if exists dbo.Users;
DROP SEQUENCE IF EXISTS SEQ_Users;
CREATE SEQUENCE SEQ_Users AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Users (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Users] NOT NULL,
    Username NVARCHAR(100) NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Full_Name NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    auth INT, -- 0-PATIENT (Bệnh nhân), 1-ADMIN, 2-RECEPTION (Đăng ký bệnh nhân), 3-DOCTOR, 4-LAB_ASSISTANT (Thực hiện & cập nhật kết quả xét nghiệm.)
                 --, 5-PHARMACY (Cấp phát thuốc, quản lý kho thuốc, xem đơn thuốc)
    Role VARCHAR(20),
    Is_Active BIT DEFAULT 1,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    Address NVARCHAR(255) NULL
);

DROP TABLE if exists dbo.UserSessions;
DROP SEQUENCE IF EXISTS SEQ_UserSessions;
CREATE SEQUENCE SEQ_UserSessions AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE UserSessions (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_UserSessions] NOT NULL,
    User_Id INT, --id user
    Token NVARCHAR(255) NOT NULL,
    Login_Time DATETIME DEFAULT GETDATE(),
    Expiry_Time DATETIME,
    Is_Revoked BIT DEFAULT 0
);

--------------Bảng constant

-------------Bảng thông tin thuốc---------------------
DROP TABLE if exists dbo.Medicine;
DROP SEQUENCE IF EXISTS SEQ_Medicine;
CREATE SEQUENCE SEQ_Medicine AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Medicine (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Medicine] NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,              -- Mã thuốc
    name NVARCHAR(255) NOT NULL,                    -- Tên thuốc
    medicine_images VARCHAR(MAX),                  -- Hình ảnh thuốc
    file_name NVARCHAR(255),                       -- tên file ảnh
    description NVARCHAR(999),                     -- Mô tả (nếu có)
    Medicine_Groups_code NVARCHAR(50) NULL,         -- nhóm thuốc
    Medicine_Units_code NVARCHAR(50),               -- Đơn vị tính (ví dụ: viên, lọ, hộp)
    medicine_Types_code NVARCHAR(100),              -- Dạng bào chế (viên nén, dung dịch,...)
    supplier_id int;
    ingredient NVARCHAR(999),                               -- Thành phần hoạt chất
    strength NVARCHAR(999),                         -- Hàm lượng
    manufacturer NVARCHAR(255),                     -- Nhà sản xuất
    origin_country NVARCHAR(100),                   -- Nước sản xuất
    purchase_price DECIMAL(15, 2),                 -- Giá mua
    sale_price DECIMAL(15, 2),                     -- Giá bán
    quantity INT,                                  -- Số lượng tồn kho
    Date_of_manufacture DATE,                      -- ngày sản xuất
    Product_expiry_date DATE,                      -- hạn sử dụng
    is_active int DEFAULT 1,                       -- Còn hoạt động hay không
    barcode TEXT,                                  -- mã vạch
    stock_quantity INT DEFAULT 0;
    min_stock_level INT DEFAULT 0;
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
----------------------nhóm thuốc
DROP TABLE if exists dbo.MedicineGroups;
DROP SEQUENCE IF EXISTS SEQ_MedicineGroups;
CREATE SEQUENCE SEQ_MedicineGroups AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE MedicineGroups (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_MedicineGroups] NOT NULL,
    code VARCHAR(50) NULL,              -- Mã nhóm thuốc
    name NVARCHAR(255) NULL,             -- Tên nhóm thuốc
    description NVARCHAR(255),                   -- Mô tả (nếu có)
    del_flag INT DEFAULT 1
);
----------------------loại thuốc
DROP TABLE if exists dbo.MedicineTypes;
DROP SEQUENCE IF EXISTS SEQ_MedicineTypes;
CREATE SEQUENCE SEQ_MedicineTypes AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE MedicineTypes (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_MedicineTypes] NOT NULL,
    code VARCHAR(50) NULL,              -- Mã loại thuốc
    name NVARCHAR(255) NULL,             -- Tên loại thuốc
    description NVARCHAR(255),                   -- Mô tả (nếu có)
    del_flag INT DEFAULT 1
);
----------------------đơn vị tính thuốc - vỉ, hộp, viên
DROP TABLE if exists dbo.MedicineUnits;
DROP SEQUENCE IF EXISTS SEQ_MedicineUnits;
CREATE SEQUENCE SEQ_MedicineUnits AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE MedicineUnits (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_MedicineUnits] NOT NULL,
    code VARCHAR(50) NULL,              -- mã đơn vị
    name NVARCHAR(255) NULL,             -- Tên đơn vị
    description NVARCHAR(255),                   -- Mô tả (nếu có)
    del_flag INT DEFAULT 1
);
---------bảng nhà cung cấp
DROP TABLE if exists dbo.Suppliers;
DROP SEQUENCE IF EXISTS SEQ_Suppliers;
CREATE SEQUENCE SEQ_Suppliers AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Suppliers (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Suppliers] NOT NULL,
    Full_Name NVARCHAR(100),
    Phone NVARCHAR(20),
    Email NVARCHAR(100),
    Address NVARCHAR(255),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
---------bảng chi nhánh
DROP TABLE if exists dbo.Branches;
DROP SEQUENCE IF EXISTS SEQ_Branches;
CREATE SEQUENCE SEQ_Branches AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Branches (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Branches] NOT NULL,
    Branches_Code NVARCHAR(100),
    Branches_Name NVARCHAR(100),
    Address NVARCHAR(255),
    Province_code VARCHAR(50),
    Province NVARCHAR(255),
    District_code  VARCHAR(50),
    District  NVARCHAR(255),
    Ward_code VARCHAR(50),
    Ward NVARCHAR(255),
    Phone VARCHAR(20),
    Manager NVARCHAR(30),
    Email NVARCHAR(100),
    Is_Active INT,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
---------bảng kho
DROP TABLE if exists dbo.Warehouses;
DROP SEQUENCE IF EXISTS SEQ_Warehouses;
CREATE SEQUENCE SEQ_Warehouses AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Warehouses (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Warehouses] NOT NULL,
    Warehouse_code VARCHAR(20),
    Warehouse_Name NVARCHAR(100),
    warehouse_type VARCHAR(20), --Loại kho (central, branch, drugstore,...)
    Branches_Code NVARCHAR(100),-- địa chỉ
    Manager_name NVARCHAR(50),  --Người phụ trách
    phone VARCHAR(20),
    Is_Active INT,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

------------Tồn kho------------
DROP TABLE if exists dbo.Stock;
DROP SEQUENCE IF EXISTS SEQ_Stock;
CREATE SEQUENCE SEQ_Stock AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Stock (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Stock] NOT NULL,
    medicine_id BIGINT NOT NULL,
    batch_no VARCHAR(20) NOT NULL,
    expiry_date DATE,
     quantity INT NOT NULL CHECK (quantity >= 0),   -- Số lượng tồn
    unit_price DECIMAL(20, 2) NOT NULL CHECK (unit_price >= 0), -- Giá nhập
    
    locked_quantity INT DEFAULT 0 CHECK (locked_quantity >= 0),  -- Số lượng bị giữ
    used_quantity INT DEFAULT 0 CHECK (used_quantity >= 0),      -- Số lượng đã dùng
    warehouse_id BIGINT, -- luôn là kho cửa hàng
    CHECK (expiry_date >= CAST(GETDATE() AS DATE)),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
---------đơn nhập hàng 
DROP TABLE if exists dbo.PurchaseOrders;
DROP SEQUENCE IF EXISTS SEQ_PurchaseOrders;
CREATE SEQUENCE SEQ_PurchaseOrders AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE PurchaseOrders (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_PurchaseOrders] NOT NULL,
    po_code VARCHAR(50) UNIQUE NOT NULL,         -- Mã đơn hàng
    supplier_id BIGINT NOT NULL,                 -- Nhà cung cấp
    expected_delivery_date DATE,                 -- ngày dự kiến giao
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT'  -- DRAFT, APPROVED, SENT, RECEIVED, CANCELLED
        CHECK (status IN ('DRAFT', 'APPROVED', 'SENT', 'RECEIVED', 'CANCELLED')),
    created_from VARCHAR(20) NOT NULL DEFAULT 'ADMIN'
        CHECK (created_from IN ('ADMIN', 'USER', 'AUTO_LOW_STOCK')),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------chi tiết nhập hàng
DROP TABLE if exists dbo.PurchaseOrderDetails;
DROP SEQUENCE IF EXISTS SEQ_PurchaseOrderDetails;
CREATE SEQUENCE SEQ_PurchaseOrderDetails AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE PurchaseOrderDetails (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_PurchaseOrderDetails] NOT NULL,
    purchase_order_id decimal(20,0),
    po_request_id decimal(20,0),
    po_request_group varchar(100),
    medicine_id decimal(20,0),
    quantity INT,
    unit_price decimal(20,0),
    batch_no varchar(20),
    expiry_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT'  -- DRAFT, APPROVED, SENT, RECEIVED, CANCELLED
        CHECK (status IN ('DRAFT', 'APPROVED', 'SENT', 'RECEIVED', 'CANCELLED')),
    description NVARCHAR(255),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
----------bảng trung gian gửi yêu cầu đặt hàng-----------------
--✅ Trạng thái LINKED: đã được tổng hợp vào 1 đơn hàng
--✅ Trạng thái APPROVED: có thể dùng cho luồng duyệt nếu cần
DROP TABLE if exists dbo.PurchaseOrderRequest;
DROP SEQUENCE IF EXISTS SEQ_PurchaseOrderRequest;
CREATE SEQUENCE SEQ_PurchaseOrderRequest AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE PurchaseOrderRequest (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_PurchaseOrderRequest] NOT NULL,
    user_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    request_group VARCHAR(50),
    quantity INT NOT NULL CHECK (quantity > 0),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' -- PENDING, APPROVED, LINKED, REJECTED
        CHECK (status IN ('PENDING', 'APPROVED', 'LINKED', 'REJECTED')),
    linked_po_id BIGINT,
    reject_reason nvarchar(255),
    Request_Flag INT DEFAULT 0,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

------------------Phục vụ hóa đơn thuốc trực tiếp---------------------
DROP TABLE if exists dbo.WalkInPurchaseInvoice;
DROP SEQUENCE IF EXISTS SEQ_WalkInPurchaseInvoice;
CREATE SEQUENCE SEQ_WalkInPurchaseInvoice AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE WalkInPurchaseInvoice (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_WalkInPurchaseInvoice] NOT NULL,
    invoice_code VARCHAR(20) NOT NULL UNIQUE, -- Mã hóa đơn
    customer_name NVARCHAR(100) NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    age INT CHECK (age >= 0),
    phone VARCHAR(20),
    card_number VARCHAR(20), -- CMND/CCCD
    address NVARCHAR(255),
    Diagnosis NVARCHAR(255),
    total_amount DECIMAL(18, 2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PAID' CHECK (payment_status IN ('PAID', 'UNPAID')),
    notes NVARCHAR(255),
    CREATED_DATE datetime2 DEFAULT GETDATE(),
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

----------------------------CHi tiết hóa đơn thuốc trực tiếp----------------------------
DROP TABLE if exists dbo.WalkInInvoiceItem;
DROP SEQUENCE IF EXISTS SEQ_WalkInInvoiceItem;
CREATE SEQUENCE SEQ_WalkInInvoiceItem AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE WalkInInvoiceItem (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_WalkInInvoiceItem] NOT NULL,
    invoice_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL,
    total_price AS (quantity * unit_price) PERSISTED
);

-----------------------------------bảng lưu trữ file---------------------------
DROP TABLE if exists dbo.ReportBusiness;
DROP SEQUENCE IF EXISTS SEQ_ReportBusiness;
CREATE SEQUENCE SEQ_ReportBusiness AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE ReportBusiness (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_ReportBusiness] NOT NULL,
    file_name NVARCHAR(255) NOT NULL,         -- Tên file: prescription_123.pdf
    file_type NVARCHAR(255) NULL,         -- Đường dẫn lưu vật lý
    file_path NVARCHAR(500) NULL,         -- Đường dẫn lưu vật lý
    file_url NVARCHAR(500) NULL,          -- Đường dẫn truy cập từ trình duyệt (/files/...)
    file_size BIGINT NULL,                         -- size
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    CREATED_DATE DATETIME2 DEFAULT GETDATE(), -- Thời điểm tạo file
    related_invoice_id BIGINT,                -- (optional) nếu gắn với đơn thuốc
    description NVARCHAR(255)                 -- Ghi chú / loại file
);

----------------------ghi log---------------------------------
CREATE TABLE request_audit_log (
    id BIGINT IDENTITY PRIMARY KEY,
    request_id BIGINT NOT NULL,
    action_type VARCHAR(20) NOT NULL,         -- CREATE / APPROVE / REJECT / VIEW / UPDATE
    entity_type NVARCHAR(50);
    action_by VARCHAR(50) NOT NULL,           -- username hoặc userId
    action_time DATE DEFAULT GETDATE(),
    remarks TEXT                              -- Ghi chú nếu cần (ví dụ: lý do từ chối)
);

-----------•    Tạo hóa đơn bán lẻ/bán sỉ
DROP TABLE if exists dbo.Bill;
DROP SEQUENCE IF EXISTS SEQ_Bill;
CREATE SEQUENCE SEQ_Bill AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Bill (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Bill] NOT NULL,
    bill_code VARCHAR(50) NOT NULL UNIQUE,        -- Mã tự sinh: HD00001,...
    bill_type VARCHAR(20) NOT NULL,               -- MANUAL | AUTO
    order_id DECIMAL(20,0) NULL,                  -- Gắn với đơn đặt nếu là AUTO
    
    customer_id DECIMAL(20,0),                    -- Người mua
    employee_id DECIMAL(20,0),                    -- Người tạo hóa đơn
    total_amount DECIMAL(18, 2),
    status VARCHAR(20),                           -- DRAFT | PAID | CANCELLED | REFUNDED
    
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------•  chi tiết  Tạo hóa đơn bán lẻ/bán sỉ
DROP TABLE if exists dbo.BillDetail;
DROP SEQUENCE IF EXISTS SEQ_BillDetail;
CREATE SEQUENCE SEQ_BillDetail AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE BillDetail (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_BillDetail] NOT NULL,
    bill_id BIGINT,
    medicine_id decimal(20,0),
    quantity INT,
    unit_price decimal(18, 2),
    total_price DECIMAL(18, 2),
    discount decimal(18, 2),
    batch_no varchar(20),
    expiry_date DATE NULL,                         -- Ngày hết hạn (nếu lấy từ Stock)
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------•  chi tiết  Tạo hóa đơn bán lẻ/bán sỉ history
DROP TABLE if exists dbo.BillHistory;
DROP SEQUENCE IF EXISTS SEQ_BillHistory;
CREATE SEQUENCE SEQ_BillHistory AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE BillHistory (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_BillHistory] NOT NULL,
    bill_id decimal(20,0),
    po_id decimal(20,0),
    note VARCHAR(255),                    -- Ghi chú lý do tạo, thay đổi
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

-- Bảng thanh toán
DROP TABLE if exists dbo.BillPayment;
DROP SEQUENCE IF EXISTS SEQ_BillPayment;
CREATE SEQUENCE SEQ_BillPayment AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE BillPayment (
    id BIGINT DEFAULT NEXT VALUE FOR [SEQ_BillPayment] NOT NULL,
    bill_id BIGINT NOT NULL,
    payment_method VARCHAR(50), -- CASH, CARD, TRANSFER
    amount DECIMAL(18, 2),
    payment_date DATETIME,
);
-----------• Tồn kho
DROP TABLE if exists dbo.Inventory;
DROP SEQUENCE IF EXISTS SEQ_Inventory;
CREATE SEQUENCE SEQ_Inventory AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Inventory (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Inventory] NOT NULL,
    medicine_id decimal(20,0),
    warehouse_id decimal(20,0),
    batch_no varchar(20),
    expiry_date DATE,
    quantity INT,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------• Payment
DROP TABLE if exists dbo.Revenues;
DROP SEQUENCE IF EXISTS SEQ_Revenues;
CREATE SEQUENCE SEQ_Revenues AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Revenues (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Revenues] NOT NULL,
    medicine_id decimal(20,0),
    total_sales decimal(20,0),
    total_profit decimal(20,0),
    date DATE,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

/****** Object:  Table [dbo].[Province]    Script Date: 5/31/2023 1:38:21 PM ******/

DROP TABLE IF EXISTS [dbo].[Province];
CREATE TABLE [dbo].[Province](
  ma_tp varchar(5) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  type nvarchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  slug varchar(30) DEFAULT NULL,
);
DROP TABLE IF EXISTS [dbo].[District]
CREATE TABLE [dbo].[District](
    ma_qh varchar(5) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    type nvarchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    ma_tp varchar(30) DEFAULT NULL,
);
/****** Object:  Table [dbo].[Ward]    Script Date: 5/31/2023 1:38:21 PM ******/

DROP TABLE IF EXISTS [dbo].[Ward];
CREATE TABLE [dbo].[Ward](
    xa_id varchar(5) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    type nvarchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    ma_qh varchar(30) DEFAULT NULL,
);

