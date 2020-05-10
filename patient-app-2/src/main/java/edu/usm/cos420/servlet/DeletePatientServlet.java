package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.PatientCloudSqlDao;
import edu.usm.cos420.dao.PatientDao;

/**
 * HttpServlet to handle deletion of patients from a database.
 */
@WebServlet(urlPatterns = {"/delete"})
public class DeletePatientServlet extends HttpServlet {
	/**
	 * Handles the GET request to delete a patient from a database.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.decode(req.getParameter("id"));

		//Get DB information
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

		//Build DB string
		String dbUrl = String.format(this.getServletContext().getInitParameter("sql.urlRemote"),
				properties.getProperty("sql.dbName"), properties.getProperty("sql.instanceName"),
				properties.getProperty("sql.userName"), properties.getProperty("sql.password"));

		//Create dao to connect to database
		PatientDao dao = null;
		try {
			dao = new PatientCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//Delete patient from the database
		try {
			dao.deletePatient(id);
			resp.sendRedirect("/list");
		} catch (Exception e) {
			throw new ServletException("Error deleting patient", e);
		}
	}
}
