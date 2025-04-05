/********************************************************************************************************
 * File:  PublicSchool.java Course materials CST 8277
 *
 * @author Himanish Rishi
 * @author Evan Stewart
 * 
 */
package acmemedical.entity;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// PUSC01 - Add missing annotations, please see Week 9 slides page 15.  Value 1 is public and value 0 is private.
// PUSC02 - Is a JSON annotation needed here?
@Entity
@DiscriminatorValue("1")
@JsonTypeName("true")
public class PublicSchool extends MedicalSchool implements Serializable {
	private static final long serialVersionUID = 1L;

	public PublicSchool() {
		super(true);
	}
}
