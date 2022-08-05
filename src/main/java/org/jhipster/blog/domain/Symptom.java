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
 * A Symptom.
 */
@Entity
@Table(name = "symptom")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Symptom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "observation")
    private String observation;

    @Lob
    @Column(name = "symptom_image")
    private byte[] symptomImage;

    @Column(name = "symptom_image_content_type")
    private String symptomImageContentType;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "symptomID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseID", "cropID", "symptomID" }, allowSetters = true)
    private Set<DiseaseControl> diseaseControls = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "pests", "diseases", "symptoms", "disorders", "plantFactoryID", "cropID" }, allowSetters = true)
    private Scouting scoutingID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "diseaseControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Disease diseaseID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pestControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Pest pestID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Symptom id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Symptom gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getObservation() {
        return this.observation;
    }

    public Symptom observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public byte[] getSymptomImage() {
        return this.symptomImage;
    }

    public Symptom symptomImage(byte[] symptomImage) {
        this.setSymptomImage(symptomImage);
        return this;
    }

    public void setSymptomImage(byte[] symptomImage) {
        this.symptomImage = symptomImage;
    }

    public String getSymptomImageContentType() {
        return this.symptomImageContentType;
    }

    public Symptom symptomImageContentType(String symptomImageContentType) {
        this.symptomImageContentType = symptomImageContentType;
        return this;
    }

    public void setSymptomImageContentType(String symptomImageContentType) {
        this.symptomImageContentType = symptomImageContentType;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Symptom createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Symptom createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Symptom updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Symptom updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<DiseaseControl> getDiseaseControls() {
        return this.diseaseControls;
    }

    public void setDiseaseControls(Set<DiseaseControl> diseaseControls) {
        if (this.diseaseControls != null) {
            this.diseaseControls.forEach(i -> i.setSymptomID(null));
        }
        if (diseaseControls != null) {
            diseaseControls.forEach(i -> i.setSymptomID(this));
        }
        this.diseaseControls = diseaseControls;
    }

    public Symptom diseaseControls(Set<DiseaseControl> diseaseControls) {
        this.setDiseaseControls(diseaseControls);
        return this;
    }

    public Symptom addDiseaseControl(DiseaseControl diseaseControl) {
        this.diseaseControls.add(diseaseControl);
        diseaseControl.setSymptomID(this);
        return this;
    }

    public Symptom removeDiseaseControl(DiseaseControl diseaseControl) {
        this.diseaseControls.remove(diseaseControl);
        diseaseControl.setSymptomID(null);
        return this;
    }

    public Scouting getScoutingID() {
        return this.scoutingID;
    }

    public void setScoutingID(Scouting scouting) {
        this.scoutingID = scouting;
    }

    public Symptom scoutingID(Scouting scouting) {
        this.setScoutingID(scouting);
        return this;
    }

    public Disease getDiseaseID() {
        return this.diseaseID;
    }

    public void setDiseaseID(Disease disease) {
        this.diseaseID = disease;
    }

    public Symptom diseaseID(Disease disease) {
        this.setDiseaseID(disease);
        return this;
    }

    public Pest getPestID() {
        return this.pestID;
    }

    public void setPestID(Pest pest) {
        this.pestID = pest;
    }

    public Symptom pestID(Pest pest) {
        this.setPestID(pest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Symptom)) {
            return false;
        }
        return id != null && id.equals(((Symptom) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Symptom{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", observation='" + getObservation() + "'" +
            ", symptomImage='" + getSymptomImage() + "'" +
            ", symptomImageContentType='" + getSymptomImageContentType() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
