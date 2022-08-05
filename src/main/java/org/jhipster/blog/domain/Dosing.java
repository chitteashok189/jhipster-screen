package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.DosingSource;

/**
 * A Dosing.
 */
@Entity
@Table(name = "dosing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dosing implements Serializable {

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
    private DosingSource source;

    @Column(name = "p_h")
    private Integer pH;

    @Column(name = "e_c")
    private Integer eC;

    @Column(name = "d_o")
    private Integer dO;

    @Column(name = "nutrient_temperature")
    private Integer nutrientTemperature;

    @Column(name = "solar_radiation")
    private Integer solarRadiation;

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

    public Dosing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Dosing gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public DosingSource getSource() {
        return this.source;
    }

    public Dosing source(DosingSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(DosingSource source) {
        this.source = source;
    }

    public Integer getpH() {
        return this.pH;
    }

    public Dosing pH(Integer pH) {
        this.setpH(pH);
        return this;
    }

    public void setpH(Integer pH) {
        this.pH = pH;
    }

    public Integer geteC() {
        return this.eC;
    }

    public Dosing eC(Integer eC) {
        this.seteC(eC);
        return this;
    }

    public void seteC(Integer eC) {
        this.eC = eC;
    }

    public Integer getdO() {
        return this.dO;
    }

    public Dosing dO(Integer dO) {
        this.setdO(dO);
        return this;
    }

    public void setdO(Integer dO) {
        this.dO = dO;
    }

    public Integer getNutrientTemperature() {
        return this.nutrientTemperature;
    }

    public Dosing nutrientTemperature(Integer nutrientTemperature) {
        this.setNutrientTemperature(nutrientTemperature);
        return this;
    }

    public void setNutrientTemperature(Integer nutrientTemperature) {
        this.nutrientTemperature = nutrientTemperature;
    }

    public Integer getSolarRadiation() {
        return this.solarRadiation;
    }

    public Dosing solarRadiation(Integer solarRadiation) {
        this.setSolarRadiation(solarRadiation);
        return this;
    }

    public void setSolarRadiation(Integer solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Dosing createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Dosing createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Dosing updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Dosing updatedOn(ZonedDateTime updatedOn) {
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

    public Dosing deviceID(Device device) {
        this.setDeviceID(device);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Dosing plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dosing)) {
            return false;
        }
        return id != null && id.equals(((Dosing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dosing{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", source='" + getSource() + "'" +
            ", pH=" + getpH() +
            ", eC=" + geteC() +
            ", dO=" + getdO() +
            ", nutrientTemperature=" + getNutrientTemperature() +
            ", solarRadiation=" + getSolarRadiation() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
