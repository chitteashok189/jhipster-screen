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
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.Tool;
import org.jhipster.blog.domain.enumeration.ToolType;
import org.jhipster.blog.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ToolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToolResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final ToolType DEFAULT_TOOL_TYPE = ToolType.Spade;
    private static final ToolType UPDATED_TOOL_TYPE = ToolType.Rake;

    private static final String DEFAULT_TOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TOOL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/tools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToolMockMvc;

    private Tool tool;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tool createEntity(EntityManager em) {
        Tool tool = new Tool()
            .gUID(DEFAULT_G_UID)
            .toolType(DEFAULT_TOOL_TYPE)
            .toolName(DEFAULT_TOOL_NAME)
            .manufacturer(DEFAULT_MANUFACTURER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return tool;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tool createUpdatedEntity(EntityManager em) {
        Tool tool = new Tool()
            .gUID(UPDATED_G_UID)
            .toolType(UPDATED_TOOL_TYPE)
            .toolName(UPDATED_TOOL_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return tool;
    }

    @BeforeEach
    public void initTest() {
        tool = createEntity(em);
    }

    @Test
    @Transactional
    void createTool() throws Exception {
        int databaseSizeBeforeCreate = toolRepository.findAll().size();
        // Create the Tool
        restToolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isCreated());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeCreate + 1);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testTool.getToolType()).isEqualTo(DEFAULT_TOOL_TYPE);
        assertThat(testTool.getToolName()).isEqualTo(DEFAULT_TOOL_NAME);
        assertThat(testTool.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testTool.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTool.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTool.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTool.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createToolWithExistingId() throws Exception {
        // Create the Tool with an existing ID
        tool.setId(1L);

        int databaseSizeBeforeCreate = toolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTools() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList
        restToolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tool.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].toolType").value(hasItem(DEFAULT_TOOL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].toolName").value(hasItem(DEFAULT_TOOL_NAME)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get the tool
        restToolMockMvc
            .perform(get(ENTITY_API_URL_ID, tool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tool.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.toolType").value(DEFAULT_TOOL_TYPE.toString()))
            .andExpect(jsonPath("$.toolName").value(DEFAULT_TOOL_NAME))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingTool() throws Exception {
        // Get the tool
        restToolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool
        Tool updatedTool = toolRepository.findById(tool.getId()).get();
        // Disconnect from session so that the updates on updatedTool are not directly saved in db
        em.detach(updatedTool);
        updatedTool
            .gUID(UPDATED_G_UID)
            .toolType(UPDATED_TOOL_TYPE)
            .toolName(UPDATED_TOOL_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restToolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTool.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTool))
            )
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testTool.getToolType()).isEqualTo(UPDATED_TOOL_TYPE);
        assertThat(testTool.getToolName()).isEqualTo(UPDATED_TOOL_NAME);
        assertThat(testTool.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testTool.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTool.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTool.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTool.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tool.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tool))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tool))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToolWithPatch() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool using partial update
        Tool partialUpdatedTool = new Tool();
        partialUpdatedTool.setId(tool.getId());

        partialUpdatedTool.gUID(UPDATED_G_UID).toolName(UPDATED_TOOL_NAME).updatedBy(UPDATED_UPDATED_BY);

        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTool))
            )
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testTool.getToolType()).isEqualTo(DEFAULT_TOOL_TYPE);
        assertThat(testTool.getToolName()).isEqualTo(UPDATED_TOOL_NAME);
        assertThat(testTool.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testTool.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTool.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTool.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTool.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateToolWithPatch() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool using partial update
        Tool partialUpdatedTool = new Tool();
        partialUpdatedTool.setId(tool.getId());

        partialUpdatedTool
            .gUID(UPDATED_G_UID)
            .toolType(UPDATED_TOOL_TYPE)
            .toolName(UPDATED_TOOL_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTool))
            )
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testTool.getToolType()).isEqualTo(UPDATED_TOOL_TYPE);
        assertThat(testTool.getToolName()).isEqualTo(UPDATED_TOOL_NAME);
        assertThat(testTool.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testTool.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTool.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTool.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTool.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tool))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tool))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeDelete = toolRepository.findAll().size();

        // Delete the tool
        restToolMockMvc
            .perform(delete(ENTITY_API_URL_ID, tool.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
