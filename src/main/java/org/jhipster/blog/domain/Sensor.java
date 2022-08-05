package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sensor.
 */
@Entity
@Table(name = "sensor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "sensor_model_name")
    private String sensorModelName;

    @Column(name = "hardware_id")
    private Long hardwareID;

    @Column(name = "port")
    private Integer port;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "farms", "sensors" }, allowSetters = true)
    private Location locationID;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "plantFactories", "sensors", "climates", "irrigations", "dosings", "lights", "plantFactoryID", "deviceLevelID", "deviceModelID",
        },
        allowSetters = true
    )
    private Device deviceID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sensors" }, allowSetters = true)
    private SensorModel sensorModelID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sensor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Sensor gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getSensorName() {
        return this.sensorName;
    }

    public Sensor sensorName(String sensorName) {
        this.setSensorName(sensorName);
        return this;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorModelName() {
        return this.sensorModelName;
    }

    public Sensor sensorModelName(String sensorModelName) {
        this.setSensorModelName(sensorModelName);
        return this;
    }

    public void setSensorModelName(String sensorModelName) {
        this.sensorModelName = sensorModelName;
    }

    public Long getHardwareID() {
        return this.hardwareID;
    }

    public Sensor hardwareID(Long hardwareID) {
        this.setHardwareID(hardwareID);
        return this;
    }

    public void setHardwareID(Long hardwareID) {
        this.hardwareID = hardwareID;
    }

    public Integer getPort() {
        return this.port;
    }

    public Sensor port(Integer port) {
        this.setPort(port);
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProperties() {
        return this.properties;
    }

    public Sensor properties(String properties) {
        this.setProperties(properties);
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return this.description;
    }

    public Sensor description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Sensor createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Sensor createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Sensor updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Sensor updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Location getLocationID() {
        return this.locationID;
    }

    public void setLocationID(Location location) {
        this.locationID = location;
    }

    public Sensor locationID(Location location) {
        this.setLocationID(location);
        return this;
    }

    public Device getDeviceID() {
        return this.deviceID;
    }

    public void setDeviceID(Device device) {
        this.deviceID = device;
    }

    public Sensor deviceID(Device device) {
        this.setDeviceID(device);
        return this;
    }

    public SensorModel getSensorModelID() {
        return this.sensorModelID;
    }

    public void setSensorModelID(SensorModel sensorModel) {
        this.sensorModelID = sensorModel;
    }

    public Sensor sensorModelID(SensorModel sensorModel) {
        this.setSensorModelID(sensorModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sensor)) {
            return false;
        }
        return id != null && id.equals(((Sensor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", sensorName='" + getSensorName() + "'" +
            ", sensorModelName='" + getSensorModelName() + "'" +
            ", hardwareID=" + getHardwareID() +
            ", port=" + getPort() +
            ", properties='" + getProperties() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
