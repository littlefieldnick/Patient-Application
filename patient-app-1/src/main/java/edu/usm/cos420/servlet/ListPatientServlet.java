package edu.usm.cos420.servlet;

import edu.usm.cos420.dao.PatientCloudSqlDao;
import edu.usm.cos420.dao.PatientDao;
import edu.usm.cos420.domain.Patient;
import edu.usm.cos420.domain.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * HttpServlet for listing patients in a database.
 */
@WebServlet(urlPatterns = {"/list"})
public class ListPatientServlet extends HttpServlet {

	/**
	 * Handles GET request by getting a list of patients from a database, and displays the list
	 * @param req: HTTP request edu.usm.cos420.servlet
	 * @param resp: HTTP response edu.usm.cos420.servlet
	 * @throws IOException
	 * @throws ServletException
	 *
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		//Get DB information
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

		//Build DB string using info stored in property file
		String dbUrl = String.format(this.getServletContext().getInitParameter("sql.urlRemote"),
				properties.getProperty("sql.dbName"), properties.getProperty("sql.instanceName"),
				properties.getProperty("sql.userName"), properties.getProperty("sql.password"));
		PatientDao dao = null;
		
		try {
			dao = new PatientCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//Get cursor parameter: current location in db list
		String startCursor = req.getParameter("cursor");
		List<Patient> patients = null;
		String endCursor = null;
		
		try {
			Result<Patient> result = dao.listPatients(startCursor);
			patients = result.result;
			endCursor = result.cursor;
		} catch (Exception e) {
			throw new ServletException("Error listing patients", e);
		}

		//Set request attributes forward
		req.getSession().getServletContext().setAttribute("patients", patients);
		
		req.setAttribute("cursor", endCursor);
	    req.setAttribute("page", "list");

		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}
}
