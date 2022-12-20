import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServlet;
import java.io.*;

import javax.lang.model.util.ElementScanner14;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class createclass extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, java.io.IOException {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/authentication";
        final String USER = "authenticationuser";
        final String PASS = "436553";
        Connection conn = null;
        Statement stmt = null;
        String username = req.getParameter("name");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String type = req.getParameter("create");
        ResultSet rs = null;

        // goto ahihi;
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        if (fullname.equals("") || username.equals("") || password.equals("") || type.equals("")) {
            out.println("full name and username and password and type cannot be empty");
            RequestDispatcher rd = req.getRequestDispatcher("create.html");
            rd.forward(req, resp);
        }

        String sql = "INSERT INTO student  VALUES ('" + username + "','" + password + "')";
        String sql2 = "INSERT INTO student_information (username,name) VALUES ('" + username + "','" + fullname + "')";
        String sql3 = "SELECT * FROM teacher where username like '" + username + "'";
        String sql4 = "SELECT * FROM student where username like '" + username + "'";
        String sql5 = "INSERT INTO teacher VALUES ('" + username + "','" + password + "','" + fullname + "')";
        String sql6 = "update student  SET email = CONCAT(username,\"@ahihi.com\")";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            if (type.equals("student")) {
                if (stmt.executeQuery(sql3).next() || stmt.executeQuery(sql4).next()) {
                    out.println("username already exists");
                    RequestDispatcher rd = req.getRequestDispatcher("/create");
                    rd.include(req, resp);
                } else {
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql2);
                    stmt.executeUpdate(sql6);
                    out.println("successfully create student account!!!");
                    // ahihi:
                    RequestDispatcher rd = req.getRequestDispatcher("login.html");
                    rd.include(req, resp);
                    out.println("ahihi");
                }
            }
            if (type.equals("teacher")) {
                if (stmt.executeQuery(sql3).next() || stmt.executeQuery(sql4).next()) {
                    out.println("username already exists");
                    RequestDispatcher rd = req.getRequestDispatcher("/create");
                    rd.include(req, resp);
                } else {

                    stmt.executeUpdate(sql5);
                    out.println("successfully create teacher account!!!");
                    // ahihi:
                    RequestDispatcher rd = req.getRequestDispatcher("login.html");
                    rd.include(req, resp);
                    out.println("ahihi");
                }
            }

        } catch (SQLException SE) {
            SE.printStackTrace();

            out.println("ahihi");

        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException SE2) {

                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException SE3) {
                    SE3.printStackTrace();
                }
        }

    }

}