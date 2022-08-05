package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.LightSource;

/**
 * A Light.
 */
@Entity
@Table(name = "light")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Light implements Serializable {

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
    private LightSource source;

    @Column(name = "light_intensity")
    private Integer lightIntensity;

    @Column(name = "daily_light_integral")
    private Integer dailyLightIntegral;

    @Column(name = "p_ar")
    private Integer pAR;

    @Column(name = "p_pfd")
    private Integer pPFD;

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

    public Light id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Light gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public LightSource getSource() {
        return this.source;
    }

    public Light source(LightSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(LightSource source) {
        this.source = source;
    }

    public Integer getLightIntensity() {
        return this.lightIntensity;
    }

    public Light lightIntensity(Integer lightIntensity) {
        this.setLightIntensity(lightIntensity);
        return this;
    }

    public void setLightIntensity(Integer lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public Integer getDailyLightIntegral() {
        return this.dailyLightIntegral;
    }

    public Light dailyLightIntegral(Integer dailyLightIntegral) {
        this.setDailyLightIntegral(dailyLightIntegral);
        return this;
    }

    public void setDailyLightIntegral(Integer dailyLightIntegral) {
        this.dailyLightIntegral = dailyLightIntegral;
    }

    public Integer getpAR() {
        return this.pAR;
    }

    public Light pAR(Integer pAR) {
        this.setpAR(pAR);
        return this;
    }

    public void setpAR(Integer pAR) {
        this.pAR = pAR;
    }

    public Integer getpPFD() {
        return this.pPFD;
    }

    public Light pPFD(Integer pPFD) {
        this.setpPFD(pPFD);
        return this;
    }

    public void setpPFD(Integer pPFD) {
        this.pPFD = pPFD;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Light createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Light createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Light updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Light updatedOn(ZonedDateTime updatedOn) {
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

    public Light deviceID(Device device) {
        this.setDeviceID(device);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Light plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Light)) {
            return false;
        }
        return id != null && id.equals(((Light) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Light{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", source='" + getSource() + "'" +
            ", lightIntensity=" + getLightIntensity() +
            ", dailyLightIntegral=" + getDailyLightIntegral() +
            ", pAR=" + getpAR() +
            ", pPFD=" + getpPFD() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
