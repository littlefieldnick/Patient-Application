package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.Date;
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
 * Update a patient's stored information in the database
 */
@WebServlet(urlPatterns = {"/update"})
public class UpdatePatientServlet extends HttpServlet{
	/**
	 * Handles GET request for updating a patients information. Patients are read from the database and used to populate
	 * the "Add a patient" form.
	 * @param req: HttpServletRequest
	 * @param resp: HttpServletResponse
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Get DB information
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

		String dbUrl = String.format(this.getServletContext().getInitParameter("sql.urlRemote"),
				properties.getProperty("sql.dbName"), properties.getProperty("sql.instanceName"),
				properties.getProperty("sql.userName"), properties.getProperty("sql.password"));
		PatientDao dao = null;
		
		try {
			dao = new PatientCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			Patient patient = dao.readPatient(Long.decode(req.getParameter("id")));
			req.setAttribute("patient", patient);
			req.setAttribute("action", "Edit");
			req.setAttribute("destination", "update");
			req.setAttribute("page", "form");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error loading patient for editing", e);
		}
	}

	/**
	 * Handles POST request to update the patient's information in the database
	 * @param req: HttpServletRequest
	 * @param resp: HttpServletResponse
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Get DB information
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

		//Construct DB string
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

		//Create patient with the new information and update in the database.
		try {
			Patient patient = new Patient();
			patient.setId(Integer.parseInt(req.getParameter("id")));
			patient.setFirstName(req.getParameter("firstName"));
			patient.setLastName(req.getParameter("lastName"));
			patient.setGender(req.getParameter("gender").charAt(0));
			patient.setAddress(req.getParameter("address"));
			patient.setBirthDate(Date.valueOf(req.getParameter("birthDate")));
					
			dao.updatePatient(patient);
			resp.sendRedirect("/read?id=" + req.getParameter("id"));
		} catch (Exception e) {
			throw new ServletException("Error updating patient", e);
		}
	}
}
