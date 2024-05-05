package com.javalab.school.execution;

import java.sql.*;
import java.util.Scanner;

public class GradeDelete {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            deleteGrade(conn, scanner);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteGrade(Connection conn, Scanner scanner) {
        PreparedStatement pstmt = null;
        try {
            System.out.println("삭제할 성적의 학생 ID를 입력하세요: ");
            String studentId = scanner.nextLine();
            System.out.println("삭제할 성적의 과목 코드를 입력하세요: ");
            String subjectCode = scanner.nextLine();

            String sql = "DELETE FROM takes WHERE student_id = ? AND subject = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, subjectCode);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("성적이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 학생의 해당 과목 성적을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}