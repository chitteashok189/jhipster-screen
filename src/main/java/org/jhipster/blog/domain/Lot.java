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
import org.jhipster.blog.domain.enumeration.HarTime;
import org.jhipster.blog.domain.enumeration.Sowing;
import org.jhipster.blog.domain.enumeration.Transplantation;
import org.jhipster.blog.domain.enumeration.Unit;

/**
 * A Lot.
 */
@Entity
@Table(name = "lot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "lot_code")
    private String lotCode;

    @Lob
    @Column(name = "lot_qr_code")
    private byte[] lotQRCode;

    @Column(name = "lot_qr_code_content_type")
    private String lotQRCodeContentType;

    @Column(name = "lot_size")
    private Integer lotSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type")
    private Unit unitType;

    @Column(name = "seedlings_germinated")
    private Integer seedlingsGerminated;

    @Column(name = "seedlings_died")
    private Integer seedlingsDied;

    @Column(name = "plants_wasted")
    private Integer plantsWasted;

    @Column(name = "trays_sown")
    private Integer traysSown;

    @Enumerated(EnumType.STRING)
    @Column(name = "sowing_time")
    private Sowing sowingTime;

    @Column(name = "trays_tranplanted")
    private Integer traysTranplanted;

    @Enumerated(EnumType.STRING)
    @Column(name = "transplantation_time")
    private Transplantation transplantationTime;

    @Column(name = "plants_harvested")
    private Integer plantsHarvested;

    @Enumerated(EnumType.STRING)
    @Column(name = "harvest_time")
    private HarTime harvestTime;

    @Column(name = "packing_date")
    private Instant packingDate;

    @Column(name = "tranportation_date")
    private Instant tranportationDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "lotID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspections", "cropID", "lotID" }, allowSetters = true)
    private Set<Harvest> harvests = new HashSet<>();

    @OneToMany(mappedBy = "lotID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionID", "lotID" }, allowSetters = true)
    private Set<InspectionRecord> inspectionRecords = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "lots" }, allowSetters = true)
    private Seed seedID;

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

    public Lot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Lot gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getLotCode() {
        return this.lotCode;
    }

    public Lot lotCode(String lotCode) {
        this.setLotCode(lotCode);
        return this;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public byte[] getLotQRCode() {
        return this.lotQRCode;
    }

    public Lot lotQRCode(byte[] lotQRCode) {
        this.setLotQRCode(lotQRCode);
        return this;
    }

    public void setLotQRCode(byte[] lotQRCode) {
        this.lotQRCode = lotQRCode;
    }

    public String getLotQRCodeContentType() {
        return this.lotQRCodeContentType;
    }

    public Lot lotQRCodeContentType(String lotQRCodeContentType) {
        this.lotQRCodeContentType = lotQRCodeContentType;
        return this;
    }

    public void setLotQRCodeContentType(String lotQRCodeContentType) {
        this.lotQRCodeContentType = lotQRCodeContentType;
    }

    public Integer getLotSize() {
        return this.lotSize;
    }

    public Lot lotSize(Integer lotSize) {
        this.setLotSize(lotSize);
        return this;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public Unit getUnitType() {
        return this.unitType;
    }

    public Lot unitType(Unit unitType) {
        this.setUnitType(unitType);
        return this;
    }

    public void setUnitType(Unit unitType) {
        this.unitType = unitType;
    }

    public Integer getSeedlingsGerminated() {
        return this.seedlingsGerminated;
    }

    public Lot seedlingsGerminated(Integer seedlingsGerminated) {
        this.setSeedlingsGerminated(seedlingsGerminated);
        return this;
    }

    public void setSeedlingsGerminated(Integer seedlingsGerminated) {
        this.seedlingsGerminated = seedlingsGerminated;
    }

    public Integer getSeedlingsDied() {
        return this.seedlingsDied;
    }

    public Lot seedlingsDied(Integer seedlingsDied) {
        this.setSeedlingsDied(seedlingsDied);
        return this;
    }

    public void setSeedlingsDied(Integer seedlingsDied) {
        this.seedlingsDied = seedlingsDied;
    }

    public Integer getPlantsWasted() {
        return this.plantsWasted;
    }

    public Lot plantsWasted(Integer plantsWasted) {
        this.setPlantsWasted(plantsWasted);
        return this;
    }

    public void setPlantsWasted(Integer plantsWasted) {
        this.plantsWasted = plantsWasted;
    }

    public Integer getTraysSown() {
        return this.traysSown;
    }

    public Lot traysSown(Integer traysSown) {
        this.setTraysSown(traysSown);
        return this;
    }

    public void setTraysSown(Integer traysSown) {
        this.traysSown = traysSown;
    }

    public Sowing getSowingTime() {
        return this.sowingTime;
    }

    public Lot sowingTime(Sowing sowingTime) {
        this.setSowingTime(sowingTime);
        return this;
    }

    public void setSowingTime(Sowing sowingTime) {
        this.sowingTime = sowingTime;
    }

    public Integer getTraysTranplanted() {
        return this.traysTranplanted;
    }

    public Lot traysTranplanted(Integer traysTranplanted) {
        this.setTraysTranplanted(traysTranplanted);
        return this;
    }

    public void setTraysTranplanted(Integer traysTranplanted) {
        this.traysTranplanted = traysTranplanted;
    }

    public Transplantation getTransplantationTime() {
        return this.transplantationTime;
    }

    public Lot transplantationTime(Transplantation transplantationTime) {
        this.setTransplantationTime(transplantationTime);
        return this;
    }

    public void setTransplantationTime(Transplantation transplantationTime) {
        this.transplantationTime = transplantationTime;
    }

    public Integer getPlantsHarvested() {
        return this.plantsHarvested;
    }

    public Lot plantsHarvested(Integer plantsHarvested) {
        this.setPlantsHarvested(plantsHarvested);
        return this;
    }

    public void setPlantsHarvested(Integer plantsHarvested) {
        this.plantsHarvested = plantsHarvested;
    }

    public HarTime getHarvestTime() {
        return this.harvestTime;
    }

    public Lot harvestTime(HarTime harvestTime) {
        this.setHarvestTime(harvestTime);
        return this;
    }

    public void setHarvestTime(HarTime harvestTime) {
        this.harvestTime = harvestTime;
    }

    public Instant getPackingDate() {
        return this.packingDate;
    }

    public Lot packingDate(Instant packingDate) {
        this.setPackingDate(packingDate);
        return this;
    }

    public void setPackingDate(Instant packingDate) {
        this.packingDate = packingDate;
    }

    public Instant getTranportationDate() {
        return this.tranportationDate;
    }

    public Lot tranportationDate(Instant tranportationDate) {
        this.setTranportationDate(tranportationDate);
        return this;
    }

    public void setTranportationDate(Instant tranportationDate) {
        this.tranportationDate = tranportationDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Lot createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Lot createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Lot updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Lot updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Harvest> getHarvests() {
        return this.harvests;
    }

    public void setHarvests(Set<Harvest> harvests) {
        if (this.harvests != null) {
            this.harvests.forEach(i -> i.setLotID(null));
        }
        if (harvests != null) {
            harvests.forEach(i -> i.setLotID(this));
        }
        this.harvests = harvests;
    }

    public Lot harvests(Set<Harvest> harvests) {
        this.setHarvests(harvests);
        return this;
    }

    public Lot addHarvest(Harvest harvest) {
        this.harvests.add(harvest);
        harvest.setLotID(this);
        return this;
    }

    public Lot removeHarvest(Harvest harvest) {
        this.harvests.remove(harvest);
        harvest.setLotID(null);
        return this;
    }

    public Set<InspectionRecord> getInspectionRecords() {
        return this.inspectionRecords;
    }

    public void setInspectionRecords(Set<InspectionRecord> inspectionRecords) {
        if (this.inspectionRecords != null) {
            this.inspectionRecords.forEach(i -> i.setLotID(null));
        }
        if (inspectionRecords != null) {
            inspectionRecords.forEach(i -> i.setLotID(this));
        }
        this.inspectionRecords = inspectionRecords;
    }

    public Lot inspectionRecords(Set<InspectionRecord> inspectionRecords) {
        this.setInspectionRecords(inspectionRecords);
        return this;
    }

    public Lot addInspectionRecord(InspectionRecord inspectionRecord) {
        this.inspectionRecords.add(inspectionRecord);
        inspectionRecord.setLotID(this);
        return this;
    }

    public Lot removeInspectionRecord(InspectionRecord inspectionRecord) {
        this.inspectionRecords.remove(inspectionRecord);
        inspectionRecord.setLotID(null);
        return this;
    }

    public Seed getSeedID() {
        return this.seedID;
    }

    public void setSeedID(Seed seed) {
        this.seedID = seed;
    }

    public Lot seedID(Seed seed) {
        this.setSeedID(seed);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public Lot cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lot)) {
            return false;
        }
        return id != null && id.equals(((Lot) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lot{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", lotCode='" + getLotCode() + "'" +
            ", lotQRCode='" + getLotQRCode() + "'" +
            ", lotQRCodeContentType='" + getLotQRCodeContentType() + "'" +
            ", lotSize=" + getLotSize() +
            ", unitType='" + getUnitType() + "'" +
            ", seedlingsGerminated=" + getSeedlingsGerminated() +
            ", seedlingsDied=" + getSeedlingsDied() +
            ", plantsWasted=" + getPlantsWasted() +
            ", traysSown=" + getTraysSown() +
            ", sowingTime='" + getSowingTime() + "'" +
            ", traysTranplanted=" + getTraysTranplanted() +
            ", transplantationTime='" + getTransplantationTime() + "'" +
            ", plantsHarvested=" + getPlantsHarvested() +
            ", harvestTime='" + getHarvestTime() + "'" +
            ", packingDate='" + getPackingDate() + "'" +
            ", tranportationDate='" + getTranportationDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
