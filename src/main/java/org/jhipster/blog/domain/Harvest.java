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
 * A Harvest.
 */
@Entity
@Table(name = "harvest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Harvest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "harvesting_date")
    private Instant harvestingDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "harvestID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionRecords", "harvestID" }, allowSetters = true)
    private Set<Inspection> inspections = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "plants",
            "calendars",
            "scoutings",
            "pestControls",
            "diseaseControls",
            "activities",
            "harvests",
            "lots",
            "plantID",
            "plantFactoryID",
            "toolID",
            "seasonID",
            "productID",
        },
        allowSetters = true
    )
    private Crop cropID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "harvests", "inspectionRecords", "seedID", "cropID" }, allowSetters = true)
    private Lot lotID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Harvest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Harvest gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Instant getHarvestingDate() {
        return this.harvestingDate;
    }

    public Harvest harvestingDate(Instant harvestingDate) {
        this.setHarvestingDate(harvestingDate);
        return this;
    }

    public void setHarvestingDate(Instant harvestingDate) {
        this.harvestingDate = harvestingDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Harvest createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Harvest createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Harvest updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Harvest updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Inspection> getInspections() {
        return this.inspections;
    }

    public void setInspections(Set<Inspection> inspections) {
        if (this.inspections != null) {
            this.inspections.forEach(i -> i.setHarvestID(null));
        }
        if (inspections != null) {
            inspections.forEach(i -> i.setHarvestID(this));
        }
        this.inspections = inspections;
    }

    public Harvest inspections(Set<Inspection> inspections) {
        this.setInspections(inspections);
        return this;
    }

    public Harvest addInspection(Inspection inspection) {
        this.inspections.add(inspection);
        inspection.setHarvestID(this);
        return this;
    }

    public Harvest removeInspection(Inspection inspection) {
        this.inspections.remove(inspection);
        inspection.setHarvestID(null);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public Harvest cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    public Lot getLotID() {
        return this.lotID;
    }

    public void setLotID(Lot lot) {
        this.lotID = lot;
    }

    public Harvest lotID(Lot lot) {
        this.setLotID(lot);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Harvest)) {
            return false;
        }
        return id != null && id.equals(((Harvest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Harvest{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", harvestingDate='" + getHarvestingDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
