package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.DisConType;

/**
 * A DiseaseControl.
 */
@Entity
@Table(name = "disease_control")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiseaseControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "control_type")
    private DisConType controlType;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "diseaseControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Disease diseaseID;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "diseaseControls", "scoutingID", "diseaseID", "pestID" }, allowSetters = true)
    private Symptom symptomID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DiseaseControl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public DiseaseControl gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public DisConType getControlType() {
        return this.controlType;
    }

    public DiseaseControl controlType(DisConType controlType) {
        this.setControlType(controlType);
        return this;
    }

    public void setControlType(DisConType controlType) {
        this.controlType = controlType;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public DiseaseControl treatment(String treatment) {
        this.setTreatment(treatment);
        return this;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public DiseaseControl createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public DiseaseControl createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public DiseaseControl updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public DiseaseControl updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Disease getDiseaseID() {
        return this.diseaseID;
    }

    public void setDiseaseID(Disease disease) {
        this.diseaseID = disease;
    }

    public DiseaseControl diseaseID(Disease disease) {
        this.setDiseaseID(disease);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public DiseaseControl cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    public Symptom getSymptomID() {
        return this.symptomID;
    }

    public void setSymptomID(Symptom symptom) {
        this.symptomID = symptom;
    }

    public DiseaseControl symptomID(Symptom symptom) {
        this.setSymptomID(symptom);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiseaseControl)) {
            return false;
        }
        return id != null && id.equals(((DiseaseControl) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiseaseControl{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", controlType='" + getControlType() + "'" +
            ", treatment='" + getTreatment() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
