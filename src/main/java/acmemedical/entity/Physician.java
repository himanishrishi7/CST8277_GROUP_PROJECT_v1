/********************************************************************************************************
 * File:  Physician.java Course Materials CST 8277
 *
 * @author Himanish Rishi
 * @author Evan Stewart
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * The persistent class for the physician database table.
 */
@SuppressWarnings("unused")
// PH01 - Add the missing annotations.
// PH02 - Do we need a mapped super class? If so, which one?
@Entity
@Table(name = "physician")
@NamedQuery(name = "Physician.findAll", query = "SELECT p FROM Physician p")
public class Physician extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

    public Physician() {
    	super();
    }

	@Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Basic(optional = false)
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	//  PH05 - Add annotations for 1:M relation.  What should be the cascade and fetch types?
	@JsonIgnore
	@OneToMany(mappedBy = "physician", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<MedicalCertificate> medicalCertificates = new HashSet<>();

	//  PH06 - Add annotations for 1:M relation.  What should be the cascade and fetch types?
	@JsonManagedReference("physician-prescriptions")
	@OneToMany(mappedBy = "physician", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Prescription> prescriptions = new HashSet<>();

	@JsonBackReference("physician-user")
	@OneToOne(mappedBy = "physician")
	private SecurityUser securityUser;

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

	//  PH07 - Is an annotation needed here?
    public Set<MedicalCertificate> getMedicalCertificates() {
		return medicalCertificates;
	}

	public void setMedicalCertificates(Set<MedicalCertificate> medicalCertificates) {
		this.medicalCertificates = medicalCertificates;
	}

	//  PH08 - Is an annotation needed here?
    public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public void setFullName(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	//Inherited hashCode/equals is sufficient for this entity class

}
