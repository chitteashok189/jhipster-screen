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
 * A DeviceLevel.
 */
@Entity
@Table(name = "device_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "device_level_type")
    private Long deviceLevelType;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "product_code")
    private Integer productCode;

    @Column(name = "ports")
    private Integer ports;

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

    @OneToMany(mappedBy = "deviceLevelID")
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

    public DeviceLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public DeviceLevel gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public DeviceLevel levelName(String levelName) {
        this.setLevelName(levelName);
        return this;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Long getDeviceLevelType() {
        return this.deviceLevelType;
    }

    public DeviceLevel deviceLevelType(Long deviceLevelType) {
        this.setDeviceLevelType(deviceLevelType);
        return this;
    }

    public void setDeviceLevelType(Long deviceLevelType) {
        this.deviceLevelType = deviceLevelType;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public DeviceLevel manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getProductCode() {
        return this.productCode;
    }

    public DeviceLevel productCode(Integer productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Integer getPorts() {
        return this.ports;
    }

    public DeviceLevel ports(Integer ports) {
        this.setPorts(ports);
        return this;
    }

    public void setPorts(Integer ports) {
        this.ports = ports;
    }

    public String getProperties() {
        return this.properties;
    }

    public DeviceLevel properties(String properties) {
        this.setProperties(properties);
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDescription() {
        return this.description;
    }

    public DeviceLevel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public DeviceLevel createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public DeviceLevel createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public DeviceLevel updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public DeviceLevel updatedOn(ZonedDateTime updatedOn) {
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
            this.devices.forEach(i -> i.setDeviceLevelID(null));
        }
        if (devices != null) {
            devices.forEach(i -> i.setDeviceLevelID(this));
        }
        this.devices = devices;
    }

    public DeviceLevel devices(Set<Device> devices) {
        this.setDevices(devices);
        return this;
    }

    public DeviceLevel addDevice(Device device) {
        this.devices.add(device);
        device.setDeviceLevelID(this);
        return this;
    }

    public DeviceLevel removeDevice(Device device) {
        this.devices.remove(device);
        device.setDeviceLevelID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceLevel)) {
            return false;
        }
        return id != null && id.equals(((DeviceLevel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceLevel{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", levelName='" + getLevelName() + "'" +
            ", deviceLevelType=" + getDeviceLevelType() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", productCode=" + getProductCode() +
            ", ports=" + getPorts() +
            ", properties='" + getProperties() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
