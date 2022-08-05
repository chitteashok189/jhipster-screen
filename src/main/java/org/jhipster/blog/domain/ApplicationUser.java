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
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "current_password")
    private String currentPassword;

    @Column(name = "password_hint")
    private String passwordHint;

    @Column(name = "is_system_enables")
    private Boolean isSystemEnables;

    @Column(name = "has_logged_out")
    private Boolean hasLoggedOut;

    @Column(name = "require_password_change")
    private Boolean requirePasswordChange;

    @Column(name = "last_currency_uom")
    private Integer lastCurrencyUom;

    @Column(name = "last_locale")
    private Integer lastLocale;

    @Column(name = "last_time_zone")
    private Integer lastTimeZone;

    @Column(name = "disabled_date_time")
    private Instant disabledDateTime;

    @Column(name = "successive_failed_logins")
    private Integer successiveFailedLogins;

    @Column(name = "application_user_security_group")
    private String applicationUserSecurityGroup;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "applicationUserID")
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

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public ApplicationUser gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getCurrentPassword() {
        return this.currentPassword;
    }

    public ApplicationUser currentPassword(String currentPassword) {
        this.setCurrentPassword(currentPassword);
        return this;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPasswordHint() {
        return this.passwordHint;
    }

    public ApplicationUser passwordHint(String passwordHint) {
        this.setPasswordHint(passwordHint);
        return this;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public Boolean getIsSystemEnables() {
        return this.isSystemEnables;
    }

    public ApplicationUser isSystemEnables(Boolean isSystemEnables) {
        this.setIsSystemEnables(isSystemEnables);
        return this;
    }

    public void setIsSystemEnables(Boolean isSystemEnables) {
        this.isSystemEnables = isSystemEnables;
    }

    public Boolean getHasLoggedOut() {
        return this.hasLoggedOut;
    }

    public ApplicationUser hasLoggedOut(Boolean hasLoggedOut) {
        this.setHasLoggedOut(hasLoggedOut);
        return this;
    }

    public void setHasLoggedOut(Boolean hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }

    public Boolean getRequirePasswordChange() {
        return this.requirePasswordChange;
    }

    public ApplicationUser requirePasswordChange(Boolean requirePasswordChange) {
        this.setRequirePasswordChange(requirePasswordChange);
        return this;
    }

    public void setRequirePasswordChange(Boolean requirePasswordChange) {
        this.requirePasswordChange = requirePasswordChange;
    }

    public Integer getLastCurrencyUom() {
        return this.lastCurrencyUom;
    }

    public ApplicationUser lastCurrencyUom(Integer lastCurrencyUom) {
        this.setLastCurrencyUom(lastCurrencyUom);
        return this;
    }

    public void setLastCurrencyUom(Integer lastCurrencyUom) {
        this.lastCurrencyUom = lastCurrencyUom;
    }

    public Integer getLastLocale() {
        return this.lastLocale;
    }

    public ApplicationUser lastLocale(Integer lastLocale) {
        this.setLastLocale(lastLocale);
        return this;
    }

    public void setLastLocale(Integer lastLocale) {
        this.lastLocale = lastLocale;
    }

    public Integer getLastTimeZone() {
        return this.lastTimeZone;
    }

    public ApplicationUser lastTimeZone(Integer lastTimeZone) {
        this.setLastTimeZone(lastTimeZone);
        return this;
    }

    public void setLastTimeZone(Integer lastTimeZone) {
        this.lastTimeZone = lastTimeZone;
    }

    public Instant getDisabledDateTime() {
        return this.disabledDateTime;
    }

    public ApplicationUser disabledDateTime(Instant disabledDateTime) {
        this.setDisabledDateTime(disabledDateTime);
        return this;
    }

    public void setDisabledDateTime(Instant disabledDateTime) {
        this.disabledDateTime = disabledDateTime;
    }

    public Integer getSuccessiveFailedLogins() {
        return this.successiveFailedLogins;
    }

    public ApplicationUser successiveFailedLogins(Integer successiveFailedLogins) {
        this.setSuccessiveFailedLogins(successiveFailedLogins);
        return this;
    }

    public void setSuccessiveFailedLogins(Integer successiveFailedLogins) {
        this.successiveFailedLogins = successiveFailedLogins;
    }

    public String getApplicationUserSecurityGroup() {
        return this.applicationUserSecurityGroup;
    }

    public ApplicationUser applicationUserSecurityGroup(String applicationUserSecurityGroup) {
        this.setApplicationUserSecurityGroup(applicationUserSecurityGroup);
        return this;
    }

    public void setApplicationUserSecurityGroup(String applicationUserSecurityGroup) {
        this.applicationUserSecurityGroup = applicationUserSecurityGroup;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ApplicationUser createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public ApplicationUser createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public ApplicationUser updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public ApplicationUser updatedOn(ZonedDateTime updatedOn) {
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
            this.parties.forEach(i -> i.setApplicationUserID(null));
        }
        if (parties != null) {
            parties.forEach(i -> i.setApplicationUserID(this));
        }
        this.parties = parties;
    }

    public ApplicationUser parties(Set<Party> parties) {
        this.setParties(parties);
        return this;
    }

    public ApplicationUser addParty(Party party) {
        this.parties.add(party);
        party.setApplicationUserID(this);
        return this;
    }

    public ApplicationUser removeParty(Party party) {
        this.parties.remove(party);
        party.setApplicationUserID(null);
        return this;
    }

    public Party getPartyID() {
        return this.partyID;
    }

    public void setPartyID(Party party) {
        this.partyID = party;
    }

    public ApplicationUser partyID(Party party) {
        this.setPartyID(party);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", currentPassword='" + getCurrentPassword() + "'" +
            ", passwordHint='" + getPasswordHint() + "'" +
            ", isSystemEnables='" + getIsSystemEnables() + "'" +
            ", hasLoggedOut='" + getHasLoggedOut() + "'" +
            ", requirePasswordChange='" + getRequirePasswordChange() + "'" +
            ", lastCurrencyUom=" + getLastCurrencyUom() +
            ", lastLocale=" + getLastLocale() +
            ", lastTimeZone=" + getLastTimeZone() +
            ", disabledDateTime='" + getDisabledDateTime() + "'" +
            ", successiveFailedLogins=" + getSuccessiveFailedLogins() +
            ", applicationUserSecurityGroup='" + getApplicationUserSecurityGroup() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
