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
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "personal_title")
    private String personalTitle;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "first_name_local")
    private String firstNameLocal;

    @Column(name = "middle_name_local")
    private String middleNameLocal;

    @Column(name = "last_name_local")
    private String lastNameLocal;

    @Column(name = "other_local")
    private String otherLocal;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Column(name = "heigth")
    private Integer heigth;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "mothers_maiden_name")
    private String mothersMaidenName;

    @Column(name = "maritial_status")
    private String maritialStatus;

    @Column(name = "social_security_no")
    private Integer socialSecurityNo;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "passport_expiry_date")
    private String passportExpiryDate;

    @Column(name = "total_years_work_experience")
    private Integer totalYearsWorkExperience;

    @Column(name = "comments")
    private String comments;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "yearswith_employer")
    private Integer yearswithEmployer;

    @Column(name = "months_with_employer")
    private Integer monthsWithEmployer;

    @Column(name = "existing_customer")
    private Integer existingCustomer;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "personID")
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

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Person gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getSalutation() {
        return this.salutation;
    }

    public Person salutation(String salutation) {
        this.setSalutation(salutation);
        return this;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Person firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Person middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Person lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalTitle() {
        return this.personalTitle;
    }

    public Person personalTitle(String personalTitle) {
        this.setPersonalTitle(personalTitle);
        return this;
    }

    public void setPersonalTitle(String personalTitle) {
        this.personalTitle = personalTitle;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public Person suffix(String suffix) {
        this.setSuffix(suffix);
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getNickName() {
        return this.nickName;
    }

    public Person nickName(String nickName) {
        this.setNickName(nickName);
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstNameLocal() {
        return this.firstNameLocal;
    }

    public Person firstNameLocal(String firstNameLocal) {
        this.setFirstNameLocal(firstNameLocal);
        return this;
    }

    public void setFirstNameLocal(String firstNameLocal) {
        this.firstNameLocal = firstNameLocal;
    }

    public String getMiddleNameLocal() {
        return this.middleNameLocal;
    }

    public Person middleNameLocal(String middleNameLocal) {
        this.setMiddleNameLocal(middleNameLocal);
        return this;
    }

    public void setMiddleNameLocal(String middleNameLocal) {
        this.middleNameLocal = middleNameLocal;
    }

    public String getLastNameLocal() {
        return this.lastNameLocal;
    }

    public Person lastNameLocal(String lastNameLocal) {
        this.setLastNameLocal(lastNameLocal);
        return this;
    }

    public void setLastNameLocal(String lastNameLocal) {
        this.lastNameLocal = lastNameLocal;
    }

    public String getOtherLocal() {
        return this.otherLocal;
    }

    public Person otherLocal(String otherLocal) {
        this.setOtherLocal(otherLocal);
        return this;
    }

    public void setOtherLocal(String otherLocal) {
        this.otherLocal = otherLocal;
    }

    public String getGender() {
        return this.gender;
    }

    public Person gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Instant getBirthDate() {
        return this.birthDate;
    }

    public Person birthDate(Instant birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getHeigth() {
        return this.heigth;
    }

    public Person heigth(Integer heigth) {
        this.setHeigth(heigth);
        return this;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public Person weight(Integer weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getMothersMaidenName() {
        return this.mothersMaidenName;
    }

    public Person mothersMaidenName(String mothersMaidenName) {
        this.setMothersMaidenName(mothersMaidenName);
        return this;
    }

    public void setMothersMaidenName(String mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }

    public String getMaritialStatus() {
        return this.maritialStatus;
    }

    public Person maritialStatus(String maritialStatus) {
        this.setMaritialStatus(maritialStatus);
        return this;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public Integer getSocialSecurityNo() {
        return this.socialSecurityNo;
    }

    public Person socialSecurityNo(Integer socialSecurityNo) {
        this.setSocialSecurityNo(socialSecurityNo);
        return this;
    }

    public void setSocialSecurityNo(Integer socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    public String getPassportNo() {
        return this.passportNo;
    }

    public Person passportNo(String passportNo) {
        this.setPassportNo(passportNo);
        return this;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportExpiryDate() {
        return this.passportExpiryDate;
    }

    public Person passportExpiryDate(String passportExpiryDate) {
        this.setPassportExpiryDate(passportExpiryDate);
        return this;
    }

    public void setPassportExpiryDate(String passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public Integer getTotalYearsWorkExperience() {
        return this.totalYearsWorkExperience;
    }

    public Person totalYearsWorkExperience(Integer totalYearsWorkExperience) {
        this.setTotalYearsWorkExperience(totalYearsWorkExperience);
        return this;
    }

    public void setTotalYearsWorkExperience(Integer totalYearsWorkExperience) {
        this.totalYearsWorkExperience = totalYearsWorkExperience;
    }

    public String getComments() {
        return this.comments;
    }

    public Person comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public Person occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getYearswithEmployer() {
        return this.yearswithEmployer;
    }

    public Person yearswithEmployer(Integer yearswithEmployer) {
        this.setYearswithEmployer(yearswithEmployer);
        return this;
    }

    public void setYearswithEmployer(Integer yearswithEmployer) {
        this.yearswithEmployer = yearswithEmployer;
    }

    public Integer getMonthsWithEmployer() {
        return this.monthsWithEmployer;
    }

    public Person monthsWithEmployer(Integer monthsWithEmployer) {
        this.setMonthsWithEmployer(monthsWithEmployer);
        return this;
    }

    public void setMonthsWithEmployer(Integer monthsWithEmployer) {
        this.monthsWithEmployer = monthsWithEmployer;
    }

    public Integer getExistingCustomer() {
        return this.existingCustomer;
    }

    public Person existingCustomer(Integer existingCustomer) {
        this.setExistingCustomer(existingCustomer);
        return this;
    }

    public void setExistingCustomer(Integer existingCustomer) {
        this.existingCustomer = existingCustomer;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Person createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Person createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Person updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Person updatedOn(ZonedDateTime updatedOn) {
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
            this.parties.forEach(i -> i.setPersonID(null));
        }
        if (parties != null) {
            parties.forEach(i -> i.setPersonID(this));
        }
        this.parties = parties;
    }

    public Person parties(Set<Party> parties) {
        this.setParties(parties);
        return this;
    }

    public Person addParty(Party party) {
        this.parties.add(party);
        party.setPersonID(this);
        return this;
    }

    public Person removeParty(Party party) {
        this.parties.remove(party);
        party.setPersonID(null);
        return this;
    }

    public Party getPartyID() {
        return this.partyID;
    }

    public void setPartyID(Party party) {
        this.partyID = party;
    }

    public Person partyID(Party party) {
        this.setPartyID(party);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", salutation='" + getSalutation() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", personalTitle='" + getPersonalTitle() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", firstNameLocal='" + getFirstNameLocal() + "'" +
            ", middleNameLocal='" + getMiddleNameLocal() + "'" +
            ", lastNameLocal='" + getLastNameLocal() + "'" +
            ", otherLocal='" + getOtherLocal() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", heigth=" + getHeigth() +
            ", weight=" + getWeight() +
            ", mothersMaidenName='" + getMothersMaidenName() + "'" +
            ", maritialStatus='" + getMaritialStatus() + "'" +
            ", socialSecurityNo=" + getSocialSecurityNo() +
            ", passportNo='" + getPassportNo() + "'" +
            ", passportExpiryDate='" + getPassportExpiryDate() + "'" +
            ", totalYearsWorkExperience=" + getTotalYearsWorkExperience() +
            ", comments='" + getComments() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", yearswithEmployer=" + getYearswithEmployer() +
            ", monthsWithEmployer=" + getMonthsWithEmployer() +
            ", existingCustomer=" + getExistingCustomer() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
