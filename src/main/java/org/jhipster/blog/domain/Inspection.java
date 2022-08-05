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
import org.jhipster.blog.domain.enumeration.Aroma;
import org.jhipster.blog.domain.enumeration.Defect;
import org.jhipster.blog.domain.enumeration.Flavour;
import org.jhipster.blog.domain.enumeration.NutritionalValueType;
import org.jhipster.blog.domain.enumeration.Texture;

/**
 * A Inspection.
 */
@Entity
@Table(name = "inspection")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inspection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "inspection_size")
    private Integer inspectionSize;

    @Column(name = "shape")
    private Integer shape;

    @Column(name = "wholeness")
    private Integer wholeness;

    @Column(name = "gloss")
    private Integer gloss;

    @Column(name = "consistency")
    private Integer consistency;

    @Enumerated(EnumType.STRING)
    @Column(name = "defects")
    private Defect defects;

    @Column(name = "colour")
    private String colour;

    @Enumerated(EnumType.STRING)
    @Column(name = "texture")
    private Texture texture;

    @Enumerated(EnumType.STRING)
    @Column(name = "aroma")
    private Aroma aroma;

    @Enumerated(EnumType.STRING)
    @Column(name = "flavour")
    private Flavour flavour;

    @Column(name = "nutritional_value")
    private Integer nutritionalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "nutritional_value_type")
    private NutritionalValueType nutritionalValueType;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "inspectionID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionID", "lotID" }, allowSetters = true)
    private Set<InspectionRecord> inspectionRecords = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "inspections", "cropID", "lotID" }, allowSetters = true)
    private Harvest harvestID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inspection id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Inspection gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Integer getInspectionSize() {
        return this.inspectionSize;
    }

    public Inspection inspectionSize(Integer inspectionSize) {
        this.setInspectionSize(inspectionSize);
        return this;
    }

    public void setInspectionSize(Integer inspectionSize) {
        this.inspectionSize = inspectionSize;
    }

    public Integer getShape() {
        return this.shape;
    }

    public Inspection shape(Integer shape) {
        this.setShape(shape);
        return this;
    }

    public void setShape(Integer shape) {
        this.shape = shape;
    }

    public Integer getWholeness() {
        return this.wholeness;
    }

    public Inspection wholeness(Integer wholeness) {
        this.setWholeness(wholeness);
        return this;
    }

    public void setWholeness(Integer wholeness) {
        this.wholeness = wholeness;
    }

    public Integer getGloss() {
        return this.gloss;
    }

    public Inspection gloss(Integer gloss) {
        this.setGloss(gloss);
        return this;
    }

    public void setGloss(Integer gloss) {
        this.gloss = gloss;
    }

    public Integer getConsistency() {
        return this.consistency;
    }

    public Inspection consistency(Integer consistency) {
        this.setConsistency(consistency);
        return this;
    }

    public void setConsistency(Integer consistency) {
        this.consistency = consistency;
    }

    public Defect getDefects() {
        return this.defects;
    }

    public Inspection defects(Defect defects) {
        this.setDefects(defects);
        return this;
    }

    public void setDefects(Defect defects) {
        this.defects = defects;
    }

    public String getColour() {
        return this.colour;
    }

    public Inspection colour(String colour) {
        this.setColour(colour);
        return this;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Inspection texture(Texture texture) {
        this.setTexture(texture);
        return this;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Aroma getAroma() {
        return this.aroma;
    }

    public Inspection aroma(Aroma aroma) {
        this.setAroma(aroma);
        return this;
    }

    public void setAroma(Aroma aroma) {
        this.aroma = aroma;
    }

    public Flavour getFlavour() {
        return this.flavour;
    }

    public Inspection flavour(Flavour flavour) {
        this.setFlavour(flavour);
        return this;
    }

    public void setFlavour(Flavour flavour) {
        this.flavour = flavour;
    }

    public Integer getNutritionalValue() {
        return this.nutritionalValue;
    }

    public Inspection nutritionalValue(Integer nutritionalValue) {
        this.setNutritionalValue(nutritionalValue);
        return this;
    }

    public void setNutritionalValue(Integer nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public NutritionalValueType getNutritionalValueType() {
        return this.nutritionalValueType;
    }

    public Inspection nutritionalValueType(NutritionalValueType nutritionalValueType) {
        this.setNutritionalValueType(nutritionalValueType);
        return this;
    }

    public void setNutritionalValueType(NutritionalValueType nutritionalValueType) {
        this.nutritionalValueType = nutritionalValueType;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Inspection createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Inspection createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Inspection updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Inspection updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<InspectionRecord> getInspectionRecords() {
        return this.inspectionRecords;
    }

    public void setInspectionRecords(Set<InspectionRecord> inspectionRecords) {
        if (this.inspectionRecords != null) {
            this.inspectionRecords.forEach(i -> i.setInspectionID(null));
        }
        if (inspectionRecords != null) {
            inspectionRecords.forEach(i -> i.setInspectionID(this));
        }
        this.inspectionRecords = inspectionRecords;
    }

    public Inspection inspectionRecords(Set<InspectionRecord> inspectionRecords) {
        this.setInspectionRecords(inspectionRecords);
        return this;
    }

    public Inspection addInspectionRecord(InspectionRecord inspectionRecord) {
        this.inspectionRecords.add(inspectionRecord);
        inspectionRecord.setInspectionID(this);
        return this;
    }

    public Inspection removeInspectionRecord(InspectionRecord inspectionRecord) {
        this.inspectionRecords.remove(inspectionRecord);
        inspectionRecord.setInspectionID(null);
        return this;
    }

    public Harvest getHarvestID() {
        return this.harvestID;
    }

    public void setHarvestID(Harvest harvest) {
        this.harvestID = harvest;
    }

    public Inspection harvestID(Harvest harvest) {
        this.setHarvestID(harvest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspection)) {
            return false;
        }
        return id != null && id.equals(((Inspection) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inspection{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", inspectionSize=" + getInspectionSize() +
            ", shape=" + getShape() +
            ", wholeness=" + getWholeness() +
            ", gloss=" + getGloss() +
            ", consistency=" + getConsistency() +
            ", defects='" + getDefects() + "'" +
            ", colour='" + getColour() + "'" +
            ", texture='" + getTexture() + "'" +
            ", aroma='" + getAroma() + "'" +
            ", flavour='" + getFlavour() + "'" +
            ", nutritionalValue=" + getNutritionalValue() +
            ", nutritionalValueType='" + getNutritionalValueType() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
