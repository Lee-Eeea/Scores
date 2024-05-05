package com.javalab.school.execution;

import java.sql.*;
import java.util.Scanner;

public class GradeInsert {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            registerGrade(conn, scanner);

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

    private static void registerGrade(Connection conn, Scanner scanner) {
        System.out.println("[새 성적 입력]");
        System.out.print("학생 ID: ");
        String studentId = scanner.nextLine();
        System.out.print("과목 코드: ");
        String subjectCode = scanner.nextLine();
        System.out.print("점수: ");
        int score = scanner.nextInt();

        String sql = "INSERT INTO takes (student_id, subject, score) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, subjectCode);
            pstmt.setInt(3, score);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("성적이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("성적 등록에 실패하였습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}