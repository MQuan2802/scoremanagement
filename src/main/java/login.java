import javax.servlet.http.HttpServlet;
import java.io.*;

import javax.lang.model.util.ElementScanner14;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class login extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, java.io.IOException {

        HttpSession session = req.getSession(true);
        String sessionid = session.getId();
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/authentication";
        final String USER = "authenticationuser";
        final String PASS = "436553";
        Connection conn = null;
        Statement stmt = null;
        String username = req.getParameter("name");
        String password = req.getParameter("password");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        ResultSet rs = null;
        String sql = "SELECT username , password from teacher WHERE username LIKE" + "'" + username + "'";
        String sql2 = "SELECT username , password from student WHERE username LIKE" + "'" + username + "'";
        String sql3 = "SELECT username,typeid from sessionid WHERE sessionid='" + sessionid + "'";
        String sql4 = "INSERT INTO sessionid VALUES ('";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(session.isNew());

            if (!session.isNew()) {
                rs = stmt.executeQuery(sql3);

                if (rs.next()) {
                    username = rs.getString("username");
                    String typeid = rs.getString("typeid");
                    req.setAttribute("name", username);
                    System.out.println(username + typeid);
                    RequestDispatcher rd = null;
                    if (typeid.equals("student")) {
                        rd = req.getRequestDispatcher("/studentinformation");
                    }
                    if (typeid.equals("teacher")) {

                        rd = req.getRequestDispatcher("/teacherpage");
                    }
                    rd.forward(req, resp);
                }
            }

            rs = stmt.executeQuery(sql);
            if (rs.next() && rs.getString("password").equals(password)) {
                sql4 = sql4 + username + "','" + sessionid + "','teacher')";
                stmt.executeUpdate(sql4);

                RequestDispatcher rd = req.getRequestDispatcher("/teacherpage");

                rd.forward(req, resp);
            } else if ((rs = stmt.executeQuery(sql2)).next() && rs.getString("password").equals(password)) {
                sql4 = sql4 + username + "','" + sessionid + "','student')";
                stmt.executeUpdate(sql4);
                RequestDispatcher rd = req.getRequestDispatcher("/studentinformation");
                rd.forward(req, resp);

            } else {
                out.println("wrong username or password");
                RequestDispatcher rd = req.getRequestDispatcher("login.html");
                rd.include(req, resp);
            }
        } catch (SQLException SE) {
            SE.printStackTrace();
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
