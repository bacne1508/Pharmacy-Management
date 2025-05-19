DROP TABLE if exists dbo.Roles;
CREATE TABLE Roles (
    Role_Id INT PRIMARY KEY IDENTITY,
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
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
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
---------bảng nhân viên y tế và thông tin y tế
DROP TABLE if exists dbo.Patients;
DROP SEQUENCE IF EXISTS SEQ_Patients;
CREATE SEQUENCE SEQ_Patients AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Patients (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Patients] NOT NULL,
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

DROP TABLE if exists dbo.MedicalRecords;
DROP SEQUENCE IF EXISTS SEQ_MedicalRecords;
CREATE SEQUENCE SEQ_MedicalRecords AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE MedicalRecords (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_MedicalRecords] NOT NULL, --bảng thông tin y tế
    Patients_Id INT,
    Notes NVARCHAR(MAX),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

--------------  Bảng Lịch hẹn & Giao tiếp giữa bác sĩ và lễ tân
DROP TABLE if exists dbo.Appointments;
DROP SEQUENCE IF EXISTS SEQ_Appointments;
CREATE SEQUENCE SEQ_Appointments AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Appointments (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Appointments] NOT NULL,
    Patients_Id INT,
    User_Id INT, --DOCTOR
    Scheduled_Date DATETIME,
    Status_code INT,
    Status_Name NVARCHAR(50), -- Scheduled, Cancelled, Completed
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

---------------Bảng Kết quả Xét nghiệm (Lab)
DROP TABLE if exists dbo.LabTests;
DROP SEQUENCE IF EXISTS SEQ_LabTests;
CREATE SEQUENCE SEQ_LabTests AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE LabTests (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_LabTests] NOT NULL,
    Patients_Id INT,
    Prescribed_By INT , --ID DOCTOR
    Performed_By INT , --ID LAB_ASSISTANT
    Test_Type NVARCHAR(100), --Loại xét nghiệm: ví dụ "Blood Test", "X-ray", "MRI"...
    Result NVARCHAR(MAX), --Kết quả chi tiết của xét nghiệm. Có thể là văn bản dài hoặc JSON (nếu cần mở rộng).
    Result_Date DATETIME,
    Status_code INT,
    Status_Name NVARCHAR(50) -- Ordered, Completed
);
-------------------Bảng Đơn thuốc và Nhà thuốc
DROP TABLE if exists dbo.Prescriptions;
DROP SEQUENCE IF EXISTS SEQ_Prescriptions;
CREATE SEQUENCE SEQ_Prescriptions AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Prescriptions (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Prescriptions] NOT NULL,
    Patients_Id INT,
    Doctor_Id INT , --ID DOCTOR
    Notes NVARCHAR(MAX),
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

------bảng quản lý kho thuốc, danh sách thuốc hiện có, cập nhật tồn kho, thuốc còn hay hết khi kê đơn
DROP TABLE if exists dbo.DrugInventory;
DROP SEQUENCE IF EXISTS SEQ_DrugInventory;
CREATE SEQUENCE SEQ_DrugInventory AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE DrugInventory (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_DrugInventory] NOT NULL,
    Drug_Name NVARCHAR(100) UNIQUE,
    Quantity_In_Stock INT,
    Unit NVARCHAR(50),
    Last_Updated DATETIME DEFAULT GETDATE()
);

DROP TABLE if exists dbo.PrescriptionItems;
DROP SEQUENCE IF EXISTS SEQ_PrescriptionItems;
CREATE SEQUENCE SEQ_PrescriptionItems AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE PrescriptionItems (
    ID BIGINT PRIMARY KEY DEFAULT NEXT VALUE FOR [SEQ_PrescriptionItems],
    Prescription_Id INT NOT NULL , --id của Prescriptions
    DrugId INT NOT NULL,--id của DrugInventory
    Dosage NVARCHAR(100),         -- Liều dùng, ví dụ: "500mg"
    Quantity INT NOT NULL,        -- Số lượng: ví dụ 10 viên
    Instructions NVARCHAR(255)    -- Hướng dẫn sử dụng: "Uống sau ăn 2 lần/ngày"
);
---------------Bảng Thanh toán và Thu ngân
DROP TABLE if exists dbo.Bills;
DROP SEQUENCE IF EXISTS SEQ_Bills;
CREATE SEQUENCE SEQ_Bills AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE Bills (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_Bills] NOT NULL,
    Patients_Id INT,
    Total_Amount DECIMAL(18, 2),
    Paid_Amount DECIMAL(18, 2),
    Status_code INT,
    Status_Name NVARCHAR(50), -- Paid, Pending, Refunded
    CREATED_DATE datetime2 NULL,
    UPDATED_DATE datetime2 NULL,
    DELETED_DATE datetime2 NULL,
    CREATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    UPDATED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    DELETED_BY varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);

DROP TABLE if exists dbo.BillItems;
DROP SEQUENCE IF EXISTS SEQ_BillItems;
CREATE SEQUENCE SEQ_BillItems AS bigint START WITH 1  INCREMENT BY 1;
CREATE TABLE BillItems (
    ID decimal(20,0) DEFAULT NEXT VALUE FOR [SEQ_BillItems] NOT NULL,
    Bill_Id INT, --id của Bills
    Description NVARCHAR(255),
    Amount DECIMAL(18, 2)
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


