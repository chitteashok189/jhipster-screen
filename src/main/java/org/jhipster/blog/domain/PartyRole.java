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
 * A PartyRole.
 */
@Entity
@Table(name = "party_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "from_agreement")
    private String fromAgreement;

    @Column(name = "to_agreement")
    private String toAgreement;

    @Column(name = "from_communication_event")
    private String fromCommunicationEvent;

    @Column(name = "to_communication_event")
    private String toCommunicationEvent;

    @Column(name = "party_id_from")
    private Long partyIdFrom;

    @Column(name = "party_id_to")
    private Long partyIdTO;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "partyRoleID")
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

    @OneToMany(mappedBy = "partyRoleID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyRoles", "roleTypeAttributes", "partyRoleID", "roleTypeAttributeID" }, allowSetters = true)
    private Set<RoleType> roleTypes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "partyRoles", "roleTypeAttributes", "partyRoleID", "roleTypeAttributeID" }, allowSetters = true)
    private RoleType roleTypeID;

    @ManyToOne
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
    private Party partyID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartyRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PartyRole gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getFromAgreement() {
        return this.fromAgreement;
    }

    public PartyRole fromAgreement(String fromAgreement) {
        this.setFromAgreement(fromAgreement);
        return this;
    }

    public void setFromAgreement(String fromAgreement) {
        this.fromAgreement = fromAgreement;
    }

    public String getToAgreement() {
        return this.toAgreement;
    }

    public PartyRole toAgreement(String toAgreement) {
        this.setToAgreement(toAgreement);
        return this;
    }

    public void setToAgreement(String toAgreement) {
        this.toAgreement = toAgreement;
    }

    public String getFromCommunicationEvent() {
        return this.fromCommunicationEvent;
    }

    public PartyRole fromCommunicationEvent(String fromCommunicationEvent) {
        this.setFromCommunicationEvent(fromCommunicationEvent);
        return this;
    }

    public void setFromCommunicationEvent(String fromCommunicationEvent) {
        this.fromCommunicationEvent = fromCommunicationEvent;
    }

    public String getToCommunicationEvent() {
        return this.toCommunicationEvent;
    }

    public PartyRole toCommunicationEvent(String toCommunicationEvent) {
        this.setToCommunicationEvent(toCommunicationEvent);
        return this;
    }

    public void setToCommunicationEvent(String toCommunicationEvent) {
        this.toCommunicationEvent = toCommunicationEvent;
    }

    public Long getPartyIdFrom() {
        return this.partyIdFrom;
    }

    public PartyRole partyIdFrom(Long partyIdFrom) {
        this.setPartyIdFrom(partyIdFrom);
        return this;
    }

    public void setPartyIdFrom(Long partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }

    public Long getPartyIdTO() {
        return this.partyIdTO;
    }

    public PartyRole partyIdTO(Long partyIdTO) {
        this.setPartyIdTO(partyIdTO);
        return this;
    }

    public void setPartyIdTO(Long partyIdTO) {
        this.partyIdTO = partyIdTO;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PartyRole createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PartyRole createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PartyRole updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PartyRole updatedOn(ZonedDateTime updatedOn) {
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
            this.parties.forEach(i -> i.setPartyRoleID(null));
        }
        if (parties != null) {
            parties.forEach(i -> i.setPartyRoleID(this));
        }
        this.parties = parties;
    }

    public PartyRole parties(Set<Party> parties) {
        this.setParties(parties);
        return this;
    }

    public PartyRole addParty(Party party) {
        this.parties.add(party);
        party.setPartyRoleID(this);
        return this;
    }

    public PartyRole removeParty(Party party) {
        this.parties.remove(party);
        party.setPartyRoleID(null);
        return this;
    }

    public Set<RoleType> getRoleTypes() {
        return this.roleTypes;
    }

    public void setRoleTypes(Set<RoleType> roleTypes) {
        if (this.roleTypes != null) {
            this.roleTypes.forEach(i -> i.setPartyRoleID(null));
        }
        if (roleTypes != null) {
            roleTypes.forEach(i -> i.setPartyRoleID(this));
        }
        this.roleTypes = roleTypes;
    }

    public PartyRole roleTypes(Set<RoleType> roleTypes) {
        this.setRoleTypes(roleTypes);
        return this;
    }

    public PartyRole addRoleType(RoleType roleType) {
        this.roleTypes.add(roleType);
        roleType.setPartyRoleID(this);
        return this;
    }

    public PartyRole removeRoleType(RoleType roleType) {
        this.roleTypes.remove(roleType);
        roleType.setPartyRoleID(null);
        return this;
    }

    public RoleType getRoleTypeID() {
        return this.roleTypeID;
    }

    public void setRoleTypeID(RoleType roleType) {
        this.roleTypeID = roleType;
    }

    public PartyRole roleTypeID(RoleType roleType) {
        this.setRoleTypeID(roleType);
        return this;
    }

    public Party getPartyID() {
        return this.partyID;
    }

    public void setPartyID(Party party) {
        this.partyID = party;
    }

    public PartyRole partyID(Party party) {
        this.setPartyID(party);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyRole)) {
            return false;
        }
        return id != null && id.equals(((PartyRole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyRole{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", fromAgreement='" + getFromAgreement() + "'" +
            ", toAgreement='" + getToAgreement() + "'" +
            ", fromCommunicationEvent='" + getFromCommunicationEvent() + "'" +
            ", toCommunicationEvent='" + getToCommunicationEvent() + "'" +
            ", partyIdFrom=" + getPartyIdFrom() +
            ", partyIdTO=" + getPartyIdTO() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
