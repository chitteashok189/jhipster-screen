package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Lot;
import org.jhipster.blog.repository.LotRepository;
import org.jhipster.blog.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.blog.domain.Lot}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LotResource {

    private final Logger log = LoggerFactory.getLogger(LotResource.class);

    private static final String ENTITY_NAME = "lot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotRepository lotRepository;

    public LotResource(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    /**
     * {@code POST  /lots} : Create a new lot.
     *
     * @param lot the lot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lot, or with status {@code 400 (Bad Request)} if the lot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lots")
    public ResponseEntity<Lot> createLot(@RequestBody Lot lot) throws URISyntaxException {
        log.debug("REST request to save Lot : {}", lot);
        if (lot.getId() != null) {
            throw new BadRequestAlertException("A new lot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lot result = lotRepository.save(lot);
        return ResponseEntity
            .created(new URI("/api/lots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lots/:id} : Updates an existing lot.
     *
     * @param id the id of the lot to save.
     * @param lot the lot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lot,
     * or with status {@code 400 (Bad Request)} if the lot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lots/{id}")
    public ResponseEntity<Lot> updateLot(@PathVariable(value = "id", required = false) final Long id, @RequestBody Lot lot)
        throws URISyntaxException {
        log.debug("REST request to update Lot : {}, {}", id, lot);
        if (lot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Lot result = lotRepository.save(lot);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lot.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lots/:id} : Partial updates given fields of an existing lot, field will ignore if it is null
     *
     * @param id the id of the lot to save.
     * @param lot the lot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lot,
     * or with status {@code 400 (Bad Request)} if the lot is not valid,
     * or with status {@code 404 (Not Found)} if the lot is not found,
     * or with status {@code 500 (Internal Server Error)} if the lot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Lot> partialUpdateLot(@PathVariable(value = "id", required = false) final Long id, @RequestBody Lot lot)
        throws URISyntaxException {
        log.debug("REST request to partial update Lot partially : {}, {}", id, lot);
        if (lot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lot> result = lotRepository
            .findById(lot.getId())
            .map(existingLot -> {
                if (lot.getgUID() != null) {
                    existingLot.setgUID(lot.getgUID());
                }
                if (lot.getLotCode() != null) {
                    existingLot.setLotCode(lot.getLotCode());
                }
                if (lot.getLotQRCode() != null) {
                    existingLot.setLotQRCode(lot.getLotQRCode());
                }
                if (lot.getLotQRCodeContentType() != null) {
                    existingLot.setLotQRCodeContentType(lot.getLotQRCodeContentType());
                }
                if (lot.getLotSize() != null) {
                    existingLot.setLotSize(lot.getLotSize());
                }
                if (lot.getUnitType() != null) {
                    existingLot.setUnitType(lot.getUnitType());
                }
                if (lot.getSeedlingsGerminated() != null) {
                    existingLot.setSeedlingsGerminated(lot.getSeedlingsGerminated());
                }
                if (lot.getSeedlingsDied() != null) {
                    existingLot.setSeedlingsDied(lot.getSeedlingsDied());
                }
                if (lot.getPlantsWasted() != null) {
                    existingLot.setPlantsWasted(lot.getPlantsWasted());
                }
                if (lot.getTraysSown() != null) {
                    existingLot.setTraysSown(lot.getTraysSown());
                }
                if (lot.getSowingTime() != null) {
                    existingLot.setSowingTime(lot.getSowingTime());
                }
                if (lot.getTraysTranplanted() != null) {
                    existingLot.setTraysTranplanted(lot.getTraysTranplanted());
                }
                if (lot.getTransplantationTime() != null) {
                    existingLot.setTransplantationTime(lot.getTransplantationTime());
                }
                if (lot.getPlantsHarvested() != null) {
                    existingLot.setPlantsHarvested(lot.getPlantsHarvested());
                }
                if (lot.getHarvestTime() != null) {
                    existingLot.setHarvestTime(lot.getHarvestTime());
                }
                if (lot.getPackingDate() != null) {
                    existingLot.setPackingDate(lot.getPackingDate());
                }
                if (lot.getTranportationDate() != null) {
                    existingLot.setTranportationDate(lot.getTranportationDate());
                }
                if (lot.getCreatedBy() != null) {
                    existingLot.setCreatedBy(lot.getCreatedBy());
                }
                if (lot.getCreatedOn() != null) {
                    existingLot.setCreatedOn(lot.getCreatedOn());
                }
                if (lot.getUpdatedBy() != null) {
                    existingLot.setUpdatedBy(lot.getUpdatedBy());
                }
                if (lot.getUpdatedOn() != null) {
                    existingLot.setUpdatedOn(lot.getUpdatedOn());
                }

                return existingLot;
            })
            .map(lotRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lot.getId().toString())
        );
    }

    /**
     * {@code GET  /lots} : get all the lots.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lots in body.
     */
    @GetMapping("/lots")
    public List<Lot> getAllLots() {
        log.debug("REST request to get all Lots");
        return lotRepository.findAll();
    }

    /**
     * {@code GET  /lots/:id} : get the "id" lot.
     *
     * @param id the id of the lot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lots/{id}")
    public ResponseEntity<Lot> getLot(@PathVariable Long id) {
        log.debug("REST request to get Lot : {}", id);
        Optional<Lot> lot = lotRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lot);
    }

    /**
     * {@code DELETE  /lots/:id} : delete the "id" lot.
     *
     * @param id the id of the lot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lots/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable Long id) {
        log.debug("REST request to delete Lot : {}", id);
        lotRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
