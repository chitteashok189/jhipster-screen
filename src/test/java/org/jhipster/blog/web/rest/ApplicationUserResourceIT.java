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
import org.jhipster.blog.domain.ApplicationUser;
import org.jhipster.blog.repository.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApplicationUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationUserResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_CURRENT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HINT = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HINT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SYSTEM_ENABLES = false;
    private static final Boolean UPDATED_IS_SYSTEM_ENABLES = true;

    private static final Boolean DEFAULT_HAS_LOGGED_OUT = false;
    private static final Boolean UPDATED_HAS_LOGGED_OUT = true;

    private static final Boolean DEFAULT_REQUIRE_PASSWORD_CHANGE = false;
    private static final Boolean UPDATED_REQUIRE_PASSWORD_CHANGE = true;

    private static final Integer DEFAULT_LAST_CURRENCY_UOM = 1;
    private static final Integer UPDATED_LAST_CURRENCY_UOM = 2;

    private static final Integer DEFAULT_LAST_LOCALE = 1;
    private static final Integer UPDATED_LAST_LOCALE = 2;

    private static final Integer DEFAULT_LAST_TIME_ZONE = 1;
    private static final Integer UPDATED_LAST_TIME_ZONE = 2;

    private static final Instant DEFAULT_DISABLED_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISABLED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SUCCESSIVE_FAILED_LOGINS = 1;
    private static final Integer UPDATED_SUCCESSIVE_FAILED_LOGINS = 2;

    private static final String DEFAULT_APPLICATION_USER_SECURITY_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_USER_SECURITY_GROUP = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/application-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationUserMockMvc;

    private ApplicationUser applicationUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUser createEntity(EntityManager em) {
        ApplicationUser applicationUser = new ApplicationUser()
            .gUID(DEFAULT_G_UID)
            .currentPassword(DEFAULT_CURRENT_PASSWORD)
            .passwordHint(DEFAULT_PASSWORD_HINT)
            .isSystemEnables(DEFAULT_IS_SYSTEM_ENABLES)
            .hasLoggedOut(DEFAULT_HAS_LOGGED_OUT)
            .requirePasswordChange(DEFAULT_REQUIRE_PASSWORD_CHANGE)
            .lastCurrencyUom(DEFAULT_LAST_CURRENCY_UOM)
            .lastLocale(DEFAULT_LAST_LOCALE)
            .lastTimeZone(DEFAULT_LAST_TIME_ZONE)
            .disabledDateTime(DEFAULT_DISABLED_DATE_TIME)
            .successiveFailedLogins(DEFAULT_SUCCESSIVE_FAILED_LOGINS)
            .applicationUserSecurityGroup(DEFAULT_APPLICATION_USER_SECURITY_GROUP)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return applicationUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationUser createUpdatedEntity(EntityManager em) {
        ApplicationUser applicationUser = new ApplicationUser()
            .gUID(UPDATED_G_UID)
            .currentPassword(UPDATED_CURRENT_PASSWORD)
            .passwordHint(UPDATED_PASSWORD_HINT)
            .isSystemEnables(UPDATED_IS_SYSTEM_ENABLES)
            .hasLoggedOut(UPDATED_HAS_LOGGED_OUT)
            .requirePasswordChange(UPDATED_REQUIRE_PASSWORD_CHANGE)
            .lastCurrencyUom(UPDATED_LAST_CURRENCY_UOM)
            .lastLocale(UPDATED_LAST_LOCALE)
            .lastTimeZone(UPDATED_LAST_TIME_ZONE)
            .disabledDateTime(UPDATED_DISABLED_DATE_TIME)
            .successiveFailedLogins(UPDATED_SUCCESSIVE_FAILED_LOGINS)
            .applicationUserSecurityGroup(UPDATED_APPLICATION_USER_SECURITY_GROUP)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return applicationUser;
    }

    @BeforeEach
    public void initTest() {
        applicationUser = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicationUser() throws Exception {
        int databaseSizeBeforeCreate = applicationUserRepository.findAll().size();
        // Create the ApplicationUser
        restApplicationUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testApplicationUser.getCurrentPassword()).isEqualTo(DEFAULT_CURRENT_PASSWORD);
        assertThat(testApplicationUser.getPasswordHint()).isEqualTo(DEFAULT_PASSWORD_HINT);
        assertThat(testApplicationUser.getIsSystemEnables()).isEqualTo(DEFAULT_IS_SYSTEM_ENABLES);
        assertThat(testApplicationUser.getHasLoggedOut()).isEqualTo(DEFAULT_HAS_LOGGED_OUT);
        assertThat(testApplicationUser.getRequirePasswordChange()).isEqualTo(DEFAULT_REQUIRE_PASSWORD_CHANGE);
        assertThat(testApplicationUser.getLastCurrencyUom()).isEqualTo(DEFAULT_LAST_CURRENCY_UOM);
        assertThat(testApplicationUser.getLastLocale()).isEqualTo(DEFAULT_LAST_LOCALE);
        assertThat(testApplicationUser.getLastTimeZone()).isEqualTo(DEFAULT_LAST_TIME_ZONE);
        assertThat(testApplicationUser.getDisabledDateTime()).isEqualTo(DEFAULT_DISABLED_DATE_TIME);
        assertThat(testApplicationUser.getSuccessiveFailedLogins()).isEqualTo(DEFAULT_SUCCESSIVE_FAILED_LOGINS);
        assertThat(testApplicationUser.getApplicationUserSecurityGroup()).isEqualTo(DEFAULT_APPLICATION_USER_SECURITY_GROUP);
        assertThat(testApplicationUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationUser.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApplicationUser.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testApplicationUser.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createApplicationUserWithExistingId() throws Exception {
        // Create the ApplicationUser with an existing ID
        applicationUser.setId(1L);

        int databaseSizeBeforeCreate = applicationUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicationUsers() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        // Get all the applicationUserList
        restApplicationUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].currentPassword").value(hasItem(DEFAULT_CURRENT_PASSWORD)))
            .andExpect(jsonPath("$.[*].passwordHint").value(hasItem(DEFAULT_PASSWORD_HINT)))
            .andExpect(jsonPath("$.[*].isSystemEnables").value(hasItem(DEFAULT_IS_SYSTEM_ENABLES.booleanValue())))
            .andExpect(jsonPath("$.[*].hasLoggedOut").value(hasItem(DEFAULT_HAS_LOGGED_OUT.booleanValue())))
            .andExpect(jsonPath("$.[*].requirePasswordChange").value(hasItem(DEFAULT_REQUIRE_PASSWORD_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastCurrencyUom").value(hasItem(DEFAULT_LAST_CURRENCY_UOM)))
            .andExpect(jsonPath("$.[*].lastLocale").value(hasItem(DEFAULT_LAST_LOCALE)))
            .andExpect(jsonPath("$.[*].lastTimeZone").value(hasItem(DEFAULT_LAST_TIME_ZONE)))
            .andExpect(jsonPath("$.[*].disabledDateTime").value(hasItem(DEFAULT_DISABLED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].successiveFailedLogins").value(hasItem(DEFAULT_SUCCESSIVE_FAILED_LOGINS)))
            .andExpect(jsonPath("$.[*].applicationUserSecurityGroup").value(hasItem(DEFAULT_APPLICATION_USER_SECURITY_GROUP)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getApplicationUser() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        // Get the applicationUser
        restApplicationUserMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationUser.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.currentPassword").value(DEFAULT_CURRENT_PASSWORD))
            .andExpect(jsonPath("$.passwordHint").value(DEFAULT_PASSWORD_HINT))
            .andExpect(jsonPath("$.isSystemEnables").value(DEFAULT_IS_SYSTEM_ENABLES.booleanValue()))
            .andExpect(jsonPath("$.hasLoggedOut").value(DEFAULT_HAS_LOGGED_OUT.booleanValue()))
            .andExpect(jsonPath("$.requirePasswordChange").value(DEFAULT_REQUIRE_PASSWORD_CHANGE.booleanValue()))
            .andExpect(jsonPath("$.lastCurrencyUom").value(DEFAULT_LAST_CURRENCY_UOM))
            .andExpect(jsonPath("$.lastLocale").value(DEFAULT_LAST_LOCALE))
            .andExpect(jsonPath("$.lastTimeZone").value(DEFAULT_LAST_TIME_ZONE))
            .andExpect(jsonPath("$.disabledDateTime").value(DEFAULT_DISABLED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.successiveFailedLogins").value(DEFAULT_SUCCESSIVE_FAILED_LOGINS))
            .andExpect(jsonPath("$.applicationUserSecurityGroup").value(DEFAULT_APPLICATION_USER_SECURITY_GROUP))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingApplicationUser() throws Exception {
        // Get the applicationUser
        restApplicationUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplicationUser() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();

        // Update the applicationUser
        ApplicationUser updatedApplicationUser = applicationUserRepository.findById(applicationUser.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationUser are not directly saved in db
        em.detach(updatedApplicationUser);
        updatedApplicationUser
            .gUID(UPDATED_G_UID)
            .currentPassword(UPDATED_CURRENT_PASSWORD)
            .passwordHint(UPDATED_PASSWORD_HINT)
            .isSystemEnables(UPDATED_IS_SYSTEM_ENABLES)
            .hasLoggedOut(UPDATED_HAS_LOGGED_OUT)
            .requirePasswordChange(UPDATED_REQUIRE_PASSWORD_CHANGE)
            .lastCurrencyUom(UPDATED_LAST_CURRENCY_UOM)
            .lastLocale(UPDATED_LAST_LOCALE)
            .lastTimeZone(UPDATED_LAST_TIME_ZONE)
            .disabledDateTime(UPDATED_DISABLED_DATE_TIME)
            .successiveFailedLogins(UPDATED_SUCCESSIVE_FAILED_LOGINS)
            .applicationUserSecurityGroup(UPDATED_APPLICATION_USER_SECURITY_GROUP)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApplicationUser))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testApplicationUser.getCurrentPassword()).isEqualTo(UPDATED_CURRENT_PASSWORD);
        assertThat(testApplicationUser.getPasswordHint()).isEqualTo(UPDATED_PASSWORD_HINT);
        assertThat(testApplicationUser.getIsSystemEnables()).isEqualTo(UPDATED_IS_SYSTEM_ENABLES);
        assertThat(testApplicationUser.getHasLoggedOut()).isEqualTo(UPDATED_HAS_LOGGED_OUT);
        assertThat(testApplicationUser.getRequirePasswordChange()).isEqualTo(UPDATED_REQUIRE_PASSWORD_CHANGE);
        assertThat(testApplicationUser.getLastCurrencyUom()).isEqualTo(UPDATED_LAST_CURRENCY_UOM);
        assertThat(testApplicationUser.getLastLocale()).isEqualTo(UPDATED_LAST_LOCALE);
        assertThat(testApplicationUser.getLastTimeZone()).isEqualTo(UPDATED_LAST_TIME_ZONE);
        assertThat(testApplicationUser.getDisabledDateTime()).isEqualTo(UPDATED_DISABLED_DATE_TIME);
        assertThat(testApplicationUser.getSuccessiveFailedLogins()).isEqualTo(UPDATED_SUCCESSIVE_FAILED_LOGINS);
        assertThat(testApplicationUser.getApplicationUserSecurityGroup()).isEqualTo(UPDATED_APPLICATION_USER_SECURITY_GROUP);
        assertThat(testApplicationUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationUser.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplicationUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationUser.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationUserWithPatch() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();

        // Update the applicationUser using partial update
        ApplicationUser partialUpdatedApplicationUser = new ApplicationUser();
        partialUpdatedApplicationUser.setId(applicationUser.getId());

        partialUpdatedApplicationUser
            .currentPassword(UPDATED_CURRENT_PASSWORD)
            .passwordHint(UPDATED_PASSWORD_HINT)
            .lastCurrencyUom(UPDATED_LAST_CURRENCY_UOM)
            .successiveFailedLogins(UPDATED_SUCCESSIVE_FAILED_LOGINS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUser))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testApplicationUser.getCurrentPassword()).isEqualTo(UPDATED_CURRENT_PASSWORD);
        assertThat(testApplicationUser.getPasswordHint()).isEqualTo(UPDATED_PASSWORD_HINT);
        assertThat(testApplicationUser.getIsSystemEnables()).isEqualTo(DEFAULT_IS_SYSTEM_ENABLES);
        assertThat(testApplicationUser.getHasLoggedOut()).isEqualTo(DEFAULT_HAS_LOGGED_OUT);
        assertThat(testApplicationUser.getRequirePasswordChange()).isEqualTo(DEFAULT_REQUIRE_PASSWORD_CHANGE);
        assertThat(testApplicationUser.getLastCurrencyUom()).isEqualTo(UPDATED_LAST_CURRENCY_UOM);
        assertThat(testApplicationUser.getLastLocale()).isEqualTo(DEFAULT_LAST_LOCALE);
        assertThat(testApplicationUser.getLastTimeZone()).isEqualTo(DEFAULT_LAST_TIME_ZONE);
        assertThat(testApplicationUser.getDisabledDateTime()).isEqualTo(DEFAULT_DISABLED_DATE_TIME);
        assertThat(testApplicationUser.getSuccessiveFailedLogins()).isEqualTo(UPDATED_SUCCESSIVE_FAILED_LOGINS);
        assertThat(testApplicationUser.getApplicationUserSecurityGroup()).isEqualTo(DEFAULT_APPLICATION_USER_SECURITY_GROUP);
        assertThat(testApplicationUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationUser.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplicationUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationUser.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateApplicationUserWithPatch() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();

        // Update the applicationUser using partial update
        ApplicationUser partialUpdatedApplicationUser = new ApplicationUser();
        partialUpdatedApplicationUser.setId(applicationUser.getId());

        partialUpdatedApplicationUser
            .gUID(UPDATED_G_UID)
            .currentPassword(UPDATED_CURRENT_PASSWORD)
            .passwordHint(UPDATED_PASSWORD_HINT)
            .isSystemEnables(UPDATED_IS_SYSTEM_ENABLES)
            .hasLoggedOut(UPDATED_HAS_LOGGED_OUT)
            .requirePasswordChange(UPDATED_REQUIRE_PASSWORD_CHANGE)
            .lastCurrencyUom(UPDATED_LAST_CURRENCY_UOM)
            .lastLocale(UPDATED_LAST_LOCALE)
            .lastTimeZone(UPDATED_LAST_TIME_ZONE)
            .disabledDateTime(UPDATED_DISABLED_DATE_TIME)
            .successiveFailedLogins(UPDATED_SUCCESSIVE_FAILED_LOGINS)
            .applicationUserSecurityGroup(UPDATED_APPLICATION_USER_SECURITY_GROUP)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplicationUser))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
        ApplicationUser testApplicationUser = applicationUserList.get(applicationUserList.size() - 1);
        assertThat(testApplicationUser.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testApplicationUser.getCurrentPassword()).isEqualTo(UPDATED_CURRENT_PASSWORD);
        assertThat(testApplicationUser.getPasswordHint()).isEqualTo(UPDATED_PASSWORD_HINT);
        assertThat(testApplicationUser.getIsSystemEnables()).isEqualTo(UPDATED_IS_SYSTEM_ENABLES);
        assertThat(testApplicationUser.getHasLoggedOut()).isEqualTo(UPDATED_HAS_LOGGED_OUT);
        assertThat(testApplicationUser.getRequirePasswordChange()).isEqualTo(UPDATED_REQUIRE_PASSWORD_CHANGE);
        assertThat(testApplicationUser.getLastCurrencyUom()).isEqualTo(UPDATED_LAST_CURRENCY_UOM);
        assertThat(testApplicationUser.getLastLocale()).isEqualTo(UPDATED_LAST_LOCALE);
        assertThat(testApplicationUser.getLastTimeZone()).isEqualTo(UPDATED_LAST_TIME_ZONE);
        assertThat(testApplicationUser.getDisabledDateTime()).isEqualTo(UPDATED_DISABLED_DATE_TIME);
        assertThat(testApplicationUser.getSuccessiveFailedLogins()).isEqualTo(UPDATED_SUCCESSIVE_FAILED_LOGINS);
        assertThat(testApplicationUser.getApplicationUserSecurityGroup()).isEqualTo(UPDATED_APPLICATION_USER_SECURITY_GROUP);
        assertThat(testApplicationUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationUser.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplicationUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationUser.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationUser() throws Exception {
        int databaseSizeBeforeUpdate = applicationUserRepository.findAll().size();
        applicationUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationUserMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationUser in the database
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationUser() throws Exception {
        // Initialize the database
        applicationUserRepository.saveAndFlush(applicationUser);

        int databaseSizeBeforeDelete = applicationUserRepository.findAll().size();

        // Delete the applicationUser
        restApplicationUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        assertThat(applicationUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
