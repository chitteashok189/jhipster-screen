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
 * A SecurityPermission.
 */
@Entity
@Table(name = "security_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityPermission implements Serializable {

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

    @OneToMany(mappedBy = "securityPermissionID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "securityGroups", "securityPermissions", "securityGroupID", "securityPermissionID" },
        allowSetters = true
    )
    private Set<SecurityGroupPermission> securityGroupPermissions = new HashSet<>();

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

    public SecurityPermission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public SecurityPermission gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getDescription() {
        return this.description;
    }

    public SecurityPermission description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public SecurityPermission createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public SecurityPermission createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public SecurityPermission updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public SecurityPermission updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<SecurityGroupPermission> getSecurityGroupPermissions() {
        return this.securityGroupPermissions;
    }

    public void setSecurityGroupPermissions(Set<SecurityGroupPermission> securityGroupPermissions) {
        if (this.securityGroupPermissions != null) {
            this.securityGroupPermissions.forEach(i -> i.setSecurityPermissionID(null));
        }
        if (securityGroupPermissions != null) {
            securityGroupPermissions.forEach(i -> i.setSecurityPermissionID(this));
        }
        this.securityGroupPermissions = securityGroupPermissions;
    }

    public SecurityPermission securityGroupPermissions(Set<SecurityGroupPermission> securityGroupPermissions) {
        this.setSecurityGroupPermissions(securityGroupPermissions);
        return this;
    }

    public SecurityPermission addSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) {
        this.securityGroupPermissions.add(securityGroupPermission);
        securityGroupPermission.setSecurityPermissionID(this);
        return this;
    }

    public SecurityPermission removeSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) {
        this.securityGroupPermissions.remove(securityGroupPermission);
        securityGroupPermission.setSecurityPermissionID(null);
        return this;
    }

    public SecurityGroupPermission getSecurityGroupPermissionID() {
        return this.securityGroupPermissionID;
    }

    public void setSecurityGroupPermissionID(SecurityGroupPermission securityGroupPermission) {
        this.securityGroupPermissionID = securityGroupPermission;
    }

    public SecurityPermission securityGroupPermissionID(SecurityGroupPermission securityGroupPermission) {
        this.setSecurityGroupPermissionID(securityGroupPermission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityPermission)) {
            return false;
        }
        return id != null && id.equals(((SecurityPermission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityPermission{" +
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
