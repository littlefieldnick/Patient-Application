/**
 * @author Nick Littlefield
 */
package edu.usm.cos420.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import java.util.Properties;

@WebServlet("/check-connection")
public class DatabaseConnectionServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        //Get DB information
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

        //Build DB string using info stored in property file
        String dbUrl = String.format(this.getServletContext().getInitParameter("sql.urlRemote"),
                properties.getProperty("sql.dbName"), properties.getProperty("sql.instanceName"),
                properties.getProperty("sql.userName"), properties.getProperty("sql.password"));
        String testQuery = "SELECT CURRENT_TIMESTAMP;";
        String result = null;

        boolean connected = false;
        try(Connection conn = DriverManager.getConnection(dbUrl)){
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(testQuery);

            while(rs.next()) {
                result = rs.getObject(1).toString();
                System.out.println(result);
            }

            if(conn != null)
                conn.close();

            connected = true;
        } catch (SQLException e){

        }

        req.setAttribute("query", testQuery);
        req.setAttribute("queryResult", result);
        req.setAttribute("connected", connected);
        req.setAttribute("dbUrl", dbUrl);
        req.setAttribute("dev", Boolean.parseBoolean(properties.getProperty("app.devserver")));

        req.setAttribute("page", "db-test");

        req.getRequestDispatcher("/base.jsp").forward(req, resp);

    }
}
