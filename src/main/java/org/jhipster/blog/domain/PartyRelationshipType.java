package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartyRelationshipType.
 */
@Entity
@Table(name = "party_relationship_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRelationshipType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private Long gUID;

    @Column(name = "has_table")
    private Boolean hasTable;

    @Column(name = "party_relationship_name")
    private String partyRelationshipName;

    @Column(name = "description")
    private String description;

    @Column(name = "role_type_id_valid_from")
    private Long roleTypeIdValidFrom;

    @Column(name = "role_type_id_valid_to")
    private Long roleTypeIdValidTo;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "partyRelationshipTypeID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyRelationshipTypes", "partyRelationshipTypeID", "securityGroupID" }, allowSetters = true)
    private Set<PartyRelationship> partyRelationships = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "partyRelationshipTypes", "partyRelationshipTypeID", "securityGroupID" }, allowSetters = true)
    private PartyRelationship partyRelationshipID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartyRelationshipType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getgUID() {
        return this.gUID;
    }

    public PartyRelationshipType gUID(Long gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(Long gUID) {
        this.gUID = gUID;
    }

    public Boolean getHasTable() {
        return this.hasTable;
    }

    public PartyRelationshipType hasTable(Boolean hasTable) {
        this.setHasTable(hasTable);
        return this;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public String getPartyRelationshipName() {
        return this.partyRelationshipName;
    }

    public PartyRelationshipType partyRelationshipName(String partyRelationshipName) {
        this.setPartyRelationshipName(partyRelationshipName);
        return this;
    }

    public void setPartyRelationshipName(String partyRelationshipName) {
        this.partyRelationshipName = partyRelationshipName;
    }

    public String getDescription() {
        return this.description;
    }

    public PartyRelationshipType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRoleTypeIdValidFrom() {
        return this.roleTypeIdValidFrom;
    }

    public PartyRelationshipType roleTypeIdValidFrom(Long roleTypeIdValidFrom) {
        this.setRoleTypeIdValidFrom(roleTypeIdValidFrom);
        return this;
    }

    public void setRoleTypeIdValidFrom(Long roleTypeIdValidFrom) {
        this.roleTypeIdValidFrom = roleTypeIdValidFrom;
    }

    public Long getRoleTypeIdValidTo() {
        return this.roleTypeIdValidTo;
    }

    public PartyRelationshipType roleTypeIdValidTo(Long roleTypeIdValidTo) {
        this.setRoleTypeIdValidTo(roleTypeIdValidTo);
        return this;
    }

    public void setRoleTypeIdValidTo(Long roleTypeIdValidTo) {
        this.roleTypeIdValidTo = roleTypeIdValidTo;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PartyRelationshipType createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PartyRelationshipType createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PartyRelationshipType updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PartyRelationshipType updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PartyRelationship> getPartyRelationships() {
        return this.partyRelationships;
    }

    public void setPartyRelationships(Set<PartyRelationship> partyRelationships) {
        if (this.partyRelationships != null) {
            this.partyRelationships.forEach(i -> i.setPartyRelationshipTypeID(null));
        }
        if (partyRelationships != null) {
            partyRelationships.forEach(i -> i.setPartyRelationshipTypeID(this));
        }
        this.partyRelationships = partyRelationships;
    }

    public PartyRelationshipType partyRelationships(Set<PartyRelationship> partyRelationships) {
        this.setPartyRelationships(partyRelationships);
        return this;
    }

    public PartyRelationshipType addPartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationships.add(partyRelationship);
        partyRelationship.setPartyRelationshipTypeID(this);
        return this;
    }

    public PartyRelationshipType removePartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationships.remove(partyRelationship);
        partyRelationship.setPartyRelationshipTypeID(null);
        return this;
    }

    public PartyRelationship getPartyRelationshipID() {
        return this.partyRelationshipID;
    }

    public void setPartyRelationshipID(PartyRelationship partyRelationship) {
        this.partyRelationshipID = partyRelationship;
    }

    public PartyRelationshipType partyRelationshipID(PartyRelationship partyRelationship) {
        this.setPartyRelationshipID(partyRelationship);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyRelationshipType)) {
            return false;
        }
        return id != null && id.equals(((PartyRelationshipType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyRelationshipType{" +
            "id=" + getId() +
            ", gUID=" + getgUID() +
            ", hasTable='" + getHasTable() + "'" +
            ", partyRelationshipName='" + getPartyRelationshipName() + "'" +
            ", description='" + getDescription() + "'" +
            ", roleTypeIdValidFrom=" + getRoleTypeIdValidFrom() +
            ", roleTypeIdValidTo=" + getRoleTypeIdValidTo() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
