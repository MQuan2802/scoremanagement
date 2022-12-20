import javax.servlet.http.HttpServlet;
import java.io.*;
import javax.lang.model.util.ElementScanner14;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class studentinformation extends HttpServlet {

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
        ResultSet rs = null;
        String studentname = req.getParameter("name");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String sql = "SELECT ID,name,math,physics,chemistry,English from student_information where username Like '"
                + studentname + "'";
        String sql3 = "SELECT username,typeid from sessionid WHERE sessionid='" + sessionid + "'";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(sessionid);
            if (!session.isNew()) {
                rs = stmt.executeQuery(sql3);
                boolean hasnext = rs.next();
                String typeid = null;
                if (hasnext)
                    typeid = rs.getString("typeid");
                if (hasnext && typeid.equals("student")) {
                    sql = "SELECT ID,name,math,physics,chemistry,English from student_information where username Like '"
                            + rs.getString("username") + "'";

                } else {
                    out.print("require login with student account first!!!!");
                    RequestDispatcher rd = req.getRequestDispatcher("/loginhtml");
                    rd.include(req, resp);
                    return;
                }
            } else {
                out.print("require login with student account first!!!!");
                RequestDispatcher rd = req.getRequestDispatcher("/loginhtml");
                rd.include(req, resp);
                return;
            }
            rs = null;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("name");
                String math = rs.getString("math");
                String physics = rs.getString("physics");
                String chemistry = rs.getString("chemistry");
                String English = rs.getString("English");
                String docType = "<!DOCTYPE html>";
                out.println(docType + "\n<html>\n" +
                        "<style>\n" +
                        "table,th,td { border: 1px solid black;}\n" +
                        "</style>\n" +
                        "<body>\n" +
                        "<table style=\"width:100%\">\n" +
                        "<tr>\n" +
                        "<th>ID</th>\n" +
                        "<th>name</th>\n" +
                        "<th>math</th>\n" +
                        "<th>physics</th>\n" +
                        "<th>chemistry</th>\n" +
                        "<th>English</th>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td>" + id + "</td>\n" +
                        "<td>" + name + "</td>\n" +
                        "<td>" + math + "</td>\n" +
                        "<td>" + physics + "</td>\n" +
                        "<td>" + chemistry + "</td>\n" +
                        "<td>" + English + "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "</body>\n</html>");
            }
        } catch (SQLException SE) {
            SE.printStackTrace();
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        }

    }

}
