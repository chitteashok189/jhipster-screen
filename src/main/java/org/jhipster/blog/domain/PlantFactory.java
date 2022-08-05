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
import org.jhipster.blog.domain.enumeration.ProFarmType;
import org.jhipster.blog.domain.enumeration.ProSubType;

/**
 * A PlantFactory.
 */
@Entity
@Table(name = "plant_factory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlantFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "plant_factory_name")
    private String plantFactoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_factory_type_id")
    private ProFarmType plantFactoryTypeID;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_factory_sub_type")
    private ProSubType plantFactorySubType;

    @Column(name = "plant_factory_description")
    private String plantFactoryDescription;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calendars", "activities", "plantFactoryID", "growBedID" }, allowSetters = true)
    private Set<Zone> zones = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "plantFactories", "sensors", "climates", "irrigations", "dosings", "lights", "plantFactoryID", "deviceLevelID", "deviceModelID",
        },
        allowSetters = true
    )
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Climate> climates = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Irrigation> irrigations = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Dosing> dosings = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Light> lights = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Crop> crops = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "weathers", "plantFactoryID", "zoneID", "cropID", "toolID", "seasonID" }, allowSetters = true)
    private Set<Calendar> calendars = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pests", "diseases", "symptoms", "disorders", "plantFactoryID", "cropID" }, allowSetters = true)
    private Set<Scouting> scoutings = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pestControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Set<Pest> pests = new HashSet<>();

    @OneToMany(mappedBy = "plantFactoryID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Set<Disease> diseases = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "plantFactories", "partyID", "locationID" }, allowSetters = true)
    private Farm farmID;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "plantFactories", "sensors", "climates", "irrigations", "dosings", "lights", "plantFactoryID", "deviceLevelID", "deviceModelID",
        },
        allowSetters = true
    )
    private Device deviceID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlantFactory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PlantFactory gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getPlantFactoryName() {
        return this.plantFactoryName;
    }

    public PlantFactory plantFactoryName(String plantFactoryName) {
        this.setPlantFactoryName(plantFactoryName);
        return this;
    }

    public void setPlantFactoryName(String plantFactoryName) {
        this.plantFactoryName = plantFactoryName;
    }

    public ProFarmType getPlantFactoryTypeID() {
        return this.plantFactoryTypeID;
    }

    public PlantFactory plantFactoryTypeID(ProFarmType plantFactoryTypeID) {
        this.setPlantFactoryTypeID(plantFactoryTypeID);
        return this;
    }

    public void setPlantFactoryTypeID(ProFarmType plantFactoryTypeID) {
        this.plantFactoryTypeID = plantFactoryTypeID;
    }

    public ProSubType getPlantFactorySubType() {
        return this.plantFactorySubType;
    }

    public PlantFactory plantFactorySubType(ProSubType plantFactorySubType) {
        this.setPlantFactorySubType(plantFactorySubType);
        return this;
    }

    public void setPlantFactorySubType(ProSubType plantFactorySubType) {
        this.plantFactorySubType = plantFactorySubType;
    }

    public String getPlantFactoryDescription() {
        return this.plantFactoryDescription;
    }

    public PlantFactory plantFactoryDescription(String plantFactoryDescription) {
        this.setPlantFactoryDescription(plantFactoryDescription);
        return this;
    }

    public void setPlantFactoryDescription(String plantFactoryDescription) {
        this.plantFactoryDescription = plantFactoryDescription;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PlantFactory createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PlantFactory createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PlantFactory updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PlantFactory updatedOn(ZonedDateTime updatedOn) {
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
            this.zones.forEach(i -> i.setPlantFactoryID(null));
        }
        if (zones != null) {
            zones.forEach(i -> i.setPlantFactoryID(this));
        }
        this.zones = zones;
    }

    public PlantFactory zones(Set<Zone> zones) {
        this.setZones(zones);
        return this;
    }

    public PlantFactory addZone(Zone zone) {
        this.zones.add(zone);
        zone.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeZone(Zone zone) {
        this.zones.remove(zone);
        zone.setPlantFactoryID(null);
        return this;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    public void setDevices(Set<Device> devices) {
        if (this.devices != null) {
            this.devices.forEach(i -> i.setPlantFactoryID(null));
        }
        if (devices != null) {
            devices.forEach(i -> i.setPlantFactoryID(this));
        }
        this.devices = devices;
    }

    public PlantFactory devices(Set<Device> devices) {
        this.setDevices(devices);
        return this;
    }

    public PlantFactory addDevice(Device device) {
        this.devices.add(device);
        device.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeDevice(Device device) {
        this.devices.remove(device);
        device.setPlantFactoryID(null);
        return this;
    }

    public Set<Climate> getClimates() {
        return this.climates;
    }

    public void setClimates(Set<Climate> climates) {
        if (this.climates != null) {
            this.climates.forEach(i -> i.setPlantFactoryID(null));
        }
        if (climates != null) {
            climates.forEach(i -> i.setPlantFactoryID(this));
        }
        this.climates = climates;
    }

    public PlantFactory climates(Set<Climate> climates) {
        this.setClimates(climates);
        return this;
    }

    public PlantFactory addClimate(Climate climate) {
        this.climates.add(climate);
        climate.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeClimate(Climate climate) {
        this.climates.remove(climate);
        climate.setPlantFactoryID(null);
        return this;
    }

    public Set<Irrigation> getIrrigations() {
        return this.irrigations;
    }

    public void setIrrigations(Set<Irrigation> irrigations) {
        if (this.irrigations != null) {
            this.irrigations.forEach(i -> i.setPlantFactoryID(null));
        }
        if (irrigations != null) {
            irrigations.forEach(i -> i.setPlantFactoryID(this));
        }
        this.irrigations = irrigations;
    }

    public PlantFactory irrigations(Set<Irrigation> irrigations) {
        this.setIrrigations(irrigations);
        return this;
    }

    public PlantFactory addIrrigation(Irrigation irrigation) {
        this.irrigations.add(irrigation);
        irrigation.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeIrrigation(Irrigation irrigation) {
        this.irrigations.remove(irrigation);
        irrigation.setPlantFactoryID(null);
        return this;
    }

    public Set<Dosing> getDosings() {
        return this.dosings;
    }

    public void setDosings(Set<Dosing> dosings) {
        if (this.dosings != null) {
            this.dosings.forEach(i -> i.setPlantFactoryID(null));
        }
        if (dosings != null) {
            dosings.forEach(i -> i.setPlantFactoryID(this));
        }
        this.dosings = dosings;
    }

    public PlantFactory dosings(Set<Dosing> dosings) {
        this.setDosings(dosings);
        return this;
    }

    public PlantFactory addDosing(Dosing dosing) {
        this.dosings.add(dosing);
        dosing.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeDosing(Dosing dosing) {
        this.dosings.remove(dosing);
        dosing.setPlantFactoryID(null);
        return this;
    }

    public Set<Light> getLights() {
        return this.lights;
    }

    public void setLights(Set<Light> lights) {
        if (this.lights != null) {
            this.lights.forEach(i -> i.setPlantFactoryID(null));
        }
        if (lights != null) {
            lights.forEach(i -> i.setPlantFactoryID(this));
        }
        this.lights = lights;
    }

    public PlantFactory lights(Set<Light> lights) {
        this.setLights(lights);
        return this;
    }

    public PlantFactory addLight(Light light) {
        this.lights.add(light);
        light.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeLight(Light light) {
        this.lights.remove(light);
        light.setPlantFactoryID(null);
        return this;
    }

    public Set<Crop> getCrops() {
        return this.crops;
    }

    public void setCrops(Set<Crop> crops) {
        if (this.crops != null) {
            this.crops.forEach(i -> i.setPlantFactoryID(null));
        }
        if (crops != null) {
            crops.forEach(i -> i.setPlantFactoryID(this));
        }
        this.crops = crops;
    }

    public PlantFactory crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public PlantFactory addCrop(Crop crop) {
        this.crops.add(crop);
        crop.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeCrop(Crop crop) {
        this.crops.remove(crop);
        crop.setPlantFactoryID(null);
        return this;
    }

    public Set<Calendar> getCalendars() {
        return this.calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        if (this.calendars != null) {
            this.calendars.forEach(i -> i.setPlantFactoryID(null));
        }
        if (calendars != null) {
            calendars.forEach(i -> i.setPlantFactoryID(this));
        }
        this.calendars = calendars;
    }

    public PlantFactory calendars(Set<Calendar> calendars) {
        this.setCalendars(calendars);
        return this;
    }

    public PlantFactory addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.setPlantFactoryID(null);
        return this;
    }

    public Set<Scouting> getScoutings() {
        return this.scoutings;
    }

    public void setScoutings(Set<Scouting> scoutings) {
        if (this.scoutings != null) {
            this.scoutings.forEach(i -> i.setPlantFactoryID(null));
        }
        if (scoutings != null) {
            scoutings.forEach(i -> i.setPlantFactoryID(this));
        }
        this.scoutings = scoutings;
    }

    public PlantFactory scoutings(Set<Scouting> scoutings) {
        this.setScoutings(scoutings);
        return this;
    }

    public PlantFactory addScouting(Scouting scouting) {
        this.scoutings.add(scouting);
        scouting.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeScouting(Scouting scouting) {
        this.scoutings.remove(scouting);
        scouting.setPlantFactoryID(null);
        return this;
    }

    public Set<Pest> getPests() {
        return this.pests;
    }

    public void setPests(Set<Pest> pests) {
        if (this.pests != null) {
            this.pests.forEach(i -> i.setPlantFactoryID(null));
        }
        if (pests != null) {
            pests.forEach(i -> i.setPlantFactoryID(this));
        }
        this.pests = pests;
    }

    public PlantFactory pests(Set<Pest> pests) {
        this.setPests(pests);
        return this;
    }

    public PlantFactory addPest(Pest pest) {
        this.pests.add(pest);
        pest.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removePest(Pest pest) {
        this.pests.remove(pest);
        pest.setPlantFactoryID(null);
        return this;
    }

    public Set<Disease> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        if (this.diseases != null) {
            this.diseases.forEach(i -> i.setPlantFactoryID(null));
        }
        if (diseases != null) {
            diseases.forEach(i -> i.setPlantFactoryID(this));
        }
        this.diseases = diseases;
    }

    public PlantFactory diseases(Set<Disease> diseases) {
        this.setDiseases(diseases);
        return this;
    }

    public PlantFactory addDisease(Disease disease) {
        this.diseases.add(disease);
        disease.setPlantFactoryID(this);
        return this;
    }

    public PlantFactory removeDisease(Disease disease) {
        this.diseases.remove(disease);
        disease.setPlantFactoryID(null);
        return this;
    }

    public Farm getFarmID() {
        return this.farmID;
    }

    public void setFarmID(Farm farm) {
        this.farmID = farm;
    }

    public PlantFactory farmID(Farm farm) {
        this.setFarmID(farm);
        return this;
    }

    public Device getDeviceID() {
        return this.deviceID;
    }

    public void setDeviceID(Device device) {
        this.deviceID = device;
    }

    public PlantFactory deviceID(Device device) {
        this.setDeviceID(device);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantFactory)) {
            return false;
        }
        return id != null && id.equals(((PlantFactory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantFactory{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", plantFactoryName='" + getPlantFactoryName() + "'" +
            ", plantFactoryTypeID='" + getPlantFactoryTypeID() + "'" +
            ", plantFactorySubType='" + getPlantFactorySubType() + "'" +
            ", plantFactoryDescription='" + getPlantFactoryDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
