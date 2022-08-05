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
 * A RoleType.
 */
@Entity
@Table(name = "role_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "has_table")
    private Boolean hasTable;

    @Column(name = "description")
    private String description;

    @Column(name = "child_role_type")
    private Long childRoleType;

    @Column(name = "valid_party_relationship_type")
    private Long validPartyRelationshipType;

    @Column(name = "valid_from_party_relationship_type")
    private Long validFromPartyRelationshipType;

    @Column(name = "party_invitation_role_association")
    private String partyInvitationRoleAssociation;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "roleTypeID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parties", "roleTypes", "roleTypeID", "partyID" }, allowSetters = true)
    private Set<PartyRole> partyRoles = new HashSet<>();

    @OneToMany(mappedBy = "roleTypeID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roleTypes", "roleTypeID" }, allowSetters = true)
    private Set<RoleTypeAttribute> roleTypeAttributes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "parties", "roleTypes", "roleTypeID", "partyID" }, allowSetters = true)
    private PartyRole partyRoleID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roleTypes", "roleTypeID" }, allowSetters = true)
    private RoleTypeAttribute roleTypeAttributeID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoleType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public RoleType gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Boolean getHasTable() {
        return this.hasTable;
    }

    public RoleType hasTable(Boolean hasTable) {
        this.setHasTable(hasTable);
        return this;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public String getDescription() {
        return this.description;
    }

    public RoleType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getChildRoleType() {
        return this.childRoleType;
    }

    public RoleType childRoleType(Long childRoleType) {
        this.setChildRoleType(childRoleType);
        return this;
    }

    public void setChildRoleType(Long childRoleType) {
        this.childRoleType = childRoleType;
    }

    public Long getValidPartyRelationshipType() {
        return this.validPartyRelationshipType;
    }

    public RoleType validPartyRelationshipType(Long validPartyRelationshipType) {
        this.setValidPartyRelationshipType(validPartyRelationshipType);
        return this;
    }

    public void setValidPartyRelationshipType(Long validPartyRelationshipType) {
        this.validPartyRelationshipType = validPartyRelationshipType;
    }

    public Long getValidFromPartyRelationshipType() {
        return this.validFromPartyRelationshipType;
    }

    public RoleType validFromPartyRelationshipType(Long validFromPartyRelationshipType) {
        this.setValidFromPartyRelationshipType(validFromPartyRelationshipType);
        return this;
    }

    public void setValidFromPartyRelationshipType(Long validFromPartyRelationshipType) {
        this.validFromPartyRelationshipType = validFromPartyRelationshipType;
    }

    public String getPartyInvitationRoleAssociation() {
        return this.partyInvitationRoleAssociation;
    }

    public RoleType partyInvitationRoleAssociation(String partyInvitationRoleAssociation) {
        this.setPartyInvitationRoleAssociation(partyInvitationRoleAssociation);
        return this;
    }

    public void setPartyInvitationRoleAssociation(String partyInvitationRoleAssociation) {
        this.partyInvitationRoleAssociation = partyInvitationRoleAssociation;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public RoleType createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public RoleType createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public RoleType updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public RoleType updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PartyRole> getPartyRoles() {
        return this.partyRoles;
    }

    public void setPartyRoles(Set<PartyRole> partyRoles) {
        if (this.partyRoles != null) {
            this.partyRoles.forEach(i -> i.setRoleTypeID(null));
        }
        if (partyRoles != null) {
            partyRoles.forEach(i -> i.setRoleTypeID(this));
        }
        this.partyRoles = partyRoles;
    }

    public RoleType partyRoles(Set<PartyRole> partyRoles) {
        this.setPartyRoles(partyRoles);
        return this;
    }

    public RoleType addPartyRole(PartyRole partyRole) {
        this.partyRoles.add(partyRole);
        partyRole.setRoleTypeID(this);
        return this;
    }

    public RoleType removePartyRole(PartyRole partyRole) {
        this.partyRoles.remove(partyRole);
        partyRole.setRoleTypeID(null);
        return this;
    }

    public Set<RoleTypeAttribute> getRoleTypeAttributes() {
        return this.roleTypeAttributes;
    }

    public void setRoleTypeAttributes(Set<RoleTypeAttribute> roleTypeAttributes) {
        if (this.roleTypeAttributes != null) {
            this.roleTypeAttributes.forEach(i -> i.setRoleTypeID(null));
        }
        if (roleTypeAttributes != null) {
            roleTypeAttributes.forEach(i -> i.setRoleTypeID(this));
        }
        this.roleTypeAttributes = roleTypeAttributes;
    }

    public RoleType roleTypeAttributes(Set<RoleTypeAttribute> roleTypeAttributes) {
        this.setRoleTypeAttributes(roleTypeAttributes);
        return this;
    }

    public RoleType addRoleTypeAttribute(RoleTypeAttribute roleTypeAttribute) {
        this.roleTypeAttributes.add(roleTypeAttribute);
        roleTypeAttribute.setRoleTypeID(this);
        return this;
    }

    public RoleType removeRoleTypeAttribute(RoleTypeAttribute roleTypeAttribute) {
        this.roleTypeAttributes.remove(roleTypeAttribute);
        roleTypeAttribute.setRoleTypeID(null);
        return this;
    }

    public PartyRole getPartyRoleID() {
        return this.partyRoleID;
    }

    public void setPartyRoleID(PartyRole partyRole) {
        this.partyRoleID = partyRole;
    }

    public RoleType partyRoleID(PartyRole partyRole) {
        this.setPartyRoleID(partyRole);
        return this;
    }

    public RoleTypeAttribute getRoleTypeAttributeID() {
        return this.roleTypeAttributeID;
    }

    public void setRoleTypeAttributeID(RoleTypeAttribute roleTypeAttribute) {
        this.roleTypeAttributeID = roleTypeAttribute;
    }

    public RoleType roleTypeAttributeID(RoleTypeAttribute roleTypeAttribute) {
        this.setRoleTypeAttributeID(roleTypeAttribute);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleType)) {
            return false;
        }
        return id != null && id.equals(((RoleType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleType{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", hasTable='" + getHasTable() + "'" +
            ", description='" + getDescription() + "'" +
            ", childRoleType=" + getChildRoleType() +
            ", validPartyRelationshipType=" + getValidPartyRelationshipType() +
            ", validFromPartyRelationshipType=" + getValidFromPartyRelationshipType() +
            ", partyInvitationRoleAssociation='" + getPartyInvitationRoleAssociation() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
