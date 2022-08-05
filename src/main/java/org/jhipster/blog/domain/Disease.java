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
import org.jhipster.blog.domain.enumeration.DisThreatLevel;

/**
 * A Disease.
 */
@Entity
@Table(name = "disease")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Disease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_level")
    private DisThreatLevel threatLevel;

    @Column(name = "name")
    private String name;

    @Column(name = "causal_organism")
    private String causalOrganism;

    @Column(name = "attachments")
    private Long attachments;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "diseaseID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseID", "cropID", "symptomID" }, allowSetters = true)
    private Set<DiseaseControl> diseaseControls = new HashSet<>();

    @OneToMany(mappedBy = "diseaseID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseControls", "scoutingID", "diseaseID", "pestID" }, allowSetters = true)
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "pests", "diseases", "symptoms", "disorders", "plantFactoryID", "cropID" }, allowSetters = true)
    private Scouting scoutingID;

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

    public Disease id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Disease gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public DisThreatLevel getThreatLevel() {
        return this.threatLevel;
    }

    public Disease threatLevel(DisThreatLevel threatLevel) {
        this.setThreatLevel(threatLevel);
        return this;
    }

    public void setThreatLevel(DisThreatLevel threatLevel) {
        this.threatLevel = threatLevel;
    }

    public String getName() {
        return this.name;
    }

    public Disease name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCausalOrganism() {
        return this.causalOrganism;
    }

    public Disease causalOrganism(String causalOrganism) {
        this.setCausalOrganism(causalOrganism);
        return this;
    }

    public void setCausalOrganism(String causalOrganism) {
        this.causalOrganism = causalOrganism;
    }

    public Long getAttachments() {
        return this.attachments;
    }

    public Disease attachments(Long attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(Long attachments) {
        this.attachments = attachments;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Disease createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Disease createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Disease updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Disease updatedOn(ZonedDateTime updatedOn) {
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
            this.diseaseControls.forEach(i -> i.setDiseaseID(null));
        }
        if (diseaseControls != null) {
            diseaseControls.forEach(i -> i.setDiseaseID(this));
        }
        this.diseaseControls = diseaseControls;
    }

    public Disease diseaseControls(Set<DiseaseControl> diseaseControls) {
        this.setDiseaseControls(diseaseControls);
        return this;
    }

    public Disease addDiseaseControl(DiseaseControl diseaseControl) {
        this.diseaseControls.add(diseaseControl);
        diseaseControl.setDiseaseID(this);
        return this;
    }

    public Disease removeDiseaseControl(DiseaseControl diseaseControl) {
        this.diseaseControls.remove(diseaseControl);
        diseaseControl.setDiseaseID(null);
        return this;
    }

    public Set<Symptom> getSymptoms() {
        return this.symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        if (this.symptoms != null) {
            this.symptoms.forEach(i -> i.setDiseaseID(null));
        }
        if (symptoms != null) {
            symptoms.forEach(i -> i.setDiseaseID(this));
        }
        this.symptoms = symptoms;
    }

    public Disease symptoms(Set<Symptom> symptoms) {
        this.setSymptoms(symptoms);
        return this;
    }

    public Disease addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.setDiseaseID(this);
        return this;
    }

    public Disease removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.setDiseaseID(null);
        return this;
    }

    public Scouting getScoutingID() {
        return this.scoutingID;
    }

    public void setScoutingID(Scouting scouting) {
        this.scoutingID = scouting;
    }

    public Disease scoutingID(Scouting scouting) {
        this.setScoutingID(scouting);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Disease plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disease)) {
            return false;
        }
        return id != null && id.equals(((Disease) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disease{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", threatLevel='" + getThreatLevel() + "'" +
            ", name='" + getName() + "'" +
            ", causalOrganism='" + getCausalOrganism() + "'" +
            ", attachments=" + getAttachments() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
