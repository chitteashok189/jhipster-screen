package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.Ec;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "recipe_type")
    private String recipeType;

    @Column(name = "p_h_min")
    private Long pHMin;

    @Column(name = "p_h_max")
    private Long pHMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "ec_min")
    private Ec ecMin;

    @Column(name = "e_c_max")
    private Integer eCMax;

    @Column(name = "air_temp_max")
    private Integer airTempMax;

    @Column(name = "air_temp_min")
    private Integer airTempMin;

    @Column(name = "humidity_max")
    private Integer humidityMax;

    @Column(name = "humidity_min")
    private Integer humidityMin;

    @Column(name = "nutrient_temp_max")
    private Integer nutrientTempMax;

    @Column(name = "nutrient_temp_min")
    private Integer nutrientTempMin;

    @Column(name = "lux_germ_max")
    private Integer luxGermMax;

    @Column(name = "lux_germ_min")
    private Integer luxGermMin;

    @Column(name = "light_germ_dor")
    private Integer lightGermDor;

    @Column(name = "light_germ_cycle")
    private Integer lightGermCycle;

    @Column(name = "lux_grow_max")
    private Integer luxGrowMax;

    @Column(name = "lux_grow_min")
    private Integer luxGrowMin;

    @Column(name = "light_grow_dor")
    private Integer lightGrowDor;

    @Column(name = "light_grow_cycle")
    private Integer lightGrowCycle;

    @Column(name = "co_2_light_max")
    private Integer co2LightMax;

    @Column(name = "co_2_light_min")
    private Integer co2LightMin;

    @Column(name = "co_2_dark_max")
    private Integer co2DarkMax;

    @Column(name = "co_2_dark_min")
    private Integer co2DarkMin;

    @Column(name = "d_o_max")
    private Integer dOMax;

    @Column(name = "d_o_min")
    private Integer dOMin;

    @Column(name = "media_moisture_max")
    private Integer mediaMoistureMax;

    @Column(name = "media_moisture_min")
    private Integer mediaMoistureMin;

    @Column(name = "nitrogen")
    private Integer nitrogen;

    @Column(name = "phosphorus")
    private Integer phosphorus;

    @Column(name = "potassium")
    private Integer potassium;

    @Column(name = "sulphur")
    private Integer sulphur;

    @Column(name = "calcium")
    private Integer calcium;

    @Column(name = "magnesium")
    private Integer magnesium;

    @Column(name = "manganese")
    private Integer manganese;

    @Column(name = "iron")
    private Integer iron;

    @Column(name = "boron")
    private Integer boron;

    @Column(name = "copper")
    private Integer copper;

    @Column(name = "zinc")
    private Integer zinc;

    @Column(name = "molybdenum")
    private Integer molybdenum;

    @Column(name = "germination_tat")
    private Integer germinationTAT;

    @Column(name = "identification_comment")
    private String identificationComment;

    @Column(name = "growing_comment")
    private String growingComment;

    @Column(name = "usage_comment")
    private String usageComment;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "recipes", "crops", "cropID" }, allowSetters = true)
    private Plant plantID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Recipe gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getPlantName() {
        return this.plantName;
    }

    public Recipe plantName(String plantName) {
        this.setPlantName(plantName);
        return this;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getRecipeType() {
        return this.recipeType;
    }

    public Recipe recipeType(String recipeType) {
        this.setRecipeType(recipeType);
        return this;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public Long getpHMin() {
        return this.pHMin;
    }

    public Recipe pHMin(Long pHMin) {
        this.setpHMin(pHMin);
        return this;
    }

    public void setpHMin(Long pHMin) {
        this.pHMin = pHMin;
    }

    public Long getpHMax() {
        return this.pHMax;
    }

    public Recipe pHMax(Long pHMax) {
        this.setpHMax(pHMax);
        return this;
    }

    public void setpHMax(Long pHMax) {
        this.pHMax = pHMax;
    }

    public Ec getEcMin() {
        return this.ecMin;
    }

    public Recipe ecMin(Ec ecMin) {
        this.setEcMin(ecMin);
        return this;
    }

    public void setEcMin(Ec ecMin) {
        this.ecMin = ecMin;
    }

    public Integer geteCMax() {
        return this.eCMax;
    }

    public Recipe eCMax(Integer eCMax) {
        this.seteCMax(eCMax);
        return this;
    }

    public void seteCMax(Integer eCMax) {
        this.eCMax = eCMax;
    }

    public Integer getAirTempMax() {
        return this.airTempMax;
    }

    public Recipe airTempMax(Integer airTempMax) {
        this.setAirTempMax(airTempMax);
        return this;
    }

    public void setAirTempMax(Integer airTempMax) {
        this.airTempMax = airTempMax;
    }

    public Integer getAirTempMin() {
        return this.airTempMin;
    }

    public Recipe airTempMin(Integer airTempMin) {
        this.setAirTempMin(airTempMin);
        return this;
    }

    public void setAirTempMin(Integer airTempMin) {
        this.airTempMin = airTempMin;
    }

    public Integer getHumidityMax() {
        return this.humidityMax;
    }

    public Recipe humidityMax(Integer humidityMax) {
        this.setHumidityMax(humidityMax);
        return this;
    }

    public void setHumidityMax(Integer humidityMax) {
        this.humidityMax = humidityMax;
    }

    public Integer getHumidityMin() {
        return this.humidityMin;
    }

    public Recipe humidityMin(Integer humidityMin) {
        this.setHumidityMin(humidityMin);
        return this;
    }

    public void setHumidityMin(Integer humidityMin) {
        this.humidityMin = humidityMin;
    }

    public Integer getNutrientTempMax() {
        return this.nutrientTempMax;
    }

    public Recipe nutrientTempMax(Integer nutrientTempMax) {
        this.setNutrientTempMax(nutrientTempMax);
        return this;
    }

    public void setNutrientTempMax(Integer nutrientTempMax) {
        this.nutrientTempMax = nutrientTempMax;
    }

    public Integer getNutrientTempMin() {
        return this.nutrientTempMin;
    }

    public Recipe nutrientTempMin(Integer nutrientTempMin) {
        this.setNutrientTempMin(nutrientTempMin);
        return this;
    }

    public void setNutrientTempMin(Integer nutrientTempMin) {
        this.nutrientTempMin = nutrientTempMin;
    }

    public Integer getLuxGermMax() {
        return this.luxGermMax;
    }

    public Recipe luxGermMax(Integer luxGermMax) {
        this.setLuxGermMax(luxGermMax);
        return this;
    }

    public void setLuxGermMax(Integer luxGermMax) {
        this.luxGermMax = luxGermMax;
    }

    public Integer getLuxGermMin() {
        return this.luxGermMin;
    }

    public Recipe luxGermMin(Integer luxGermMin) {
        this.setLuxGermMin(luxGermMin);
        return this;
    }

    public void setLuxGermMin(Integer luxGermMin) {
        this.luxGermMin = luxGermMin;
    }

    public Integer getLightGermDor() {
        return this.lightGermDor;
    }

    public Recipe lightGermDor(Integer lightGermDor) {
        this.setLightGermDor(lightGermDor);
        return this;
    }

    public void setLightGermDor(Integer lightGermDor) {
        this.lightGermDor = lightGermDor;
    }

    public Integer getLightGermCycle() {
        return this.lightGermCycle;
    }

    public Recipe lightGermCycle(Integer lightGermCycle) {
        this.setLightGermCycle(lightGermCycle);
        return this;
    }

    public void setLightGermCycle(Integer lightGermCycle) {
        this.lightGermCycle = lightGermCycle;
    }

    public Integer getLuxGrowMax() {
        return this.luxGrowMax;
    }

    public Recipe luxGrowMax(Integer luxGrowMax) {
        this.setLuxGrowMax(luxGrowMax);
        return this;
    }

    public void setLuxGrowMax(Integer luxGrowMax) {
        this.luxGrowMax = luxGrowMax;
    }

    public Integer getLuxGrowMin() {
        return this.luxGrowMin;
    }

    public Recipe luxGrowMin(Integer luxGrowMin) {
        this.setLuxGrowMin(luxGrowMin);
        return this;
    }

    public void setLuxGrowMin(Integer luxGrowMin) {
        this.luxGrowMin = luxGrowMin;
    }

    public Integer getLightGrowDor() {
        return this.lightGrowDor;
    }

    public Recipe lightGrowDor(Integer lightGrowDor) {
        this.setLightGrowDor(lightGrowDor);
        return this;
    }

    public void setLightGrowDor(Integer lightGrowDor) {
        this.lightGrowDor = lightGrowDor;
    }

    public Integer getLightGrowCycle() {
        return this.lightGrowCycle;
    }

    public Recipe lightGrowCycle(Integer lightGrowCycle) {
        this.setLightGrowCycle(lightGrowCycle);
        return this;
    }

    public void setLightGrowCycle(Integer lightGrowCycle) {
        this.lightGrowCycle = lightGrowCycle;
    }

    public Integer getCo2LightMax() {
        return this.co2LightMax;
    }

    public Recipe co2LightMax(Integer co2LightMax) {
        this.setCo2LightMax(co2LightMax);
        return this;
    }

    public void setCo2LightMax(Integer co2LightMax) {
        this.co2LightMax = co2LightMax;
    }

    public Integer getCo2LightMin() {
        return this.co2LightMin;
    }

    public Recipe co2LightMin(Integer co2LightMin) {
        this.setCo2LightMin(co2LightMin);
        return this;
    }

    public void setCo2LightMin(Integer co2LightMin) {
        this.co2LightMin = co2LightMin;
    }

    public Integer getCo2DarkMax() {
        return this.co2DarkMax;
    }

    public Recipe co2DarkMax(Integer co2DarkMax) {
        this.setCo2DarkMax(co2DarkMax);
        return this;
    }

    public void setCo2DarkMax(Integer co2DarkMax) {
        this.co2DarkMax = co2DarkMax;
    }

    public Integer getCo2DarkMin() {
        return this.co2DarkMin;
    }

    public Recipe co2DarkMin(Integer co2DarkMin) {
        this.setCo2DarkMin(co2DarkMin);
        return this;
    }

    public void setCo2DarkMin(Integer co2DarkMin) {
        this.co2DarkMin = co2DarkMin;
    }

    public Integer getdOMax() {
        return this.dOMax;
    }

    public Recipe dOMax(Integer dOMax) {
        this.setdOMax(dOMax);
        return this;
    }

    public void setdOMax(Integer dOMax) {
        this.dOMax = dOMax;
    }

    public Integer getdOMin() {
        return this.dOMin;
    }

    public Recipe dOMin(Integer dOMin) {
        this.setdOMin(dOMin);
        return this;
    }

    public void setdOMin(Integer dOMin) {
        this.dOMin = dOMin;
    }

    public Integer getMediaMoistureMax() {
        return this.mediaMoistureMax;
    }

    public Recipe mediaMoistureMax(Integer mediaMoistureMax) {
        this.setMediaMoistureMax(mediaMoistureMax);
        return this;
    }

    public void setMediaMoistureMax(Integer mediaMoistureMax) {
        this.mediaMoistureMax = mediaMoistureMax;
    }

    public Integer getMediaMoistureMin() {
        return this.mediaMoistureMin;
    }

    public Recipe mediaMoistureMin(Integer mediaMoistureMin) {
        this.setMediaMoistureMin(mediaMoistureMin);
        return this;
    }

    public void setMediaMoistureMin(Integer mediaMoistureMin) {
        this.mediaMoistureMin = mediaMoistureMin;
    }

    public Integer getNitrogen() {
        return this.nitrogen;
    }

    public Recipe nitrogen(Integer nitrogen) {
        this.setNitrogen(nitrogen);
        return this;
    }

    public void setNitrogen(Integer nitrogen) {
        this.nitrogen = nitrogen;
    }

    public Integer getPhosphorus() {
        return this.phosphorus;
    }

    public Recipe phosphorus(Integer phosphorus) {
        this.setPhosphorus(phosphorus);
        return this;
    }

    public void setPhosphorus(Integer phosphorus) {
        this.phosphorus = phosphorus;
    }

    public Integer getPotassium() {
        return this.potassium;
    }

    public Recipe potassium(Integer potassium) {
        this.setPotassium(potassium);
        return this;
    }

    public void setPotassium(Integer potassium) {
        this.potassium = potassium;
    }

    public Integer getSulphur() {
        return this.sulphur;
    }

    public Recipe sulphur(Integer sulphur) {
        this.setSulphur(sulphur);
        return this;
    }

    public void setSulphur(Integer sulphur) {
        this.sulphur = sulphur;
    }

    public Integer getCalcium() {
        return this.calcium;
    }

    public Recipe calcium(Integer calcium) {
        this.setCalcium(calcium);
        return this;
    }

    public void setCalcium(Integer calcium) {
        this.calcium = calcium;
    }

    public Integer getMagnesium() {
        return this.magnesium;
    }

    public Recipe magnesium(Integer magnesium) {
        this.setMagnesium(magnesium);
        return this;
    }

    public void setMagnesium(Integer magnesium) {
        this.magnesium = magnesium;
    }

    public Integer getManganese() {
        return this.manganese;
    }

    public Recipe manganese(Integer manganese) {
        this.setManganese(manganese);
        return this;
    }

    public void setManganese(Integer manganese) {
        this.manganese = manganese;
    }

    public Integer getIron() {
        return this.iron;
    }

    public Recipe iron(Integer iron) {
        this.setIron(iron);
        return this;
    }

    public void setIron(Integer iron) {
        this.iron = iron;
    }

    public Integer getBoron() {
        return this.boron;
    }

    public Recipe boron(Integer boron) {
        this.setBoron(boron);
        return this;
    }

    public void setBoron(Integer boron) {
        this.boron = boron;
    }

    public Integer getCopper() {
        return this.copper;
    }

    public Recipe copper(Integer copper) {
        this.setCopper(copper);
        return this;
    }

    public void setCopper(Integer copper) {
        this.copper = copper;
    }

    public Integer getZinc() {
        return this.zinc;
    }

    public Recipe zinc(Integer zinc) {
        this.setZinc(zinc);
        return this;
    }

    public void setZinc(Integer zinc) {
        this.zinc = zinc;
    }

    public Integer getMolybdenum() {
        return this.molybdenum;
    }

    public Recipe molybdenum(Integer molybdenum) {
        this.setMolybdenum(molybdenum);
        return this;
    }

    public void setMolybdenum(Integer molybdenum) {
        this.molybdenum = molybdenum;
    }

    public Integer getGerminationTAT() {
        return this.germinationTAT;
    }

    public Recipe germinationTAT(Integer germinationTAT) {
        this.setGerminationTAT(germinationTAT);
        return this;
    }

    public void setGerminationTAT(Integer germinationTAT) {
        this.germinationTAT = germinationTAT;
    }

    public String getIdentificationComment() {
        return this.identificationComment;
    }

    public Recipe identificationComment(String identificationComment) {
        this.setIdentificationComment(identificationComment);
        return this;
    }

    public void setIdentificationComment(String identificationComment) {
        this.identificationComment = identificationComment;
    }

    public String getGrowingComment() {
        return this.growingComment;
    }

    public Recipe growingComment(String growingComment) {
        this.setGrowingComment(growingComment);
        return this;
    }

    public void setGrowingComment(String growingComment) {
        this.growingComment = growingComment;
    }

    public String getUsageComment() {
        return this.usageComment;
    }

    public Recipe usageComment(String usageComment) {
        this.setUsageComment(usageComment);
        return this;
    }

    public void setUsageComment(String usageComment) {
        this.usageComment = usageComment;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Recipe createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Recipe createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Recipe updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Recipe updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Plant getPlantID() {
        return this.plantID;
    }

    public void setPlantID(Plant plant) {
        this.plantID = plant;
    }

    public Recipe plantID(Plant plant) {
        this.setPlantID(plant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        return id != null && id.equals(((Recipe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", plantName='" + getPlantName() + "'" +
            ", recipeType='" + getRecipeType() + "'" +
            ", pHMin=" + getpHMin() +
            ", pHMax=" + getpHMax() +
            ", ecMin='" + getEcMin() + "'" +
            ", eCMax=" + geteCMax() +
            ", airTempMax=" + getAirTempMax() +
            ", airTempMin=" + getAirTempMin() +
            ", humidityMax=" + getHumidityMax() +
            ", humidityMin=" + getHumidityMin() +
            ", nutrientTempMax=" + getNutrientTempMax() +
            ", nutrientTempMin=" + getNutrientTempMin() +
            ", luxGermMax=" + getLuxGermMax() +
            ", luxGermMin=" + getLuxGermMin() +
            ", lightGermDor=" + getLightGermDor() +
            ", lightGermCycle=" + getLightGermCycle() +
            ", luxGrowMax=" + getLuxGrowMax() +
            ", luxGrowMin=" + getLuxGrowMin() +
            ", lightGrowDor=" + getLightGrowDor() +
            ", lightGrowCycle=" + getLightGrowCycle() +
            ", co2LightMax=" + getCo2LightMax() +
            ", co2LightMin=" + getCo2LightMin() +
            ", co2DarkMax=" + getCo2DarkMax() +
            ", co2DarkMin=" + getCo2DarkMin() +
            ", dOMax=" + getdOMax() +
            ", dOMin=" + getdOMin() +
            ", mediaMoistureMax=" + getMediaMoistureMax() +
            ", mediaMoistureMin=" + getMediaMoistureMin() +
            ", nitrogen=" + getNitrogen() +
            ", phosphorus=" + getPhosphorus() +
            ", potassium=" + getPotassium() +
            ", sulphur=" + getSulphur() +
            ", calcium=" + getCalcium() +
            ", magnesium=" + getMagnesium() +
            ", manganese=" + getManganese() +
            ", iron=" + getIron() +
            ", boron=" + getBoron() +
            ", copper=" + getCopper() +
            ", zinc=" + getZinc() +
            ", molybdenum=" + getMolybdenum() +
            ", germinationTAT=" + getGerminationTAT() +
            ", identificationComment='" + getIdentificationComment() + "'" +
            ", growingComment='" + getGrowingComment() + "'" +
            ", usageComment='" + getUsageComment() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
