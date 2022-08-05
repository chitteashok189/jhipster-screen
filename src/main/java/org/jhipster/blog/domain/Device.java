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
import org.jhipster.blog.domain.enumeration.DeviceType;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "device_asset")
    private String deviceAsset;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    private DeviceType deviceType;

    @Column(name = "hardware_id")
    private Long hardwareID;

    @Column(name = "reporting_interval_location")
    private Integer reportingIntervalLocation;

    @Column(name = "parent_device")
    private String parentDevice;

    @Column(name = "properties")
    private String properties;

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

    @OneToMany(mappedBy = "deviceID")
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

    @OneToMany(mappedBy = "deviceID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locationID", "deviceID", "sensorModelID" }, allowSetters = true)
    private Set<Sensor> sensors = new HashSet<>();

    @OneToMany(mappedBy = "deviceID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Climate> climates = new HashSet<>();

    @OneToMany(mappedBy = "deviceID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Irrigation> irrigations = new HashSet<>();

    @OneToMany(mappedBy = "deviceID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Dosing> dosings = new HashSet<>();

    @OneToMany(mappedBy = "deviceID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deviceID", "plantFactoryID" }, allowSetters = true)
    private Set<Light> lights = new HashSet<>();

    @ManyToOne
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
    private PlantFactory plantFactoryID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "devices" }, allowSetters = true)
    private DeviceLevel deviceLevelID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "devices" }, allowSetters = true)
    private DeviceModel deviceModelID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Device id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Device gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public Device deviceModel(String deviceModel) {
        this.setDeviceModel(deviceModel);
        return this;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceAsset() {
        return this.deviceAsset;
    }

    public Device deviceAsset(String deviceAsset) {
        this.setDeviceAsset(deviceAsset);
        return this;
    }

    public void setDeviceAsset(String deviceAsset) {
        this.deviceAsset = deviceAsset;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public Device deviceType(DeviceType deviceType) {
        this.setDeviceType(deviceType);
        return this;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Long getHardwareID() {
        return this.hardwareID;
    }

    public Device hardwareID(Long hardwareID) {
        this.setHardwareID(hardwareID);
        return this;
    }

    public void setHardwareID(Long hardwareID) {
        this.hardwareID = hardwareID;
    }

    public Integer getReportingIntervalLocation() {
        return this.reportingIntervalLocation;
    }

    public Device reportingIntervalLocation(Integer reportingIntervalLocation) {
        this.setReportingIntervalLocation(reportingIntervalLocation);
        return this;
    }

    public void setReportingIntervalLocation(Integer reportingIntervalLocation) {
        this.reportingIntervalLocation = reportingIntervalLocation;
    }

    public String getParentDevice() {
        return this.parentDevice;
    }

    public Device parentDevice(String parentDevice) {
        this.setParentDevice(parentDevice);
        return this;
    }

    public void setParentDevice(String parentDevice) {
        this.parentDevice = parentDevice;
    }

    public String getProperties() {
        return this.properties;
    }

    public Device properties(String properties) {
        this.setProperties(properties);
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return this.description;
    }

    public Device description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Device createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Device createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Device updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Device updatedOn(ZonedDateTime updatedOn) {
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
            this.plantFactories.forEach(i -> i.setDeviceID(null));
        }
        if (plantFactories != null) {
            plantFactories.forEach(i -> i.setDeviceID(this));
        }
        this.plantFactories = plantFactories;
    }

    public Device plantFactories(Set<PlantFactory> plantFactories) {
        this.setPlantFactories(plantFactories);
        return this;
    }

    public Device addPlantFactory(PlantFactory plantFactory) {
        this.plantFactories.add(plantFactory);
        plantFactory.setDeviceID(this);
        return this;
    }

    public Device removePlantFactory(PlantFactory plantFactory) {
        this.plantFactories.remove(plantFactory);
        plantFactory.setDeviceID(null);
        return this;
    }

    public Set<Sensor> getSensors() {
        return this.sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        if (this.sensors != null) {
            this.sensors.forEach(i -> i.setDeviceID(null));
        }
        if (sensors != null) {
            sensors.forEach(i -> i.setDeviceID(this));
        }
        this.sensors = sensors;
    }

    public Device sensors(Set<Sensor> sensors) {
        this.setSensors(sensors);
        return this;
    }

    public Device addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setDeviceID(this);
        return this;
    }

    public Device removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setDeviceID(null);
        return this;
    }

    public Set<Climate> getClimates() {
        return this.climates;
    }

    public void setClimates(Set<Climate> climates) {
        if (this.climates != null) {
            this.climates.forEach(i -> i.setDeviceID(null));
        }
        if (climates != null) {
            climates.forEach(i -> i.setDeviceID(this));
        }
        this.climates = climates;
    }

    public Device climates(Set<Climate> climates) {
        this.setClimates(climates);
        return this;
    }

    public Device addClimate(Climate climate) {
        this.climates.add(climate);
        climate.setDeviceID(this);
        return this;
    }

    public Device removeClimate(Climate climate) {
        this.climates.remove(climate);
        climate.setDeviceID(null);
        return this;
    }

    public Set<Irrigation> getIrrigations() {
        return this.irrigations;
    }

    public void setIrrigations(Set<Irrigation> irrigations) {
        if (this.irrigations != null) {
            this.irrigations.forEach(i -> i.setDeviceID(null));
        }
        if (irrigations != null) {
            irrigations.forEach(i -> i.setDeviceID(this));
        }
        this.irrigations = irrigations;
    }

    public Device irrigations(Set<Irrigation> irrigations) {
        this.setIrrigations(irrigations);
        return this;
    }

    public Device addIrrigation(Irrigation irrigation) {
        this.irrigations.add(irrigation);
        irrigation.setDeviceID(this);
        return this;
    }

    public Device removeIrrigation(Irrigation irrigation) {
        this.irrigations.remove(irrigation);
        irrigation.setDeviceID(null);
        return this;
    }

    public Set<Dosing> getDosings() {
        return this.dosings;
    }

    public void setDosings(Set<Dosing> dosings) {
        if (this.dosings != null) {
            this.dosings.forEach(i -> i.setDeviceID(null));
        }
        if (dosings != null) {
            dosings.forEach(i -> i.setDeviceID(this));
        }
        this.dosings = dosings;
    }

    public Device dosings(Set<Dosing> dosings) {
        this.setDosings(dosings);
        return this;
    }

    public Device addDosing(Dosing dosing) {
        this.dosings.add(dosing);
        dosing.setDeviceID(this);
        return this;
    }

    public Device removeDosing(Dosing dosing) {
        this.dosings.remove(dosing);
        dosing.setDeviceID(null);
        return this;
    }

    public Set<Light> getLights() {
        return this.lights;
    }

    public void setLights(Set<Light> lights) {
        if (this.lights != null) {
            this.lights.forEach(i -> i.setDeviceID(null));
        }
        if (lights != null) {
            lights.forEach(i -> i.setDeviceID(this));
        }
        this.lights = lights;
    }

    public Device lights(Set<Light> lights) {
        this.setLights(lights);
        return this;
    }

    public Device addLight(Light light) {
        this.lights.add(light);
        light.setDeviceID(this);
        return this;
    }

    public Device removeLight(Light light) {
        this.lights.remove(light);
        light.setDeviceID(null);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Device plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    public DeviceLevel getDeviceLevelID() {
        return this.deviceLevelID;
    }

    public void setDeviceLevelID(DeviceLevel deviceLevel) {
        this.deviceLevelID = deviceLevel;
    }

    public Device deviceLevelID(DeviceLevel deviceLevel) {
        this.setDeviceLevelID(deviceLevel);
        return this;
    }

    public DeviceModel getDeviceModelID() {
        return this.deviceModelID;
    }

    public void setDeviceModelID(DeviceModel deviceModel) {
        this.deviceModelID = deviceModel;
    }

    public Device deviceModelID(DeviceModel deviceModel) {
        this.setDeviceModelID(deviceModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", deviceModel='" + getDeviceModel() + "'" +
            ", deviceAsset='" + getDeviceAsset() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            ", hardwareID=" + getHardwareID() +
            ", reportingIntervalLocation=" + getReportingIntervalLocation() +
            ", parentDevice='" + getParentDevice() + "'" +
            ", properties='" + getProperties() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
