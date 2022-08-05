package org.jhipster.blog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.AlertStatus;
import org.jhipster.blog.domain.enumeration.AlertType;
import org.jhipster.blog.domain.enumeration.PreType;
import org.jhipster.blog.domain.enumeration.Remediation;

/**
 * A Alert.
 */
@Entity
@Table(name = "alert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type")
    private AlertType alertType;

    @Column(name = "description")
    private String description;

    @Column(name = "date_period")
    private Integer datePeriod;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "minimum_temperature")
    private Integer minimumTemperature;

    @Column(name = "maximum_temperature")
    private Integer maximumTemperature;

    @Column(name = "min_humidity")
    private Integer minHumidity;

    @Column(name = "max_humidity")
    private Integer maxHumidity;

    @Enumerated(EnumType.STRING)
    @Column(name = "precipitation_type")
    private PreType precipitationType;

    @Column(name = "min_precipitation")
    private Integer minPrecipitation;

    @Column(name = "max_precipitation")
    private Integer maxPrecipitation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AlertStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "remediation")
    private Remediation remediation;

    @Column(name = "farm_assigned")
    private String farmAssigned;

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

    public Alert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Alert gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getName() {
        return this.name;
    }

    public Alert name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlertType getAlertType() {
        return this.alertType;
    }

    public Alert alertType(AlertType alertType) {
        this.setAlertType(alertType);
        return this;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public String getDescription() {
        return this.description;
    }

    public Alert description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDatePeriod() {
        return this.datePeriod;
    }

    public Alert datePeriod(Integer datePeriod) {
        this.setDatePeriod(datePeriod);
        return this;
    }

    public void setDatePeriod(Integer datePeriod) {
        this.datePeriod = datePeriod;
    }

    public Integer getDurationDays() {
        return this.durationDays;
    }

    public Alert durationDays(Integer durationDays) {
        this.setDurationDays(durationDays);
        return this;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Integer getMinimumTemperature() {
        return this.minimumTemperature;
    }

    public Alert minimumTemperature(Integer minimumTemperature) {
        this.setMinimumTemperature(minimumTemperature);
        return this;
    }

    public void setMinimumTemperature(Integer minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public Integer getMaximumTemperature() {
        return this.maximumTemperature;
    }

    public Alert maximumTemperature(Integer maximumTemperature) {
        this.setMaximumTemperature(maximumTemperature);
        return this;
    }

    public void setMaximumTemperature(Integer maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public Integer getMinHumidity() {
        return this.minHumidity;
    }

    public Alert minHumidity(Integer minHumidity) {
        this.setMinHumidity(minHumidity);
        return this;
    }

    public void setMinHumidity(Integer minHumidity) {
        this.minHumidity = minHumidity;
    }

    public Integer getMaxHumidity() {
        return this.maxHumidity;
    }

    public Alert maxHumidity(Integer maxHumidity) {
        this.setMaxHumidity(maxHumidity);
        return this;
    }

    public void setMaxHumidity(Integer maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public PreType getPrecipitationType() {
        return this.precipitationType;
    }

    public Alert precipitationType(PreType precipitationType) {
        this.setPrecipitationType(precipitationType);
        return this;
    }

    public void setPrecipitationType(PreType precipitationType) {
        this.precipitationType = precipitationType;
    }

    public Integer getMinPrecipitation() {
        return this.minPrecipitation;
    }

    public Alert minPrecipitation(Integer minPrecipitation) {
        this.setMinPrecipitation(minPrecipitation);
        return this;
    }

    public void setMinPrecipitation(Integer minPrecipitation) {
        this.minPrecipitation = minPrecipitation;
    }

    public Integer getMaxPrecipitation() {
        return this.maxPrecipitation;
    }

    public Alert maxPrecipitation(Integer maxPrecipitation) {
        this.setMaxPrecipitation(maxPrecipitation);
        return this;
    }

    public void setMaxPrecipitation(Integer maxPrecipitation) {
        this.maxPrecipitation = maxPrecipitation;
    }

    public AlertStatus getStatus() {
        return this.status;
    }

    public Alert status(AlertStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public Remediation getRemediation() {
        return this.remediation;
    }

    public Alert remediation(Remediation remediation) {
        this.setRemediation(remediation);
        return this;
    }

    public void setRemediation(Remediation remediation) {
        this.remediation = remediation;
    }

    public String getFarmAssigned() {
        return this.farmAssigned;
    }

    public Alert farmAssigned(String farmAssigned) {
        this.setFarmAssigned(farmAssigned);
        return this;
    }

    public void setFarmAssigned(String farmAssigned) {
        this.farmAssigned = farmAssigned;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Alert createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Alert createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Alert updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Alert updatedOn(ZonedDateTime updatedOn) {
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
        if (!(o instanceof Alert)) {
            return false;
        }
        return id != null && id.equals(((Alert) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alert{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", name='" + getName() + "'" +
            ", alertType='" + getAlertType() + "'" +
            ", description='" + getDescription() + "'" +
            ", datePeriod=" + getDatePeriod() +
            ", durationDays=" + getDurationDays() +
            ", minimumTemperature=" + getMinimumTemperature() +
            ", maximumTemperature=" + getMaximumTemperature() +
            ", minHumidity=" + getMinHumidity() +
            ", maxHumidity=" + getMaxHumidity() +
            ", precipitationType='" + getPrecipitationType() + "'" +
            ", minPrecipitation=" + getMinPrecipitation() +
            ", maxPrecipitation=" + getMaxPrecipitation() +
            ", status='" + getStatus() + "'" +
            ", remediation='" + getRemediation() + "'" +
            ", farmAssigned='" + getFarmAssigned() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
