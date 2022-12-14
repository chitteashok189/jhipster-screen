package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Person;
import org.jhipster.blog.repository.PersonRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Person}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonRepository personRepository;

    public PersonResource(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * {@code POST  /people} : Create a new person.
     *
     * @param person the person to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new person, or with status {@code 400 (Bad Request)} if the person has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Person result = personRepository.save(person);
        return ResponseEntity
            .created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /people/:id} : Updates an existing person.
     *
     * @param id the id of the person to save.
     * @param person the person to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated person,
     * or with status {@code 400 (Bad Request)} if the person is not valid,
     * or with status {@code 500 (Internal Server Error)} if the person couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id", required = false) final Long id, @RequestBody Person person)
        throws URISyntaxException {
        log.debug("REST request to update Person : {}, {}", id, person);
        if (person.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, person.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Person result = personRepository.save(person);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, person.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /people/:id} : Partial updates given fields of an existing person, field will ignore if it is null
     *
     * @param id the id of the person to save.
     * @param person the person to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated person,
     * or with status {@code 400 (Bad Request)} if the person is not valid,
     * or with status {@code 404 (Not Found)} if the person is not found,
     * or with status {@code 500 (Internal Server Error)} if the person couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/people/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Person> partialUpdatePerson(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Person person
    ) throws URISyntaxException {
        log.debug("REST request to partial update Person partially : {}, {}", id, person);
        if (person.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, person.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Person> result = personRepository
            .findById(person.getId())
            .map(existingPerson -> {
                if (person.getgUID() != null) {
                    existingPerson.setgUID(person.getgUID());
                }
                if (person.getSalutation() != null) {
                    existingPerson.setSalutation(person.getSalutation());
                }
                if (person.getFirstName() != null) {
                    existingPerson.setFirstName(person.getFirstName());
                }
                if (person.getMiddleName() != null) {
                    existingPerson.setMiddleName(person.getMiddleName());
                }
                if (person.getLastName() != null) {
                    existingPerson.setLastName(person.getLastName());
                }
                if (person.getPersonalTitle() != null) {
                    existingPerson.setPersonalTitle(person.getPersonalTitle());
                }
                if (person.getSuffix() != null) {
                    existingPerson.setSuffix(person.getSuffix());
                }
                if (person.getNickName() != null) {
                    existingPerson.setNickName(person.getNickName());
                }
                if (person.getFirstNameLocal() != null) {
                    existingPerson.setFirstNameLocal(person.getFirstNameLocal());
                }
                if (person.getMiddleNameLocal() != null) {
                    existingPerson.setMiddleNameLocal(person.getMiddleNameLocal());
                }
                if (person.getLastNameLocal() != null) {
                    existingPerson.setLastNameLocal(person.getLastNameLocal());
                }
                if (person.getOtherLocal() != null) {
                    existingPerson.setOtherLocal(person.getOtherLocal());
                }
                if (person.getGender() != null) {
                    existingPerson.setGender(person.getGender());
                }
                if (person.getBirthDate() != null) {
                    existingPerson.setBirthDate(person.getBirthDate());
                }
                if (person.getHeigth() != null) {
                    existingPerson.setHeigth(person.getHeigth());
                }
                if (person.getWeight() != null) {
                    existingPerson.setWeight(person.getWeight());
                }
                if (person.getMothersMaidenName() != null) {
                    existingPerson.setMothersMaidenName(person.getMothersMaidenName());
                }
                if (person.getMaritialStatus() != null) {
                    existingPerson.setMaritialStatus(person.getMaritialStatus());
                }
                if (person.getSocialSecurityNo() != null) {
                    existingPerson.setSocialSecurityNo(person.getSocialSecurityNo());
                }
                if (person.getPassportNo() != null) {
                    existingPerson.setPassportNo(person.getPassportNo());
                }
                if (person.getPassportExpiryDate() != null) {
                    existingPerson.setPassportExpiryDate(person.getPassportExpiryDate());
                }
                if (person.getTotalYearsWorkExperience() != null) {
                    existingPerson.setTotalYearsWorkExperience(person.getTotalYearsWorkExperience());
                }
                if (person.getComments() != null) {
                    existingPerson.setComments(person.getComments());
                }
                if (person.getOccupation() != null) {
                    existingPerson.setOccupation(person.getOccupation());
                }
                if (person.getYearswithEmployer() != null) {
                    existingPerson.setYearswithEmployer(person.getYearswithEmployer());
                }
                if (person.getMonthsWithEmployer() != null) {
                    existingPerson.setMonthsWithEmployer(person.getMonthsWithEmployer());
                }
                if (person.getExistingCustomer() != null) {
                    existingPerson.setExistingCustomer(person.getExistingCustomer());
                }
                if (person.getCreatedBy() != null) {
                    existingPerson.setCreatedBy(person.getCreatedBy());
                }
                if (person.getCreatedOn() != null) {
                    existingPerson.setCreatedOn(person.getCreatedOn());
                }
                if (person.getUpdatedBy() != null) {
                    existingPerson.setUpdatedBy(person.getUpdatedBy());
                }
                if (person.getUpdatedOn() != null) {
                    existingPerson.setUpdatedOn(person.getUpdatedOn());
                }

                return existingPerson;
            })
            .map(personRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, person.getId().toString())
        );
    }

    /**
     * {@code GET  /people} : get all the people.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("/people")
    public List<Person> getAllPeople() {
        log.debug("REST request to get all People");
        return personRepository.findAll();
    }

    /**
     * {@code GET  /people/:id} : get the "id" person.
     *
     * @param id the id of the person to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the person, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Optional<Person> person = personRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(person);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" person.
     *
     * @param id the id of the person to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
