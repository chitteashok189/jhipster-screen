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
import org.jhipster.blog.domain.enumeration.ThreatLevel;

/**
 * A Pest.
 */
@Entity
@Table(name = "pest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pest implements Serializable {

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
    private ThreatLevel threatLevel;

    @Column(name = "name")
    private String name;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "attachements")
    private Long attachements;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "pestID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pestID", "cropID" }, allowSetters = true)
    private Set<PestControl> pestControls = new HashSet<>();

    @OneToMany(mappedBy = "pestID")
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

    public Pest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Pest gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public ThreatLevel getThreatLevel() {
        return this.threatLevel;
    }

    public Pest threatLevel(ThreatLevel threatLevel) {
        this.setThreatLevel(threatLevel);
        return this;
    }

    public void setThreatLevel(ThreatLevel threatLevel) {
        this.threatLevel = threatLevel;
    }

    public String getName() {
        return this.name;
    }

    public Pest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public Pest scientificName(String scientificName) {
        this.setScientificName(scientificName);
        return this;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public Long getAttachements() {
        return this.attachements;
    }

    public Pest attachements(Long attachements) {
        this.setAttachements(attachements);
        return this;
    }

    public void setAttachements(Long attachements) {
        this.attachements = attachements;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Pest createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Pest createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Pest updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Pest updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PestControl> getPestControls() {
        return this.pestControls;
    }

    public void setPestControls(Set<PestControl> pestControls) {
        if (this.pestControls != null) {
            this.pestControls.forEach(i -> i.setPestID(null));
        }
        if (pestControls != null) {
            pestControls.forEach(i -> i.setPestID(this));
        }
        this.pestControls = pestControls;
    }

    public Pest pestControls(Set<PestControl> pestControls) {
        this.setPestControls(pestControls);
        return this;
    }

    public Pest addPestControl(PestControl pestControl) {
        this.pestControls.add(pestControl);
        pestControl.setPestID(this);
        return this;
    }

    public Pest removePestControl(PestControl pestControl) {
        this.pestControls.remove(pestControl);
        pestControl.setPestID(null);
        return this;
    }

    public Set<Symptom> getSymptoms() {
        return this.symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        if (this.symptoms != null) {
            this.symptoms.forEach(i -> i.setPestID(null));
        }
        if (symptoms != null) {
            symptoms.forEach(i -> i.setPestID(this));
        }
        this.symptoms = symptoms;
    }

    public Pest symptoms(Set<Symptom> symptoms) {
        this.setSymptoms(symptoms);
        return this;
    }

    public Pest addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.setPestID(this);
        return this;
    }

    public Pest removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.setPestID(null);
        return this;
    }

    public Scouting getScoutingID() {
        return this.scoutingID;
    }

    public void setScoutingID(Scouting scouting) {
        this.scoutingID = scouting;
    }

    public Pest scoutingID(Scouting scouting) {
        this.setScoutingID(scouting);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Pest plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pest)) {
            return false;
        }
        return id != null && id.equals(((Pest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pest{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", threatLevel='" + getThreatLevel() + "'" +
            ", name='" + getName() + "'" +
            ", scientificName='" + getScientificName() + "'" +
            ", attachements=" + getAttachements() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
