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
import org.jhipster.blog.domain.enumeration.CropTyp;
import org.jhipster.blog.domain.enumeration.GroPhase;
import org.jhipster.blog.domain.enumeration.Horti;
import org.jhipster.blog.domain.enumeration.PlantingMat;

/**
 * A Crop.
 */
@Entity
@Table(name = "crop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "crop_code")
    private String cropCode;

    @Column(name = "crop_name")
    private String cropName;

    @Enumerated(EnumType.STRING)
    @Column(name = "crop_type")
    private CropTyp cropType;

    @Enumerated(EnumType.STRING)
    @Column(name = "horticulture_type")
    private Horti horticultureType;

    @Column(name = "is_hybrid")
    private Boolean isHybrid;

    @Column(name = "cultivar")
    private String cultivar;

    @Column(name = "sowing_date")
    private Instant sowingDate;

    @Column(name = "sowing_depth")
    private Integer sowingDepth;

    @Column(name = "row_spacing_max")
    private Integer rowSpacingMax;

    @Column(name = "row_spacing_min")
    private Integer rowSpacingMin;

    @Column(name = "seed_depth_max")
    private Integer seedDepthMax;

    @Column(name = "seed_depth_min")
    private Integer seedDepthMin;

    @Column(name = "seed_spacing_max")
    private Integer seedSpacingMax;

    @Column(name = "seed_spacing_min")
    private Integer seedSpacingMin;

    @Column(name = "yearly_crops")
    private Integer yearlyCrops;

    @Column(name = "growing_season")
    private String growingSeason;

    @Enumerated(EnumType.STRING)
    @Column(name = "growing_phase")
    private GroPhase growingPhase;

    @Column(name = "planting_date")
    private Instant plantingDate;

    @Column(name = "plant_spacing")
    private Integer plantSpacing;

    @Enumerated(EnumType.STRING)
    @Column(name = "planting_material")
    private PlantingMat plantingMaterial;

    @Column(name = "transplantation_date")
    private Instant transplantationDate;

    @Column(name = "fertigationschedule_id")
    private Long fertigationscheduleID;

    @Column(name = "planned_yield")
    private Integer plannedYield;

    @Column(name = "actual_yield")
    private Integer actualYield;

    @Column(name = "yield_unit")
    private Integer yieldUnit;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recipes", "crops", "cropID" }, allowSetters = true)
    private Set<Plant> plants = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "weathers", "plantFactoryID", "zoneID", "cropID", "toolID", "seasonID" }, allowSetters = true)
    private Set<Calendar> calendars = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pests", "diseases", "symptoms", "disorders", "plantFactoryID", "cropID" }, allowSetters = true)
    private Set<Scouting> scoutings = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pestID", "cropID" }, allowSetters = true)
    private Set<PestControl> pestControls = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diseaseID", "cropID", "symptomID" }, allowSetters = true)
    private Set<DiseaseControl> diseaseControls = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "zoneID", "cropID" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspections", "cropID", "lotID" }, allowSetters = true)
    private Set<Harvest> harvests = new HashSet<>();

    @OneToMany(mappedBy = "cropID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "harvests", "inspectionRecords", "seedID", "cropID" }, allowSetters = true)
    private Set<Lot> lots = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "recipes", "crops", "cropID" }, allowSetters = true)
    private Plant plantID;

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
    @JsonIgnoreProperties(value = { "crops", "calendars" }, allowSetters = true)
    private Tool toolID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "crops", "calendars" }, allowSetters = true)
    private Season seasonID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "crops" }, allowSetters = true)
    private Product productID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Crop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Crop gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getCropCode() {
        return this.cropCode;
    }

    public Crop cropCode(String cropCode) {
        this.setCropCode(cropCode);
        return this;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getCropName() {
        return this.cropName;
    }

    public Crop cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public CropTyp getCropType() {
        return this.cropType;
    }

    public Crop cropType(CropTyp cropType) {
        this.setCropType(cropType);
        return this;
    }

    public void setCropType(CropTyp cropType) {
        this.cropType = cropType;
    }

    public Horti getHorticultureType() {
        return this.horticultureType;
    }

    public Crop horticultureType(Horti horticultureType) {
        this.setHorticultureType(horticultureType);
        return this;
    }

    public void setHorticultureType(Horti horticultureType) {
        this.horticultureType = horticultureType;
    }

    public Boolean getIsHybrid() {
        return this.isHybrid;
    }

    public Crop isHybrid(Boolean isHybrid) {
        this.setIsHybrid(isHybrid);
        return this;
    }

    public void setIsHybrid(Boolean isHybrid) {
        this.isHybrid = isHybrid;
    }

    public String getCultivar() {
        return this.cultivar;
    }

    public Crop cultivar(String cultivar) {
        this.setCultivar(cultivar);
        return this;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public Instant getSowingDate() {
        return this.sowingDate;
    }

    public Crop sowingDate(Instant sowingDate) {
        this.setSowingDate(sowingDate);
        return this;
    }

    public void setSowingDate(Instant sowingDate) {
        this.sowingDate = sowingDate;
    }

    public Integer getSowingDepth() {
        return this.sowingDepth;
    }

    public Crop sowingDepth(Integer sowingDepth) {
        this.setSowingDepth(sowingDepth);
        return this;
    }

    public void setSowingDepth(Integer sowingDepth) {
        this.sowingDepth = sowingDepth;
    }

    public Integer getRowSpacingMax() {
        return this.rowSpacingMax;
    }

    public Crop rowSpacingMax(Integer rowSpacingMax) {
        this.setRowSpacingMax(rowSpacingMax);
        return this;
    }

    public void setRowSpacingMax(Integer rowSpacingMax) {
        this.rowSpacingMax = rowSpacingMax;
    }

    public Integer getRowSpacingMin() {
        return this.rowSpacingMin;
    }

    public Crop rowSpacingMin(Integer rowSpacingMin) {
        this.setRowSpacingMin(rowSpacingMin);
        return this;
    }

    public void setRowSpacingMin(Integer rowSpacingMin) {
        this.rowSpacingMin = rowSpacingMin;
    }

    public Integer getSeedDepthMax() {
        return this.seedDepthMax;
    }

    public Crop seedDepthMax(Integer seedDepthMax) {
        this.setSeedDepthMax(seedDepthMax);
        return this;
    }

    public void setSeedDepthMax(Integer seedDepthMax) {
        this.seedDepthMax = seedDepthMax;
    }

    public Integer getSeedDepthMin() {
        return this.seedDepthMin;
    }

    public Crop seedDepthMin(Integer seedDepthMin) {
        this.setSeedDepthMin(seedDepthMin);
        return this;
    }

    public void setSeedDepthMin(Integer seedDepthMin) {
        this.seedDepthMin = seedDepthMin;
    }

    public Integer getSeedSpacingMax() {
        return this.seedSpacingMax;
    }

    public Crop seedSpacingMax(Integer seedSpacingMax) {
        this.setSeedSpacingMax(seedSpacingMax);
        return this;
    }

    public void setSeedSpacingMax(Integer seedSpacingMax) {
        this.seedSpacingMax = seedSpacingMax;
    }

    public Integer getSeedSpacingMin() {
        return this.seedSpacingMin;
    }

    public Crop seedSpacingMin(Integer seedSpacingMin) {
        this.setSeedSpacingMin(seedSpacingMin);
        return this;
    }

    public void setSeedSpacingMin(Integer seedSpacingMin) {
        this.seedSpacingMin = seedSpacingMin;
    }

    public Integer getYearlyCrops() {
        return this.yearlyCrops;
    }

    public Crop yearlyCrops(Integer yearlyCrops) {
        this.setYearlyCrops(yearlyCrops);
        return this;
    }

    public void setYearlyCrops(Integer yearlyCrops) {
        this.yearlyCrops = yearlyCrops;
    }

    public String getGrowingSeason() {
        return this.growingSeason;
    }

    public Crop growingSeason(String growingSeason) {
        this.setGrowingSeason(growingSeason);
        return this;
    }

    public void setGrowingSeason(String growingSeason) {
        this.growingSeason = growingSeason;
    }

    public GroPhase getGrowingPhase() {
        return this.growingPhase;
    }

    public Crop growingPhase(GroPhase growingPhase) {
        this.setGrowingPhase(growingPhase);
        return this;
    }

    public void setGrowingPhase(GroPhase growingPhase) {
        this.growingPhase = growingPhase;
    }

    public Instant getPlantingDate() {
        return this.plantingDate;
    }

    public Crop plantingDate(Instant plantingDate) {
        this.setPlantingDate(plantingDate);
        return this;
    }

    public void setPlantingDate(Instant plantingDate) {
        this.plantingDate = plantingDate;
    }

    public Integer getPlantSpacing() {
        return this.plantSpacing;
    }

    public Crop plantSpacing(Integer plantSpacing) {
        this.setPlantSpacing(plantSpacing);
        return this;
    }

    public void setPlantSpacing(Integer plantSpacing) {
        this.plantSpacing = plantSpacing;
    }

    public PlantingMat getPlantingMaterial() {
        return this.plantingMaterial;
    }

    public Crop plantingMaterial(PlantingMat plantingMaterial) {
        this.setPlantingMaterial(plantingMaterial);
        return this;
    }

    public void setPlantingMaterial(PlantingMat plantingMaterial) {
        this.plantingMaterial = plantingMaterial;
    }

    public Instant getTransplantationDate() {
        return this.transplantationDate;
    }

    public Crop transplantationDate(Instant transplantationDate) {
        this.setTransplantationDate(transplantationDate);
        return this;
    }

    public void setTransplantationDate(Instant transplantationDate) {
        this.transplantationDate = transplantationDate;
    }

    public Long getFertigationscheduleID() {
        return this.fertigationscheduleID;
    }

    public Crop fertigationscheduleID(Long fertigationscheduleID) {
        this.setFertigationscheduleID(fertigationscheduleID);
        return this;
    }

    public void setFertigationscheduleID(Long fertigationscheduleID) {
        this.fertigationscheduleID = fertigationscheduleID;
    }

    public Integer getPlannedYield() {
        return this.plannedYield;
    }

    public Crop plannedYield(Integer plannedYield) {
        this.setPlannedYield(plannedYield);
        return this;
    }

    public void setPlannedYield(Integer plannedYield) {
        this.plannedYield = plannedYield;
    }

    public Integer getActualYield() {
        return this.actualYield;
    }

    public Crop actualYield(Integer actualYield) {
        this.setActualYield(actualYield);
        return this;
    }

    public void setActualYield(Integer actualYield) {
        this.actualYield = actualYield;
    }

    public Integer getYieldUnit() {
        return this.yieldUnit;
    }

    public Crop yieldUnit(Integer yieldUnit) {
        this.setYieldUnit(yieldUnit);
        return this;
    }

    public void setYieldUnit(Integer yieldUnit) {
        this.yieldUnit = yieldUnit;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Crop createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Crop createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Crop updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Crop updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Plant> getPlants() {
        return this.plants;
    }

    public void setPlants(Set<Plant> plants) {
        if (this.plants != null) {
            this.plants.forEach(i -> i.setCropID(null));
        }
        if (plants != null) {
            plants.forEach(i -> i.setCropID(this));
        }
        this.plants = plants;
    }

    public Crop plants(Set<Plant> plants) {
        this.setPlants(plants);
        return this;
    }

    public Crop addPlant(Plant plant) {
        this.plants.add(plant);
        plant.setCropID(this);
        return this;
    }

    public Crop removePlant(Plant plant) {
        this.plants.remove(plant);
        plant.setCropID(null);
        return this;
    }

    public Set<Calendar> getCalendars() {
        return this.calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        if (this.calendars != null) {
            this.calendars.forEach(i -> i.setCropID(null));
        }
        if (calendars != null) {
            calendars.forEach(i -> i.setCropID(this));
        }
        this.calendars = calendars;
    }

    public Crop calendars(Set<Calendar> calendars) {
        this.setCalendars(calendars);
        return this;
    }

    public Crop addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.setCropID(this);
        return this;
    }

    public Crop removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.setCropID(null);
        return this;
    }

    public Set<Scouting> getScoutings() {
        return this.scoutings;
    }

    public void setScoutings(Set<Scouting> scoutings) {
        if (this.scoutings != null) {
            this.scoutings.forEach(i -> i.setCropID(null));
        }
        if (scoutings != null) {
            scoutings.forEach(i -> i.setCropID(this));
        }
        this.scoutings = scoutings;
    }

    public Crop scoutings(Set<Scouting> scoutings) {
        this.setScoutings(scoutings);
        return this;
    }

    public Crop addScouting(Scouting scouting) {
        this.scoutings.add(scouting);
        scouting.setCropID(this);
        return this;
    }

    public Crop removeScouting(Scouting scouting) {
        this.scoutings.remove(scouting);
        scouting.setCropID(null);
        return this;
    }

    public Set<PestControl> getPestControls() {
        return this.pestControls;
    }

    public void setPestControls(Set<PestControl> pestControls) {
        if (this.pestControls != null) {
            this.pestControls.forEach(i -> i.setCropID(null));
        }
        if (pestControls != null) {
            pestControls.forEach(i -> i.setCropID(this));
        }
        this.pestControls = pestControls;
    }

    public Crop pestControls(Set<PestControl> pestControls) {
        this.setPestControls(pestControls);
        return this;
    }

    public Crop addPestControl(PestControl pestControl) {
        this.pestControls.add(pestControl);
        pestControl.setCropID(this);
        return this;
    }

    public Crop removePestControl(PestControl pestControl) {
        this.pestControls.remove(pestControl);
        pestControl.setCropID(null);
        return this;
    }

    public Set<DiseaseControl> getDiseaseControls() {
        return this.diseaseControls;
    }

    public void setDiseaseControls(Set<DiseaseControl> diseaseControls) {
        if (this.diseaseControls != null) {
            this.diseaseControls.forEach(i -> i.setCropID(null));
        }
        if (diseaseControls != null) {
            diseaseControls.forEach(i -> i.setCropID(this));
        }
        this.diseaseControls = diseaseControls;
    }

    public Crop diseaseControls(Set<DiseaseControl> diseaseControls) {
        this.setDiseaseControls(diseaseControls);
        return this;
    }

    public Crop addDiseaseControl(DiseaseControl diseaseControl) {
        this.diseaseControls.add(diseaseControl);
        diseaseControl.setCropID(this);
        return this;
    }

    public Crop removeDiseaseControl(DiseaseControl diseaseControl) {
        this.diseaseControls.remove(diseaseControl);
        diseaseControl.setCropID(null);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.setCropID(null));
        }
        if (activities != null) {
            activities.forEach(i -> i.setCropID(this));
        }
        this.activities = activities;
    }

    public Crop activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Crop addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setCropID(this);
        return this;
    }

    public Crop removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setCropID(null);
        return this;
    }

    public Set<Harvest> getHarvests() {
        return this.harvests;
    }

    public void setHarvests(Set<Harvest> harvests) {
        if (this.harvests != null) {
            this.harvests.forEach(i -> i.setCropID(null));
        }
        if (harvests != null) {
            harvests.forEach(i -> i.setCropID(this));
        }
        this.harvests = harvests;
    }

    public Crop harvests(Set<Harvest> harvests) {
        this.setHarvests(harvests);
        return this;
    }

    public Crop addHarvest(Harvest harvest) {
        this.harvests.add(harvest);
        harvest.setCropID(this);
        return this;
    }

    public Crop removeHarvest(Harvest harvest) {
        this.harvests.remove(harvest);
        harvest.setCropID(null);
        return this;
    }

    public Set<Lot> getLots() {
        return this.lots;
    }

    public void setLots(Set<Lot> lots) {
        if (this.lots != null) {
            this.lots.forEach(i -> i.setCropID(null));
        }
        if (lots != null) {
            lots.forEach(i -> i.setCropID(this));
        }
        this.lots = lots;
    }

    public Crop lots(Set<Lot> lots) {
        this.setLots(lots);
        return this;
    }

    public Crop addLot(Lot lot) {
        this.lots.add(lot);
        lot.setCropID(this);
        return this;
    }

    public Crop removeLot(Lot lot) {
        this.lots.remove(lot);
        lot.setCropID(null);
        return this;
    }

    public Plant getPlantID() {
        return this.plantID;
    }

    public void setPlantID(Plant plant) {
        this.plantID = plant;
    }

    public Crop plantID(Plant plant) {
        this.setPlantID(plant);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Crop plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    public Tool getToolID() {
        return this.toolID;
    }

    public void setToolID(Tool tool) {
        this.toolID = tool;
    }

    public Crop toolID(Tool tool) {
        this.setToolID(tool);
        return this;
    }

    public Season getSeasonID() {
        return this.seasonID;
    }

    public void setSeasonID(Season season) {
        this.seasonID = season;
    }

    public Crop seasonID(Season season) {
        this.setSeasonID(season);
        return this;
    }

    public Product getProductID() {
        return this.productID;
    }

    public void setProductID(Product product) {
        this.productID = product;
    }

    public Crop productID(Product product) {
        this.setProductID(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crop)) {
            return false;
        }
        return id != null && id.equals(((Crop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crop{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", cropCode='" + getCropCode() + "'" +
            ", cropName='" + getCropName() + "'" +
            ", cropType='" + getCropType() + "'" +
            ", horticultureType='" + getHorticultureType() + "'" +
            ", isHybrid='" + getIsHybrid() + "'" +
            ", cultivar='" + getCultivar() + "'" +
            ", sowingDate='" + getSowingDate() + "'" +
            ", sowingDepth=" + getSowingDepth() +
            ", rowSpacingMax=" + getRowSpacingMax() +
            ", rowSpacingMin=" + getRowSpacingMin() +
            ", seedDepthMax=" + getSeedDepthMax() +
            ", seedDepthMin=" + getSeedDepthMin() +
            ", seedSpacingMax=" + getSeedSpacingMax() +
            ", seedSpacingMin=" + getSeedSpacingMin() +
            ", yearlyCrops=" + getYearlyCrops() +
            ", growingSeason='" + getGrowingSeason() + "'" +
            ", growingPhase='" + getGrowingPhase() + "'" +
            ", plantingDate='" + getPlantingDate() + "'" +
            ", plantSpacing=" + getPlantSpacing() +
            ", plantingMaterial='" + getPlantingMaterial() + "'" +
            ", transplantationDate='" + getTransplantationDate() + "'" +
            ", fertigationscheduleID=" + getFertigationscheduleID() +
            ", plannedYield=" + getPlannedYield() +
            ", actualYield=" + getActualYield() +
            ", yieldUnit=" + getYieldUnit() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
