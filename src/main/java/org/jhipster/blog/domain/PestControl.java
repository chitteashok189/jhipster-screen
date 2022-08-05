package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.ConType;

/**
 * A PestControl.
 */
@Entity
@Table(name = "pest_control")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PestControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "nature_of_damage")
    private String natureOfDamage;

    @Enumerated(EnumType.STRING)
    @Column(name = "control_type")
    private ConType controlType;

    @Column(name = "control_measures")
    private String controlMeasures;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pestControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Pest pestID;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "plants",
            "calendars",
            "scoutings",
            "pestControls",
            "diseaseControls",
            "activities",
            "harvests",
            "lots",
            "plantID",
            "plantFactoryID",
            "toolID",
            "seasonID",
            "productID",
        },
        allowSetters = true
    )
    private Crop cropID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PestControl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public PestControl gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getNatureOfDamage() {
        return this.natureOfDamage;
    }

    public PestControl natureOfDamage(String natureOfDamage) {
        this.setNatureOfDamage(natureOfDamage);
        return this;
    }

    public void setNatureOfDamage(String natureOfDamage) {
        this.natureOfDamage = natureOfDamage;
    }

    public ConType getControlType() {
        return this.controlType;
    }

    public PestControl controlType(ConType controlType) {
        this.setControlType(controlType);
        return this;
    }

    public void setControlType(ConType controlType) {
        this.controlType = controlType;
    }

    public String getControlMeasures() {
        return this.controlMeasures;
    }

    public PestControl controlMeasures(String controlMeasures) {
        this.setControlMeasures(controlMeasures);
        return this;
    }

    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public PestControl createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public PestControl createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PestControl updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public PestControl updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Pest getPestID() {
        return this.pestID;
    }

    public void setPestID(Pest pest) {
        this.pestID = pest;
    }

    public PestControl pestID(Pest pest) {
        this.setPestID(pest);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public PestControl cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PestControl)) {
            return false;
        }
        return id != null && id.equals(((PestControl) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PestControl{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", natureOfDamage='" + getNatureOfDamage() + "'" +
            ", controlType='" + getControlType() + "'" +
            ", controlMeasures='" + getControlMeasures() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
