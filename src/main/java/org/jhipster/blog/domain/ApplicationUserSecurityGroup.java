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
 * A ApplicationUserSecurityGroup.
 */
@Entity
@Table(name = "application_user_security_grou")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationUserSecurityGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "thru_date")
    private Instant thruDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "applicationUserSecurityGroupID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<SecurityGroup> securityGroups = new HashSet<>();

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

    public ApplicationUserSecurityGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public ApplicationUserSecurityGroup gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Instant getFromDate() {
        return this.fromDate;
    }

    public ApplicationUserSecurityGroup fromDate(Instant fromDate) {
        this.setFromDate(fromDate);
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getThruDate() {
        return this.thruDate;
    }

    public ApplicationUserSecurityGroup thruDate(Instant thruDate) {
        this.setThruDate(thruDate);
        return this;
    }

    public void setThruDate(Instant thruDate) {
        this.thruDate = thruDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ApplicationUserSecurityGroup createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public ApplicationUserSecurityGroup createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public ApplicationUserSecurityGroup updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public ApplicationUserSecurityGroup updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<SecurityGroup> getSecurityGroups() {
        return this.securityGroups;
    }

    public void setSecurityGroups(Set<SecurityGroup> securityGroups) {
        if (this.securityGroups != null) {
            this.securityGroups.forEach(i -> i.setApplicationUserSecurityGroupID(null));
        }
        if (securityGroups != null) {
            securityGroups.forEach(i -> i.setApplicationUserSecurityGroupID(this));
        }
        this.securityGroups = securityGroups;
    }

    public ApplicationUserSecurityGroup securityGroups(Set<SecurityGroup> securityGroups) {
        this.setSecurityGroups(securityGroups);
        return this;
    }

    public ApplicationUserSecurityGroup addSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroups.add(securityGroup);
        securityGroup.setApplicationUserSecurityGroupID(this);
        return this;
    }

    public ApplicationUserSecurityGroup removeSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroups.remove(securityGroup);
        securityGroup.setApplicationUserSecurityGroupID(null);
        return this;
    }

    public SecurityGroup getSecurityGroupID() {
        return this.securityGroupID;
    }

    public void setSecurityGroupID(SecurityGroup securityGroup) {
        this.securityGroupID = securityGroup;
    }

    public ApplicationUserSecurityGroup securityGroupID(SecurityGroup securityGroup) {
        this.setSecurityGroupID(securityGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserSecurityGroup)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUserSecurityGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserSecurityGroup{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
