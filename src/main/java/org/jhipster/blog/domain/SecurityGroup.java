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
 * A SecurityGroup.
 */
@Entity
@Table(name = "security_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "securityGroupID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partyRelationshipTypes", "partyRelationshipTypeID", "securityGroupID" }, allowSetters = true)
    private Set<PartyRelationship> partyRelationships = new HashSet<>();

    @OneToMany(mappedBy = "securityGroupID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityGroups", "securityGroupID" }, allowSetters = true)
    private Set<ApplicationUserSecurityGroup> applicationUserSecurityGroups = new HashSet<>();

    @OneToMany(mappedBy = "securityGroupID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "securityGroups", "securityPermissions", "securityGroupID", "securityPermissionID" },
        allowSetters = true
    )
    private Set<SecurityGroupPermission> securityGroupPermissions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityGroups", "securityGroupID" }, allowSetters = true)
    private ApplicationUserSecurityGroup applicationUserSecurityGroupID;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "securityGroups", "securityPermissions", "securityGroupID", "securityPermissionID" },
        allowSetters = true
    )
    private SecurityGroupPermission securityGroupPermissionID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecurityGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public SecurityGroup gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getDescription() {
        return this.description;
    }

    public SecurityGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public SecurityGroup createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public SecurityGroup createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public SecurityGroup updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public SecurityGroup updatedOn(ZonedDateTime updatedOn) {
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
            this.partyRelationships.forEach(i -> i.setSecurityGroupID(null));
        }
        if (partyRelationships != null) {
            partyRelationships.forEach(i -> i.setSecurityGroupID(this));
        }
        this.partyRelationships = partyRelationships;
    }

    public SecurityGroup partyRelationships(Set<PartyRelationship> partyRelationships) {
        this.setPartyRelationships(partyRelationships);
        return this;
    }

    public SecurityGroup addPartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationships.add(partyRelationship);
        partyRelationship.setSecurityGroupID(this);
        return this;
    }

    public SecurityGroup removePartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationships.remove(partyRelationship);
        partyRelationship.setSecurityGroupID(null);
        return this;
    }

    public Set<ApplicationUserSecurityGroup> getApplicationUserSecurityGroups() {
        return this.applicationUserSecurityGroups;
    }

    public void setApplicationUserSecurityGroups(Set<ApplicationUserSecurityGroup> applicationUserSecurityGroups) {
        if (this.applicationUserSecurityGroups != null) {
            this.applicationUserSecurityGroups.forEach(i -> i.setSecurityGroupID(null));
        }
        if (applicationUserSecurityGroups != null) {
            applicationUserSecurityGroups.forEach(i -> i.setSecurityGroupID(this));
        }
        this.applicationUserSecurityGroups = applicationUserSecurityGroups;
    }

    public SecurityGroup applicationUserSecurityGroups(Set<ApplicationUserSecurityGroup> applicationUserSecurityGroups) {
        this.setApplicationUserSecurityGroups(applicationUserSecurityGroups);
        return this;
    }

    public SecurityGroup addApplicationUserSecurityGroup(ApplicationUserSecurityGroup applicationUserSecurityGroup) {
        this.applicationUserSecurityGroups.add(applicationUserSecurityGroup);
        applicationUserSecurityGroup.setSecurityGroupID(this);
        return this;
    }

    public SecurityGroup removeApplicationUserSecurityGroup(ApplicationUserSecurityGroup applicationUserSecurityGroup) {
        this.applicationUserSecurityGroups.remove(applicationUserSecurityGroup);
        applicationUserSecurityGroup.setSecurityGroupID(null);
        return this;
    }

    public Set<SecurityGroupPermission> getSecurityGroupPermissions() {
        return this.securityGroupPermissions;
    }

    public void setSecurityGroupPermissions(Set<SecurityGroupPermission> securityGroupPermissions) {
        if (this.securityGroupPermissions != null) {
            this.securityGroupPermissions.forEach(i -> i.setSecurityGroupID(null));
        }
        if (securityGroupPermissions != null) {
            securityGroupPermissions.forEach(i -> i.setSecurityGroupID(this));
        }
        this.securityGroupPermissions = securityGroupPermissions;
    }

    public SecurityGroup securityGroupPermissions(Set<SecurityGroupPermission> securityGroupPermissions) {
        this.setSecurityGroupPermissions(securityGroupPermissions);
        return this;
    }

    public SecurityGroup addSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) {
        this.securityGroupPermissions.add(securityGroupPermission);
        securityGroupPermission.setSecurityGroupID(this);
        return this;
    }

    public SecurityGroup removeSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) {
        this.securityGroupPermissions.remove(securityGroupPermission);
        securityGroupPermission.setSecurityGroupID(null);
        return this;
    }

    public ApplicationUserSecurityGroup getApplicationUserSecurityGroupID() {
        return this.applicationUserSecurityGroupID;
    }

    public void setApplicationUserSecurityGroupID(ApplicationUserSecurityGroup applicationUserSecurityGroup) {
        this.applicationUserSecurityGroupID = applicationUserSecurityGroup;
    }

    public SecurityGroup applicationUserSecurityGroupID(ApplicationUserSecurityGroup applicationUserSecurityGroup) {
        this.setApplicationUserSecurityGroupID(applicationUserSecurityGroup);
        return this;
    }

    public SecurityGroupPermission getSecurityGroupPermissionID() {
        return this.securityGroupPermissionID;
    }

    public void setSecurityGroupPermissionID(SecurityGroupPermission securityGroupPermission) {
        this.securityGroupPermissionID = securityGroupPermission;
    }

    public SecurityGroup securityGroupPermissionID(SecurityGroupPermission securityGroupPermission) {
        this.setSecurityGroupPermissionID(securityGroupPermission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityGroup)) {
            return false;
        }
        return id != null && id.equals(((SecurityGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityGroup{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
