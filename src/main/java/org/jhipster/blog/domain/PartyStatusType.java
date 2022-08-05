package org.jhipster.blog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartyStatusType.
 */
@Entity
@Table(name = "party_status_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyStatusType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "parent_type_id")
    private Long parentTypeID;

    @Column(name = "has_table")
    private Boolean hasTable;

    @Column(name = "description")
    private String description;

    @Column(name = "child_status_type")
    private Long childStatusType;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartyStatusType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PartyStatusType gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Long getParentTypeID() {
        return this.parentTypeID;
    }

    public PartyStatusType parentTypeID(Long parentTypeID) {
        this.setParentTypeID(parentTypeID);
        return this;
    }

    public void setParentTypeID(Long parentTypeID) {
        this.parentTypeID = parentTypeID;
    }

    public Boolean getHasTable() {
        return this.hasTable;
    }

    public PartyStatusType hasTable(Boolean hasTable) {
        this.setHasTable(hasTable);
        return this;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public String getDescription() {
        return this.description;
    }

    public PartyStatusType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getChildStatusType() {
        return this.childStatusType;
    }

    public PartyStatusType childStatusType(Long childStatusType) {
        this.setChildStatusType(childStatusType);
        return this;
    }

    public void setChildStatusType(Long childStatusType) {
        this.childStatusType = childStatusType;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PartyStatusType createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PartyStatusType createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PartyStatusType updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PartyStatusType updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyStatusType)) {
            return false;
        }
        return id != null && id.equals(((PartyStatusType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyStatusType{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", parentTypeID=" + getParentTypeID() +
            ", hasTable='" + getHasTable() + "'" +
            ", description='" + getDescription() + "'" +
            ", childStatusType=" + getChildStatusType() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
