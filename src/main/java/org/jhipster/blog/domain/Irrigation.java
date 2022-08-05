package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.IrriSource;

/**
 * A Irrigation.
 */
@Entity
@Table(name = "irrigation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Irrigation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private IrriSource source;

    @Column(name = "nutrient_level")
    private Integer nutrientLevel;

    @Column(name = "solar_radiation")
    private Integer solarRadiation;

    @Column(name = "inlet_flow")
    private Integer inletFlow;

    @Column(name = "outlet_flow")
    private Integer outletFlow;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "plantFactories", "sensors", "climates", "irrigations", "dosings", "lights", "plantFactoryID", "deviceLevelID", "deviceModelID",
        },
        allowSetters = true
    )
    private Device deviceID;

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

    public Irrigation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Irrigation gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public IrriSource getSource() {
        return this.source;
    }

    public Irrigation source(IrriSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(IrriSource source) {
        this.source = source;
    }

    public Integer getNutrientLevel() {
        return this.nutrientLevel;
    }

    public Irrigation nutrientLevel(Integer nutrientLevel) {
        this.setNutrientLevel(nutrientLevel);
        return this;
    }

    public void setNutrientLevel(Integer nutrientLevel) {
        this.nutrientLevel = nutrientLevel;
    }

    public Integer getSolarRadiation() {
        return this.solarRadiation;
    }

    public Irrigation solarRadiation(Integer solarRadiation) {
        this.setSolarRadiation(solarRadiation);
        return this;
    }

    public void setSolarRadiation(Integer solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public Integer getInletFlow() {
        return this.inletFlow;
    }

    public Irrigation inletFlow(Integer inletFlow) {
        this.setInletFlow(inletFlow);
        return this;
    }

    public void setInletFlow(Integer inletFlow) {
        this.inletFlow = inletFlow;
    }

    public Integer getOutletFlow() {
        return this.outletFlow;
    }

    public Irrigation outletFlow(Integer outletFlow) {
        this.setOutletFlow(outletFlow);
        return this;
    }

    public void setOutletFlow(Integer outletFlow) {
        this.outletFlow = outletFlow;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Irrigation createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Irrigation createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Irrigation updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Irrigation updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Device getDeviceID() {
        return this.deviceID;
    }

    public void setDeviceID(Device device) {
        this.deviceID = device;
    }

    public Irrigation deviceID(Device device) {
        this.setDeviceID(device);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Irrigation plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Irrigation)) {
            return false;
        }
        return id != null && id.equals(((Irrigation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Irrigation{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", source='" + getSource() + "'" +
            ", nutrientLevel=" + getNutrientLevel() +
            ", solarRadiation=" + getSolarRadiation() +
            ", inletFlow=" + getInletFlow() +
            ", outletFlow=" + getOutletFlow() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
