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
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "building_no")
    private Integer buildingNo;

    @Column(name = "street")
    private String street;

    @Column(name = "area")
    private String area;

    @Column(name = "country")
    private Long country;

    @Column(name = "state")
    private Long state;

    @Column(name = "city")
    private Long city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "other_address")
    private String otherAddress;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "locationID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plantFactories", "partyID", "locationID" }, allowSetters = true)
    private Set<Farm> farms = new HashSet<>();

    @OneToMany(mappedBy = "locationID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locationID", "deviceID", "sensorModelID" }, allowSetters = true)
    private Set<Sensor> sensors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Location gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Integer getBuildingNo() {
        return this.buildingNo;
    }

    public Location buildingNo(Integer buildingNo) {
        this.setBuildingNo(buildingNo);
        return this;
    }

    public void setBuildingNo(Integer buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getStreet() {
        return this.street;
    }

    public Location street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return this.area;
    }

    public Location area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getCountry() {
        return this.country;
    }

    public Location country(Long country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getState() {
        return this.state;
    }

    public Location state(Long state) {
        this.setState(state);
        return this;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getCity() {
        return this.city;
    }

    public Location city(Long city) {
        this.setCity(city);
        return this;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Location postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOtherAddress() {
        return this.otherAddress;
    }

    public Location otherAddress(String otherAddress) {
        this.setOtherAddress(otherAddress);
        return this;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Location createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Location createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Location updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Location updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Farm> getFarms() {
        return this.farms;
    }

    public void setFarms(Set<Farm> farms) {
        if (this.farms != null) {
            this.farms.forEach(i -> i.setLocationID(null));
        }
        if (farms != null) {
            farms.forEach(i -> i.setLocationID(this));
        }
        this.farms = farms;
    }

    public Location farms(Set<Farm> farms) {
        this.setFarms(farms);
        return this;
    }

    public Location addFarm(Farm farm) {
        this.farms.add(farm);
        farm.setLocationID(this);
        return this;
    }

    public Location removeFarm(Farm farm) {
        this.farms.remove(farm);
        farm.setLocationID(null);
        return this;
    }

    public Set<Sensor> getSensors() {
        return this.sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        if (this.sensors != null) {
            this.sensors.forEach(i -> i.setLocationID(null));
        }
        if (sensors != null) {
            sensors.forEach(i -> i.setLocationID(this));
        }
        this.sensors = sensors;
    }

    public Location sensors(Set<Sensor> sensors) {
        this.setSensors(sensors);
        return this;
    }

    public Location addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setLocationID(this);
        return this;
    }

    public Location removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setLocationID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", buildingNo=" + getBuildingNo() +
            ", street='" + getStreet() + "'" +
            ", area='" + getArea() + "'" +
            ", country=" + getCountry() +
            ", state=" + getState() +
            ", city=" + getCity() +
            ", postalCode='" + getPostalCode() + "'" +
            ", otherAddress='" + getOtherAddress() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
