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
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "status_id")
    private Boolean statusID;

    @Column(name = "description")
    private String description;

    @Column(name = "external_id")
    private Integer externalID;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyID" }, allowSetters = true)
    private Set<PartyAttribute> partyAttributes = new HashSet<>();

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyID" }, allowSetters = true)
    private Set<PartyClassification> partyClassifications = new HashSet<>();

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parties", "roleTypes", "roleTypeID", "partyID" }, allowSetters = true)
    private Set<PartyRole> partyRoles = new HashSet<>();

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyID" }, allowSetters = true)
    private Set<PartyNote> partyNotes = new HashSet<>();

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parties", "partyID" }, allowSetters = true)
    private Set<Person> people = new HashSet<>();

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parties", "partyID" }, allowSetters = true)
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

    @OneToMany(mappedBy = "partyID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plantFactories", "partyID", "locationID" }, allowSetters = true)
    private Set<Farm> farms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "parties" }, allowSetters = true)
    private PartyGroup partyGroupID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parties" }, allowSetters = true)
    private PartyType partyTypeID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parties", "partyID" }, allowSetters = true)
    private ApplicationUser applicationUserID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parties", "roleTypes", "roleTypeID", "partyID" }, allowSetters = true)
    private PartyRole partyRoleID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parties", "partyID" }, allowSetters = true)
    private Person personID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Party id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Party gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getPartyName() {
        return this.partyName;
    }

    public Party partyName(String partyName) {
        this.setPartyName(partyName);
        return this;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Boolean getStatusID() {
        return this.statusID;
    }

    public Party statusID(Boolean statusID) {
        this.setStatusID(statusID);
        return this;
    }

    public void setStatusID(Boolean statusID) {
        this.statusID = statusID;
    }

    public String getDescription() {
        return this.description;
    }

    public Party description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExternalID() {
        return this.externalID;
    }

    public Party externalID(Integer externalID) {
        this.setExternalID(externalID);
        return this;
    }

    public void setExternalID(Integer externalID) {
        this.externalID = externalID;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Party createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Party createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Party updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Party updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PartyAttribute> getPartyAttributes() {
        return this.partyAttributes;
    }

    public void setPartyAttributes(Set<PartyAttribute> partyAttributes) {
        if (this.partyAttributes != null) {
            this.partyAttributes.forEach(i -> i.setPartyID(null));
        }
        if (partyAttributes != null) {
            partyAttributes.forEach(i -> i.setPartyID(this));
        }
        this.partyAttributes = partyAttributes;
    }

    public Party partyAttributes(Set<PartyAttribute> partyAttributes) {
        this.setPartyAttributes(partyAttributes);
        return this;
    }

    public Party addPartyAttribute(PartyAttribute partyAttribute) {
        this.partyAttributes.add(partyAttribute);
        partyAttribute.setPartyID(this);
        return this;
    }

    public Party removePartyAttribute(PartyAttribute partyAttribute) {
        this.partyAttributes.remove(partyAttribute);
        partyAttribute.setPartyID(null);
        return this;
    }

    public Set<PartyClassification> getPartyClassifications() {
        return this.partyClassifications;
    }

    public void setPartyClassifications(Set<PartyClassification> partyClassifications) {
        if (this.partyClassifications != null) {
            this.partyClassifications.forEach(i -> i.setPartyID(null));
        }
        if (partyClassifications != null) {
            partyClassifications.forEach(i -> i.setPartyID(this));
        }
        this.partyClassifications = partyClassifications;
    }

    public Party partyClassifications(Set<PartyClassification> partyClassifications) {
        this.setPartyClassifications(partyClassifications);
        return this;
    }

    public Party addPartyClassification(PartyClassification partyClassification) {
        this.partyClassifications.add(partyClassification);
        partyClassification.setPartyID(this);
        return this;
    }

    public Party removePartyClassification(PartyClassification partyClassification) {
        this.partyClassifications.remove(partyClassification);
        partyClassification.setPartyID(null);
        return this;
    }

    public Set<PartyRole> getPartyRoles() {
        return this.partyRoles;
    }

    public void setPartyRoles(Set<PartyRole> partyRoles) {
        if (this.partyRoles != null) {
            this.partyRoles.forEach(i -> i.setPartyID(null));
        }
        if (partyRoles != null) {
            partyRoles.forEach(i -> i.setPartyID(this));
        }
        this.partyRoles = partyRoles;
    }

    public Party partyRoles(Set<PartyRole> partyRoles) {
        this.setPartyRoles(partyRoles);
        return this;
    }

    public Party addPartyRole(PartyRole partyRole) {
        this.partyRoles.add(partyRole);
        partyRole.setPartyID(this);
        return this;
    }

    public Party removePartyRole(PartyRole partyRole) {
        this.partyRoles.remove(partyRole);
        partyRole.setPartyID(null);
        return this;
    }

    public Set<PartyNote> getPartyNotes() {
        return this.partyNotes;
    }

    public void setPartyNotes(Set<PartyNote> partyNotes) {
        if (this.partyNotes != null) {
            this.partyNotes.forEach(i -> i.setPartyID(null));
        }
        if (partyNotes != null) {
            partyNotes.forEach(i -> i.setPartyID(this));
        }
        this.partyNotes = partyNotes;
    }

    public Party partyNotes(Set<PartyNote> partyNotes) {
        this.setPartyNotes(partyNotes);
        return this;
    }

    public Party addPartyNote(PartyNote partyNote) {
        this.partyNotes.add(partyNote);
        partyNote.setPartyID(this);
        return this;
    }

    public Party removePartyNote(PartyNote partyNote) {
        this.partyNotes.remove(partyNote);
        partyNote.setPartyID(null);
        return this;
    }

    public Set<Person> getPeople() {
        return this.people;
    }

    public void setPeople(Set<Person> people) {
        if (this.people != null) {
            this.people.forEach(i -> i.setPartyID(null));
        }
        if (people != null) {
            people.forEach(i -> i.setPartyID(this));
        }
        this.people = people;
    }

    public Party people(Set<Person> people) {
        this.setPeople(people);
        return this;
    }

    public Party addPerson(Person person) {
        this.people.add(person);
        person.setPartyID(this);
        return this;
    }

    public Party removePerson(Person person) {
        this.people.remove(person);
        person.setPartyID(null);
        return this;
    }

    public Set<ApplicationUser> getApplicationUsers() {
        return this.applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        if (this.applicationUsers != null) {
            this.applicationUsers.forEach(i -> i.setPartyID(null));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.setPartyID(this));
        }
        this.applicationUsers = applicationUsers;
    }

    public Party applicationUsers(Set<ApplicationUser> applicationUsers) {
        this.setApplicationUsers(applicationUsers);
        return this;
    }

    public Party addApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.add(applicationUser);
        applicationUser.setPartyID(this);
        return this;
    }

    public Party removeApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.remove(applicationUser);
        applicationUser.setPartyID(null);
        return this;
    }

    public Set<Farm> getFarms() {
        return this.farms;
    }

    public void setFarms(Set<Farm> farms) {
        if (this.farms != null) {
            this.farms.forEach(i -> i.setPartyID(null));
        }
        if (farms != null) {
            farms.forEach(i -> i.setPartyID(this));
        }
        this.farms = farms;
    }

    public Party farms(Set<Farm> farms) {
        this.setFarms(farms);
        return this;
    }

    public Party addFarm(Farm farm) {
        this.farms.add(farm);
        farm.setPartyID(this);
        return this;
    }

    public Party removeFarm(Farm farm) {
        this.farms.remove(farm);
        farm.setPartyID(null);
        return this;
    }

    public PartyGroup getPartyGroupID() {
        return this.partyGroupID;
    }

    public void setPartyGroupID(PartyGroup partyGroup) {
        this.partyGroupID = partyGroup;
    }

    public Party partyGroupID(PartyGroup partyGroup) {
        this.setPartyGroupID(partyGroup);
        return this;
    }

    public PartyType getPartyTypeID() {
        return this.partyTypeID;
    }

    public void setPartyTypeID(PartyType partyType) {
        this.partyTypeID = partyType;
    }

    public Party partyTypeID(PartyType partyType) {
        this.setPartyTypeID(partyType);
        return this;
    }

    public ApplicationUser getApplicationUserID() {
        return this.applicationUserID;
    }

    public void setApplicationUserID(ApplicationUser applicationUser) {
        this.applicationUserID = applicationUser;
    }

    public Party applicationUserID(ApplicationUser applicationUser) {
        this.setApplicationUserID(applicationUser);
        return this;
    }

    public PartyRole getPartyRoleID() {
        return this.partyRoleID;
    }

    public void setPartyRoleID(PartyRole partyRole) {
        this.partyRoleID = partyRole;
    }

    public Party partyRoleID(PartyRole partyRole) {
        this.setPartyRoleID(partyRole);
        return this;
    }

    public Person getPersonID() {
        return this.personID;
    }

    public void setPersonID(Person person) {
        this.personID = person;
    }

    public Party personID(Person person) {
        this.setPersonID(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Party)) {
            return false;
        }
        return id != null && id.equals(((Party) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Party{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", partyName='" + getPartyName() + "'" +
            ", statusID='" + getStatusID() + "'" +
            ", description='" + getDescription() + "'" +
            ", externalID=" + getExternalID() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
