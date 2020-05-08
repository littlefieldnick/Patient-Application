package edu.usm.cos420.domain;

import java.util.Date;

/**
 * Represents a patient visiting a clinic.
 */
public class Patient {
	/** This patients unique identifier. */
	int id;

	/** This patient's first name. */
	String firstName;

	/** This patient's last name. */
	String lastName;

	/** This patient's gender: M for male, F for female. */
	char gender;

	/** This patient's address. */
	String address;

	/** This patient's date of birth. */
	Date birthDate;


	/**
	 * Returns the id associated with the patient.
	 *
	 * @return patient's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for the patient.
	 *
	 * @param id: the patient's unique identifier
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the first name of the patient.
	 *
	 * @return patient's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the patient.
	 *
	 * @param firstName: the patients's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the patient.
	 *
	 * @return patient's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the patient.
	 *
	 * @param lastName: the patients's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the gender of the patient.
	 *
	 * @return the patient's gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * Sets the specified gender of the patient.
	 *
	 * @param gender: the patients's first gender: M for Male, F for Female
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * Returns the current address of the patient.
	 *
	 * @return this patient's address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the current address for the patient.
	 *
	 * @param address: the current address of the patient
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the date of birth of the patient.
	 *
	 * @return this customer's name
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the date of birth of the patient.
	 *
	 * @param birthDate: the patients's first name
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
