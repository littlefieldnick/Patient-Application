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

    /**
     * Handles GET request to test database connection. Test is done by connection to the database and querying for the
     * system time.
     * @param req: HttpServletRequest
     * @param resp: HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
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

        //Make a connection and try to query for system time
        try(Connection conn = DriverManager.getConnection(dbUrl)){
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(testQuery);

            while(rs.next()) {
                result = rs.getObject(1).toString();
            }

            if(conn != null)
                conn.close();

            //Query was successful, set connected to true
            connected = true;
        } catch (SQLException e){

        }

        //Set attributes
        req.setAttribute("query", testQuery);
        req.setAttribute("queryResult", result);
        req.setAttribute("connected", connected);
        req.setAttribute("dbUrl", dbUrl);
        req.setAttribute("dev", Boolean.parseBoolean(properties.getProperty("app.devserver")));

        req.setAttribute("page", "db-test");

        req.getRequestDispatcher("/base.jsp").forward(req, resp);

    }
}
