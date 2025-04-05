/********************************************************************************************************
 * File:  MedicalCertificate.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
/********************************************************************************************************
 * File:  MedicalCertificate.java Course Materials CST 8277
 *
 *@author Himanish Rishi
 *@author Evan Stewart
 *
 * 
 */
import jakarta.persistence.*;


import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("unused")

/**
 * The persistent class for the medical_certificate database table.
 */
@Entity
@Table(name = "medical_certificate")
@NamedQuery(name = "MedicalCertificate.findAll", query = "SELECT mc FROM MedicalCertificate mc")
@NamedQueries({
	@NamedQuery(name = MedicalCertificate.ID_CARD_QUERY_NAME, query = "SELECT mc FROM MedicalCertificate mc WHERE mc.id = :id")
})
@AttributeOverride(name = "id", column = @Column(name = "certificate_id"))
public class MedicalCertificate extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "physician_id")
	private Physician physician;

	@JsonManagedReference("certificate-training")
	@OneToOne
	@JoinColumn(name = "training_id")
	private MedicalTraining medicalTraining;

	@Basic
	@Column(name = "signed")
	private byte signed;

	public static final String ID_CARD_QUERY_NAME = "MedicalCertificate.findById";

	public MedicalCertificate() {
		super();
	}
	
	public MedicalCertificate(MedicalTraining medicalTraining, Physician owner, byte signed) {
		this();
		this.medicalTraining = medicalTraining;
		this.physician = owner;
		this.signed = signed;
	}

	public MedicalTraining getMedicalTraining() {
		return medicalTraining;
	}

	public void setMedicalTraining(MedicalTraining medicalTraining) {
		this.medicalTraining = medicalTraining;
	}

	public Physician getOwner() {
		return physician;
	}

	public void setOwner(Physician owner) {
		this.physician = owner;
	}

	public byte getSigned() {
		return signed;
	}

	public void setSigned(byte signed) {
		this.signed = signed;
	}
	//Inherited hashCode/equals is sufficient for this entity class

}
