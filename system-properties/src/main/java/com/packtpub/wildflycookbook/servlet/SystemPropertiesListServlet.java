package com.packtpub.wildflycookbook.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by lfugaro on 9/9/14.
 */
public class SystemPropertiesListServlet extends HttpServlet {

    protected static final Logger logger = LoggerFactory.getLogger(SystemPropertiesListServlet.class);

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void destroy() {}

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + this.getServletName() + "</title>");
        out.println("</head>");

        out.println("<body>");
        out.println("<h1>");
        out.println(this.getServletName());
        out.println("</h1><br><br>");
        out.println("<p style=\"text-align: center;\">");
        out.println("<table><thead><th clospan=\"2\">System properties</th>");
        out.println("<tbody>");


        String prefix = request.getParameter("prefix");
        boolean prefixed = prefix != null && !"".equals(prefix);
        logger.info("prefix = " + prefix);

        Properties properties = System.getProperties();
        Enumeration<String> propertyNames = (Enumeration<String>) properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String key = propertyNames.nextElement();
            if (prefixed && !key.startsWith(prefix)) {
                continue;
            }
            String val = properties.getProperty(key);
            logger.info("*****************");
            logger.info("Key = " + key);
            logger.info("Val = " + val);
            logger.info("*****************");
            out.println("<tr>");
            out.println("<td>");
            out.println(key);
            out.println("</td>");
            out.println("<td>");
            out.println(val);
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "System properties list Servlet";
    }

}
