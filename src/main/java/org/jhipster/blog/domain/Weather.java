package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Weather.
 */
@Entity
@Table(name = "weather")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "city_id")
    private Long cityID;

    @Column(name = "start_timestamp")
    private Integer startTimestamp;

    @Column(name = "end_timestamp")
    private Integer endTimestamp;

    @Column(name = "weather_status_id")
    private Long weatherStatusID;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "feels_like_temperature")
    private Integer feelsLikeTemperature;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "wind_speed")
    private Integer windSpeed;

    @Column(name = "wind_direction")
    private Integer windDirection;

    @Column(name = "pressureinmmhg")
    private Integer pressureinmmhg;

    @Column(name = "visibilityinmph")
    private Integer visibilityinmph;

    @Column(name = "cloud_cover")
    private Integer cloudCover;

    @Column(name = "precipitation")
    private Integer precipitation;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "weathers", "plantFactoryID", "zoneID", "cropID", "toolID", "seasonID" }, allowSetters = true)
    private Calendar calendarID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Weather id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Weather gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Long getCityID() {
        return this.cityID;
    }

    public Weather cityID(Long cityID) {
        this.setCityID(cityID);
        return this;
    }

    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }

    public Integer getStartTimestamp() {
        return this.startTimestamp;
    }

    public Weather startTimestamp(Integer startTimestamp) {
        this.setStartTimestamp(startTimestamp);
        return this;
    }

    public void setStartTimestamp(Integer startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Integer getEndTimestamp() {
        return this.endTimestamp;
    }

    public Weather endTimestamp(Integer endTimestamp) {
        this.setEndTimestamp(endTimestamp);
        return this;
    }

    public void setEndTimestamp(Integer endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Long getWeatherStatusID() {
        return this.weatherStatusID;
    }

    public Weather weatherStatusID(Long weatherStatusID) {
        this.setWeatherStatusID(weatherStatusID);
        return this;
    }

    public void setWeatherStatusID(Long weatherStatusID) {
        this.weatherStatusID = weatherStatusID;
    }

    public Integer getTemperature() {
        return this.temperature;
    }

    public Weather temperature(Integer temperature) {
        this.setTemperature(temperature);
        return this;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getFeelsLikeTemperature() {
        return this.feelsLikeTemperature;
    }

    public Weather feelsLikeTemperature(Integer feelsLikeTemperature) {
        this.setFeelsLikeTemperature(feelsLikeTemperature);
        return this;
    }

    public void setFeelsLikeTemperature(Integer feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public Integer getHumidity() {
        return this.humidity;
    }

    public Weather humidity(Integer humidity) {
        this.setHumidity(humidity);
        return this;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getWindSpeed() {
        return this.windSpeed;
    }

    public Weather windSpeed(Integer windSpeed) {
        this.setWindSpeed(windSpeed);
        return this;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return this.windDirection;
    }

    public Weather windDirection(Integer windDirection) {
        this.setWindDirection(windDirection);
        return this;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getPressureinmmhg() {
        return this.pressureinmmhg;
    }

    public Weather pressureinmmhg(Integer pressureinmmhg) {
        this.setPressureinmmhg(pressureinmmhg);
        return this;
    }

    public void setPressureinmmhg(Integer pressureinmmhg) {
        this.pressureinmmhg = pressureinmmhg;
    }

    public Integer getVisibilityinmph() {
        return this.visibilityinmph;
    }

    public Weather visibilityinmph(Integer visibilityinmph) {
        this.setVisibilityinmph(visibilityinmph);
        return this;
    }

    public void setVisibilityinmph(Integer visibilityinmph) {
        this.visibilityinmph = visibilityinmph;
    }

    public Integer getCloudCover() {
        return this.cloudCover;
    }

    public Weather cloudCover(Integer cloudCover) {
        this.setCloudCover(cloudCover);
        return this;
    }

    public void setCloudCover(Integer cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Integer getPrecipitation() {
        return this.precipitation;
    }

    public Weather precipitation(Integer precipitation) {
        this.setPrecipitation(precipitation);
        return this;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Weather createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Weather createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Weather updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Weather updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Calendar getCalendarID() {
        return this.calendarID;
    }

    public void setCalendarID(Calendar calendar) {
        this.calendarID = calendar;
    }

    public Weather calendarID(Calendar calendar) {
        this.setCalendarID(calendar);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Weather)) {
            return false;
        }
        return id != null && id.equals(((Weather) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Weather{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", cityID=" + getCityID() +
            ", startTimestamp=" + getStartTimestamp() +
            ", endTimestamp=" + getEndTimestamp() +
            ", weatherStatusID=" + getWeatherStatusID() +
            ", temperature=" + getTemperature() +
            ", feelsLikeTemperature=" + getFeelsLikeTemperature() +
            ", humidity=" + getHumidity() +
            ", windSpeed=" + getWindSpeed() +
            ", windDirection=" + getWindDirection() +
            ", pressureinmmhg=" + getPressureinmmhg() +
            ", visibilityinmph=" + getVisibilityinmph() +
            ", cloudCover=" + getCloudCover() +
            ", precipitation=" + getPrecipitation() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
