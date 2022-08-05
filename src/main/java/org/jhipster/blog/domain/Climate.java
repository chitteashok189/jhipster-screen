package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.CliSource;

/**
 * A Climate.
 */
@Entity
@Table(name = "climate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Climate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private CliSource source;

    @Column(name = "air_temperature")
    private Integer airTemperature;

    @Column(name = "relative_humidity")
    private Integer relativeHumidity;

    @Column(name = "v_pd")
    private Integer vPD;

    @Column(name = "evapotranspiration")
    private Integer evapotranspiration;

    @Column(name = "barometric_pressure")
    private Integer barometricPressure;

    @Column(name = "sea_level_pressure")
    private Integer seaLevelPressure;

    @Column(name = "co_2_level")
    private Integer co2Level;

    @Column(name = "dew_point")
    private Integer dewPoint;

    @Column(name = "solar_radiation")
    private Integer solarRadiation;

    @Column(name = "heat_index")
    private Integer heatIndex;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "plantFactories", "sensors", "climates", "irrigations", "dosings", "lights", "plantFactoryID", "deviceLevelID", "deviceModelID",
        },
        allowSetters = true
    )
    private Device deviceID;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Climate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Climate gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public CliSource getSource() {
        return this.source;
    }

    public Climate source(CliSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(CliSource source) {
        this.source = source;
    }

    public Integer getAirTemperature() {
        return this.airTemperature;
    }

    public Climate airTemperature(Integer airTemperature) {
        this.setAirTemperature(airTemperature);
        return this;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Integer getRelativeHumidity() {
        return this.relativeHumidity;
    }

    public Climate relativeHumidity(Integer relativeHumidity) {
        this.setRelativeHumidity(relativeHumidity);
        return this;
    }

    public void setRelativeHumidity(Integer relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Integer getvPD() {
        return this.vPD;
    }

    public Climate vPD(Integer vPD) {
        this.setvPD(vPD);
        return this;
    }

    public void setvPD(Integer vPD) {
        this.vPD = vPD;
    }

    public Integer getEvapotranspiration() {
        return this.evapotranspiration;
    }

    public Climate evapotranspiration(Integer evapotranspiration) {
        this.setEvapotranspiration(evapotranspiration);
        return this;
    }

    public void setEvapotranspiration(Integer evapotranspiration) {
        this.evapotranspiration = evapotranspiration;
    }

    public Integer getBarometricPressure() {
        return this.barometricPressure;
    }

    public Climate barometricPressure(Integer barometricPressure) {
        this.setBarometricPressure(barometricPressure);
        return this;
    }

    public void setBarometricPressure(Integer barometricPressure) {
        this.barometricPressure = barometricPressure;
    }

    public Integer getSeaLevelPressure() {
        return this.seaLevelPressure;
    }

    public Climate seaLevelPressure(Integer seaLevelPressure) {
        this.setSeaLevelPressure(seaLevelPressure);
        return this;
    }

    public void setSeaLevelPressure(Integer seaLevelPressure) {
        this.seaLevelPressure = seaLevelPressure;
    }

    public Integer getCo2Level() {
        return this.co2Level;
    }

    public Climate co2Level(Integer co2Level) {
        this.setCo2Level(co2Level);
        return this;
    }

    public void setCo2Level(Integer co2Level) {
        this.co2Level = co2Level;
    }

    public Integer getDewPoint() {
        return this.dewPoint;
    }

    public Climate dewPoint(Integer dewPoint) {
        this.setDewPoint(dewPoint);
        return this;
    }

    public void setDewPoint(Integer dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getSolarRadiation() {
        return this.solarRadiation;
    }

    public Climate solarRadiation(Integer solarRadiation) {
        this.setSolarRadiation(solarRadiation);
        return this;
    }

    public void setSolarRadiation(Integer solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public Integer getHeatIndex() {
        return this.heatIndex;
    }

    public Climate heatIndex(Integer heatIndex) {
        this.setHeatIndex(heatIndex);
        return this;
    }

    public void setHeatIndex(Integer heatIndex) {
        this.heatIndex = heatIndex;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Climate createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Climate createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Climate updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Climate updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Device getDeviceID() {
        return this.deviceID;
    }

    public void setDeviceID(Device device) {
        this.deviceID = device;
    }

    public Climate deviceID(Device device) {
        this.setDeviceID(device);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Climate plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Climate)) {
            return false;
        }
        return id != null && id.equals(((Climate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Climate{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", source='" + getSource() + "'" +
            ", airTemperature=" + getAirTemperature() +
            ", relativeHumidity=" + getRelativeHumidity() +
            ", vPD=" + getvPD() +
            ", evapotranspiration=" + getEvapotranspiration() +
            ", barometricPressure=" + getBarometricPressure() +
            ", seaLevelPressure=" + getSeaLevelPressure() +
            ", co2Level=" + getCo2Level() +
            ", dewPoint=" + getDewPoint() +
            ", solarRadiation=" + getSolarRadiation() +
            ", heatIndex=" + getHeatIndex() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
