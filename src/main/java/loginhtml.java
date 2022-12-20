import javax.servlet.http.HttpServlet;
import java.io.*;

import javax.lang.model.util.ElementScanner14;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class loginhtml extends HttpServlet {
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

        String sql3 = "SELECT username,typeid from sessionid WHERE sessionid='" + sessionid + "'";

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println(session.isNew());

            if (!session.isNew()) {
                rs = stmt.executeQuery(sql3);

                if (rs.next()) {

                    RequestDispatcher rd = req.getRequestDispatcher("/loginclass");
                    rd.forward(req, resp);
                }
            }
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/login.jsp");
            rd.forward(req, resp);

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
