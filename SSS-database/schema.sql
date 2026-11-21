USE SSS_DATABASE;
GO

-- 1. Tạo bảng students (Golden Record)
IF OBJECT_ID('dbo.students', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.students (
        student_id     VARCHAR(10)   NOT NULL PRIMARY KEY,
        full_name      NVARCHAR(255) NOT NULL,
        major          NVARCHAR(255) NULL,
        class_name     NVARCHAR(50)  NULL,
        department     NVARCHAR(255) NULL,
        admission_year INT           NULL,
        last_synced_at DATETIME2     NULL,
        cpa            FLOAT         NULL,
        national_id    VARCHAR(12)   NULL UNIQUE
    );
    PRINT 'Bang students da duoc tao thanh cong.';
END
ELSE
BEGIN
    -- Bổ sung 2 cột mới nếu bảng đã tồn tại từ bản cũ
    IF COL_LENGTH('dbo.students', 'admission_year') IS NULL
        ALTER TABLE dbo.students ADD admission_year INT NULL;

    IF COL_LENGTH('dbo.students', 'last_synced_at') IS NULL
        ALTER TABLE dbo.students ADD last_synced_at DATETIME2 NULL;

    PRINT 'Bang students da ton tai, da kiem tra bo sung cot.';
END
GO

-- 2. Tạo bảng student_xref (Identity Mapping Registry - ADR 01)
IF OBJECT_ID('dbo.student_xref', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.student_xref (
        id            BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        source_system VARCHAR(20)          NOT NULL,
        source_id     VARCHAR(64)          NOT NULL,
        student_id    VARCHAR(10)          NULL,
        match_rule    VARCHAR(20)          NULL,
        confidence    FLOAT                NULL,
        status        VARCHAR(20)          NOT NULL,
        note          NVARCHAR(255)        NULL,
        updated_at    DATETIME2            NULL,

        CONSTRAINT UQ_student_xref_source UNIQUE (source_system, source_id),
        CONSTRAINT FK_student_xref_student FOREIGN KEY (student_id)
            REFERENCES dbo.students(student_id)
    );

    -- Index hỗ trợ tra cứu nhanh theo MSSV và trạng thái đối soát
    CREATE INDEX IX_student_xref_student_id ON dbo.student_xref(student_id);
    CREATE INDEX IX_student_xref_status     ON dbo.student_xref(status);

    PRINT 'Bang student_xref da duoc tao thanh cong.';
END
ELSE
BEGIN
    PRINT 'Bang student_xref da ton tai, bo qua buoc tao bang.';
END
GO

-- 3. Nạp dữ liệu mẫu (chỉ chèn nếu chưa tồn tại)
IF NOT EXISTS (SELECT 1 FROM dbo.students WHERE student_id = '20221234')
BEGIN
    INSERT INTO dbo.students (student_id, full_name, major, class_name, department, admission_year, last_synced_at, cpa, national_id)
    VALUES ('20221234', N'Nguyễn Văn A', N'Computer Science', N'IT1-K67', N'School of ICT', 2022, SYSDATETIME(), 3.8, '001204123456');
END

IF NOT EXISTS (SELECT 1 FROM dbo.students WHERE student_id = '20225678')
BEGIN
    INSERT INTO dbo.students (student_id, full_name, major, class_name, department, admission_year, last_synced_at, cpa, national_id)
    VALUES ('20225678', N'Trần Văn B', N'Computer Engineering', N'IT2-K67', N'School of ICT', 2022, SYSDATETIME(), 3.6, '002204654321');
END
GO