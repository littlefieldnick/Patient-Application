package edu.usm.cos420.dao;

import edu.usm.cos420.domain.Patient;
import edu.usm.cos420.domain.Result;

import java.sql.SQLException;

/**
 * Data access object interface for patients
 */
public interface PatientDao {
	/**
	 *
	 * @param startCursor: current location in the list of patients
	 * @return list of patients with offset startCursor
	 * @throws SQLException
	 */
	Result<Patient> listPatients(String startCursor) throws SQLException;
}
