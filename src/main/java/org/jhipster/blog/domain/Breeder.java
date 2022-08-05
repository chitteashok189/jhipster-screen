package org.jhipster.blog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Breeder.
 */
@Entity
@Table(name = "breeder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Breeder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "breeder_name")
    private String breederName;

    @Column(name = "breeder_type")
    private Long breederType;

    @Column(name = "breeder_status")
    private Long breederStatus;

    @Column(name = "breeder_description")
    private String breederDescription;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Breeder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Breeder gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getBreederName() {
        return this.breederName;
    }

    public Breeder breederName(String breederName) {
        this.setBreederName(breederName);
        return this;
    }

    public void setBreederName(String breederName) {
        this.breederName = breederName;
    }

    public Long getBreederType() {
        return this.breederType;
    }

    public Breeder breederType(Long breederType) {
        this.setBreederType(breederType);
        return this;
    }

    public void setBreederType(Long breederType) {
        this.breederType = breederType;
    }

    public Long getBreederStatus() {
        return this.breederStatus;
    }

    public Breeder breederStatus(Long breederStatus) {
        this.setBreederStatus(breederStatus);
        return this;
    }

    public void setBreederStatus(Long breederStatus) {
        this.breederStatus = breederStatus;
    }

    public String getBreederDescription() {
        return this.breederDescription;
    }

    public Breeder breederDescription(String breederDescription) {
        this.setBreederDescription(breederDescription);
        return this;
    }

    public void setBreederDescription(String breederDescription) {
        this.breederDescription = breederDescription;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Breeder createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Breeder createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Breeder updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Breeder updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Breeder)) {
            return false;
        }
        return id != null && id.equals(((Breeder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Breeder{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", breederName='" + getBreederName() + "'" +
            ", breederType=" + getBreederType() +
            ", breederStatus=" + getBreederStatus() +
            ", breederDescription='" + getBreederDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
