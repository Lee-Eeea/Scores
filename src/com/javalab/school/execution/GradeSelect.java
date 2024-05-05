package com.javalab.school.execution;

import java.sql.*;

public class GradeSelect {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB  접속 성공");

            displayTakes(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } //end of main

    private static void displayTakes (Connection conn) {
        System.out.println("등록된 성적 목록");
        System.out.println("학번\t과목\t성적");
        String sql = "SELECT t.* FROM TAKES t ORDER BY t.student_id";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String subject = rs.getString("subject");
                String score = rs.getString("score");
                System.out.println(studentId + "\t" + subject + "\t" + score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------");
    }
}