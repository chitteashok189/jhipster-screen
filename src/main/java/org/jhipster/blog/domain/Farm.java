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
import org.jhipster.blog.domain.enumeration.FarmType;

/**
 * A Farm.
 */
@Entity
@Table(name = "farm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Farm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "farm_name")
    private String farmName;

    @Enumerated(EnumType.STRING)
    @Column(name = "farm_type")
    private FarmType farmType;

    @Column(name = "farm_description")
    private String farmDescription;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "farmID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "zones",
            "devices",
            "climates",
            "irrigations",
            "dosings",
            "lights",
            "crops",
            "calendars",
            "scoutings",
            "pests",
            "diseases",
            "farmID",
            "deviceID",
        },
        allowSetters = true
    )
    private Set<PlantFactory> plantFactories = new HashSet<>();

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "farms", "sensors" }, allowSetters = true)
    private Location locationID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Farm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Farm gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getFarmName() {
        return this.farmName;
    }

    public Farm farmName(String farmName) {
        this.setFarmName(farmName);
        return this;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public FarmType getFarmType() {
        return this.farmType;
    }

    public Farm farmType(FarmType farmType) {
        this.setFarmType(farmType);
        return this;
    }

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public String getFarmDescription() {
        return this.farmDescription;
    }

    public Farm farmDescription(String farmDescription) {
        this.setFarmDescription(farmDescription);
        return this;
    }

    public void setFarmDescription(String farmDescription) {
        this.farmDescription = farmDescription;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Farm createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Farm createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Farm updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Farm updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PlantFactory> getPlantFactories() {
        return this.plantFactories;
    }

    public void setPlantFactories(Set<PlantFactory> plantFactories) {
        if (this.plantFactories != null) {
            this.plantFactories.forEach(i -> i.setFarmID(null));
        }
        if (plantFactories != null) {
            plantFactories.forEach(i -> i.setFarmID(this));
        }
        this.plantFactories = plantFactories;
    }

    public Farm plantFactories(Set<PlantFactory> plantFactories) {
        this.setPlantFactories(plantFactories);
        return this;
    }

    public Farm addPlantFactory(PlantFactory plantFactory) {
        this.plantFactories.add(plantFactory);
        plantFactory.setFarmID(this);
        return this;
    }

    public Farm removePlantFactory(PlantFactory plantFactory) {
        this.plantFactories.remove(plantFactory);
        plantFactory.setFarmID(null);
        return this;
    }

    public Party getPartyID() {
        return this.partyID;
    }

    public void setPartyID(Party party) {
        this.partyID = party;
    }

    public Farm partyID(Party party) {
        this.setPartyID(party);
        return this;
    }

    public Location getLocationID() {
        return this.locationID;
    }

    public void setLocationID(Location location) {
        this.locationID = location;
    }

    public Farm locationID(Location location) {
        this.setLocationID(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Farm)) {
            return false;
        }
        return id != null && id.equals(((Farm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Farm{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", farmName='" + getFarmName() + "'" +
            ", farmType='" + getFarmType() + "'" +
            ", farmDescription='" + getFarmDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
