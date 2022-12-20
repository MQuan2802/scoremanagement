import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServlet;
import java.io.*;

import javax.lang.model.util.ElementScanner14;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class mail extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String to = req.getAttribute("mail").toString();
        String from = "ntmquan282@gmail.com";
        String host = "ahihi.comn";

        Properties properties = new Properties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hihi@ahihi.comn", "436553");
                // return new PasswordAuthentication("ntmquan282@gmail.com",
                // "34q0BMGjNnUAKvJS");
            }
        });
        try {

            String English = "";
            String math = "";
            String physics = "";
            String chemistry = "";
            String name = req.getAttribute("name").toString();
            if (req.getAttribute("math") != null) {
                math = req.getAttribute("math").toString();
            }
            if (req.getAttribute("physics") != null) {
                physics = req.getAttribute("physics").toString();
            }
            if (req.getAttribute("chemistry") != null) {
                chemistry = req.getAttribute("chemistry").toString();
            }
            if (req.getAttribute("English") != null) {
                English = req.getAttribute("English").toString();
            }
            String mess = "\n<html>\n" +
                    "<style>\n" +
                    "table,th,td { border: 1px solid black;}\n" +
                    "</style>\n" +
                    "<body>\n" +
                    "<table style=\"width:100%\">\n" +
                    "<tr>\n" +
                    // "<th>ID</th>\n" +
                    "<th>name</th>\n" +
                    "<th>math</th>\n" +
                    "<th>physics</th>\n" +
                    "<th>chemistry</th>\n" +
                    "<th>English</th>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>" + name + "</td>\n" +
                    "<td>" + math + "</td>\n" +
                    "<td>" + physics + "</td>\n" +
                    "<td>" + chemistry + "</td>\n" +
                    "<td>" + English + "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</body>\n</html>";

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your grade had been Update");
            message.setContent(mess, "text/html");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}
