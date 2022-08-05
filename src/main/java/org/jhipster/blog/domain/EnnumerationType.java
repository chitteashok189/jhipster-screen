package org.jhipster.blog.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EnnumerationType.
 */
@Entity
@Table(name = "ennumeration_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnnumerationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ennumeration_type")
    private Long ennumerationType;

    @Column(name = "has_table")
    private Boolean hasTable;

    @Column(name = "description")
    private String description;

    @Column(name = "ennumeration")
    private String ennumeration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EnnumerationType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnnumerationType() {
        return this.ennumerationType;
    }

    public EnnumerationType ennumerationType(Long ennumerationType) {
        this.setEnnumerationType(ennumerationType);
        return this;
    }

    public void setEnnumerationType(Long ennumerationType) {
        this.ennumerationType = ennumerationType;
    }

    public Boolean getHasTable() {
        return this.hasTable;
    }

    public EnnumerationType hasTable(Boolean hasTable) {
        this.setHasTable(hasTable);
        return this;
    }

    public void setHasTable(Boolean hasTable) {
        this.hasTable = hasTable;
    }

    public String getDescription() {
        return this.description;
    }

    public EnnumerationType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnnumeration() {
        return this.ennumeration;
    }

    public EnnumerationType ennumeration(String ennumeration) {
        this.setEnnumeration(ennumeration);
        return this;
    }

    public void setEnnumeration(String ennumeration) {
        this.ennumeration = ennumeration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnnumerationType)) {
            return false;
        }
        return id != null && id.equals(((EnnumerationType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnnumerationType{" +
            "id=" + getId() +
            ", ennumerationType=" + getEnnumerationType() +
            ", hasTable='" + getHasTable() + "'" +
            ", description='" + getDescription() + "'" +
            ", ennumeration='" + getEnnumeration() + "'" +
            "}";
    }
}
