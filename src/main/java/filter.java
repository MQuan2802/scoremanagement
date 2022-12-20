import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class filter implements Filter {
    public void init(FilterConfig config)
            throws ServletException {

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filter)
            throws java.io.IOException, ServletException {
        HttpServletRequest ahihireq = (HttpServletRequest) req;
        HttpServletResponse ahihiresp = (HttpServletResponse) resp;
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("this is restricted area");
    }
}
