/********************************************************************************************************
 * File:  SecurityUser.java Course Materials CST 8277
 *
 * @author Himanish Rishi
 * @author Evan Stewart
 * 
 */
package acmemedical.entity;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


import acmemedical.rest.serializer.SecurityRoleSerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

@SuppressWarnings("unused")

/**
 * User class used for (JSR-375) Jakarta EE Security authorization/authentication
 */

// SU01 - Make this into JPA entity and add all the necessary annotations inside the class.
@Entity
@Table(name = "security_user")
@NamedQuery(name = "SecurityUser.findAll", query = "SELECT su FROM SecurityUser su")
@NamedQuery(name = "SecurityUser.findByPhysicianId", query = "SELECT su FROM SecurityUser su WHERE su.physician.id = :physicianId")
@NamedQuery(name = "SecurityUser.userByName", query = "SELECT su FROM SecurityUser su WHERE su.username = :param1")
public class SecurityUser implements Serializable, Principal {
    /** Explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    // SU02 - Add annotations.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    protected int id;
    
    // SU03 - Add annotations.
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 100)
    protected String username;
    
    // SU04 - Add annotations.
    @Basic(optional = false)
    @Column(name = "password_hash", nullable = false, length = 256)
    protected String pwHash;
    
    // SU05 - Add annotations.
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id", referencedColumnName = "id")
    @JsonManagedReference("physician-user")
    protected Physician physician;
    
    // SU06 - Add annotations.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    @JsonIgnoreProperties({"users", "password"})
    protected Set<SecurityRole> roles = new HashSet<>();

    public SecurityUser() {
        super();
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }
    
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    //  SU07 - Setup custom JSON serializer
    @JsonSerialize(using = SecurityRoleSerializer.class)
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    public Physician getPhysician() {
        return physician;
    }
    
    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    // Principal
    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        // Only include member variables that really contribute to an object's identity
        // i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
        // they shouldn't be part of the hashCode calculation
        return prime * result + Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SecurityUser otherSecurityUser) {
            // See comment (above) in hashCode():  Compare using only member variables that are
            // truly part of an object's identity
            return Objects.equals(this.getId(), otherSecurityUser.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SecurityUser [id = ").append(id).append(", username = ").append(username).append("]");
        return builder.toString();
    }
    
}
