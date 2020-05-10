package edu.usm.cos420.dao;

import edu.usm.cos420.domain.Patient;
import edu.usm.cos420.domain.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a connection to a Cloud SQL database. Can currently perform listing operations only.
 */
public class PatientCloudSqlDao implements PatientDao {
	String dbUrl;

	/**
	 * Creates a new connection to a Cloud SQL database
	 * @param dbUrl: connection URL to connect to database
	 * @throws SQLException
	 */
	public PatientCloudSqlDao(String dbUrl) throws SQLException {
		this.dbUrl = dbUrl;
		this.createPatientTable();
	}

	/**
	 * Create a patient table in database associated with @param dbUrl if one already doesn't exist
	 * @throws SQLException
	 */
	public void createPatientTable() throws SQLException {
		try(Connection conn = DriverManager.getConnection(this.dbUrl)){
			String createDbQuery =  "CREATE TABLE IF NOT EXISTS patients ( id SERIAL PRIMARY KEY, "
					+ "firstName VARCHAR(255), lastName VARCHAR(255), gender VARCHAR(255), address VARCHAR(255), birthDate DATE)";

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(createDbQuery);

			if(conn != null)
				conn.close();
		}
	}

	/**
	 * Connect to Cloud SQL database and get a list of current patients
	 * @param cursor: Current location in the list of patients
	 * @return list of patients in the database
	 * @throws SQLException
	 */
	@Override
	public Result<Patient> listPatients(String cursor) throws SQLException {
		int offset = 0;
		int totalNumRows = 0;
		if (cursor != null && !cursor.equals("")) {
			offset = Integer.parseInt(cursor);
		}
		
		final String listPatientsString = "SELECT id, firstName, lastName, gender, address, birthDate, count(*) OVER() AS total_count FROM patients ORDER BY lastName, firstName ASC "
				+ "LIMIT 10 OFFSET ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
				PreparedStatement listPatientStmt = conn.prepareStatement(listPatientsString)) {
			listPatientStmt.setInt(1, offset);
			List<Patient> resultPatients = new ArrayList<>();

			//Loop through the results and create corresponding Patient objects
			try (ResultSet rs = listPatientStmt.executeQuery()) {
				while (rs.next()) {
					Patient patient = new Patient();
					patient.setId(rs.getInt(1));
					patient.setFirstName(rs.getString(2));
					patient.setLastName(rs.getString(3));
					patient.setGender(rs.getString(4).charAt(0));
					patient.setAddress(rs.getString(5));
					patient.setBirthDate(rs.getDate(6));

					resultPatients.add(patient);

					totalNumRows = rs.getInt("total_count");
				}
			}

			if (totalNumRows > offset + 10) {
				return new Result<>(resultPatients, Integer.toString(offset + 10));
			} else {
				return new Result<>(resultPatients);
			}
		}
	}

}

