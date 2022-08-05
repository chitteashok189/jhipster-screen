package org.jhipster.blog.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.blog.domain.enumeration.NutForms;
import org.jhipster.blog.domain.enumeration.NutTypeID;
import org.jhipster.blog.domain.enumeration.NutrientType;

/**
 * A Nutrient.
 */
@Entity
@Table(name = "nutrient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nutrient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "nutrient_name")
    private String nutrientName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NutrientType type;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "nutrient_label")
    private Integer nutrientLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "nutrient_forms")
    private NutForms nutrientForms;

    @Enumerated(EnumType.STRING)
    @Column(name = "nutrient_type_id")
    private NutTypeID nutrientTypeID;

    @Column(name = "price")
    private Integer price;

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

    public Nutrient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Nutrient gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getNutrientName() {
        return this.nutrientName;
    }

    public Nutrient nutrientName(String nutrientName) {
        this.setNutrientName(nutrientName);
        return this;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public NutrientType getType() {
        return this.type;
    }

    public Nutrient type(NutrientType type) {
        this.setType(type);
        return this;
    }

    public void setType(NutrientType type) {
        this.type = type;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public Nutrient brandName(String brandName) {
        this.setBrandName(brandName);
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getNutrientLabel() {
        return this.nutrientLabel;
    }

    public Nutrient nutrientLabel(Integer nutrientLabel) {
        this.setNutrientLabel(nutrientLabel);
        return this;
    }

    public void setNutrientLabel(Integer nutrientLabel) {
        this.nutrientLabel = nutrientLabel;
    }

    public NutForms getNutrientForms() {
        return this.nutrientForms;
    }

    public Nutrient nutrientForms(NutForms nutrientForms) {
        this.setNutrientForms(nutrientForms);
        return this;
    }

    public void setNutrientForms(NutForms nutrientForms) {
        this.nutrientForms = nutrientForms;
    }

    public NutTypeID getNutrientTypeID() {
        return this.nutrientTypeID;
    }

    public Nutrient nutrientTypeID(NutTypeID nutrientTypeID) {
        this.setNutrientTypeID(nutrientTypeID);
        return this;
    }

    public void setNutrientTypeID(NutTypeID nutrientTypeID) {
        this.nutrientTypeID = nutrientTypeID;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Nutrient price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Nutrient createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Nutrient createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Nutrient updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Nutrient updatedOn(ZonedDateTime updatedOn) {
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
        if (!(o instanceof Nutrient)) {
            return false;
        }
        return id != null && id.equals(((Nutrient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nutrient{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", nutrientName='" + getNutrientName() + "'" +
            ", type='" + getType() + "'" +
            ", brandName='" + getBrandName() + "'" +
            ", nutrientLabel=" + getNutrientLabel() +
            ", nutrientForms='" + getNutrientForms() + "'" +
            ", nutrientTypeID='" + getNutrientTypeID() + "'" +
            ", price=" + getPrice() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
