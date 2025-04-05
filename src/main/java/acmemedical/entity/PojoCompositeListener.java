/********************************************************************************************************
 * File:  PojoCompositeListener.java Course Materials CST 8277
 *
 * @author Himanish Rishi
 * @author Evan Stewart
 * 
 */
package acmemedical.entity;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@SuppressWarnings("unused")

public class PojoCompositeListener {

	@PrePersist
	public void setCreatedOnDate(PojoBaseCompositeKey<?> pojoBaseComposite) {
		LocalDateTime now = LocalDateTime.now();
		pojoBaseComposite.setCreated(now);
		pojoBaseComposite.setUpdated(now);
	}

	@PreUpdate
	public void setUpdatedDate(PojoBaseCompositeKey<?> pojoBaseComposite) {
		pojoBaseComposite.setUpdated(LocalDateTime.now());
	}

}
