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
import org.jhipster.blog.domain.enumeration.GrowBedType;

/**
 * A GrowBed.
 */
@Entity
@Table(name = "grow_bed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GrowBed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "grow_bed_type")
    private GrowBedType growBedType;

    @Column(name = "grow_bed_name")
    private String growBedName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "growBedID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calendars", "activities", "plantFactoryID", "growBedID" }, allowSetters = true)
    private Set<Zone> zones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrowBed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public GrowBed gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public GrowBedType getGrowBedType() {
        return this.growBedType;
    }

    public GrowBed growBedType(GrowBedType growBedType) {
        this.setGrowBedType(growBedType);
        return this;
    }

    public void setGrowBedType(GrowBedType growBedType) {
        this.growBedType = growBedType;
    }

    public String getGrowBedName() {
        return this.growBedName;
    }

    public GrowBed growBedName(String growBedName) {
        this.setGrowBedName(growBedName);
        return this;
    }

    public void setGrowBedName(String growBedName) {
        this.growBedName = growBedName;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public GrowBed manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public GrowBed createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public GrowBed createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public GrowBed updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public GrowBed updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Zone> getZones() {
        return this.zones;
    }

    public void setZones(Set<Zone> zones) {
        if (this.zones != null) {
            this.zones.forEach(i -> i.setGrowBedID(null));
        }
        if (zones != null) {
            zones.forEach(i -> i.setGrowBedID(this));
        }
        this.zones = zones;
    }

    public GrowBed zones(Set<Zone> zones) {
        this.setZones(zones);
        return this;
    }

    public GrowBed addZone(Zone zone) {
        this.zones.add(zone);
        zone.setGrowBedID(this);
        return this;
    }

    public GrowBed removeZone(Zone zone) {
        this.zones.remove(zone);
        zone.setGrowBedID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrowBed)) {
            return false;
        }
        return id != null && id.equals(((GrowBed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrowBed{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", growBedType='" + getGrowBedType() + "'" +
            ", growBedName='" + getGrowBedName() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
