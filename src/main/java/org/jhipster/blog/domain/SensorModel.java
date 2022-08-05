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
 * A SensorModel.
 */
@Entity
@Table(name = "sensor_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SensorModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "sensor_type")
    private Long sensorType;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "sensor_measure")
    private Integer sensorMeasure;

    @Column(name = "model_name")
    private String modelName;

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

    @OneToMany(mappedBy = "sensorModelID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locationID", "deviceID", "sensorModelID" }, allowSetters = true)
    private Set<Sensor> sensors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SensorModel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public SensorModel gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Long getSensorType() {
        return this.sensorType;
    }

    public SensorModel sensorType(Long sensorType) {
        this.setSensorType(sensorType);
        return this;
    }

    public void setSensorType(Long sensorType) {
        this.sensorType = sensorType;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public SensorModel manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public SensorModel productCode(String productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getSensorMeasure() {
        return this.sensorMeasure;
    }

    public SensorModel sensorMeasure(Integer sensorMeasure) {
        this.setSensorMeasure(sensorMeasure);
        return this;
    }

    public void setSensorMeasure(Integer sensorMeasure) {
        this.sensorMeasure = sensorMeasure;
    }

    public String getModelName() {
        return this.modelName;
    }

    public SensorModel modelName(String modelName) {
        this.setModelName(modelName);
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProperties() {
        return this.properties;
    }

    public SensorModel properties(String properties) {
        this.setProperties(properties);
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return this.description;
    }

    public SensorModel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public SensorModel createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public SensorModel createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public SensorModel updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public SensorModel updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Sensor> getSensors() {
        return this.sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        if (this.sensors != null) {
            this.sensors.forEach(i -> i.setSensorModelID(null));
        }
        if (sensors != null) {
            sensors.forEach(i -> i.setSensorModelID(this));
        }
        this.sensors = sensors;
    }

    public SensorModel sensors(Set<Sensor> sensors) {
        this.setSensors(sensors);
        return this;
    }

    public SensorModel addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setSensorModelID(this);
        return this;
    }

    public SensorModel removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setSensorModelID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorModel)) {
            return false;
        }
        return id != null && id.equals(((SensorModel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensorModel{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", sensorType=" + getSensorType() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", productCode='" + getProductCode() + "'" +
            ", sensorMeasure=" + getSensorMeasure() +
            ", modelName='" + getModelName() + "'" +
            ", properties='" + getProperties() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
