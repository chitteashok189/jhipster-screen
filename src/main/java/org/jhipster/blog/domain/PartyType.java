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
import org.jhipster.blog.domain.enumeration.ParentType;

/**
 * A PartyType.
 */
@Entity
@Table(name = "party_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_type_id")
    private ParentType parentTypeID;

    @Column(name = "has_table")
    private String hasTable;

    @Column(name = "description")
    private String description;

    @Column(name = "party_type_attr")
    private String partyTypeAttr;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "partyTypeID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "partyAttributes",
            "partyClassifications",
            "partyRoles",
            "partyNotes",
            "people",
            "applicationUsers",
            "farms",
            "partyGroupID",
            "partyTypeID",
            "applicationUserID",
            "partyRoleID",
            "personID",
        },
        allowSetters = true
    )
    private Set<Party> parties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartyType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PartyType gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public ParentType getParentTypeID() {
        return this.parentTypeID;
    }

    public PartyType parentTypeID(ParentType parentTypeID) {
        this.setParentTypeID(parentTypeID);
        return this;
    }

    public void setParentTypeID(ParentType parentTypeID) {
        this.parentTypeID = parentTypeID;
    }

    public String getHasTable() {
        return this.hasTable;
    }

    public PartyType hasTable(String hasTable) {
        this.setHasTable(hasTable);
        return this;
    }

    public void setHasTable(String hasTable) {
        this.hasTable = hasTable;
    }

    public String getDescription() {
        return this.description;
    }

    public PartyType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartyTypeAttr() {
        return this.partyTypeAttr;
    }

    public PartyType partyTypeAttr(String partyTypeAttr) {
        this.setPartyTypeAttr(partyTypeAttr);
        return this;
    }

    public void setPartyTypeAttr(String partyTypeAttr) {
        this.partyTypeAttr = partyTypeAttr;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PartyType createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PartyType createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PartyType updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PartyType updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Party> getParties() {
        return this.parties;
    }

    public void setParties(Set<Party> parties) {
        if (this.parties != null) {
            this.parties.forEach(i -> i.setPartyTypeID(null));
        }
        if (parties != null) {
            parties.forEach(i -> i.setPartyTypeID(this));
        }
        this.parties = parties;
    }

    public PartyType parties(Set<Party> parties) {
        this.setParties(parties);
        return this;
    }

    public PartyType addParty(Party party) {
        this.parties.add(party);
        party.setPartyTypeID(this);
        return this;
    }

    public PartyType removeParty(Party party) {
        this.parties.remove(party);
        party.setPartyTypeID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyType)) {
            return false;
        }
        return id != null && id.equals(((PartyType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyType{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", parentTypeID='" + getParentTypeID() + "'" +
            ", hasTable='" + getHasTable() + "'" +
            ", description='" + getDescription() + "'" +
            ", partyTypeAttr='" + getPartyTypeAttr() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
