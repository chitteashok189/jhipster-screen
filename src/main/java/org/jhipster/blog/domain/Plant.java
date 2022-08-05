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
import org.jhipster.blog.domain.enumeration.HarvestMonth;
import org.jhipster.blog.domain.enumeration.Seeding;
import org.jhipster.blog.domain.enumeration.TransplantMonth;

/**
 * A Plant.
 */
@Entity
@Table(name = "plant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "plant_spacing")
    private Integer plantSpacing;

    @Enumerated(EnumType.STRING)
    @Column(name = "seeding_month")
    private Seeding seedingMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "transplant_month")
    private TransplantMonth transplantMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "harvest_month")
    private HarvestMonth harvestMonth;

    @Column(name = "origin_country")
    private Long originCountry;

    @Column(name = "yearly_crops")
    private Integer yearlyCrops;

    @Column(name = "native_temperature")
    private Integer nativeTemperature;

    @Column(name = "native_humidity")
    private Integer nativeHumidity;

    @Column(name = "native_day_duration")
    private Integer nativeDayDuration;

    @Column(name = "native_night_duration")
    private Integer nativeNightDuration;

    @Column(name = "native_soil_moisture")
    private Integer nativeSoilMoisture;

    @Column(name = "planting_period")
    private Integer plantingPeriod;

    @Column(name = "yield_unit")
    private Integer yieldUnit;

    @Column(name = "growth_height_min")
    private Integer growthHeightMin;

    @Column(name = "growth_height_max")
    private Integer growthHeightMax;

    @Column(name = "grown_spread_min")
    private Integer grownSpreadMin;

    @Column(name = "grown_spread_max")
    private Integer grownSpreadMax;

    @Column(name = "grown_weight_max")
    private Integer grownWeightMax;

    @Column(name = "grown_weight_min")
    private Integer grownWeightMin;

    @Column(name = "growing_media")
    private String growingMedia;

    @Column(name = "documents")
    private String documents;

    @Column(name = "notes")
    private String notes;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "plantID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plantID" }, allowSetters = true)
    private Set<Recipe> recipes = new HashSet<>();

    @OneToMany(mappedBy = "plantID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Crop> crops = new HashSet<>();

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

    public Plant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Plant gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public Plant commonName(String commonName) {
        this.setCommonName(commonName);
        return this;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public Plant scientificName(String scientificName) {
        this.setScientificName(scientificName);
        return this;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public Plant familyName(String familyName) {
        this.setFamilyName(familyName);
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Integer getPlantSpacing() {
        return this.plantSpacing;
    }

    public Plant plantSpacing(Integer plantSpacing) {
        this.setPlantSpacing(plantSpacing);
        return this;
    }

    public void setPlantSpacing(Integer plantSpacing) {
        this.plantSpacing = plantSpacing;
    }

    public Seeding getSeedingMonth() {
        return this.seedingMonth;
    }

    public Plant seedingMonth(Seeding seedingMonth) {
        this.setSeedingMonth(seedingMonth);
        return this;
    }

    public void setSeedingMonth(Seeding seedingMonth) {
        this.seedingMonth = seedingMonth;
    }

    public TransplantMonth getTransplantMonth() {
        return this.transplantMonth;
    }

    public Plant transplantMonth(TransplantMonth transplantMonth) {
        this.setTransplantMonth(transplantMonth);
        return this;
    }

    public void setTransplantMonth(TransplantMonth transplantMonth) {
        this.transplantMonth = transplantMonth;
    }

    public HarvestMonth getHarvestMonth() {
        return this.harvestMonth;
    }

    public Plant harvestMonth(HarvestMonth harvestMonth) {
        this.setHarvestMonth(harvestMonth);
        return this;
    }

    public void setHarvestMonth(HarvestMonth harvestMonth) {
        this.harvestMonth = harvestMonth;
    }

    public Long getOriginCountry() {
        return this.originCountry;
    }

    public Plant originCountry(Long originCountry) {
        this.setOriginCountry(originCountry);
        return this;
    }

    public void setOriginCountry(Long originCountry) {
        this.originCountry = originCountry;
    }

    public Integer getYearlyCrops() {
        return this.yearlyCrops;
    }

    public Plant yearlyCrops(Integer yearlyCrops) {
        this.setYearlyCrops(yearlyCrops);
        return this;
    }

    public void setYearlyCrops(Integer yearlyCrops) {
        this.yearlyCrops = yearlyCrops;
    }

    public Integer getNativeTemperature() {
        return this.nativeTemperature;
    }

    public Plant nativeTemperature(Integer nativeTemperature) {
        this.setNativeTemperature(nativeTemperature);
        return this;
    }

    public void setNativeTemperature(Integer nativeTemperature) {
        this.nativeTemperature = nativeTemperature;
    }

    public Integer getNativeHumidity() {
        return this.nativeHumidity;
    }

    public Plant nativeHumidity(Integer nativeHumidity) {
        this.setNativeHumidity(nativeHumidity);
        return this;
    }

    public void setNativeHumidity(Integer nativeHumidity) {
        this.nativeHumidity = nativeHumidity;
    }

    public Integer getNativeDayDuration() {
        return this.nativeDayDuration;
    }

    public Plant nativeDayDuration(Integer nativeDayDuration) {
        this.setNativeDayDuration(nativeDayDuration);
        return this;
    }

    public void setNativeDayDuration(Integer nativeDayDuration) {
        this.nativeDayDuration = nativeDayDuration;
    }

    public Integer getNativeNightDuration() {
        return this.nativeNightDuration;
    }

    public Plant nativeNightDuration(Integer nativeNightDuration) {
        this.setNativeNightDuration(nativeNightDuration);
        return this;
    }

    public void setNativeNightDuration(Integer nativeNightDuration) {
        this.nativeNightDuration = nativeNightDuration;
    }

    public Integer getNativeSoilMoisture() {
        return this.nativeSoilMoisture;
    }

    public Plant nativeSoilMoisture(Integer nativeSoilMoisture) {
        this.setNativeSoilMoisture(nativeSoilMoisture);
        return this;
    }

    public void setNativeSoilMoisture(Integer nativeSoilMoisture) {
        this.nativeSoilMoisture = nativeSoilMoisture;
    }

    public Integer getPlantingPeriod() {
        return this.plantingPeriod;
    }

    public Plant plantingPeriod(Integer plantingPeriod) {
        this.setPlantingPeriod(plantingPeriod);
        return this;
    }

    public void setPlantingPeriod(Integer plantingPeriod) {
        this.plantingPeriod = plantingPeriod;
    }

    public Integer getYieldUnit() {
        return this.yieldUnit;
    }

    public Plant yieldUnit(Integer yieldUnit) {
        this.setYieldUnit(yieldUnit);
        return this;
    }

    public void setYieldUnit(Integer yieldUnit) {
        this.yieldUnit = yieldUnit;
    }

    public Integer getGrowthHeightMin() {
        return this.growthHeightMin;
    }

    public Plant growthHeightMin(Integer growthHeightMin) {
        this.setGrowthHeightMin(growthHeightMin);
        return this;
    }

    public void setGrowthHeightMin(Integer growthHeightMin) {
        this.growthHeightMin = growthHeightMin;
    }

    public Integer getGrowthHeightMax() {
        return this.growthHeightMax;
    }

    public Plant growthHeightMax(Integer growthHeightMax) {
        this.setGrowthHeightMax(growthHeightMax);
        return this;
    }

    public void setGrowthHeightMax(Integer growthHeightMax) {
        this.growthHeightMax = growthHeightMax;
    }

    public Integer getGrownSpreadMin() {
        return this.grownSpreadMin;
    }

    public Plant grownSpreadMin(Integer grownSpreadMin) {
        this.setGrownSpreadMin(grownSpreadMin);
        return this;
    }

    public void setGrownSpreadMin(Integer grownSpreadMin) {
        this.grownSpreadMin = grownSpreadMin;
    }

    public Integer getGrownSpreadMax() {
        return this.grownSpreadMax;
    }

    public Plant grownSpreadMax(Integer grownSpreadMax) {
        this.setGrownSpreadMax(grownSpreadMax);
        return this;
    }

    public void setGrownSpreadMax(Integer grownSpreadMax) {
        this.grownSpreadMax = grownSpreadMax;
    }

    public Integer getGrownWeightMax() {
        return this.grownWeightMax;
    }

    public Plant grownWeightMax(Integer grownWeightMax) {
        this.setGrownWeightMax(grownWeightMax);
        return this;
    }

    public void setGrownWeightMax(Integer grownWeightMax) {
        this.grownWeightMax = grownWeightMax;
    }

    public Integer getGrownWeightMin() {
        return this.grownWeightMin;
    }

    public Plant grownWeightMin(Integer grownWeightMin) {
        this.setGrownWeightMin(grownWeightMin);
        return this;
    }

    public void setGrownWeightMin(Integer grownWeightMin) {
        this.grownWeightMin = grownWeightMin;
    }

    public String getGrowingMedia() {
        return this.growingMedia;
    }

    public Plant growingMedia(String growingMedia) {
        this.setGrowingMedia(growingMedia);
        return this;
    }

    public void setGrowingMedia(String growingMedia) {
        this.growingMedia = growingMedia;
    }

    public String getDocuments() {
        return this.documents;
    }

    public Plant documents(String documents) {
        this.setDocuments(documents);
        return this;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getNotes() {
        return this.notes;
    }

    public Plant notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public Plant attachments(byte[] attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return this.attachmentsContentType;
    }

    public Plant attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Plant createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Plant createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Plant updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Plant updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        if (this.recipes != null) {
            this.recipes.forEach(i -> i.setPlantID(null));
        }
        if (recipes != null) {
            recipes.forEach(i -> i.setPlantID(this));
        }
        this.recipes = recipes;
    }

    public Plant recipes(Set<Recipe> recipes) {
        this.setRecipes(recipes);
        return this;
    }

    public Plant addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
        recipe.setPlantID(this);
        return this;
    }

    public Plant removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
        recipe.setPlantID(null);
        return this;
    }

    public Set<Crop> getCrops() {
        return this.crops;
    }

    public void setCrops(Set<Crop> crops) {
        if (this.crops != null) {
            this.crops.forEach(i -> i.setPlantID(null));
        }
        if (crops != null) {
            crops.forEach(i -> i.setPlantID(this));
        }
        this.crops = crops;
    }

    public Plant crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public Plant addCrop(Crop crop) {
        this.crops.add(crop);
        crop.setPlantID(this);
        return this;
    }

    public Plant removeCrop(Crop crop) {
        this.crops.remove(crop);
        crop.setPlantID(null);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public Plant cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plant)) {
            return false;
        }
        return id != null && id.equals(((Plant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plant{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", commonName='" + getCommonName() + "'" +
            ", scientificName='" + getScientificName() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", plantSpacing=" + getPlantSpacing() +
            ", seedingMonth='" + getSeedingMonth() + "'" +
            ", transplantMonth='" + getTransplantMonth() + "'" +
            ", harvestMonth='" + getHarvestMonth() + "'" +
            ", originCountry=" + getOriginCountry() +
            ", yearlyCrops=" + getYearlyCrops() +
            ", nativeTemperature=" + getNativeTemperature() +
            ", nativeHumidity=" + getNativeHumidity() +
            ", nativeDayDuration=" + getNativeDayDuration() +
            ", nativeNightDuration=" + getNativeNightDuration() +
            ", nativeSoilMoisture=" + getNativeSoilMoisture() +
            ", plantingPeriod=" + getPlantingPeriod() +
            ", yieldUnit=" + getYieldUnit() +
            ", growthHeightMin=" + getGrowthHeightMin() +
            ", growthHeightMax=" + getGrowthHeightMax() +
            ", grownSpreadMin=" + getGrownSpreadMin() +
            ", grownSpreadMax=" + getGrownSpreadMax() +
            ", grownWeightMax=" + getGrownWeightMax() +
            ", grownWeightMin=" + getGrownWeightMin() +
            ", growingMedia='" + getGrowingMedia() + "'" +
            ", documents='" + getDocuments() + "'" +
            ", notes='" + getNotes() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
