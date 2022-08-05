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
 * A DeviceModel.
 */
@Entity
@Table(name = "device_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "type")
    private Long type;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "product_code")
    private Integer productCode;

    @Column(name = "properties")
    private Long properties;

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

    @OneToMany(mappedBy = "deviceModelID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "plantFactories", "sensors", "climates", "irrigations", "dosings", "lights", "plantFactoryID", "deviceLevelID", "deviceModelID",
        },
        allowSetters = true
    )
    private Set<Device> devices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeviceModel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public DeviceModel gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getModelName() {
        return this.modelName;
    }

    public DeviceModel modelName(String modelName) {
        this.setModelName(modelName);
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getType() {
        return this.type;
    }

    public DeviceModel type(Long type) {
        this.setType(type);
        return this;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public DeviceModel manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getProductCode() {
        return this.productCode;
    }

    public DeviceModel productCode(Integer productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Long getProperties() {
        return this.properties;
    }

    public DeviceModel properties(Long properties) {
        this.setProperties(properties);
        return this;
    }

    public void setProperties(Long properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return this.description;
    }

    public DeviceModel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public DeviceModel createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public DeviceModel createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public DeviceModel updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public DeviceModel updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    public void setDevices(Set<Device> devices) {
        if (this.devices != null) {
            this.devices.forEach(i -> i.setDeviceModelID(null));
        }
        if (devices != null) {
            devices.forEach(i -> i.setDeviceModelID(this));
        }
        this.devices = devices;
    }

    public DeviceModel devices(Set<Device> devices) {
        this.setDevices(devices);
        return this;
    }

    public DeviceModel addDevice(Device device) {
        this.devices.add(device);
        device.setDeviceModelID(this);
        return this;
    }

    public DeviceModel removeDevice(Device device) {
        this.devices.remove(device);
        device.setDeviceModelID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceModel)) {
            return false;
        }
        return id != null && id.equals(((DeviceModel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceModel{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", modelName='" + getModelName() + "'" +
            ", type=" + getType() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", productCode=" + getProductCode() +
            ", properties=" + getProperties() +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
