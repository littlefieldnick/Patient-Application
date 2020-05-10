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
import edu.usm.cos420.domain.Patient;

/**
 * HttpServlet to handle display the information for a patient.
 */
@WebServlet(urlPatterns = {"/read"})
public class ReadPatientServlet extends HttpServlet{
	/**
	 * Handles GET request to display a patients information.
	 * @param req: HttpServletRequest
	 * @param resp: HttpServletResponse
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Long id = Long.decode(req.getParameter("id"));

		//Get DB information
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

		//Construct the DB url.
		String dbUrl = String.format(this.getServletContext().getInitParameter("sql.urlRemote"),
				properties.getProperty("sql.dbName"), properties.getProperty("sql.instanceName"),
				properties.getProperty("sql.userName"), properties.getProperty("sql.password"));

		PatientDao dao = null;

		//Create dao to connect to database
		try {
			dao = new PatientCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//Read the patient from the database, and redirect to "view.jsp"
		try {
			Patient patient = dao.readPatient(id);
			req.setAttribute("patient", patient);
			req.setAttribute("page", "view");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error reading patient", e);
		}
	}
}
