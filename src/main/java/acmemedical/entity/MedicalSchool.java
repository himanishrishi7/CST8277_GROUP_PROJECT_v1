/********************************************************************************************************
 * File:  MedicalSchool.java Course Materials CST 8277
 *
  * @author Himanish Rishi
  * @author Evan Stewart
 * 
 */
package acmemedical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The persistent class for the medical_school database table.
 */
@Entity
@Table(name = "medical_school")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "public", discriminatorType = DiscriminatorType.INTEGER)
@NamedQuery(name = "MedicalSchool.findAll", query = "SELECT ms FROM MedicalSchool ms")
@NamedQuery(name= "MedicalSchool.findById", query = "SELECT ms FROM MedicalSchool ms WHERE ms.id = :id")
@NamedQuery(name = "MedicalSchool.isDuplicate", 
    query = "SELECT COUNT(ms) FROM MedicalSchool ms WHERE LOWER(ms.name) = LOWER(:param1)")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "isPublic"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PublicSchool.class, name = "true"),
    @JsonSubTypes.Type(value = PrivateSchool.class, name = "false")
})
@AttributeOverride(name = "id", column = @Column(name = "school_id"))
public abstract class MedicalSchool extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "school")
	@JsonIgnore
	private Set<MedicalTraining> medicalTrainings = new HashSet<>();

	@Basic
	@Column(name = "public", insertable = false, updatable = false)
	private boolean isPublic;

	public static final String IS_DUPLICATE_QUERY_NAME = "MedicalSchool.isDuplicate";
	public static final String SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME = "MedicalSchool.findById";

	public MedicalSchool() {
		super();
	}

    public MedicalSchool(boolean isPublic) {
        this();
        this.isPublic = isPublic;
    }

	public Set<MedicalTraining> getMedicalTrainings() {
		return medicalTrainings;
	}

	public void setMedicalTrainings(Set<MedicalTraining> medicalTrainings) {
		this.medicalTrainings = medicalTrainings;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isIsPublic() {
		return this.isPublic;
	}

	public boolean getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	//Inherited hashCode/equals is NOT sufficient for this entity class
	
	/**
	 * Very important:  Use getter's for member variables because JPA sometimes needs to intercept those calls<br/>
	 * and go to the database to retrieve the value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// Only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		
		// The database schema for the MEDICAL_SCHOOL table has a UNIQUE constraint for the NAME column,
		// so we should include that in the hash/equals calculations
		
		return prime * result + Objects.hash(getId(), getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof MedicalSchool otherMedicalSchool) {
			// See comment (above) in hashCode():  Compare using only member variables that are
			// truly part of an object's identity
			return Objects.equals(this.getId(), otherMedicalSchool.getId()) &&
				Objects.equals(this.getName(), otherMedicalSchool.getName());
		}
		return false;
	}
}
