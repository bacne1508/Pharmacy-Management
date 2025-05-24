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
--------------Bảng Log hoạt động / Audit 

DROP TABLE if exists dbo.AuditLogs;
DROP SEQUENCE IF EXISTS SEQ_AuditLogs;
CREATE SEQUENCE SEQ_AuditLogs AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE AuditLogs (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_AuditLogs] NOT NULL,
    User_Id INT ,
    Action NVARCHAR(255),
    Timestamp DATETIME DEFAULT GETDATE(),
    Details NVARCHAR(MAX)
);

--------------Bảng constant

-------------Bảng thông tin thuốc---------------------
DROP TABLE if exists dbo.Medicine;
DROP SEQUENCE IF EXISTS SEQ_Medicine;
CREATE SEQUENCE SEQ_Medicine AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Medicine (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Medicine] NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,              -- Mã thuốc
    name VARCHAR(255) NOT NULL,                    -- Tên thuốc
    description TEXT,                              -- Mô tả (nếu có)
    Medicine_Groups_code VARCHAR(50) NULL,         -- nhóm thuốc
    Medicine_Units_code VARCHAR(50),               -- Đơn vị tính (ví dụ: viên, lọ, hộp)
    medicine_Types_code VARCHAR(100),              -- Dạng bào chế (viên nén, dung dịch,...)
    ingredient TEXT,                               -- Thành phần hoạt chất
    strength VARCHAR(100),                         -- Hàm lượng
    manufacturer VARCHAR(255),                     -- Nhà sản xuất
    origin_country VARCHAR(100),                   -- Nước sản xuất
    purchase_price DECIMAL(15, 2),                 -- Giá mua
    sale_price DECIMAL(15, 2),                     -- Giá bán
    quantity INT,                                  -- Số lượng tồn kho
    Date_of_manufacture DATE,                      -- ngày sản xuất
    Product_expiry_date DATE,                      -- hạn sử dụng
    is_active int DEFAULT 1,                       -- Còn hoạt động hay không
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
    name VARCHAR(255) NULL,             -- Tên nhóm thuốc
    description TEXT,                   -- Mô tả (nếu có)
);
----------------------loại thuốc
DROP TABLE if exists dbo.MedicineTypes;
DROP SEQUENCE IF EXISTS SEQ_MedicineTypes;
CREATE SEQUENCE SEQ_MedicineTypes AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE MedicineTypes (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_MedicineTypes] NOT NULL,
    code VARCHAR(50) NULL,              -- Mã loại thuốc
    name VARCHAR(255) NULL,             -- Tên loại thuốc
    description TEXT,                   -- Mô tả (nếu có)
);
----------------------đơn vị tính thuốc - vỉ, hộp, viên
DROP TABLE if exists dbo.MedicineUnits;
DROP SEQUENCE IF EXISTS SEQ_MedicineUnits;
CREATE SEQUENCE SEQ_MedicineUnits AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE MedicineUnits (
    id decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_MedicineUnits] NOT NULL,
    code VARCHAR(50) NULL,              -- mã đơn vị
    name VARCHAR(255) NULL,             -- Tên đơn vị
    description TEXT,                   -- Mô tả (nếu có)
);
---------bảng nhà cung cấp
DROP TABLE if exists dbo.Suppliers;
DROP SEQUENCE IF EXISTS SEQ_Suppliers;
CREATE SEQUENCE SEQ_Suppliers AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Suppliers (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Suppliers] NOT NULL,
    First_Name NVARCHAR(100),
    Last_Name NVARCHAR(100),
    Date_Of_Birth DATE,
    Gender NVARCHAR(10),
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
    Branches_Name NVARCHAR(100),
    Address NVARCHAR(255),
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
    Warehouses_Name NVARCHAR(100),
    Branches_id decimal(20,0),
    Manager_id decimal(20,0),
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
    supplier_id decimal(20,0),
    employee_id decimal(20,0),
    warehouse_id decimal(20,0),
    status INT,
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
    medicine_id decimal(20,0),
    quantity INT,
    unit_price decimal(20,0),
    batch_no varchar(20),
    expiry_date DATE,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------•    Tạo hóa đơn bán lẻ/bán sỉ
DROP TABLE if exists dbo.SalesOrders;
DROP SEQUENCE IF EXISTS SEQ_SalesOrders;
CREATE SEQUENCE SEQ_SalesOrders AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE SalesOrders (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_SalesOrders] NOT NULL,
    customer_id decimal(20,0),
    employee_id decimal(20,0),
    warehouse_id decimal(20,0),
    status INT,
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------•  chi tiết  Tạo hóa đơn bán lẻ/bán sỉ
DROP TABLE if exists dbo.SalesOrderDetails;
DROP SEQUENCE IF EXISTS SEQ_SalesOrderDetails;
CREATE SEQUENCE SEQ_SalesOrderDetails AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE SalesOrderDetails (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_SalesOrderDetails] NOT NULL,
    sales_order_id decimal(20,0),
    medicine_id decimal(20,0),
    quantity INT,
    unit_price decimal(20,0),
    discount decimal(20,0),
    batch_no varchar(20),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);
-----------•  chi tiết  Tạo hóa đơn bán lẻ/bán sỉ
DROP TABLE if exists dbo.SalesPurchasesHistory;
DROP SEQUENCE IF EXISTS SEQ_SalesPurchasesHistory;
CREATE SEQUENCE SEQ_SalesPurchasesHistory AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE SalesPurchasesHistory (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_SalesPurchasesHistory] NOT NULL,
    sales_order_id decimal(20,0),
    purchase_order_id decimal(20,0),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
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
