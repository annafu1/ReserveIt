package com.rocks.zipcode.web.rest;

import com.rocks.zipcode.repository.FoodMenuRepository;
import com.rocks.zipcode.service.FoodMenuService;
import com.rocks.zipcode.service.dto.FoodMenuDTO;
import com.rocks.zipcode.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.rocks.zipcode.domain.FoodMenu}.
 */
@RestController
@RequestMapping("/api")
public class FoodMenuResource {

    private final Logger log = LoggerFactory.getLogger(FoodMenuResource.class);

    private static final String ENTITY_NAME = "foodMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodMenuService foodMenuService;

    private final FoodMenuRepository foodMenuRepository;

    public FoodMenuResource(FoodMenuService foodMenuService, FoodMenuRepository foodMenuRepository) {
        this.foodMenuService = foodMenuService;
        this.foodMenuRepository = foodMenuRepository;
    }

    /**
     * {@code POST  /food-menus} : Create a new foodMenu.
     *
     * @param foodMenuDTO the foodMenuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodMenuDTO, or with status {@code 400 (Bad Request)} if the foodMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/food-menus")
    public ResponseEntity<FoodMenuDTO> createFoodMenu(@RequestBody FoodMenuDTO foodMenuDTO) throws URISyntaxException {
        log.debug("REST request to save FoodMenu : {}", foodMenuDTO);
        if (foodMenuDTO.getId() != null) {
            throw new BadRequestAlertException("A new foodMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodMenuDTO result = foodMenuService.save(foodMenuDTO);
        return ResponseEntity
            .created(new URI("/api/food-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-menus/:id} : Updates an existing foodMenu.
     *
     * @param id the id of the foodMenuDTO to save.
     * @param foodMenuDTO the foodMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodMenuDTO,
     * or with status {@code 400 (Bad Request)} if the foodMenuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/food-menus/{id}")
    public ResponseEntity<FoodMenuDTO> updateFoodMenu(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoodMenuDTO foodMenuDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FoodMenu : {}, {}", id, foodMenuDTO);
        if (foodMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodMenuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoodMenuDTO result = foodMenuService.update(foodMenuDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodMenuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /food-menus/:id} : Partial updates given fields of an existing foodMenu, field will ignore if it is null
     *
     * @param id the id of the foodMenuDTO to save.
     * @param foodMenuDTO the foodMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodMenuDTO,
     * or with status {@code 400 (Bad Request)} if the foodMenuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the foodMenuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the foodMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/food-menus/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FoodMenuDTO> partialUpdateFoodMenu(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoodMenuDTO foodMenuDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FoodMenu partially : {}, {}", id, foodMenuDTO);
        if (foodMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodMenuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoodMenuDTO> result = foodMenuService.partialUpdate(foodMenuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodMenuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /food-menus} : get all the foodMenus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodMenus in body.
     */
    @GetMapping("/food-menus")
    public List<FoodMenuDTO> getAllFoodMenus() {
        log.debug("REST request to get all FoodMenus");
        return foodMenuService.findAll();
    }

    /**
     * {@code GET  /food-menus/:id} : get the "id" foodMenu.
     *
     * @param id the id of the foodMenuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodMenuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/food-menus/{id}")
    public ResponseEntity<FoodMenuDTO> getFoodMenu(@PathVariable Long id) {
        log.debug("REST request to get FoodMenu : {}", id);
        Optional<FoodMenuDTO> foodMenuDTO = foodMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodMenuDTO);
    }

    /**
     * {@code DELETE  /food-menus/:id} : delete the "id" foodMenu.
     *
     * @param id the id of the foodMenuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/food-menus/{id}")
    public ResponseEntity<Void> deleteFoodMenu(@PathVariable Long id) {
        log.debug("REST request to delete FoodMenu : {}", id);
        foodMenuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
