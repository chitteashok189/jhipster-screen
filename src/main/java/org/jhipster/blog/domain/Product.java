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
import org.jhipster.blog.domain.enumeration.ProType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Integer productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProType productType;

    @Column(name = "u_om")
    private Integer uOM;

    @Column(name = "other_product_details")
    private String otherProductDetails;

    @Column(name = "previous_entry")
    private Long previousEntry;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Column(name = "product_entry_name")
    private String productEntryName;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "length")
    private Integer length;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @OneToMany(mappedBy = "productID")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public Product gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public Product productCode(String productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return this.productPrice;
    }

    public Product productPrice(Integer productPrice) {
        this.setProductPrice(productPrice);
        return this;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public ProType getProductType() {
        return this.productType;
    }

    public Product productType(ProType productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(ProType productType) {
        this.productType = productType;
    }

    public Integer getuOM() {
        return this.uOM;
    }

    public Product uOM(Integer uOM) {
        this.setuOM(uOM);
        return this;
    }

    public void setuOM(Integer uOM) {
        this.uOM = uOM;
    }

    public String getOtherProductDetails() {
        return this.otherProductDetails;
    }

    public Product otherProductDetails(String otherProductDetails) {
        this.setOtherProductDetails(otherProductDetails);
        return this;
    }

    public void setOtherProductDetails(String otherProductDetails) {
        this.otherProductDetails = otherProductDetails;
    }

    public Long getPreviousEntry() {
        return this.previousEntry;
    }

    public Product previousEntry(Long previousEntry) {
        this.setPreviousEntry(previousEntry);
        return this;
    }

    public void setPreviousEntry(Long previousEntry) {
        this.previousEntry = previousEntry;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Product manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public Product productDescription(String productDescription) {
        this.setProductDescription(productDescription);
        return this;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public Product imageFileName(String imageFileName) {
        this.setImageFileName(imageFileName);
        return this;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getProductEntryName() {
        return this.productEntryName;
    }

    public Product productEntryName(String productEntryName) {
        this.setProductEntryName(productEntryName);
        return this;
    }

    public void setProductEntryName(String productEntryName) {
        this.productEntryName = productEntryName;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Product capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getLength() {
        return this.length;
    }

    public Product length(Integer length) {
        this.setLength(length);
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Product width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Product height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Product createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public Product createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Product updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public Product updatedOn(ZonedDateTime updatedOn) {
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
            this.crops.forEach(i -> i.setProductID(null));
        }
        if (crops != null) {
            crops.forEach(i -> i.setProductID(this));
        }
        this.crops = crops;
    }

    public Product crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public Product addCrop(Crop crop) {
        this.crops.add(crop);
        crop.setProductID(this);
        return this;
    }

    public Product removeCrop(Crop crop) {
        this.crops.remove(crop);
        crop.setProductID(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", productCode='" + getProductCode() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productPrice=" + getProductPrice() +
            ", productType='" + getProductType() + "'" +
            ", uOM=" + getuOM() +
            ", otherProductDetails='" + getOtherProductDetails() + "'" +
            ", previousEntry=" + getPreviousEntry() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            ", imageFileName='" + getImageFileName() + "'" +
            ", productEntryName='" + getProductEntryName() + "'" +
            ", capacity=" + getCapacity() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
