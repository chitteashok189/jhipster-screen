package org.jhipster.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.blog.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.Lot;
import org.jhipster.blog.domain.enumeration.HarTime;
import org.jhipster.blog.domain.enumeration.Sowing;
import org.jhipster.blog.domain.enumeration.Transplantation;
import org.jhipster.blog.domain.enumeration.Unit;
import org.jhipster.blog.repository.LotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link LotResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LotResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_LOT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOT_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOT_QR_CODE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOT_QR_CODE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOT_QR_CODE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOT_QR_CODE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_LOT_SIZE = 1;
    private static final Integer UPDATED_LOT_SIZE = 2;

    private static final Unit DEFAULT_UNIT_TYPE = Unit.Cavities;
    private static final Unit UPDATED_UNIT_TYPE = Unit.Cells;

    private static final Integer DEFAULT_SEEDLINGS_GERMINATED = 1;
    private static final Integer UPDATED_SEEDLINGS_GERMINATED = 2;

    private static final Integer DEFAULT_SEEDLINGS_DIED = 1;
    private static final Integer UPDATED_SEEDLINGS_DIED = 2;

    private static final Integer DEFAULT_PLANTS_WASTED = 1;
    private static final Integer UPDATED_PLANTS_WASTED = 2;

    private static final Integer DEFAULT_TRAYS_SOWN = 1;
    private static final Integer UPDATED_TRAYS_SOWN = 2;

    private static final Sowing DEFAULT_SOWING_TIME = Sowing.Daily;
    private static final Sowing UPDATED_SOWING_TIME = Sowing.Weekly;

    private static final Integer DEFAULT_TRAYS_TRANPLANTED = 1;
    private static final Integer UPDATED_TRAYS_TRANPLANTED = 2;

    private static final Transplantation DEFAULT_TRANSPLANTATION_TIME = Transplantation.Daily;
    private static final Transplantation UPDATED_TRANSPLANTATION_TIME = Transplantation.Weekly;

    private static final Integer DEFAULT_PLANTS_HARVESTED = 1;
    private static final Integer UPDATED_PLANTS_HARVESTED = 2;

    private static final HarTime DEFAULT_HARVEST_TIME = HarTime.Daily;
    private static final HarTime UPDATED_HARVEST_TIME = HarTime.Weekly;

    private static final Instant DEFAULT_PACKING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PACKING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TRANPORTATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANPORTATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/lots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLotMockMvc;

    private Lot lot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lot createEntity(EntityManager em) {
        Lot lot = new Lot()
            .gUID(DEFAULT_G_UID)
            .lotCode(DEFAULT_LOT_CODE)
            .lotQRCode(DEFAULT_LOT_QR_CODE)
            .lotQRCodeContentType(DEFAULT_LOT_QR_CODE_CONTENT_TYPE)
            .lotSize(DEFAULT_LOT_SIZE)
            .unitType(DEFAULT_UNIT_TYPE)
            .seedlingsGerminated(DEFAULT_SEEDLINGS_GERMINATED)
            .seedlingsDied(DEFAULT_SEEDLINGS_DIED)
            .plantsWasted(DEFAULT_PLANTS_WASTED)
            .traysSown(DEFAULT_TRAYS_SOWN)
            .sowingTime(DEFAULT_SOWING_TIME)
            .traysTranplanted(DEFAULT_TRAYS_TRANPLANTED)
            .transplantationTime(DEFAULT_TRANSPLANTATION_TIME)
            .plantsHarvested(DEFAULT_PLANTS_HARVESTED)
            .harvestTime(DEFAULT_HARVEST_TIME)
            .packingDate(DEFAULT_PACKING_DATE)
            .tranportationDate(DEFAULT_TRANPORTATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return lot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lot createUpdatedEntity(EntityManager em) {
        Lot lot = new Lot()
            .gUID(UPDATED_G_UID)
            .lotCode(UPDATED_LOT_CODE)
            .lotQRCode(UPDATED_LOT_QR_CODE)
            .lotQRCodeContentType(UPDATED_LOT_QR_CODE_CONTENT_TYPE)
            .lotSize(UPDATED_LOT_SIZE)
            .unitType(UPDATED_UNIT_TYPE)
            .seedlingsGerminated(UPDATED_SEEDLINGS_GERMINATED)
            .seedlingsDied(UPDATED_SEEDLINGS_DIED)
            .plantsWasted(UPDATED_PLANTS_WASTED)
            .traysSown(UPDATED_TRAYS_SOWN)
            .sowingTime(UPDATED_SOWING_TIME)
            .traysTranplanted(UPDATED_TRAYS_TRANPLANTED)
            .transplantationTime(UPDATED_TRANSPLANTATION_TIME)
            .plantsHarvested(UPDATED_PLANTS_HARVESTED)
            .harvestTime(UPDATED_HARVEST_TIME)
            .packingDate(UPDATED_PACKING_DATE)
            .tranportationDate(UPDATED_TRANPORTATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return lot;
    }

    @BeforeEach
    public void initTest() {
        lot = createEntity(em);
    }

    @Test
    @Transactional
    void createLot() throws Exception {
        int databaseSizeBeforeCreate = lotRepository.findAll().size();
        // Create the Lot
        restLotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isCreated());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeCreate + 1);
        Lot testLot = lotList.get(lotList.size() - 1);
        assertThat(testLot.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testLot.getLotCode()).isEqualTo(DEFAULT_LOT_CODE);
        assertThat(testLot.getLotQRCode()).isEqualTo(DEFAULT_LOT_QR_CODE);
        assertThat(testLot.getLotQRCodeContentType()).isEqualTo(DEFAULT_LOT_QR_CODE_CONTENT_TYPE);
        assertThat(testLot.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testLot.getUnitType()).isEqualTo(DEFAULT_UNIT_TYPE);
        assertThat(testLot.getSeedlingsGerminated()).isEqualTo(DEFAULT_SEEDLINGS_GERMINATED);
        assertThat(testLot.getSeedlingsDied()).isEqualTo(DEFAULT_SEEDLINGS_DIED);
        assertThat(testLot.getPlantsWasted()).isEqualTo(DEFAULT_PLANTS_WASTED);
        assertThat(testLot.getTraysSown()).isEqualTo(DEFAULT_TRAYS_SOWN);
        assertThat(testLot.getSowingTime()).isEqualTo(DEFAULT_SOWING_TIME);
        assertThat(testLot.getTraysTranplanted()).isEqualTo(DEFAULT_TRAYS_TRANPLANTED);
        assertThat(testLot.getTransplantationTime()).isEqualTo(DEFAULT_TRANSPLANTATION_TIME);
        assertThat(testLot.getPlantsHarvested()).isEqualTo(DEFAULT_PLANTS_HARVESTED);
        assertThat(testLot.getHarvestTime()).isEqualTo(DEFAULT_HARVEST_TIME);
        assertThat(testLot.getPackingDate()).isEqualTo(DEFAULT_PACKING_DATE);
        assertThat(testLot.getTranportationDate()).isEqualTo(DEFAULT_TRANPORTATION_DATE);
        assertThat(testLot.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLot.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLot.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLot.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createLotWithExistingId() throws Exception {
        // Create the Lot with an existing ID
        lot.setId(1L);

        int databaseSizeBeforeCreate = lotRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLots() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        // Get all the lotList
        restLotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lot.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].lotCode").value(hasItem(DEFAULT_LOT_CODE)))
            .andExpect(jsonPath("$.[*].lotQRCodeContentType").value(hasItem(DEFAULT_LOT_QR_CODE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lotQRCode").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOT_QR_CODE))))
            .andExpect(jsonPath("$.[*].lotSize").value(hasItem(DEFAULT_LOT_SIZE)))
            .andExpect(jsonPath("$.[*].unitType").value(hasItem(DEFAULT_UNIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].seedlingsGerminated").value(hasItem(DEFAULT_SEEDLINGS_GERMINATED)))
            .andExpect(jsonPath("$.[*].seedlingsDied").value(hasItem(DEFAULT_SEEDLINGS_DIED)))
            .andExpect(jsonPath("$.[*].plantsWasted").value(hasItem(DEFAULT_PLANTS_WASTED)))
            .andExpect(jsonPath("$.[*].traysSown").value(hasItem(DEFAULT_TRAYS_SOWN)))
            .andExpect(jsonPath("$.[*].sowingTime").value(hasItem(DEFAULT_SOWING_TIME.toString())))
            .andExpect(jsonPath("$.[*].traysTranplanted").value(hasItem(DEFAULT_TRAYS_TRANPLANTED)))
            .andExpect(jsonPath("$.[*].transplantationTime").value(hasItem(DEFAULT_TRANSPLANTATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].plantsHarvested").value(hasItem(DEFAULT_PLANTS_HARVESTED)))
            .andExpect(jsonPath("$.[*].harvestTime").value(hasItem(DEFAULT_HARVEST_TIME.toString())))
            .andExpect(jsonPath("$.[*].packingDate").value(hasItem(DEFAULT_PACKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].tranportationDate").value(hasItem(DEFAULT_TRANPORTATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getLot() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        // Get the lot
        restLotMockMvc
            .perform(get(ENTITY_API_URL_ID, lot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lot.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.lotCode").value(DEFAULT_LOT_CODE))
            .andExpect(jsonPath("$.lotQRCodeContentType").value(DEFAULT_LOT_QR_CODE_CONTENT_TYPE))
            .andExpect(jsonPath("$.lotQRCode").value(Base64Utils.encodeToString(DEFAULT_LOT_QR_CODE)))
            .andExpect(jsonPath("$.lotSize").value(DEFAULT_LOT_SIZE))
            .andExpect(jsonPath("$.unitType").value(DEFAULT_UNIT_TYPE.toString()))
            .andExpect(jsonPath("$.seedlingsGerminated").value(DEFAULT_SEEDLINGS_GERMINATED))
            .andExpect(jsonPath("$.seedlingsDied").value(DEFAULT_SEEDLINGS_DIED))
            .andExpect(jsonPath("$.plantsWasted").value(DEFAULT_PLANTS_WASTED))
            .andExpect(jsonPath("$.traysSown").value(DEFAULT_TRAYS_SOWN))
            .andExpect(jsonPath("$.sowingTime").value(DEFAULT_SOWING_TIME.toString()))
            .andExpect(jsonPath("$.traysTranplanted").value(DEFAULT_TRAYS_TRANPLANTED))
            .andExpect(jsonPath("$.transplantationTime").value(DEFAULT_TRANSPLANTATION_TIME.toString()))
            .andExpect(jsonPath("$.plantsHarvested").value(DEFAULT_PLANTS_HARVESTED))
            .andExpect(jsonPath("$.harvestTime").value(DEFAULT_HARVEST_TIME.toString()))
            .andExpect(jsonPath("$.packingDate").value(DEFAULT_PACKING_DATE.toString()))
            .andExpect(jsonPath("$.tranportationDate").value(DEFAULT_TRANPORTATION_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingLot() throws Exception {
        // Get the lot
        restLotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLot() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        int databaseSizeBeforeUpdate = lotRepository.findAll().size();

        // Update the lot
        Lot updatedLot = lotRepository.findById(lot.getId()).get();
        // Disconnect from session so that the updates on updatedLot are not directly saved in db
        em.detach(updatedLot);
        updatedLot
            .gUID(UPDATED_G_UID)
            .lotCode(UPDATED_LOT_CODE)
            .lotQRCode(UPDATED_LOT_QR_CODE)
            .lotQRCodeContentType(UPDATED_LOT_QR_CODE_CONTENT_TYPE)
            .lotSize(UPDATED_LOT_SIZE)
            .unitType(UPDATED_UNIT_TYPE)
            .seedlingsGerminated(UPDATED_SEEDLINGS_GERMINATED)
            .seedlingsDied(UPDATED_SEEDLINGS_DIED)
            .plantsWasted(UPDATED_PLANTS_WASTED)
            .traysSown(UPDATED_TRAYS_SOWN)
            .sowingTime(UPDATED_SOWING_TIME)
            .traysTranplanted(UPDATED_TRAYS_TRANPLANTED)
            .transplantationTime(UPDATED_TRANSPLANTATION_TIME)
            .plantsHarvested(UPDATED_PLANTS_HARVESTED)
            .harvestTime(UPDATED_HARVEST_TIME)
            .packingDate(UPDATED_PACKING_DATE)
            .tranportationDate(UPDATED_TRANPORTATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restLotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLot.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLot))
            )
            .andExpect(status().isOk());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
        Lot testLot = lotList.get(lotList.size() - 1);
        assertThat(testLot.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testLot.getLotCode()).isEqualTo(UPDATED_LOT_CODE);
        assertThat(testLot.getLotQRCode()).isEqualTo(UPDATED_LOT_QR_CODE);
        assertThat(testLot.getLotQRCodeContentType()).isEqualTo(UPDATED_LOT_QR_CODE_CONTENT_TYPE);
        assertThat(testLot.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testLot.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testLot.getSeedlingsGerminated()).isEqualTo(UPDATED_SEEDLINGS_GERMINATED);
        assertThat(testLot.getSeedlingsDied()).isEqualTo(UPDATED_SEEDLINGS_DIED);
        assertThat(testLot.getPlantsWasted()).isEqualTo(UPDATED_PLANTS_WASTED);
        assertThat(testLot.getTraysSown()).isEqualTo(UPDATED_TRAYS_SOWN);
        assertThat(testLot.getSowingTime()).isEqualTo(UPDATED_SOWING_TIME);
        assertThat(testLot.getTraysTranplanted()).isEqualTo(UPDATED_TRAYS_TRANPLANTED);
        assertThat(testLot.getTransplantationTime()).isEqualTo(UPDATED_TRANSPLANTATION_TIME);
        assertThat(testLot.getPlantsHarvested()).isEqualTo(UPDATED_PLANTS_HARVESTED);
        assertThat(testLot.getHarvestTime()).isEqualTo(UPDATED_HARVEST_TIME);
        assertThat(testLot.getPackingDate()).isEqualTo(UPDATED_PACKING_DATE);
        assertThat(testLot.getTranportationDate()).isEqualTo(UPDATED_TRANPORTATION_DATE);
        assertThat(testLot.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLot.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLot.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLot.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();
        lot.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lot.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();
        lot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();
        lot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLotWithPatch() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        int databaseSizeBeforeUpdate = lotRepository.findAll().size();

        // Update the lot using partial update
        Lot partialUpdatedLot = new Lot();
        partialUpdatedLot.setId(lot.getId());

        partialUpdatedLot
            .gUID(UPDATED_G_UID)
            .lotQRCode(UPDATED_LOT_QR_CODE)
            .lotQRCodeContentType(UPDATED_LOT_QR_CODE_CONTENT_TYPE)
            .unitType(UPDATED_UNIT_TYPE)
            .seedlingsGerminated(UPDATED_SEEDLINGS_GERMINATED)
            .plantsWasted(UPDATED_PLANTS_WASTED)
            .traysSown(UPDATED_TRAYS_SOWN)
            .sowingTime(UPDATED_SOWING_TIME)
            .traysTranplanted(UPDATED_TRAYS_TRANPLANTED)
            .transplantationTime(UPDATED_TRANSPLANTATION_TIME)
            .plantsHarvested(UPDATED_PLANTS_HARVESTED)
            .harvestTime(UPDATED_HARVEST_TIME)
            .packingDate(UPDATED_PACKING_DATE)
            .tranportationDate(UPDATED_TRANPORTATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restLotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLot))
            )
            .andExpect(status().isOk());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
        Lot testLot = lotList.get(lotList.size() - 1);
        assertThat(testLot.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testLot.getLotCode()).isEqualTo(DEFAULT_LOT_CODE);
        assertThat(testLot.getLotQRCode()).isEqualTo(UPDATED_LOT_QR_CODE);
        assertThat(testLot.getLotQRCodeContentType()).isEqualTo(UPDATED_LOT_QR_CODE_CONTENT_TYPE);
        assertThat(testLot.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testLot.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testLot.getSeedlingsGerminated()).isEqualTo(UPDATED_SEEDLINGS_GERMINATED);
        assertThat(testLot.getSeedlingsDied()).isEqualTo(DEFAULT_SEEDLINGS_DIED);
        assertThat(testLot.getPlantsWasted()).isEqualTo(UPDATED_PLANTS_WASTED);
        assertThat(testLot.getTraysSown()).isEqualTo(UPDATED_TRAYS_SOWN);
        assertThat(testLot.getSowingTime()).isEqualTo(UPDATED_SOWING_TIME);
        assertThat(testLot.getTraysTranplanted()).isEqualTo(UPDATED_TRAYS_TRANPLANTED);
        assertThat(testLot.getTransplantationTime()).isEqualTo(UPDATED_TRANSPLANTATION_TIME);
        assertThat(testLot.getPlantsHarvested()).isEqualTo(UPDATED_PLANTS_HARVESTED);
        assertThat(testLot.getHarvestTime()).isEqualTo(UPDATED_HARVEST_TIME);
        assertThat(testLot.getPackingDate()).isEqualTo(UPDATED_PACKING_DATE);
        assertThat(testLot.getTranportationDate()).isEqualTo(UPDATED_TRANPORTATION_DATE);
        assertThat(testLot.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLot.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLot.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLot.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateLotWithPatch() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        int databaseSizeBeforeUpdate = lotRepository.findAll().size();

        // Update the lot using partial update
        Lot partialUpdatedLot = new Lot();
        partialUpdatedLot.setId(lot.getId());

        partialUpdatedLot
            .gUID(UPDATED_G_UID)
            .lotCode(UPDATED_LOT_CODE)
            .lotQRCode(UPDATED_LOT_QR_CODE)
            .lotQRCodeContentType(UPDATED_LOT_QR_CODE_CONTENT_TYPE)
            .lotSize(UPDATED_LOT_SIZE)
            .unitType(UPDATED_UNIT_TYPE)
            .seedlingsGerminated(UPDATED_SEEDLINGS_GERMINATED)
            .seedlingsDied(UPDATED_SEEDLINGS_DIED)
            .plantsWasted(UPDATED_PLANTS_WASTED)
            .traysSown(UPDATED_TRAYS_SOWN)
            .sowingTime(UPDATED_SOWING_TIME)
            .traysTranplanted(UPDATED_TRAYS_TRANPLANTED)
            .transplantationTime(UPDATED_TRANSPLANTATION_TIME)
            .plantsHarvested(UPDATED_PLANTS_HARVESTED)
            .harvestTime(UPDATED_HARVEST_TIME)
            .packingDate(UPDATED_PACKING_DATE)
            .tranportationDate(UPDATED_TRANPORTATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restLotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLot))
            )
            .andExpect(status().isOk());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
        Lot testLot = lotList.get(lotList.size() - 1);
        assertThat(testLot.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testLot.getLotCode()).isEqualTo(UPDATED_LOT_CODE);
        assertThat(testLot.getLotQRCode()).isEqualTo(UPDATED_LOT_QR_CODE);
        assertThat(testLot.getLotQRCodeContentType()).isEqualTo(UPDATED_LOT_QR_CODE_CONTENT_TYPE);
        assertThat(testLot.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testLot.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testLot.getSeedlingsGerminated()).isEqualTo(UPDATED_SEEDLINGS_GERMINATED);
        assertThat(testLot.getSeedlingsDied()).isEqualTo(UPDATED_SEEDLINGS_DIED);
        assertThat(testLot.getPlantsWasted()).isEqualTo(UPDATED_PLANTS_WASTED);
        assertThat(testLot.getTraysSown()).isEqualTo(UPDATED_TRAYS_SOWN);
        assertThat(testLot.getSowingTime()).isEqualTo(UPDATED_SOWING_TIME);
        assertThat(testLot.getTraysTranplanted()).isEqualTo(UPDATED_TRAYS_TRANPLANTED);
        assertThat(testLot.getTransplantationTime()).isEqualTo(UPDATED_TRANSPLANTATION_TIME);
        assertThat(testLot.getPlantsHarvested()).isEqualTo(UPDATED_PLANTS_HARVESTED);
        assertThat(testLot.getHarvestTime()).isEqualTo(UPDATED_HARVEST_TIME);
        assertThat(testLot.getPackingDate()).isEqualTo(UPDATED_PACKING_DATE);
        assertThat(testLot.getTranportationDate()).isEqualTo(UPDATED_TRANPORTATION_DATE);
        assertThat(testLot.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLot.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLot.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLot.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();
        lot.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();
        lot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();
        lot.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLot() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        int databaseSizeBeforeDelete = lotRepository.findAll().size();

        // Delete the lot
        restLotMockMvc.perform(delete(ENTITY_API_URL_ID, lot.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
