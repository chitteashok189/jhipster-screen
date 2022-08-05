package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RoleTypeAttribute.
 */
@Entity
@Table(name = "role_type_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleTypeAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "roleTypeAttributeID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyRoles", "roleTypeAttributes", "partyRoleID", "roleTypeAttributeID" }, allowSetters = true)
    private Set<RoleType> roleTypes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "partyRoles", "roleTypeAttributes", "partyRoleID", "roleTypeAttributeID" }, allowSetters = true)
    private RoleType roleTypeID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoleTypeAttribute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public RoleTypeAttribute gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public RoleTypeAttribute attributeName(String attributeName) {
        this.setAttributeName(attributeName);
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getDescription() {
        return this.description;
    }

    public RoleTypeAttribute description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public RoleTypeAttribute createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public RoleTypeAttribute createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public RoleTypeAttribute updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public RoleTypeAttribute updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<RoleType> getRoleTypes() {
        return this.roleTypes;
    }

    public void setRoleTypes(Set<RoleType> roleTypes) {
        if (this.roleTypes != null) {
            this.roleTypes.forEach(i -> i.setRoleTypeAttributeID(null));
        }
        if (roleTypes != null) {
            roleTypes.forEach(i -> i.setRoleTypeAttributeID(this));
        }
        this.roleTypes = roleTypes;
    }

    public RoleTypeAttribute roleTypes(Set<RoleType> roleTypes) {
        this.setRoleTypes(roleTypes);
        return this;
    }

    public RoleTypeAttribute addRoleType(RoleType roleType) {
        this.roleTypes.add(roleType);
        roleType.setRoleTypeAttributeID(this);
        return this;
    }

    public RoleTypeAttribute removeRoleType(RoleType roleType) {
        this.roleTypes.remove(roleType);
        roleType.setRoleTypeAttributeID(null);
        return this;
    }

    public RoleType getRoleTypeID() {
        return this.roleTypeID;
    }

    public void setRoleTypeID(RoleType roleType) {
        this.roleTypeID = roleType;
    }

    public RoleTypeAttribute roleTypeID(RoleType roleType) {
        this.setRoleTypeID(roleType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleTypeAttribute)) {
            return false;
        }
        return id != null && id.equals(((RoleTypeAttribute) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleTypeAttribute{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", attributeName='" + getAttributeName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
