package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartyRelationship.
 */
@Entity
@Table(name = "party_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "party_id_to")
    private Long partyIdTo;

    @Column(name = "party_id_from")
    private Long partyIdFrom;

    @Column(name = "role_type_id_from")
    private Long roleTypeIdFrom;

    @Column(name = "role_type_id_to")
    private Long roleTypeIdTo;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "thru_date")
    private Instant thruDate;

    @Column(name = "relationship_name")
    private String relationshipName;

    @Column(name = "position_title")
    private String positionTitle;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "partyRelationshipID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyRelationships", "partyRelationshipID" }, allowSetters = true)
    private Set<PartyRelationshipType> partyRelationshipTypes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "partyRelationships", "partyRelationshipID" }, allowSetters = true)
    private PartyRelationshipType partyRelationshipTypeID;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "partyRelationships",
            "applicationUserSecurityGroups",
            "securityGroupPermissions",
            "applicationUserSecurityGroupID",
            "securityGroupPermissionID",
        },
        allowSetters = true
    )
    private SecurityGroup securityGroupID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartyRelationship id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PartyRelationship gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Long getPartyIdTo() {
        return this.partyIdTo;
    }

    public PartyRelationship partyIdTo(Long partyIdTo) {
        this.setPartyIdTo(partyIdTo);
        return this;
    }

    public void setPartyIdTo(Long partyIdTo) {
        this.partyIdTo = partyIdTo;
    }

    public Long getPartyIdFrom() {
        return this.partyIdFrom;
    }

    public PartyRelationship partyIdFrom(Long partyIdFrom) {
        this.setPartyIdFrom(partyIdFrom);
        return this;
    }

    public void setPartyIdFrom(Long partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }

    public Long getRoleTypeIdFrom() {
        return this.roleTypeIdFrom;
    }

    public PartyRelationship roleTypeIdFrom(Long roleTypeIdFrom) {
        this.setRoleTypeIdFrom(roleTypeIdFrom);
        return this;
    }

    public void setRoleTypeIdFrom(Long roleTypeIdFrom) {
        this.roleTypeIdFrom = roleTypeIdFrom;
    }

    public Long getRoleTypeIdTo() {
        return this.roleTypeIdTo;
    }

    public PartyRelationship roleTypeIdTo(Long roleTypeIdTo) {
        this.setRoleTypeIdTo(roleTypeIdTo);
        return this;
    }

    public void setRoleTypeIdTo(Long roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }

    public Instant getFromDate() {
        return this.fromDate;
    }

    public PartyRelationship fromDate(Instant fromDate) {
        this.setFromDate(fromDate);
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getThruDate() {
        return this.thruDate;
    }

    public PartyRelationship thruDate(Instant thruDate) {
        this.setThruDate(thruDate);
        return this;
    }

    public void setThruDate(Instant thruDate) {
        this.thruDate = thruDate;
    }

    public String getRelationshipName() {
        return this.relationshipName;
    }

    public PartyRelationship relationshipName(String relationshipName) {
        this.setRelationshipName(relationshipName);
        return this;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public String getPositionTitle() {
        return this.positionTitle;
    }

    public PartyRelationship positionTitle(String positionTitle) {
        this.setPositionTitle(positionTitle);
        return this;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getComments() {
        return this.comments;
    }

    public PartyRelationship comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PartyRelationship createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PartyRelationship createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PartyRelationship updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PartyRelationship updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PartyRelationshipType> getPartyRelationshipTypes() {
        return this.partyRelationshipTypes;
    }

    public void setPartyRelationshipTypes(Set<PartyRelationshipType> partyRelationshipTypes) {
        if (this.partyRelationshipTypes != null) {
            this.partyRelationshipTypes.forEach(i -> i.setPartyRelationshipID(null));
        }
        if (partyRelationshipTypes != null) {
            partyRelationshipTypes.forEach(i -> i.setPartyRelationshipID(this));
        }
        this.partyRelationshipTypes = partyRelationshipTypes;
    }

    public PartyRelationship partyRelationshipTypes(Set<PartyRelationshipType> partyRelationshipTypes) {
        this.setPartyRelationshipTypes(partyRelationshipTypes);
        return this;
    }

    public PartyRelationship addPartyRelationshipType(PartyRelationshipType partyRelationshipType) {
        this.partyRelationshipTypes.add(partyRelationshipType);
        partyRelationshipType.setPartyRelationshipID(this);
        return this;
    }

    public PartyRelationship removePartyRelationshipType(PartyRelationshipType partyRelationshipType) {
        this.partyRelationshipTypes.remove(partyRelationshipType);
        partyRelationshipType.setPartyRelationshipID(null);
        return this;
    }

    public PartyRelationshipType getPartyRelationshipTypeID() {
        return this.partyRelationshipTypeID;
    }

    public void setPartyRelationshipTypeID(PartyRelationshipType partyRelationshipType) {
        this.partyRelationshipTypeID = partyRelationshipType;
    }

    public PartyRelationship partyRelationshipTypeID(PartyRelationshipType partyRelationshipType) {
        this.setPartyRelationshipTypeID(partyRelationshipType);
        return this;
    }

    public SecurityGroup getSecurityGroupID() {
        return this.securityGroupID;
    }

    public void setSecurityGroupID(SecurityGroup securityGroup) {
        this.securityGroupID = securityGroup;
    }

    public PartyRelationship securityGroupID(SecurityGroup securityGroup) {
        this.setSecurityGroupID(securityGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyRelationship)) {
            return false;
        }
        return id != null && id.equals(((PartyRelationship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyRelationship{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", partyIdTo=" + getPartyIdTo() +
            ", partyIdFrom=" + getPartyIdFrom() +
            ", roleTypeIdFrom=" + getRoleTypeIdFrom() +
            ", roleTypeIdTo=" + getRoleTypeIdTo() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", relationshipName='" + getRelationshipName() + "'" +
            ", positionTitle='" + getPositionTitle() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
