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
 * A SecurityGroupPermission.
 */
@Entity
@Table(name = "security_group_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityGroupPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "securityGroupPermissionID")
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

    @OneToMany(mappedBy = "securityGroupPermissionID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityGroupPermissions", "securityGroupPermissionID" }, allowSetters = true)
    private Set<SecurityPermission> securityPermissions = new HashSet<>();

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityGroupPermissions", "securityGroupPermissionID" }, allowSetters = true)
    private SecurityPermission securityPermissionID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecurityGroupPermission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public SecurityGroupPermission gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public SecurityGroupPermission createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public SecurityGroupPermission createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public SecurityGroupPermission updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public SecurityGroupPermission updatedOn(ZonedDateTime updatedOn) {
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
            this.securityGroups.forEach(i -> i.setSecurityGroupPermissionID(null));
        }
        if (securityGroups != null) {
            securityGroups.forEach(i -> i.setSecurityGroupPermissionID(this));
        }
        this.securityGroups = securityGroups;
    }

    public SecurityGroupPermission securityGroups(Set<SecurityGroup> securityGroups) {
        this.setSecurityGroups(securityGroups);
        return this;
    }

    public SecurityGroupPermission addSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroups.add(securityGroup);
        securityGroup.setSecurityGroupPermissionID(this);
        return this;
    }

    public SecurityGroupPermission removeSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroups.remove(securityGroup);
        securityGroup.setSecurityGroupPermissionID(null);
        return this;
    }

    public Set<SecurityPermission> getSecurityPermissions() {
        return this.securityPermissions;
    }

    public void setSecurityPermissions(Set<SecurityPermission> securityPermissions) {
        if (this.securityPermissions != null) {
            this.securityPermissions.forEach(i -> i.setSecurityGroupPermissionID(null));
        }
        if (securityPermissions != null) {
            securityPermissions.forEach(i -> i.setSecurityGroupPermissionID(this));
        }
        this.securityPermissions = securityPermissions;
    }

    public SecurityGroupPermission securityPermissions(Set<SecurityPermission> securityPermissions) {
        this.setSecurityPermissions(securityPermissions);
        return this;
    }

    public SecurityGroupPermission addSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.add(securityPermission);
        securityPermission.setSecurityGroupPermissionID(this);
        return this;
    }

    public SecurityGroupPermission removeSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.remove(securityPermission);
        securityPermission.setSecurityGroupPermissionID(null);
        return this;
    }

    public SecurityGroup getSecurityGroupID() {
        return this.securityGroupID;
    }

    public void setSecurityGroupID(SecurityGroup securityGroup) {
        this.securityGroupID = securityGroup;
    }

    public SecurityGroupPermission securityGroupID(SecurityGroup securityGroup) {
        this.setSecurityGroupID(securityGroup);
        return this;
    }

    public SecurityPermission getSecurityPermissionID() {
        return this.securityPermissionID;
    }

    public void setSecurityPermissionID(SecurityPermission securityPermission) {
        this.securityPermissionID = securityPermission;
    }

    public SecurityGroupPermission securityPermissionID(SecurityPermission securityPermission) {
        this.setSecurityPermissionID(securityPermission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityGroupPermission)) {
            return false;
        }
        return id != null && id.equals(((SecurityGroupPermission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityGroupPermission{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
