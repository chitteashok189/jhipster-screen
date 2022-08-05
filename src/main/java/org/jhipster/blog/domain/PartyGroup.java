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
 * A PartyGroup.
 */
@Entity
@Table(name = "party_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private Long gUID;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "local_group_name")
    private String localGroupName;

    @Column(name = "office_site_name")
    private String officeSiteName;

    @Column(name = "comments")
    private String comments;

    @Column(name = "logo_image_url")
    private String logoImageUrl;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "partyGroupID")
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

    public PartyGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getgUID() {
        return this.gUID;
    }

    public PartyGroup gUID(Long gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(Long gUID) {
        this.gUID = gUID;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public PartyGroup groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLocalGroupName() {
        return this.localGroupName;
    }

    public PartyGroup localGroupName(String localGroupName) {
        this.setLocalGroupName(localGroupName);
        return this;
    }

    public void setLocalGroupName(String localGroupName) {
        this.localGroupName = localGroupName;
    }

    public String getOfficeSiteName() {
        return this.officeSiteName;
    }

    public PartyGroup officeSiteName(String officeSiteName) {
        this.setOfficeSiteName(officeSiteName);
        return this;
    }

    public void setOfficeSiteName(String officeSiteName) {
        this.officeSiteName = officeSiteName;
    }

    public String getComments() {
        return this.comments;
    }

    public PartyGroup comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLogoImageUrl() {
        return this.logoImageUrl;
    }

    public PartyGroup logoImageUrl(String logoImageUrl) {
        this.setLogoImageUrl(logoImageUrl);
        return this;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PartyGroup createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PartyGroup createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PartyGroup updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PartyGroup updatedOn(ZonedDateTime updatedOn) {
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
            this.parties.forEach(i -> i.setPartyGroupID(null));
        }
        if (parties != null) {
            parties.forEach(i -> i.setPartyGroupID(this));
        }
        this.parties = parties;
    }

    public PartyGroup parties(Set<Party> parties) {
        this.setParties(parties);
        return this;
    }

    public PartyGroup addParty(Party party) {
        this.parties.add(party);
        party.setPartyGroupID(this);
        return this;
    }

    public PartyGroup removeParty(Party party) {
        this.parties.remove(party);
        party.setPartyGroupID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyGroup)) {
            return false;
        }
        return id != null && id.equals(((PartyGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyGroup{" +
            "id=" + getId() +
            ", gUID=" + getgUID() +
            ", groupName='" + getGroupName() + "'" +
            ", localGroupName='" + getLocalGroupName() + "'" +
            ", officeSiteName='" + getOfficeSiteName() + "'" +
            ", comments='" + getComments() + "'" +
            ", logoImageUrl='" + getLogoImageUrl() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
