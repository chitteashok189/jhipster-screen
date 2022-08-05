package org.jhipster.blog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.PlantSource;

/**
 * A PlantFactoryController.
 */
@Entity
@Table(name = "plant_factory_controller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlantFactoryController implements Serializable {

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
    private PlantSource source;

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

    @Column(name = "nutrient_tank_level")
    private Integer nutrientTankLevel;

    @Column(name = "dew_point")
    private Integer dewPoint;

    @Column(name = "heat_index")
    private Integer heatIndex;

    @Column(name = "turbidity")
    private Integer turbidity;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlantFactoryController id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PlantFactoryController gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public PlantSource getSource() {
        return this.source;
    }

    public PlantFactoryController source(PlantSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(PlantSource source) {
        this.source = source;
    }

    public Integer getAirTemperature() {
        return this.airTemperature;
    }

    public PlantFactoryController airTemperature(Integer airTemperature) {
        this.setAirTemperature(airTemperature);
        return this;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Integer getRelativeHumidity() {
        return this.relativeHumidity;
    }

    public PlantFactoryController relativeHumidity(Integer relativeHumidity) {
        this.setRelativeHumidity(relativeHumidity);
        return this;
    }

    public void setRelativeHumidity(Integer relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Integer getvPD() {
        return this.vPD;
    }

    public PlantFactoryController vPD(Integer vPD) {
        this.setvPD(vPD);
        return this;
    }

    public void setvPD(Integer vPD) {
        this.vPD = vPD;
    }

    public Integer getEvapotranspiration() {
        return this.evapotranspiration;
    }

    public PlantFactoryController evapotranspiration(Integer evapotranspiration) {
        this.setEvapotranspiration(evapotranspiration);
        return this;
    }

    public void setEvapotranspiration(Integer evapotranspiration) {
        this.evapotranspiration = evapotranspiration;
    }

    public Integer getBarometricPressure() {
        return this.barometricPressure;
    }

    public PlantFactoryController barometricPressure(Integer barometricPressure) {
        this.setBarometricPressure(barometricPressure);
        return this;
    }

    public void setBarometricPressure(Integer barometricPressure) {
        this.barometricPressure = barometricPressure;
    }

    public Integer getSeaLevelPressure() {
        return this.seaLevelPressure;
    }

    public PlantFactoryController seaLevelPressure(Integer seaLevelPressure) {
        this.setSeaLevelPressure(seaLevelPressure);
        return this;
    }

    public void setSeaLevelPressure(Integer seaLevelPressure) {
        this.seaLevelPressure = seaLevelPressure;
    }

    public Integer getCo2Level() {
        return this.co2Level;
    }

    public PlantFactoryController co2Level(Integer co2Level) {
        this.setCo2Level(co2Level);
        return this;
    }

    public void setCo2Level(Integer co2Level) {
        this.co2Level = co2Level;
    }

    public Integer getNutrientTankLevel() {
        return this.nutrientTankLevel;
    }

    public PlantFactoryController nutrientTankLevel(Integer nutrientTankLevel) {
        this.setNutrientTankLevel(nutrientTankLevel);
        return this;
    }

    public void setNutrientTankLevel(Integer nutrientTankLevel) {
        this.nutrientTankLevel = nutrientTankLevel;
    }

    public Integer getDewPoint() {
        return this.dewPoint;
    }

    public PlantFactoryController dewPoint(Integer dewPoint) {
        this.setDewPoint(dewPoint);
        return this;
    }

    public void setDewPoint(Integer dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getHeatIndex() {
        return this.heatIndex;
    }

    public PlantFactoryController heatIndex(Integer heatIndex) {
        this.setHeatIndex(heatIndex);
        return this;
    }

    public void setHeatIndex(Integer heatIndex) {
        this.heatIndex = heatIndex;
    }

    public Integer getTurbidity() {
        return this.turbidity;
    }

    public PlantFactoryController turbidity(Integer turbidity) {
        this.setTurbidity(turbidity);
        return this;
    }

    public void setTurbidity(Integer turbidity) {
        this.turbidity = turbidity;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PlantFactoryController createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PlantFactoryController createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PlantFactoryController updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PlantFactoryController updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantFactoryController)) {
            return false;
        }
        return id != null && id.equals(((PlantFactoryController) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantFactoryController{" +
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
            ", nutrientTankLevel=" + getNutrientTankLevel() +
            ", dewPoint=" + getDewPoint() +
            ", heatIndex=" + getHeatIndex() +
            ", turbidity=" + getTurbidity() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
