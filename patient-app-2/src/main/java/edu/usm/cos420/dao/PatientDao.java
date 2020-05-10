package edu.usm.cos420.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.usm.cos420.domain.Patient;
import edu.usm.cos420.domain.Result;

public interface PatientDao {
	/**
	 * Create a patient and store in the database
	 * @param patient: patient being created
	 * @return id: the id of the patient
	 * @throws SQLException
	 */
	Long createPatient(Patient patient) throws SQLException;

	/**
	 * Read a patient from the database with the given id.
	 * @param patientId: the id of the patient to find
	 * @return patient: the patient with the given id
	 * @throws SQLException
	 */
	Patient readPatient(Long patientId) throws SQLException;

	/**
	 * Update a patient's information in the database.
	 * @param patient
	 * @throws SQLException
	 */
	void updatePatient(Patient patient) throws SQLException;

	/**
	 * Delete a patient from the database
	 * @param patientId: id of the patient to delete
	 * @throws SQLException
	 */
	void deletePatient(Long patientId) throws SQLException;

	/**
	 *
	 * @param startCursor: current location in the list of patients
	 * @return list of patients with offset startCursor
	 * @throws SQLException
	 */
	Result<Patient> listPatients(String startCursor) throws SQLException;
}
