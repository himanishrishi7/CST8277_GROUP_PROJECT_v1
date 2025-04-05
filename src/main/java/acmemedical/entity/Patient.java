/********************************************************************************************************
 * File:  Patient.java Course Materials CST 8277
 *
 * @author Himanish Rishi
 * @author Evan Stewart
 * 
 */
package acmemedical.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


@SuppressWarnings("unused")

/**
 * The persistent class for the patient database table.
 */
@Entity
@Table(name = "patient")
@NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
public class Patient extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Basic(optional = false)
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@Basic
	@Column(name = "year_of_birth")
	private int year;

	@Basic
	@Column(name = "home_address", length = 100)
	private String address;

	@Basic
	@Column(name = "height_cm")
	private int height;

	@Basic
	@Column(name = "weight_kg")
	private int weight;

	@Basic
	@Column(name = "smoker")
	private byte smoker;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<Prescription> prescriptions = new HashSet<>();

	public Patient() {
		super();
	}

	public Patient(String firstName, String lastName, int year, String address, int height, int weight, byte smoker) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.year = year;
		this.address = address;
		this.height = height;
		this.weight = weight;
		this.smoker = smoker;
	}

	public Patient setPatient(String firstName, String lastName, int year, String address, int height, int weight, byte smoker) {
		setFirstName(firstName);
		setLastName(lastName);
		setYear(year);
		setAddress(address);
		setHeight(height);
		setWeight(weight);
		setSmoker(smoker);
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public byte getSmoker() {
		return smoker;
	}

	public void setSmoker(byte smoker) {
		this.smoker = smoker;
	}
	
	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescription(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	//Inherited hashCode/equals is sufficient for this Entity class

}
