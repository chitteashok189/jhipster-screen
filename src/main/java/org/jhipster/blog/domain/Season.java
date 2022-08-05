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
import org.jhipster.blog.domain.enumeration.SeaTime;
import org.jhipster.blog.domain.enumeration.SeaType;

/**
 * A Season.
 */
@Entity
@Table(name = "season")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Season implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "season_type")
    private SeaType seasonType;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "area")
    private Integer area;

    @Enumerated(EnumType.STRING)
    @Column(name = "season_time")
    private SeaTime seasonTime;

    @Column(name = "seasonstart_date")
    private Instant seasonstartDate;

    @Column(name = "season_end_date")
    private Instant seasonEndDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "seasonID")
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

    @OneToMany(mappedBy = "seasonID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "weathers", "plantFactoryID", "zoneID", "cropID", "toolID", "seasonID" }, allowSetters = true)
    private Set<Calendar> calendars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Season id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Season gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public SeaType getSeasonType() {
        return this.seasonType;
    }

    public Season seasonType(SeaType seasonType) {
        this.setSeasonType(seasonType);
        return this;
    }

    public void setSeasonType(SeaType seasonType) {
        this.seasonType = seasonType;
    }

    public String getCropName() {
        return this.cropName;
    }

    public Season cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getArea() {
        return this.area;
    }

    public Season area(Integer area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public SeaTime getSeasonTime() {
        return this.seasonTime;
    }

    public Season seasonTime(SeaTime seasonTime) {
        this.setSeasonTime(seasonTime);
        return this;
    }

    public void setSeasonTime(SeaTime seasonTime) {
        this.seasonTime = seasonTime;
    }

    public Instant getSeasonstartDate() {
        return this.seasonstartDate;
    }

    public Season seasonstartDate(Instant seasonstartDate) {
        this.setSeasonstartDate(seasonstartDate);
        return this;
    }

    public void setSeasonstartDate(Instant seasonstartDate) {
        this.seasonstartDate = seasonstartDate;
    }

    public Instant getSeasonEndDate() {
        return this.seasonEndDate;
    }

    public Season seasonEndDate(Instant seasonEndDate) {
        this.setSeasonEndDate(seasonEndDate);
        return this;
    }

    public void setSeasonEndDate(Instant seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Season createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Season createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Season updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Season updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Crop> getCrops() {
        return this.crops;
    }

    public void setCrops(Set<Crop> crops) {
        if (this.crops != null) {
            this.crops.forEach(i -> i.setSeasonID(null));
        }
        if (crops != null) {
            crops.forEach(i -> i.setSeasonID(this));
        }
        this.crops = crops;
    }

    public Season crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public Season addCrop(Crop crop) {
        this.crops.add(crop);
        crop.setSeasonID(this);
        return this;
    }

    public Season removeCrop(Crop crop) {
        this.crops.remove(crop);
        crop.setSeasonID(null);
        return this;
    }

    public Set<Calendar> getCalendars() {
        return this.calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        if (this.calendars != null) {
            this.calendars.forEach(i -> i.setSeasonID(null));
        }
        if (calendars != null) {
            calendars.forEach(i -> i.setSeasonID(this));
        }
        this.calendars = calendars;
    }

    public Season calendars(Set<Calendar> calendars) {
        this.setCalendars(calendars);
        return this;
    }

    public Season addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.setSeasonID(this);
        return this;
    }

    public Season removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.setSeasonID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Season)) {
            return false;
        }
        return id != null && id.equals(((Season) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Season{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", seasonType='" + getSeasonType() + "'" +
            ", cropName='" + getCropName() + "'" +
            ", area=" + getArea() +
            ", seasonTime='" + getSeasonTime() + "'" +
            ", seasonstartDate='" + getSeasonstartDate() + "'" +
            ", seasonEndDate='" + getSeasonEndDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
