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

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "calender_date")
    private Instant calenderDate;

    @Column(name = "calender_year")
    private Integer calenderYear;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "month_of_year")
    private Integer monthOfYear;

    @Column(name = "week_of_month")
    private Integer weekOfMonth;

    @Column(name = "week_of_quarter")
    private Integer weekOfQuarter;

    @Column(name = "week_of_year")
    private Integer weekOfYear;

    @Column(name = "day_of_quarter")
    private Integer dayOfQuarter;

    @Column(name = "day_of_year")
    private Integer dayOfYear;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "calendarID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calendarID" }, allowSetters = true)
    private Set<Weather> weathers = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "calendars", "activities", "plantFactoryID", "growBedID" }, allowSetters = true)
    private Zone zoneID;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "crops", "calendars" }, allowSetters = true)
    private Tool toolID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "crops", "calendars" }, allowSetters = true)
    private Season seasonID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Calendar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Calendar gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Instant getCalenderDate() {
        return this.calenderDate;
    }

    public Calendar calenderDate(Instant calenderDate) {
        this.setCalenderDate(calenderDate);
        return this;
    }

    public void setCalenderDate(Instant calenderDate) {
        this.calenderDate = calenderDate;
    }

    public Integer getCalenderYear() {
        return this.calenderYear;
    }

    public Calendar calenderYear(Integer calenderYear) {
        this.setCalenderYear(calenderYear);
        return this;
    }

    public void setCalenderYear(Integer calenderYear) {
        this.calenderYear = calenderYear;
    }

    public Integer getDayOfWeek() {
        return this.dayOfWeek;
    }

    public Calendar dayOfWeek(Integer dayOfWeek) {
        this.setDayOfWeek(dayOfWeek);
        return this;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getMonthOfYear() {
        return this.monthOfYear;
    }

    public Calendar monthOfYear(Integer monthOfYear) {
        this.setMonthOfYear(monthOfYear);
        return this;
    }

    public void setMonthOfYear(Integer monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public Integer getWeekOfMonth() {
        return this.weekOfMonth;
    }

    public Calendar weekOfMonth(Integer weekOfMonth) {
        this.setWeekOfMonth(weekOfMonth);
        return this;
    }

    public void setWeekOfMonth(Integer weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

    public Integer getWeekOfQuarter() {
        return this.weekOfQuarter;
    }

    public Calendar weekOfQuarter(Integer weekOfQuarter) {
        this.setWeekOfQuarter(weekOfQuarter);
        return this;
    }

    public void setWeekOfQuarter(Integer weekOfQuarter) {
        this.weekOfQuarter = weekOfQuarter;
    }

    public Integer getWeekOfYear() {
        return this.weekOfYear;
    }

    public Calendar weekOfYear(Integer weekOfYear) {
        this.setWeekOfYear(weekOfYear);
        return this;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Integer getDayOfQuarter() {
        return this.dayOfQuarter;
    }

    public Calendar dayOfQuarter(Integer dayOfQuarter) {
        this.setDayOfQuarter(dayOfQuarter);
        return this;
    }

    public void setDayOfQuarter(Integer dayOfQuarter) {
        this.dayOfQuarter = dayOfQuarter;
    }

    public Integer getDayOfYear() {
        return this.dayOfYear;
    }

    public Calendar dayOfYear(Integer dayOfYear) {
        this.setDayOfYear(dayOfYear);
        return this;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Calendar createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Calendar createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Calendar updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Calendar updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Weather> getWeathers() {
        return this.weathers;
    }

    public void setWeathers(Set<Weather> weathers) {
        if (this.weathers != null) {
            this.weathers.forEach(i -> i.setCalendarID(null));
        }
        if (weathers != null) {
            weathers.forEach(i -> i.setCalendarID(this));
        }
        this.weathers = weathers;
    }

    public Calendar weathers(Set<Weather> weathers) {
        this.setWeathers(weathers);
        return this;
    }

    public Calendar addWeather(Weather weather) {
        this.weathers.add(weather);
        weather.setCalendarID(this);
        return this;
    }

    public Calendar removeWeather(Weather weather) {
        this.weathers.remove(weather);
        weather.setCalendarID(null);
        return this;
    }

    public PlantFactory getPlantFactoryID() {
        return this.plantFactoryID;
    }

    public void setPlantFactoryID(PlantFactory plantFactory) {
        this.plantFactoryID = plantFactory;
    }

    public Calendar plantFactoryID(PlantFactory plantFactory) {
        this.setPlantFactoryID(plantFactory);
        return this;
    }

    public Zone getZoneID() {
        return this.zoneID;
    }

    public void setZoneID(Zone zone) {
        this.zoneID = zone;
    }

    public Calendar zoneID(Zone zone) {
        this.setZoneID(zone);
        return this;
    }

    public Crop getCropID() {
        return this.cropID;
    }

    public void setCropID(Crop crop) {
        this.cropID = crop;
    }

    public Calendar cropID(Crop crop) {
        this.setCropID(crop);
        return this;
    }

    public Tool getToolID() {
        return this.toolID;
    }

    public void setToolID(Tool tool) {
        this.toolID = tool;
    }

    public Calendar toolID(Tool tool) {
        this.setToolID(tool);
        return this;
    }

    public Season getSeasonID() {
        return this.seasonID;
    }

    public void setSeasonID(Season season) {
        this.seasonID = season;
    }

    public Calendar seasonID(Season season) {
        this.setSeasonID(season);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calendar)) {
            return false;
        }
        return id != null && id.equals(((Calendar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", calenderDate='" + getCalenderDate() + "'" +
            ", calenderYear=" + getCalenderYear() +
            ", dayOfWeek=" + getDayOfWeek() +
            ", monthOfYear=" + getMonthOfYear() +
            ", weekOfMonth=" + getWeekOfMonth() +
            ", weekOfQuarter=" + getWeekOfQuarter() +
            ", weekOfYear=" + getWeekOfYear() +
            ", dayOfQuarter=" + getDayOfQuarter() +
            ", dayOfYear=" + getDayOfYear() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
