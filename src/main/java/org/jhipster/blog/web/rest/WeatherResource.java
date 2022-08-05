package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Weather;
import org.jhipster.blog.repository.WeatherRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Weather}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    private static final String ENTITY_NAME = "weather";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeatherRepository weatherRepository;

    public WeatherResource(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * {@code POST  /weathers} : Create a new weather.
     *
     * @param weather the weather to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weather, or with status {@code 400 (Bad Request)} if the weather has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weathers")
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to save Weather : {}", weather);
        if (weather.getId() != null) {
            throw new BadRequestAlertException("A new weather cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Weather result = weatherRepository.save(weather);
        return ResponseEntity
            .created(new URI("/api/weathers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weathers/:id} : Updates an existing weather.
     *
     * @param id the id of the weather to save.
     * @param weather the weather to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weather,
     * or with status {@code 400 (Bad Request)} if the weather is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weather couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weathers/{id}")
    public ResponseEntity<Weather> updateWeather(@PathVariable(value = "id", required = false) final Long id, @RequestBody Weather weather)
        throws URISyntaxException {
        log.debug("REST request to update Weather : {}, {}", id, weather);
        if (weather.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weather.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weatherRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Weather result = weatherRepository.save(weather);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weather.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /weathers/:id} : Partial updates given fields of an existing weather, field will ignore if it is null
     *
     * @param id the id of the weather to save.
     * @param weather the weather to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weather,
     * or with status {@code 400 (Bad Request)} if the weather is not valid,
     * or with status {@code 404 (Not Found)} if the weather is not found,
     * or with status {@code 500 (Internal Server Error)} if the weather couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/weathers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Weather> partialUpdateWeather(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Weather weather
    ) throws URISyntaxException {
        log.debug("REST request to partial update Weather partially : {}, {}", id, weather);
        if (weather.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weather.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weatherRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Weather> result = weatherRepository
            .findById(weather.getId())
            .map(existingWeather -> {
                if (weather.getgUID() != null) {
                    existingWeather.setgUID(weather.getgUID());
                }
                if (weather.getCityID() != null) {
                    existingWeather.setCityID(weather.getCityID());
                }
                if (weather.getStartTimestamp() != null) {
                    existingWeather.setStartTimestamp(weather.getStartTimestamp());
                }
                if (weather.getEndTimestamp() != null) {
                    existingWeather.setEndTimestamp(weather.getEndTimestamp());
                }
                if (weather.getWeatherStatusID() != null) {
                    existingWeather.setWeatherStatusID(weather.getWeatherStatusID());
                }
                if (weather.getTemperature() != null) {
                    existingWeather.setTemperature(weather.getTemperature());
                }
                if (weather.getFeelsLikeTemperature() != null) {
                    existingWeather.setFeelsLikeTemperature(weather.getFeelsLikeTemperature());
                }
                if (weather.getHumidity() != null) {
                    existingWeather.setHumidity(weather.getHumidity());
                }
                if (weather.getWindSpeed() != null) {
                    existingWeather.setWindSpeed(weather.getWindSpeed());
                }
                if (weather.getWindDirection() != null) {
                    existingWeather.setWindDirection(weather.getWindDirection());
                }
                if (weather.getPressureinmmhg() != null) {
                    existingWeather.setPressureinmmhg(weather.getPressureinmmhg());
                }
                if (weather.getVisibilityinmph() != null) {
                    existingWeather.setVisibilityinmph(weather.getVisibilityinmph());
                }
                if (weather.getCloudCover() != null) {
                    existingWeather.setCloudCover(weather.getCloudCover());
                }
                if (weather.getPrecipitation() != null) {
                    existingWeather.setPrecipitation(weather.getPrecipitation());
                }
                if (weather.getCreatedBy() != null) {
                    existingWeather.setCreatedBy(weather.getCreatedBy());
                }
                if (weather.getCreatedOn() != null) {
                    existingWeather.setCreatedOn(weather.getCreatedOn());
                }
                if (weather.getUpdatedBy() != null) {
                    existingWeather.setUpdatedBy(weather.getUpdatedBy());
                }
                if (weather.getUpdatedOn() != null) {
                    existingWeather.setUpdatedOn(weather.getUpdatedOn());
                }

                return existingWeather;
            })
            .map(weatherRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weather.getId().toString())
        );
    }

    /**
     * {@code GET  /weathers} : get all the weathers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weathers in body.
     */
    @GetMapping("/weathers")
    public List<Weather> getAllWeathers() {
        log.debug("REST request to get all Weathers");
        return weatherRepository.findAll();
    }

    /**
     * {@code GET  /weathers/:id} : get the "id" weather.
     *
     * @param id the id of the weather to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weather, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weathers/{id}")
    public ResponseEntity<Weather> getWeather(@PathVariable Long id) {
        log.debug("REST request to get Weather : {}", id);
        Optional<Weather> weather = weatherRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(weather);
    }

    /**
     * {@code DELETE  /weathers/:id} : delete the "id" weather.
     *
     * @param id the id of the weather to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weathers/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        log.debug("REST request to delete Weather : {}", id);
        weatherRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
