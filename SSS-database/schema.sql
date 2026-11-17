-- Chỉ định sử dụng đúng cơ sở dữ liệu
USE SSS_DATABASE;
GO

-- Kiểm tra nếu bảng students chưa tồn tại thì mới tạo mới
IF OBJECT_ID('dbo.students', 'U') IS NULL
BEGIN
    CREATE TABLE students (
        student_id VARCHAR(10) NOT NULL PRIMARY KEY,
        full_name NVARCHAR(255) NOT NULL,
        major NVARCHAR(255),
        class_name NVARCHAR(50),
        department NVARCHAR(255),
        cpa FLOAT,
        national_id VARCHAR(12) UNIQUE
    );
    PRINT 'Bang students da duoc tao thanh cong.';
END
ELSE
BEGIN
    PRINT 'Bang students da ton tai, bo qua buoc tao bang.';
END
GO

INSERT INTO students (student_id, full_name, major, class_name, department, cpa, national_id)
VALUES 
    ('20221234', N'Nguyễn Văn A', 'Computer Science', 'IT1-K67', 'School of ICT', 3.8, '001204123456'),
    ('20225678', N'Trần Văn B', 'Computer Engineering', 'IT2-K67', 'School of ICT', 3.6, '002204654321');
GO