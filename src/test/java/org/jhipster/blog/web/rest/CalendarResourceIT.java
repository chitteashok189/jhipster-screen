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
import org.jhipster.blog.domain.Calendar;
import org.jhipster.blog.repository.CalendarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CalendarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalendarResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final Instant DEFAULT_CALENDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALENDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CALENDER_YEAR = 1;
    private static final Integer UPDATED_CALENDER_YEAR = 2;

    private static final Integer DEFAULT_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_DAY_OF_WEEK = 2;

    private static final Integer DEFAULT_MONTH_OF_YEAR = 1;
    private static final Integer UPDATED_MONTH_OF_YEAR = 2;

    private static final Integer DEFAULT_WEEK_OF_MONTH = 1;
    private static final Integer UPDATED_WEEK_OF_MONTH = 2;

    private static final Integer DEFAULT_WEEK_OF_QUARTER = 1;
    private static final Integer UPDATED_WEEK_OF_QUARTER = 2;

    private static final Integer DEFAULT_WEEK_OF_YEAR = 1;
    private static final Integer UPDATED_WEEK_OF_YEAR = 2;

    private static final Integer DEFAULT_DAY_OF_QUARTER = 1;
    private static final Integer UPDATED_DAY_OF_QUARTER = 2;

    private static final Integer DEFAULT_DAY_OF_YEAR = 1;
    private static final Integer UPDATED_DAY_OF_YEAR = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/calendars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarMockMvc;

    private Calendar calendar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .gUID(DEFAULT_G_UID)
            .calenderDate(DEFAULT_CALENDER_DATE)
            .calenderYear(DEFAULT_CALENDER_YEAR)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .monthOfYear(DEFAULT_MONTH_OF_YEAR)
            .weekOfMonth(DEFAULT_WEEK_OF_MONTH)
            .weekOfQuarter(DEFAULT_WEEK_OF_QUARTER)
            .weekOfYear(DEFAULT_WEEK_OF_YEAR)
            .dayOfQuarter(DEFAULT_DAY_OF_QUARTER)
            .dayOfYear(DEFAULT_DAY_OF_YEAR)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return calendar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createUpdatedEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .gUID(UPDATED_G_UID)
            .calenderDate(UPDATED_CALENDER_DATE)
            .calenderYear(UPDATED_CALENDER_YEAR)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .monthOfYear(UPDATED_MONTH_OF_YEAR)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .weekOfQuarter(UPDATED_WEEK_OF_QUARTER)
            .weekOfYear(UPDATED_WEEK_OF_YEAR)
            .dayOfQuarter(UPDATED_DAY_OF_QUARTER)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return calendar;
    }

    @BeforeEach
    public void initTest() {
        calendar = createEntity(em);
    }

    @Test
    @Transactional
    void createCalendar() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();
        // Create the Calendar
        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isCreated());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate + 1);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testCalendar.getCalenderDate()).isEqualTo(DEFAULT_CALENDER_DATE);
        assertThat(testCalendar.getCalenderYear()).isEqualTo(DEFAULT_CALENDER_YEAR);
        assertThat(testCalendar.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testCalendar.getMonthOfYear()).isEqualTo(DEFAULT_MONTH_OF_YEAR);
        assertThat(testCalendar.getWeekOfMonth()).isEqualTo(DEFAULT_WEEK_OF_MONTH);
        assertThat(testCalendar.getWeekOfQuarter()).isEqualTo(DEFAULT_WEEK_OF_QUARTER);
        assertThat(testCalendar.getWeekOfYear()).isEqualTo(DEFAULT_WEEK_OF_YEAR);
        assertThat(testCalendar.getDayOfQuarter()).isEqualTo(DEFAULT_DAY_OF_QUARTER);
        assertThat(testCalendar.getDayOfYear()).isEqualTo(DEFAULT_DAY_OF_YEAR);
        assertThat(testCalendar.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCalendar.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCalendar.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCalendar.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createCalendarWithExistingId() throws Exception {
        // Create the Calendar with an existing ID
        calendar.setId(1L);

        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCalendars() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList
        restCalendarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].calenderDate").value(hasItem(DEFAULT_CALENDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].calenderYear").value(hasItem(DEFAULT_CALENDER_YEAR)))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].monthOfYear").value(hasItem(DEFAULT_MONTH_OF_YEAR)))
            .andExpect(jsonPath("$.[*].weekOfMonth").value(hasItem(DEFAULT_WEEK_OF_MONTH)))
            .andExpect(jsonPath("$.[*].weekOfQuarter").value(hasItem(DEFAULT_WEEK_OF_QUARTER)))
            .andExpect(jsonPath("$.[*].weekOfYear").value(hasItem(DEFAULT_WEEK_OF_YEAR)))
            .andExpect(jsonPath("$.[*].dayOfQuarter").value(hasItem(DEFAULT_DAY_OF_QUARTER)))
            .andExpect(jsonPath("$.[*].dayOfYear").value(hasItem(DEFAULT_DAY_OF_YEAR)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get the calendar
        restCalendarMockMvc
            .perform(get(ENTITY_API_URL_ID, calendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendar.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.calenderDate").value(DEFAULT_CALENDER_DATE.toString()))
            .andExpect(jsonPath("$.calenderYear").value(DEFAULT_CALENDER_YEAR))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.monthOfYear").value(DEFAULT_MONTH_OF_YEAR))
            .andExpect(jsonPath("$.weekOfMonth").value(DEFAULT_WEEK_OF_MONTH))
            .andExpect(jsonPath("$.weekOfQuarter").value(DEFAULT_WEEK_OF_QUARTER))
            .andExpect(jsonPath("$.weekOfYear").value(DEFAULT_WEEK_OF_YEAR))
            .andExpect(jsonPath("$.dayOfQuarter").value(DEFAULT_DAY_OF_QUARTER))
            .andExpect(jsonPath("$.dayOfYear").value(DEFAULT_DAY_OF_YEAR))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingCalendar() throws Exception {
        // Get the calendar
        restCalendarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar
        Calendar updatedCalendar = calendarRepository.findById(calendar.getId()).get();
        // Disconnect from session so that the updates on updatedCalendar are not directly saved in db
        em.detach(updatedCalendar);
        updatedCalendar
            .gUID(UPDATED_G_UID)
            .calenderDate(UPDATED_CALENDER_DATE)
            .calenderYear(UPDATED_CALENDER_YEAR)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .monthOfYear(UPDATED_MONTH_OF_YEAR)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .weekOfQuarter(UPDATED_WEEK_OF_QUARTER)
            .weekOfYear(UPDATED_WEEK_OF_YEAR)
            .dayOfQuarter(UPDATED_DAY_OF_QUARTER)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restCalendarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCalendar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCalendar))
            )
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testCalendar.getCalenderDate()).isEqualTo(UPDATED_CALENDER_DATE);
        assertThat(testCalendar.getCalenderYear()).isEqualTo(UPDATED_CALENDER_YEAR);
        assertThat(testCalendar.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testCalendar.getMonthOfYear()).isEqualTo(UPDATED_MONTH_OF_YEAR);
        assertThat(testCalendar.getWeekOfMonth()).isEqualTo(UPDATED_WEEK_OF_MONTH);
        assertThat(testCalendar.getWeekOfQuarter()).isEqualTo(UPDATED_WEEK_OF_QUARTER);
        assertThat(testCalendar.getWeekOfYear()).isEqualTo(UPDATED_WEEK_OF_YEAR);
        assertThat(testCalendar.getDayOfQuarter()).isEqualTo(UPDATED_DAY_OF_QUARTER);
        assertThat(testCalendar.getDayOfYear()).isEqualTo(UPDATED_DAY_OF_YEAR);
        assertThat(testCalendar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCalendar.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCalendar.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCalendar.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalendarWithPatch() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar using partial update
        Calendar partialUpdatedCalendar = new Calendar();
        partialUpdatedCalendar.setId(calendar.getId());

        partialUpdatedCalendar
            .gUID(UPDATED_G_UID)
            .calenderDate(UPDATED_CALENDER_DATE)
            .monthOfYear(UPDATED_MONTH_OF_YEAR)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .weekOfYear(UPDATED_WEEK_OF_YEAR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendar))
            )
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testCalendar.getCalenderDate()).isEqualTo(UPDATED_CALENDER_DATE);
        assertThat(testCalendar.getCalenderYear()).isEqualTo(DEFAULT_CALENDER_YEAR);
        assertThat(testCalendar.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testCalendar.getMonthOfYear()).isEqualTo(UPDATED_MONTH_OF_YEAR);
        assertThat(testCalendar.getWeekOfMonth()).isEqualTo(UPDATED_WEEK_OF_MONTH);
        assertThat(testCalendar.getWeekOfQuarter()).isEqualTo(DEFAULT_WEEK_OF_QUARTER);
        assertThat(testCalendar.getWeekOfYear()).isEqualTo(UPDATED_WEEK_OF_YEAR);
        assertThat(testCalendar.getDayOfQuarter()).isEqualTo(DEFAULT_DAY_OF_QUARTER);
        assertThat(testCalendar.getDayOfYear()).isEqualTo(DEFAULT_DAY_OF_YEAR);
        assertThat(testCalendar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCalendar.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCalendar.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCalendar.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateCalendarWithPatch() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar using partial update
        Calendar partialUpdatedCalendar = new Calendar();
        partialUpdatedCalendar.setId(calendar.getId());

        partialUpdatedCalendar
            .gUID(UPDATED_G_UID)
            .calenderDate(UPDATED_CALENDER_DATE)
            .calenderYear(UPDATED_CALENDER_YEAR)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .monthOfYear(UPDATED_MONTH_OF_YEAR)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .weekOfQuarter(UPDATED_WEEK_OF_QUARTER)
            .weekOfYear(UPDATED_WEEK_OF_YEAR)
            .dayOfQuarter(UPDATED_DAY_OF_QUARTER)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendar))
            )
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testCalendar.getCalenderDate()).isEqualTo(UPDATED_CALENDER_DATE);
        assertThat(testCalendar.getCalenderYear()).isEqualTo(UPDATED_CALENDER_YEAR);
        assertThat(testCalendar.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testCalendar.getMonthOfYear()).isEqualTo(UPDATED_MONTH_OF_YEAR);
        assertThat(testCalendar.getWeekOfMonth()).isEqualTo(UPDATED_WEEK_OF_MONTH);
        assertThat(testCalendar.getWeekOfQuarter()).isEqualTo(UPDATED_WEEK_OF_QUARTER);
        assertThat(testCalendar.getWeekOfYear()).isEqualTo(UPDATED_WEEK_OF_YEAR);
        assertThat(testCalendar.getDayOfQuarter()).isEqualTo(UPDATED_DAY_OF_QUARTER);
        assertThat(testCalendar.getDayOfYear()).isEqualTo(UPDATED_DAY_OF_YEAR);
        assertThat(testCalendar.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCalendar.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCalendar.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCalendar.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calendar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeDelete = calendarRepository.findAll().size();

        // Delete the calendar
        restCalendarMockMvc
            .perform(delete(ENTITY_API_URL_ID, calendar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
