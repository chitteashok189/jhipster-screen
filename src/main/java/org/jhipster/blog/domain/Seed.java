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
import org.jhipster.blog.domain.enumeration.SeedClass;
import org.jhipster.blog.domain.enumeration.SeedRate;
import org.jhipster.blog.domain.enumeration.Treatment;

/**
 * A Seed.
 */
@Entity
@Table(name = "seed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "breeder_id")
    private String breederID;

    @Enumerated(EnumType.STRING)
    @Column(name = "seed_class")
    private SeedClass seedClass;

    @Column(name = "variety")
    private String variety;

    @Enumerated(EnumType.STRING)
    @Column(name = "seed_rate")
    private SeedRate seedRate;

    @Column(name = "germination_percentage")
    private Integer germinationPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "treatment")
    private Treatment treatment;

    @Column(name = "origin")
    private String origin;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "seedID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "harvests", "inspectionRecords", "seedID", "cropID" }, allowSetters = true)
    private Set<Lot> lots = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Seed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Seed gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getBreederID() {
        return this.breederID;
    }

    public Seed breederID(String breederID) {
        this.setBreederID(breederID);
        return this;
    }

    public void setBreederID(String breederID) {
        this.breederID = breederID;
    }

    public SeedClass getSeedClass() {
        return this.seedClass;
    }

    public Seed seedClass(SeedClass seedClass) {
        this.setSeedClass(seedClass);
        return this;
    }

    public void setSeedClass(SeedClass seedClass) {
        this.seedClass = seedClass;
    }

    public String getVariety() {
        return this.variety;
    }

    public Seed variety(String variety) {
        this.setVariety(variety);
        return this;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public SeedRate getSeedRate() {
        return this.seedRate;
    }

    public Seed seedRate(SeedRate seedRate) {
        this.setSeedRate(seedRate);
        return this;
    }

    public void setSeedRate(SeedRate seedRate) {
        this.seedRate = seedRate;
    }

    public Integer getGerminationPercentage() {
        return this.germinationPercentage;
    }

    public Seed germinationPercentage(Integer germinationPercentage) {
        this.setGerminationPercentage(germinationPercentage);
        return this;
    }

    public void setGerminationPercentage(Integer germinationPercentage) {
        this.germinationPercentage = germinationPercentage;
    }

    public Treatment getTreatment() {
        return this.treatment;
    }

    public Seed treatment(Treatment treatment) {
        this.setTreatment(treatment);
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getOrigin() {
        return this.origin;
    }

    public Seed origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Seed createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Seed createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Seed updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Seed updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Lot> getLots() {
        return this.lots;
    }

    public void setLots(Set<Lot> lots) {
        if (this.lots != null) {
            this.lots.forEach(i -> i.setSeedID(null));
        }
        if (lots != null) {
            lots.forEach(i -> i.setSeedID(this));
        }
        this.lots = lots;
    }

    public Seed lots(Set<Lot> lots) {
        this.setLots(lots);
        return this;
    }

    public Seed addLot(Lot lot) {
        this.lots.add(lot);
        lot.setSeedID(this);
        return this;
    }

    public Seed removeLot(Lot lot) {
        this.lots.remove(lot);
        lot.setSeedID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seed)) {
            return false;
        }
        return id != null && id.equals(((Seed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seed{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", breederID='" + getBreederID() + "'" +
            ", seedClass='" + getSeedClass() + "'" +
            ", variety='" + getVariety() + "'" +
            ", seedRate='" + getSeedRate() + "'" +
            ", germinationPercentage=" + getGerminationPercentage() +
            ", treatment='" + getTreatment() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
