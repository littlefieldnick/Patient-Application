package edu.usm.cos420.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.usm.cos420.domain.Patient;
import edu.usm.cos420.domain.Result;

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
	 * Create a new patient in the database with the provided patient information
	 * @param patient: patient being created
	 * @return id: the id of the patient that has been create
	 * @throws SQLException
	 */
	@Override
	public Long createPatient(Patient patient) throws SQLException {
		Long id = 0L;
		final String createPatientString = "INSERT INTO patients "
				+ "(firstName, lastName, gender, address, birthDate) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
				final PreparedStatement createPatientStmt = conn.prepareStatement(createPatientString,
						Statement.RETURN_GENERATED_KEYS)) {
			createPatientStmt.setString(1, patient.getFirstName());
			createPatientStmt.setString(2, patient.getLastName());
			createPatientStmt.setString(3, Character.toString(patient.getGender()));
			createPatientStmt.setString(4, patient.getAddress());
			createPatientStmt.setDate(5, (Date) patient.getBirthDate());

			createPatientStmt.executeUpdate();
			try (ResultSet keys = createPatientStmt.getGeneratedKeys()) {
				keys.next();
				id = keys.getLong(1);
			}
		}
		
		return id;
	}

	/**
	 * Lookup and return a patient from the database with the provided id
	 * @param patientId: the id of the patient to find
	 * @return patient: the patient that matches the given id
	 * @throws SQLException
	 */
	@Override
	public Patient readPatient(Long patientId) throws SQLException {
		final String readPatientString = "SELECT * FROM patients WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
		    PreparedStatement readPatientStmt = conn.prepareStatement(readPatientString)) {
			readPatientStmt.setLong(1, patientId);
			try (ResultSet keys = readPatientStmt.executeQuery()) {
				keys.next();
				Patient patient = new Patient();
				patient.setId(keys.getInt(1));
				patient.setFirstName(keys.getString(2));
				patient.setLastName(keys.getString(3));
				patient.setGender(keys.getString(4).charAt(0));
				patient.setAddress(keys.getString(5));
				patient.setBirthDate(keys.getDate(6));

				return patient;
			}
		}
	}

	/**
	 * Update a patient's information in the database
	 * @param patient: patient information to be updated
	 * @throws SQLException
	 */
	@Override
	public void updatePatient(Patient patient) throws SQLException {
		final String updatePatientString = "UPDATE patients SET firstName = ?, lastName = ?, gender = ?, address = ?, birthDate = ?  WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
			PreparedStatement updatePatientStmt = conn.prepareStatement(updatePatientString)) {
			updatePatientStmt.setString(1, patient.getFirstName());
			updatePatientStmt.setString(2, patient.getLastName());
			updatePatientStmt.setString(3, Character.toString(patient.getGender()));
			updatePatientStmt.setString(4, patient.getAddress());
			updatePatientStmt.setDate(5, (Date) patient.getBirthDate());
			updatePatientStmt.setLong(6, patient.getId());
			updatePatientStmt.executeUpdate();
		}

	}

	/**
	 * Delete a patient in the database with the corresponding id
	 * @param patientId: id of the patient to delete
	 * @throws SQLException
	 */
	@Override
	public void deletePatient(Long patientId) throws SQLException {
		final String deletePatientString = "DELETE FROM patients WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
			PreparedStatement deletePatientStmt = conn.prepareStatement(deletePatientString)) {
			deletePatientStmt.setLong(1, patientId);
			deletePatientStmt.executeUpdate();
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

