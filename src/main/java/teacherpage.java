import javax.servlet.http.HttpServlet;
import java.io.*;
import java.util.Objects;
import javax.lang.model.util.ElementScanner14;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class teacherpage extends HttpServlet {
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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
        String ID = req.getParameter("ID");
        String math = req.getParameter("math");
        String physics = req.getParameter("physics");
        String chemistry = req.getParameter("chemistry");
        String English = req.getParameter("English");
        ResultSet rs = null;
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String sql = "select ID, name, math, physics, chemistry, English from student_information";
        String sqlmath = "UPDATE student_information SET math=" + math + " WHERE ID IN (" + ID + ")";
        String sqlphysics = "UPDATE student_information SET physics=" + physics + " WHERE ID IN (" + ID + ")";
        String sqlchemistry = "UPDATE student_information SET chemistry=" + chemistry + " WHERE ID IN (" + ID + ")";
        String sqlEnglish = "UPDATE student_information SET English=" + English + " WHERE ID IN (" + ID + ")";
        String sqllogin = "SELECT username,typeid from sessionid WHERE sessionid='" + sessionid + "'";

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            if (!session.isNew()) {
                rs = stmt.executeQuery(sqllogin);
                boolean hasnext = rs.next();
                String typeid = null;
                if (hasnext) {
                    typeid = rs.getString("typeid");
                }
                if (!hasnext || !typeid.equals("teacher")) {
                    out.println("wrong account!!!");
                    RequestDispatcher rd = req.getRequestDispatcher("/loginhtml");
                    rd.include(req, resp);
                    stmt.close();
                    return;
                }
            } else {
                out.print("require login with teacher account first!!!!");
                RequestDispatcher rd = req.getRequestDispatcher("/loginhtml");
                rd.include(req, resp);
                return;
            }

            if (isInteger(ID)
                    && (isInteger(math) || isInteger(physics) || isInteger(chemistry) && isInteger(English))) {
                String sqlgetstudentnameupdate = "SELECT * from student_information WHERE ID = " + ID;

                rs = stmt.executeQuery(sqlgetstudentnameupdate);

                if (rs.next()) {
                    String sqlgetstudentemail = "SELECT email from student WHERE username ='" + rs.getString("username")
                            + "'";
                    req.setAttribute("name", rs.getString("name"));
                    rs.close();
                    ResultSet rs2 = stmt.executeQuery(sqlgetstudentemail);
                    rs2.next();
                    req.setAttribute("mail", rs2.getString("email"));
                    rs2.close();
                    // if (!ID.equals("") && isInteger(ID)) {
                    if (isInteger(math)) {
                        stmt.executeUpdate(sqlmath);
                        req.setAttribute("math", math);
                    }

                    if (isInteger(physics)) {
                        stmt.executeUpdate(sqlphysics);
                        req.setAttribute("physics", physics);
                    }

                    if (isInteger(chemistry)) {
                        stmt.executeUpdate(sqlchemistry);
                        req.setAttribute("chemistry", chemistry);
                    }
                    if (isInteger(English)) {
                        stmt.executeUpdate(sqlEnglish);
                        req.setAttribute("English", English);
                    }

                    RequestDispatcher rdmail = req.getRequestDispatcher("/mail");
                    rdmail.include(req, resp);
                }
            }
            rs = stmt.executeQuery(sql);
            out.println("<!DOCTYPE html>\n" + "\n<html>\n" +
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
                    "</tr>\n");
            while (rs.next()) {
                out.println("<tr>\n" +
                        "<td>" + rs.getInt("ID") + "</td>\n" +
                        "<td>" + rs.getString("name") + "</td>\n" +
                        "<td>" + rs.getInt("math") + "</td>\n" +
                        "<td>" + rs.getInt("physics") + "</td>\n" +
                        "<td>" + rs.getInt("chemistry") + "</td>\n" +
                        "<td>" + rs.getInt("English") + "</td>\n" +
                        "</tr>\n");

            }
            out.println("</table>\n" +
                    "<form action = \n\"/scoremanagement/teacherpage\">\n" +
                    "ID:<input type =\"text\" name=\"ID\"/><br /><br/>\n" +

                    "math: <input type = \"text\" name=\"math\"/><br/><br/>\n" +
                    "physics: <input type = \"text\" name=\"physics\"/><br/><br/>\n" +
                    "chemistry: <input type = \"text\" name=\"chemistry\"/><br/><br/>\n" +
                    "English: <input type = \"text\" name=\"English\"/><br/><br/>\n" +
                    "<input type = \"submit\" value=\"update\">\n" +
                    "</form>\n" +
                    "</body>\n</html>");
        } catch (SQLException SE) {
            SE.printStackTrace();
            System.out.println(SE.getErrorCode());
            System.out.println(sqlmath);
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException SE2) {
                    SE2.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException SE3) {
                    SE3.printStackTrace();
                }
            }
        }

    }

}
