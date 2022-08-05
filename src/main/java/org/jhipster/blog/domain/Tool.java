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
import org.jhipster.blog.domain.enumeration.ToolType;

/**
 * A Tool.
 */
@Entity
@Table(name = "tool")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "tool_type")
    private ToolType toolType;

    @Column(name = "tool_name")
    private String toolName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "toolID")
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

    @OneToMany(mappedBy = "toolID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "weathers", "plantFactoryID", "zoneID", "cropID", "toolID", "seasonID" }, allowSetters = true)
    private Set<Calendar> calendars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tool id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Tool gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public ToolType getToolType() {
        return this.toolType;
    }

    public Tool toolType(ToolType toolType) {
        this.setToolType(toolType);
        return this;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public String getToolName() {
        return this.toolName;
    }

    public Tool toolName(String toolName) {
        this.setToolName(toolName);
        return this;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Tool manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Tool createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Tool createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Tool updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Tool updatedOn(ZonedDateTime updatedOn) {
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
            this.crops.forEach(i -> i.setToolID(null));
        }
        if (crops != null) {
            crops.forEach(i -> i.setToolID(this));
        }
        this.crops = crops;
    }

    public Tool crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public Tool addCrop(Crop crop) {
        this.crops.add(crop);
        crop.setToolID(this);
        return this;
    }

    public Tool removeCrop(Crop crop) {
        this.crops.remove(crop);
        crop.setToolID(null);
        return this;
    }

    public Set<Calendar> getCalendars() {
        return this.calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        if (this.calendars != null) {
            this.calendars.forEach(i -> i.setToolID(null));
        }
        if (calendars != null) {
            calendars.forEach(i -> i.setToolID(this));
        }
        this.calendars = calendars;
    }

    public Tool calendars(Set<Calendar> calendars) {
        this.setCalendars(calendars);
        return this;
    }

    public Tool addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.setToolID(this);
        return this;
    }

    public Tool removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.setToolID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tool)) {
            return false;
        }
        return id != null && id.equals(((Tool) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tool{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", toolType='" + getToolType() + "'" +
            ", toolName='" + getToolName() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
