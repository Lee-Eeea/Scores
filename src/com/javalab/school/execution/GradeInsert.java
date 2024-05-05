package com.javalab.school.execution;

import java.sql.*;
import java.util.Scanner;

public class GradeInsert {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            insertGrades(conn, scanner);

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
    }

    private static void insertGrades(Connection conn, Scanner scanner) {
        System.out.println("[학생 성적 등록]");

        // Gathering information
        System.out.print("학생 ID: ");
        String studentId = scanner.nextLine();
        System.out.print("과목: ");
        String subject = scanner.nextLine();
        System.out.print("성적: ");
        String score = scanner.nextLine();

        // PreparedStatement for inserting grades
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO grades (student_id, subject, score) " +
                    "VALUES (?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, subject);
            pstmt.setString(3, score);

            pstmt.executeUpdate();
            System.out.println("성적이 성공적으로 등록되었습니다.");
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