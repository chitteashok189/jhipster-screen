package org.jhipster.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InspectionRecord.
 */
@Entity
@Table(name = "inspection_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InspectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "g_uid")
    private UUID gUID;

    @Column(name = "raw_material_batch_no")
    private Integer rawMaterialBatchNo;

    @Column(name = "product_batch_no")
    private Integer productBatchNo;

    @Column(name = "raw_material_batch_code")
    private Integer rawMaterialBatchCode;

    @Column(name = "input_batch_no")
    private Integer inputBatchNo;

    @Column(name = "input_batch_code")
    private Integer inputBatchCode;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inspectionRecords", "harvestID" }, allowSetters = true)
    private Inspection inspectionID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "harvests", "inspectionRecords", "seedID", "cropID" }, allowSetters = true)
    private Lot lotID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InspectionRecord id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getgUID() {
        return this.gUID;
    }

    public InspectionRecord gUID(UUID gUID) {
        this.setgUID(gUID);
        return this;
    }

    public void setgUID(UUID gUID) {
        this.gUID = gUID;
    }

    public Integer getRawMaterialBatchNo() {
        return this.rawMaterialBatchNo;
    }

    public InspectionRecord rawMaterialBatchNo(Integer rawMaterialBatchNo) {
        this.setRawMaterialBatchNo(rawMaterialBatchNo);
        return this;
    }

    public void setRawMaterialBatchNo(Integer rawMaterialBatchNo) {
        this.rawMaterialBatchNo = rawMaterialBatchNo;
    }

    public Integer getProductBatchNo() {
        return this.productBatchNo;
    }

    public InspectionRecord productBatchNo(Integer productBatchNo) {
        this.setProductBatchNo(productBatchNo);
        return this;
    }

    public void setProductBatchNo(Integer productBatchNo) {
        this.productBatchNo = productBatchNo;
    }

    public Integer getRawMaterialBatchCode() {
        return this.rawMaterialBatchCode;
    }

    public InspectionRecord rawMaterialBatchCode(Integer rawMaterialBatchCode) {
        this.setRawMaterialBatchCode(rawMaterialBatchCode);
        return this;
    }

    public void setRawMaterialBatchCode(Integer rawMaterialBatchCode) {
        this.rawMaterialBatchCode = rawMaterialBatchCode;
    }

    public Integer getInputBatchNo() {
        return this.inputBatchNo;
    }

    public InspectionRecord inputBatchNo(Integer inputBatchNo) {
        this.setInputBatchNo(inputBatchNo);
        return this;
    }

    public void setInputBatchNo(Integer inputBatchNo) {
        this.inputBatchNo = inputBatchNo;
    }

    public Integer getInputBatchCode() {
        return this.inputBatchCode;
    }

    public InspectionRecord inputBatchCode(Integer inputBatchCode) {
        this.setInputBatchCode(inputBatchCode);
        return this;
    }

    public void setInputBatchCode(Integer inputBatchCode) {
        this.inputBatchCode = inputBatchCode;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public InspectionRecord attachments(byte[] attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return this.attachmentsContentType;
    }

    public InspectionRecord attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public InspectionRecord createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public InspectionRecord createdOn(ZonedDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public InspectionRecord updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public InspectionRecord updatedOn(ZonedDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Inspection getInspectionID() {
        return this.inspectionID;
    }

    public void setInspectionID(Inspection inspection) {
        this.inspectionID = inspection;
    }

    public InspectionRecord inspectionID(Inspection inspection) {
        this.setInspectionID(inspection);
        return this;
    }

    public Lot getLotID() {
        return this.lotID;
    }

    public void setLotID(Lot lot) {
        this.lotID = lot;
    }

    public InspectionRecord lotID(Lot lot) {
        this.setLotID(lot);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRecord)) {
            return false;
        }
        return id != null && id.equals(((InspectionRecord) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InspectionRecord{" +
            "id=" + getId() +
            ", gUID='" + getgUID() + "'" +
            ", rawMaterialBatchNo=" + getRawMaterialBatchNo() +
            ", productBatchNo=" + getProductBatchNo() +
            ", rawMaterialBatchCode=" + getRawMaterialBatchCode() +
            ", inputBatchNo=" + getInputBatchNo() +
            ", inputBatchCode=" + getInputBatchCode() +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
