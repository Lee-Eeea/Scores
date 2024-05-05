package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class GradeUpdate {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");

            System.out.println("DB 접속 성공");

            updateGrade(conn, scanner);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void updateGrade(Connection conn, Scanner scanner) {
        System.out.println("수정할 학생의 ID를 입력하세요: ");
        String studentId = scanner.nextLine();

        System.out.println("수정할 과목을 입력하세요: ");
        String subject = scanner.nextLine();

        System.out.println("새로운 성적을 입력하세요: ");
        String newScore = scanner.nextLine();

        PreparedStatement pstmt = null;

        try {
            // SQL 쿼리문 작성
            String sql = "UPDATE grade SET score = ? WHERE student_id = ? AND subject = ?";
            pstmt = conn.prepareStatement(sql);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, newScore);
            pstmt.setString(2, studentId);
            pstmt.setString(3, subject);

            // SQL 실행
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("성적이 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 학생의 성적을 찾을 수 없습니다.");
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