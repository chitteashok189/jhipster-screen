package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.CropState;
import org.jhipster.blog.domain.enumeration.ScoutingType;

/**
 * A Scouting.
 */
@Entity
@Table(name = "scouting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Scouting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "scouting_date")
    private Instant scoutingDate;

    @Column(name = "scout")
    private String scout;

    @Enumerated(EnumType.STRING)
    @Column(name = "scouting_type")
    private ScoutingType scoutingType;

    @Column(name = "scouting_coordinates")
    private Integer scoutingCoordinates;

    @Column(name = "scouting_area")
    private Integer scoutingArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "crop_state")
    private CropState cropState;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "scoutingID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pestControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Set<Pest> pests = new HashSet<>();

    @OneToMany(mappedBy = "scoutingID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseControls", "symptoms", "scoutingID", "plantFactoryID" }, allowSetters = true)
    private Set<Disease> diseases = new HashSet<>();

    @OneToMany(mappedBy = "scoutingID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseControls", "scoutingID", "diseaseID", "pestID" }, allowSetters = true)
    private Set<Symptom> symptoms = new HashSet<>();

    @OneToMany(mappedBy = "scoutingID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "scoutingID" }, allowSetters = true)
    private Set<Disorder> disorders = new HashSet<>();

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

    public Scouting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Scouting gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Instant getScoutingDate() {
        return this.scoutingDate;
    }

    public Scouting scoutingDate(Instant scoutingDate) {
        this.setScoutingDate(scoutingDate);
        return this;
    }

    public void setScoutingDate(Instant scoutingDate) {
        this.scoutingDate = scoutingDate;
    }

    public String getScout() {
        return this.scout;
    }

    public Scouting scout(String scout) {
        this.setScout(scout);
        return this;
    }

    public void setScout(String scout) {
        this.scout = scout;
    }

    public ScoutingType getScoutingType() {
        return this.scoutingType;
    }

    public Scouting scoutingType(ScoutingType scoutingType) {
        this.setScoutingType(scoutingType);
        return this;
    }

    public void setScoutingType(ScoutingType scoutingType) {
        this.scoutingType = scoutingType;
    }

    public Integer getScoutingCoordinates() {
        return this.scoutingCoordinates;
    }

    public Scouting scoutingCoordinates(Integer scoutingCoordinates) {
        this.setScoutingCoordinates(scoutingCoordinates);
        return this;
    }

    public void setScoutingCoordinates(Integer scoutingCoordinates) {
        this.scoutingCoordinates = scoutingCoordinates;
    }

    public Integer getScoutingArea() {
        return this.scoutingArea;
    }

    public Scouting scoutingArea(Integer scoutingArea) {
        this.setScoutingArea(scoutingArea);
        return this;
    }

    public void setScoutingArea(Integer scoutingArea) {
        this.scoutingArea = scoutingArea;
    }

    public CropState getCropState() {
        return this.cropState;
    }

    public Scouting cropState(CropState cropState) {
        this.setCropState(cropState);
        return this;
    }

    public void setCropState(CropState cropState) {
        this.cropState = cropState;
    }

    public String getComments() {
        return this.comments;
    }

    public Scouting comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Scouting createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Scouting createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Scouting updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Scouting updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Pest> getPests() {
        return this.pests;
    }

    public void setPests(Set<Pest> pests) {
        if (this.pests != null) {
            this.pests.forEach(i -> i.setScoutingID(null));
        }
        if (pests != null) {
            pests.forEach(i -> i.setScoutingID(this));
        }
        this.pests = pests;
    }

    public Scouting pests(Set<Pest> pests) {
        this.setPests(pests);
        return this;
    }

    public Scouting addPest(Pest pest) {
        this.pests.add(pest);
        pest.setScoutingID(this);
        return this;
    }

    public Scouting removePest(Pest pest) {
        this.pests.remove(pest);
        pest.setScoutingID(null);
        return this;
    }

    public Set<Disease> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        if (this.diseases != null) {
            this.diseases.forEach(i -> i.setScoutingID(null));
        }
        if (diseases != null) {
            diseases.forEach(i -> i.setScoutingID(this));
        }
        this.diseases = diseases;
    }

    public Scouting diseases(Set<Disease> diseases) {
        this.setDiseases(diseases);
        return this;
    }

    public Scouting addDisease(Disease disease) {
        this.diseases.add(disease);
        disease.setScoutingID(this);
        return this;
    }

    public Scouting removeDisease(Disease disease) {
        this.diseases.remove(disease);
        disease.setScoutingID(null);
        return this;
    }

    public Set<Symptom> getSymptoms() {
        return this.symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        if (this.symptoms != null) {
            this.symptoms.forEach(i -> i.setScoutingID(null));
        }
        if (symptoms != null) {
            symptoms.forEach(i -> i.setScoutingID(this));
        }
        this.symptoms = symptoms;
    }

    public Scouting symptoms(Set<Symptom> symptoms) {
        this.setSymptoms(symptoms);
        return this;
    }

    public Scouting addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.setScoutingID(this);
        return this;
    }

    public Scouting removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.setScoutingID(null);
        return this;
    }

    public Set<Disorder> getDisorders() {
        return this.disorders;
    }

    public void setDisorders(Set<Disorder> disorders) {
        if (this.disorders != null) {
            this.disorders.forEach(i -> i.setScoutingID(null));
        }
        if (disorders != null) {
            disorders.forEach(i -> i.setScoutingID(this));
        }
        this.disorders = disorders;
    }

    public Scouting disorders(Set<Disorder> disorders) {
        this.setDisorders(disorders);
        return this;
    }

    public Scouting addDisorder(Disorder disorder) {
        this.disorders.add(disorder);
        disorder.setScoutingID(this);
        return this;
    }

    public Scouting removeDisorder(Disorder disorder) {
        this.disorders.remove(disorder);
        disorder.setScoutingID(null);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Scouting plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public Scouting cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scouting)) {
            return false;
        }
        return id != null && id.equals(((Scouting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scouting{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", scoutingDate='" + getScoutingDate() + "'" +
            ", scout='" + getScout() + "'" +
            ", scoutingType='" + getScoutingType() + "'" +
            ", scoutingCoordinates=" + getScoutingCoordinates() +
            ", scoutingArea=" + getScoutingArea() +
            ", cropState='" + getCropState() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
