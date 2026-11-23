import { useEffect, useState } from 'react';
import axiosClient from './api/axiosClient';

interface Student {
  studentId: string;
  name: string;
  major: string;
  className: string;
  admissionYear: number;
}

function App() {
  const [students, setStudents] = useState<Student[]>([]);
  const [error, setError] = useState<string>('');

  useEffect(() => {
    axiosClient.get<Student[]>('/students') // Đổi lại đúng endpoint trong StudentController của bạn
      .then((res) => setStudents(res.data))
      .catch((err) => setError('Không gọi được API: ' + err.message));
  }, []);

  return (
    <div style={{ padding: '24px', fontFamily: 'sans-serif' }}>
      <h1>SSS - Hệ thống Hỗ trợ Sinh viên</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <table border={1} cellPadding={8} style={{ borderCollapse: 'collapse', width: '100%' }}>
        <thead>
          <tr>
            <th>MSSV</th>
            <th>Họ và tên</th>
            <th>Ngành</th>
            <th>Lớp</th>
            <th>Khóa</th>
          </tr>
        </thead>
        <tbody>
          {students.map((s) => (
            <tr key={s.studentId}>
              <td>{s.studentId}</td>
              <td>{s.name}</td>
              <td>{s.major}</td>
              <td>{s.className}</td>
              <td>{s.admissionYear}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;