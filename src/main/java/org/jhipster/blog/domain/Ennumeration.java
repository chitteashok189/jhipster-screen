package org.jhipster.blog.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ennumeration.
 */
@Entity
@Table(name = "ennumeration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ennumeration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "enum_code")
    private Integer enumCode;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ennumeration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnumCode() {
        return this.enumCode;
    }

    public Ennumeration enumCode(Integer enumCode) {
        this.setEnumCode(enumCode);
        return this;
    }

    public void setEnumCode(Integer enumCode) {
        this.enumCode = enumCode;
    }

    public String getDescription() {
        return this.description;
    }

    public Ennumeration description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ennumeration)) {
            return false;
        }
        return id != null && id.equals(((Ennumeration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ennumeration{" +
            "id=" + getId() +
            ", enumCode=" + getEnumCode() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
