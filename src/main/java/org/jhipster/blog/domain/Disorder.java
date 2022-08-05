package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Disorder.
 */
@Entity
@Table(name = "disorder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Disorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "physiological_disorder")
    private String physiologicalDisorder;

    @Column(name = "cause")
    private String cause;

    @Column(name = "control_measure")
    private String controlMeasure;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pests", "diseases", "symptoms", "disorders", "plantFactoryID", "cropID" }, allowSetters = true)
    private Scouting scoutingID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Disorder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Disorder gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getPhysiologicalDisorder() {
        return this.physiologicalDisorder;
    }

    public Disorder physiologicalDisorder(String physiologicalDisorder) {
        this.setPhysiologicalDisorder(physiologicalDisorder);
        return this;
    }

    public void setPhysiologicalDisorder(String physiologicalDisorder) {
        this.physiologicalDisorder = physiologicalDisorder;
    }

    public String getCause() {
        return this.cause;
    }

    public Disorder cause(String cause) {
        this.setCause(cause);
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getControlMeasure() {
        return this.controlMeasure;
    }

    public Disorder controlMeasure(String controlMeasure) {
        this.setControlMeasure(controlMeasure);
        return this;
    }

    public void setControlMeasure(String controlMeasure) {
        this.controlMeasure = controlMeasure;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Disorder createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Disorder createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Disorder updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Disorder updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Scouting getScoutingID() {
        return this.scoutingID;
    }

    public void setScoutingID(Scouting scouting) {
        this.scoutingID = scouting;
    }

    public Disorder scoutingID(Scouting scouting) {
        this.setScoutingID(scouting);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disorder)) {
            return false;
        }
        return id != null && id.equals(((Disorder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disorder{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", physiologicalDisorder='" + getPhysiologicalDisorder() + "'" +
            ", cause='" + getCause() + "'" +
            ", controlMeasure='" + getControlMeasure() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
