package org.jhipster.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.blog.domain.Recipe;
import org.jhipster.blog.repository.RecipeRepository;
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
 * REST controller for managing {@link org.jhipster.blog.domain.Recipe}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecipeResource {

    private final Logger log = LoggerFactory.getLogger(RecipeResource.class);

    private static final String ENTITY_NAME = "recipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeRepository recipeRepository;

    public RecipeResource(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * {@code POST  /recipes} : Create a new recipe.
     *
     * @param recipe the recipe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipe, or with status {@code 400 (Bad Request)} if the recipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) throws URISyntaxException {
        log.debug("REST request to save Recipe : {}", recipe);
        if (recipe.getId() != null) {
            throw new BadRequestAlertException("A new recipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recipe result = recipeRepository.save(recipe);
        return ResponseEntity
            .created(new URI("/api/recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipes/:id} : Updates an existing recipe.
     *
     * @param id the id of the recipe to save.
     * @param recipe the recipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipe,
     * or with status {@code 400 (Bad Request)} if the recipe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable(value = "id", required = false) final Long id, @RequestBody Recipe recipe)
        throws URISyntaxException {
        log.debug("REST request to update Recipe : {}, {}", id, recipe);
        if (recipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Recipe result = recipeRepository.save(recipe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recipes/:id} : Partial updates given fields of an existing recipe, field will ignore if it is null
     *
     * @param id the id of the recipe to save.
     * @param recipe the recipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipe,
     * or with status {@code 400 (Bad Request)} if the recipe is not valid,
     * or with status {@code 404 (Not Found)} if the recipe is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recipes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Recipe> partialUpdateRecipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Recipe recipe
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recipe partially : {}, {}", id, recipe);
        if (recipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Recipe> result = recipeRepository
            .findById(recipe.getId())
            .map(existingRecipe -> {
                if (recipe.getgUID() != null) {
                    existingRecipe.setgUID(recipe.getgUID());
                }
                if (recipe.getPlantName() != null) {
                    existingRecipe.setPlantName(recipe.getPlantName());
                }
                if (recipe.getRecipeType() != null) {
                    existingRecipe.setRecipeType(recipe.getRecipeType());
                }
                if (recipe.getpHMin() != null) {
                    existingRecipe.setpHMin(recipe.getpHMin());
                }
                if (recipe.getpHMax() != null) {
                    existingRecipe.setpHMax(recipe.getpHMax());
                }
                if (recipe.getEcMin() != null) {
                    existingRecipe.setEcMin(recipe.getEcMin());
                }
                if (recipe.geteCMax() != null) {
                    existingRecipe.seteCMax(recipe.geteCMax());
                }
                if (recipe.getAirTempMax() != null) {
                    existingRecipe.setAirTempMax(recipe.getAirTempMax());
                }
                if (recipe.getAirTempMin() != null) {
                    existingRecipe.setAirTempMin(recipe.getAirTempMin());
                }
                if (recipe.getHumidityMax() != null) {
                    existingRecipe.setHumidityMax(recipe.getHumidityMax());
                }
                if (recipe.getHumidityMin() != null) {
                    existingRecipe.setHumidityMin(recipe.getHumidityMin());
                }
                if (recipe.getNutrientTempMax() != null) {
                    existingRecipe.setNutrientTempMax(recipe.getNutrientTempMax());
                }
                if (recipe.getNutrientTempMin() != null) {
                    existingRecipe.setNutrientTempMin(recipe.getNutrientTempMin());
                }
                if (recipe.getLuxGermMax() != null) {
                    existingRecipe.setLuxGermMax(recipe.getLuxGermMax());
                }
                if (recipe.getLuxGermMin() != null) {
                    existingRecipe.setLuxGermMin(recipe.getLuxGermMin());
                }
                if (recipe.getLightGermDor() != null) {
                    existingRecipe.setLightGermDor(recipe.getLightGermDor());
                }
                if (recipe.getLightGermCycle() != null) {
                    existingRecipe.setLightGermCycle(recipe.getLightGermCycle());
                }
                if (recipe.getLuxGrowMax() != null) {
                    existingRecipe.setLuxGrowMax(recipe.getLuxGrowMax());
                }
                if (recipe.getLuxGrowMin() != null) {
                    existingRecipe.setLuxGrowMin(recipe.getLuxGrowMin());
                }
                if (recipe.getLightGrowDor() != null) {
                    existingRecipe.setLightGrowDor(recipe.getLightGrowDor());
                }
                if (recipe.getLightGrowCycle() != null) {
                    existingRecipe.setLightGrowCycle(recipe.getLightGrowCycle());
                }
                if (recipe.getCo2LightMax() != null) {
                    existingRecipe.setCo2LightMax(recipe.getCo2LightMax());
                }
                if (recipe.getCo2LightMin() != null) {
                    existingRecipe.setCo2LightMin(recipe.getCo2LightMin());
                }
                if (recipe.getCo2DarkMax() != null) {
                    existingRecipe.setCo2DarkMax(recipe.getCo2DarkMax());
                }
                if (recipe.getCo2DarkMin() != null) {
                    existingRecipe.setCo2DarkMin(recipe.getCo2DarkMin());
                }
                if (recipe.getdOMax() != null) {
                    existingRecipe.setdOMax(recipe.getdOMax());
                }
                if (recipe.getdOMin() != null) {
                    existingRecipe.setdOMin(recipe.getdOMin());
                }
                if (recipe.getMediaMoistureMax() != null) {
                    existingRecipe.setMediaMoistureMax(recipe.getMediaMoistureMax());
                }
                if (recipe.getMediaMoistureMin() != null) {
                    existingRecipe.setMediaMoistureMin(recipe.getMediaMoistureMin());
                }
                if (recipe.getNitrogen() != null) {
                    existingRecipe.setNitrogen(recipe.getNitrogen());
                }
                if (recipe.getPhosphorus() != null) {
                    existingRecipe.setPhosphorus(recipe.getPhosphorus());
                }
                if (recipe.getPotassium() != null) {
                    existingRecipe.setPotassium(recipe.getPotassium());
                }
                if (recipe.getSulphur() != null) {
                    existingRecipe.setSulphur(recipe.getSulphur());
                }
                if (recipe.getCalcium() != null) {
                    existingRecipe.setCalcium(recipe.getCalcium());
                }
                if (recipe.getMagnesium() != null) {
                    existingRecipe.setMagnesium(recipe.getMagnesium());
                }
                if (recipe.getManganese() != null) {
                    existingRecipe.setManganese(recipe.getManganese());
                }
                if (recipe.getIron() != null) {
                    existingRecipe.setIron(recipe.getIron());
                }
                if (recipe.getBoron() != null) {
                    existingRecipe.setBoron(recipe.getBoron());
                }
                if (recipe.getCopper() != null) {
                    existingRecipe.setCopper(recipe.getCopper());
                }
                if (recipe.getZinc() != null) {
                    existingRecipe.setZinc(recipe.getZinc());
                }
                if (recipe.getMolybdenum() != null) {
                    existingRecipe.setMolybdenum(recipe.getMolybdenum());
                }
                if (recipe.getGerminationTAT() != null) {
                    existingRecipe.setGerminationTAT(recipe.getGerminationTAT());
                }
                if (recipe.getIdentificationComment() != null) {
                    existingRecipe.setIdentificationComment(recipe.getIdentificationComment());
                }
                if (recipe.getGrowingComment() != null) {
                    existingRecipe.setGrowingComment(recipe.getGrowingComment());
                }
                if (recipe.getUsageComment() != null) {
                    existingRecipe.setUsageComment(recipe.getUsageComment());
                }
                if (recipe.getCreatedBy() != null) {
                    existingRecipe.setCreatedBy(recipe.getCreatedBy());
                }
                if (recipe.getCreatedOn() != null) {
                    existingRecipe.setCreatedOn(recipe.getCreatedOn());
                }
                if (recipe.getUpdatedBy() != null) {
                    existingRecipe.setUpdatedBy(recipe.getUpdatedBy());
                }
                if (recipe.getUpdatedOn() != null) {
                    existingRecipe.setUpdatedOn(recipe.getUpdatedOn());
                }

                return existingRecipe;
            })
            .map(recipeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipe.getId().toString())
        );
    }

    /**
     * {@code GET  /recipes} : get all the recipes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipes in body.
     */
    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        log.debug("REST request to get all Recipes");
        return recipeRepository.findAll();
    }

    /**
     * {@code GET  /recipes/:id} : get the "id" recipe.
     *
     * @param id the id of the recipe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        log.debug("REST request to get Recipe : {}", id);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recipe);
    }

    /**
     * {@code DELETE  /recipes/:id} : delete the "id" recipe.
     *
     * @param id the id of the recipe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        log.debug("REST request to delete Recipe : {}", id);
        recipeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
