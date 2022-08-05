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
import org.jhipster.blog.domain.Person;
import org.jhipster.blog.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_SALUTATION = "AAAAAAAAAA";
    private static final String UPDATED_SALUTATION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONAL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_PERSONAL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIX = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HEIGTH = 1;
    private static final Integer UPDATED_HEIGTH = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final String DEFAULT_MOTHERS_MAIDEN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHERS_MAIDEN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MARITIAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITIAL_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOCIAL_SECURITY_NO = 1;
    private static final Integer UPDATED_SOCIAL_SECURITY_NO = 2;

    private static final String DEFAULT_PASSPORT_NO = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_EXPIRY_DATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_YEARS_WORK_EXPERIENCE = 1;
    private static final Integer UPDATED_TOTAL_YEARS_WORK_EXPERIENCE = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEARSWITH_EMPLOYER = 1;
    private static final Integer UPDATED_YEARSWITH_EMPLOYER = 2;

    private static final Integer DEFAULT_MONTHS_WITH_EMPLOYER = 1;
    private static final Integer UPDATED_MONTHS_WITH_EMPLOYER = 2;

    private static final Integer DEFAULT_EXISTING_CUSTOMER = 1;
    private static final Integer UPDATED_EXISTING_CUSTOMER = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .gUID(DEFAULT_G_UID)
            .salutation(DEFAULT_SALUTATION)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .personalTitle(DEFAULT_PERSONAL_TITLE)
            .suffix(DEFAULT_SUFFIX)
            .nickName(DEFAULT_NICK_NAME)
            .firstNameLocal(DEFAULT_FIRST_NAME_LOCAL)
            .middleNameLocal(DEFAULT_MIDDLE_NAME_LOCAL)
            .lastNameLocal(DEFAULT_LAST_NAME_LOCAL)
            .otherLocal(DEFAULT_OTHER_LOCAL)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .heigth(DEFAULT_HEIGTH)
            .weight(DEFAULT_WEIGHT)
            .mothersMaidenName(DEFAULT_MOTHERS_MAIDEN_NAME)
            .maritialStatus(DEFAULT_MARITIAL_STATUS)
            .socialSecurityNo(DEFAULT_SOCIAL_SECURITY_NO)
            .passportNo(DEFAULT_PASSPORT_NO)
            .passportExpiryDate(DEFAULT_PASSPORT_EXPIRY_DATE)
            .totalYearsWorkExperience(DEFAULT_TOTAL_YEARS_WORK_EXPERIENCE)
            .comments(DEFAULT_COMMENTS)
            .occupation(DEFAULT_OCCUPATION)
            .yearswithEmployer(DEFAULT_YEARSWITH_EMPLOYER)
            .monthsWithEmployer(DEFAULT_MONTHS_WITH_EMPLOYER)
            .existingCustomer(DEFAULT_EXISTING_CUSTOMER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .gUID(UPDATED_G_UID)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .personalTitle(UPDATED_PERSONAL_TITLE)
            .suffix(UPDATED_SUFFIX)
            .nickName(UPDATED_NICK_NAME)
            .firstNameLocal(UPDATED_FIRST_NAME_LOCAL)
            .middleNameLocal(UPDATED_MIDDLE_NAME_LOCAL)
            .lastNameLocal(UPDATED_LAST_NAME_LOCAL)
            .otherLocal(UPDATED_OTHER_LOCAL)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .heigth(UPDATED_HEIGTH)
            .weight(UPDATED_WEIGHT)
            .mothersMaidenName(UPDATED_MOTHERS_MAIDEN_NAME)
            .maritialStatus(UPDATED_MARITIAL_STATUS)
            .socialSecurityNo(UPDATED_SOCIAL_SECURITY_NO)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiryDate(UPDATED_PASSPORT_EXPIRY_DATE)
            .totalYearsWorkExperience(UPDATED_TOTAL_YEARS_WORK_EXPERIENCE)
            .comments(UPDATED_COMMENTS)
            .occupation(UPDATED_OCCUPATION)
            .yearswithEmployer(UPDATED_YEARSWITH_EMPLOYER)
            .monthsWithEmployer(UPDATED_MONTHS_WITH_EMPLOYER)
            .existingCustomer(UPDATED_EXISTING_CUSTOMER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPerson.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPerson.getPersonalTitle()).isEqualTo(DEFAULT_PERSONAL_TITLE);
        assertThat(testPerson.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testPerson.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testPerson.getFirstNameLocal()).isEqualTo(DEFAULT_FIRST_NAME_LOCAL);
        assertThat(testPerson.getMiddleNameLocal()).isEqualTo(DEFAULT_MIDDLE_NAME_LOCAL);
        assertThat(testPerson.getLastNameLocal()).isEqualTo(DEFAULT_LAST_NAME_LOCAL);
        assertThat(testPerson.getOtherLocal()).isEqualTo(DEFAULT_OTHER_LOCAL);
        assertThat(testPerson.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPerson.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPerson.getHeigth()).isEqualTo(DEFAULT_HEIGTH);
        assertThat(testPerson.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testPerson.getMothersMaidenName()).isEqualTo(DEFAULT_MOTHERS_MAIDEN_NAME);
        assertThat(testPerson.getMaritialStatus()).isEqualTo(DEFAULT_MARITIAL_STATUS);
        assertThat(testPerson.getSocialSecurityNo()).isEqualTo(DEFAULT_SOCIAL_SECURITY_NO);
        assertThat(testPerson.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testPerson.getPassportExpiryDate()).isEqualTo(DEFAULT_PASSPORT_EXPIRY_DATE);
        assertThat(testPerson.getTotalYearsWorkExperience()).isEqualTo(DEFAULT_TOTAL_YEARS_WORK_EXPERIENCE);
        assertThat(testPerson.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPerson.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testPerson.getYearswithEmployer()).isEqualTo(DEFAULT_YEARSWITH_EMPLOYER);
        assertThat(testPerson.getMonthsWithEmployer()).isEqualTo(DEFAULT_MONTHS_WITH_EMPLOYER);
        assertThat(testPerson.getExistingCustomer()).isEqualTo(DEFAULT_EXISTING_CUSTOMER);
        assertThat(testPerson.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPerson.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPerson.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPerson.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].personalTitle").value(hasItem(DEFAULT_PERSONAL_TITLE)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME)))
            .andExpect(jsonPath("$.[*].firstNameLocal").value(hasItem(DEFAULT_FIRST_NAME_LOCAL)))
            .andExpect(jsonPath("$.[*].middleNameLocal").value(hasItem(DEFAULT_MIDDLE_NAME_LOCAL)))
            .andExpect(jsonPath("$.[*].lastNameLocal").value(hasItem(DEFAULT_LAST_NAME_LOCAL)))
            .andExpect(jsonPath("$.[*].otherLocal").value(hasItem(DEFAULT_OTHER_LOCAL)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].heigth").value(hasItem(DEFAULT_HEIGTH)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].mothersMaidenName").value(hasItem(DEFAULT_MOTHERS_MAIDEN_NAME)))
            .andExpect(jsonPath("$.[*].maritialStatus").value(hasItem(DEFAULT_MARITIAL_STATUS)))
            .andExpect(jsonPath("$.[*].socialSecurityNo").value(hasItem(DEFAULT_SOCIAL_SECURITY_NO)))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportExpiryDate").value(hasItem(DEFAULT_PASSPORT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].totalYearsWorkExperience").value(hasItem(DEFAULT_TOTAL_YEARS_WORK_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].yearswithEmployer").value(hasItem(DEFAULT_YEARSWITH_EMPLOYER)))
            .andExpect(jsonPath("$.[*].monthsWithEmployer").value(hasItem(DEFAULT_MONTHS_WITH_EMPLOYER)))
            .andExpect(jsonPath("$.[*].existingCustomer").value(hasItem(DEFAULT_EXISTING_CUSTOMER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.personalTitle").value(DEFAULT_PERSONAL_TITLE))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME))
            .andExpect(jsonPath("$.firstNameLocal").value(DEFAULT_FIRST_NAME_LOCAL))
            .andExpect(jsonPath("$.middleNameLocal").value(DEFAULT_MIDDLE_NAME_LOCAL))
            .andExpect(jsonPath("$.lastNameLocal").value(DEFAULT_LAST_NAME_LOCAL))
            .andExpect(jsonPath("$.otherLocal").value(DEFAULT_OTHER_LOCAL))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.heigth").value(DEFAULT_HEIGTH))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.mothersMaidenName").value(DEFAULT_MOTHERS_MAIDEN_NAME))
            .andExpect(jsonPath("$.maritialStatus").value(DEFAULT_MARITIAL_STATUS))
            .andExpect(jsonPath("$.socialSecurityNo").value(DEFAULT_SOCIAL_SECURITY_NO))
            .andExpect(jsonPath("$.passportNo").value(DEFAULT_PASSPORT_NO))
            .andExpect(jsonPath("$.passportExpiryDate").value(DEFAULT_PASSPORT_EXPIRY_DATE))
            .andExpect(jsonPath("$.totalYearsWorkExperience").value(DEFAULT_TOTAL_YEARS_WORK_EXPERIENCE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.yearswithEmployer").value(DEFAULT_YEARSWITH_EMPLOYER))
            .andExpect(jsonPath("$.monthsWithEmployer").value(DEFAULT_MONTHS_WITH_EMPLOYER))
            .andExpect(jsonPath("$.existingCustomer").value(DEFAULT_EXISTING_CUSTOMER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .gUID(UPDATED_G_UID)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .personalTitle(UPDATED_PERSONAL_TITLE)
            .suffix(UPDATED_SUFFIX)
            .nickName(UPDATED_NICK_NAME)
            .firstNameLocal(UPDATED_FIRST_NAME_LOCAL)
            .middleNameLocal(UPDATED_MIDDLE_NAME_LOCAL)
            .lastNameLocal(UPDATED_LAST_NAME_LOCAL)
            .otherLocal(UPDATED_OTHER_LOCAL)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .heigth(UPDATED_HEIGTH)
            .weight(UPDATED_WEIGHT)
            .mothersMaidenName(UPDATED_MOTHERS_MAIDEN_NAME)
            .maritialStatus(UPDATED_MARITIAL_STATUS)
            .socialSecurityNo(UPDATED_SOCIAL_SECURITY_NO)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiryDate(UPDATED_PASSPORT_EXPIRY_DATE)
            .totalYearsWorkExperience(UPDATED_TOTAL_YEARS_WORK_EXPERIENCE)
            .comments(UPDATED_COMMENTS)
            .occupation(UPDATED_OCCUPATION)
            .yearswithEmployer(UPDATED_YEARSWITH_EMPLOYER)
            .monthsWithEmployer(UPDATED_MONTHS_WITH_EMPLOYER)
            .existingCustomer(UPDATED_EXISTING_CUSTOMER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPerson.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPerson.getPersonalTitle()).isEqualTo(UPDATED_PERSONAL_TITLE);
        assertThat(testPerson.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testPerson.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testPerson.getFirstNameLocal()).isEqualTo(UPDATED_FIRST_NAME_LOCAL);
        assertThat(testPerson.getMiddleNameLocal()).isEqualTo(UPDATED_MIDDLE_NAME_LOCAL);
        assertThat(testPerson.getLastNameLocal()).isEqualTo(UPDATED_LAST_NAME_LOCAL);
        assertThat(testPerson.getOtherLocal()).isEqualTo(UPDATED_OTHER_LOCAL);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getHeigth()).isEqualTo(UPDATED_HEIGTH);
        assertThat(testPerson.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPerson.getMothersMaidenName()).isEqualTo(UPDATED_MOTHERS_MAIDEN_NAME);
        assertThat(testPerson.getMaritialStatus()).isEqualTo(UPDATED_MARITIAL_STATUS);
        assertThat(testPerson.getSocialSecurityNo()).isEqualTo(UPDATED_SOCIAL_SECURITY_NO);
        assertThat(testPerson.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testPerson.getPassportExpiryDate()).isEqualTo(UPDATED_PASSPORT_EXPIRY_DATE);
        assertThat(testPerson.getTotalYearsWorkExperience()).isEqualTo(UPDATED_TOTAL_YEARS_WORK_EXPERIENCE);
        assertThat(testPerson.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPerson.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testPerson.getYearswithEmployer()).isEqualTo(UPDATED_YEARSWITH_EMPLOYER);
        assertThat(testPerson.getMonthsWithEmployer()).isEqualTo(UPDATED_MONTHS_WITH_EMPLOYER);
        assertThat(testPerson.getExistingCustomer()).isEqualTo(UPDATED_EXISTING_CUSTOMER);
        assertThat(testPerson.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPerson.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPerson.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPerson.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, person.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .firstName(UPDATED_FIRST_NAME)
            .nickName(UPDATED_NICK_NAME)
            .firstNameLocal(UPDATED_FIRST_NAME_LOCAL)
            .middleNameLocal(UPDATED_MIDDLE_NAME_LOCAL)
            .lastNameLocal(UPDATED_LAST_NAME_LOCAL)
            .otherLocal(UPDATED_OTHER_LOCAL)
            .gender(UPDATED_GENDER)
            .mothersMaidenName(UPDATED_MOTHERS_MAIDEN_NAME)
            .socialSecurityNo(UPDATED_SOCIAL_SECURITY_NO)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiryDate(UPDATED_PASSPORT_EXPIRY_DATE)
            .comments(UPDATED_COMMENTS)
            .monthsWithEmployer(UPDATED_MONTHS_WITH_EMPLOYER)
            .existingCustomer(UPDATED_EXISTING_CUSTOMER)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testPerson.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPerson.getPersonalTitle()).isEqualTo(DEFAULT_PERSONAL_TITLE);
        assertThat(testPerson.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testPerson.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testPerson.getFirstNameLocal()).isEqualTo(UPDATED_FIRST_NAME_LOCAL);
        assertThat(testPerson.getMiddleNameLocal()).isEqualTo(UPDATED_MIDDLE_NAME_LOCAL);
        assertThat(testPerson.getLastNameLocal()).isEqualTo(UPDATED_LAST_NAME_LOCAL);
        assertThat(testPerson.getOtherLocal()).isEqualTo(UPDATED_OTHER_LOCAL);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPerson.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPerson.getHeigth()).isEqualTo(DEFAULT_HEIGTH);
        assertThat(testPerson.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testPerson.getMothersMaidenName()).isEqualTo(UPDATED_MOTHERS_MAIDEN_NAME);
        assertThat(testPerson.getMaritialStatus()).isEqualTo(DEFAULT_MARITIAL_STATUS);
        assertThat(testPerson.getSocialSecurityNo()).isEqualTo(UPDATED_SOCIAL_SECURITY_NO);
        assertThat(testPerson.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testPerson.getPassportExpiryDate()).isEqualTo(UPDATED_PASSPORT_EXPIRY_DATE);
        assertThat(testPerson.getTotalYearsWorkExperience()).isEqualTo(DEFAULT_TOTAL_YEARS_WORK_EXPERIENCE);
        assertThat(testPerson.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPerson.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testPerson.getYearswithEmployer()).isEqualTo(DEFAULT_YEARSWITH_EMPLOYER);
        assertThat(testPerson.getMonthsWithEmployer()).isEqualTo(UPDATED_MONTHS_WITH_EMPLOYER);
        assertThat(testPerson.getExistingCustomer()).isEqualTo(UPDATED_EXISTING_CUSTOMER);
        assertThat(testPerson.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPerson.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPerson.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPerson.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .gUID(UPDATED_G_UID)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .personalTitle(UPDATED_PERSONAL_TITLE)
            .suffix(UPDATED_SUFFIX)
            .nickName(UPDATED_NICK_NAME)
            .firstNameLocal(UPDATED_FIRST_NAME_LOCAL)
            .middleNameLocal(UPDATED_MIDDLE_NAME_LOCAL)
            .lastNameLocal(UPDATED_LAST_NAME_LOCAL)
            .otherLocal(UPDATED_OTHER_LOCAL)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .heigth(UPDATED_HEIGTH)
            .weight(UPDATED_WEIGHT)
            .mothersMaidenName(UPDATED_MOTHERS_MAIDEN_NAME)
            .maritialStatus(UPDATED_MARITIAL_STATUS)
            .socialSecurityNo(UPDATED_SOCIAL_SECURITY_NO)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiryDate(UPDATED_PASSPORT_EXPIRY_DATE)
            .totalYearsWorkExperience(UPDATED_TOTAL_YEARS_WORK_EXPERIENCE)
            .comments(UPDATED_COMMENTS)
            .occupation(UPDATED_OCCUPATION)
            .yearswithEmployer(UPDATED_YEARSWITH_EMPLOYER)
            .monthsWithEmployer(UPDATED_MONTHS_WITH_EMPLOYER)
            .existingCustomer(UPDATED_EXISTING_CUSTOMER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testPerson.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPerson.getPersonalTitle()).isEqualTo(UPDATED_PERSONAL_TITLE);
        assertThat(testPerson.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testPerson.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testPerson.getFirstNameLocal()).isEqualTo(UPDATED_FIRST_NAME_LOCAL);
        assertThat(testPerson.getMiddleNameLocal()).isEqualTo(UPDATED_MIDDLE_NAME_LOCAL);
        assertThat(testPerson.getLastNameLocal()).isEqualTo(UPDATED_LAST_NAME_LOCAL);
        assertThat(testPerson.getOtherLocal()).isEqualTo(UPDATED_OTHER_LOCAL);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getHeigth()).isEqualTo(UPDATED_HEIGTH);
        assertThat(testPerson.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPerson.getMothersMaidenName()).isEqualTo(UPDATED_MOTHERS_MAIDEN_NAME);
        assertThat(testPerson.getMaritialStatus()).isEqualTo(UPDATED_MARITIAL_STATUS);
        assertThat(testPerson.getSocialSecurityNo()).isEqualTo(UPDATED_SOCIAL_SECURITY_NO);
        assertThat(testPerson.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testPerson.getPassportExpiryDate()).isEqualTo(UPDATED_PASSPORT_EXPIRY_DATE);
        assertThat(testPerson.getTotalYearsWorkExperience()).isEqualTo(UPDATED_TOTAL_YEARS_WORK_EXPERIENCE);
        assertThat(testPerson.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPerson.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testPerson.getYearswithEmployer()).isEqualTo(UPDATED_YEARSWITH_EMPLOYER);
        assertThat(testPerson.getMonthsWithEmployer()).isEqualTo(UPDATED_MONTHS_WITH_EMPLOYER);
        assertThat(testPerson.getExistingCustomer()).isEqualTo(UPDATED_EXISTING_CUSTOMER);
        assertThat(testPerson.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPerson.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPerson.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPerson.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, person.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
