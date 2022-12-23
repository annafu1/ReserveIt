package com.rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rocks.zipcode.IntegrationTest;
import com.rocks.zipcode.domain.FoodMenu;
import com.rocks.zipcode.domain.enumeration.FoodMenuItem;
import com.rocks.zipcode.repository.FoodMenuRepository;
import com.rocks.zipcode.service.dto.FoodMenuDTO;
import com.rocks.zipcode.service.mapper.FoodMenuMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FoodMenuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FoodMenuResourceIT {

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final FoodMenuItem DEFAULT_FOOD_MENU_ITEM = FoodMenuItem.Lunch;
    private static final FoodMenuItem UPDATED_FOOD_MENU_ITEM = FoodMenuItem.Dinner;

    private static final Integer DEFAULT_QUANTITY_OF_ITEM = 1;
    private static final Integer UPDATED_QUANTITY_OF_ITEM = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_ITEM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/food-menus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoodMenuRepository foodMenuRepository;

    @Autowired
    private FoodMenuMapper foodMenuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodMenuMockMvc;

    private FoodMenu foodMenu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodMenu createEntity(EntityManager em) {
        FoodMenu foodMenu = new FoodMenu()
            .itemName(DEFAULT_ITEM_NAME)
            .foodMenuItem(DEFAULT_FOOD_MENU_ITEM)
            .quantityOfItem(DEFAULT_QUANTITY_OF_ITEM)
            .price(DEFAULT_PRICE)
            .itemDescription(DEFAULT_ITEM_DESCRIPTION);
        return foodMenu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodMenu createUpdatedEntity(EntityManager em) {
        FoodMenu foodMenu = new FoodMenu()
            .itemName(UPDATED_ITEM_NAME)
            .foodMenuItem(UPDATED_FOOD_MENU_ITEM)
            .quantityOfItem(UPDATED_QUANTITY_OF_ITEM)
            .price(UPDATED_PRICE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION);
        return foodMenu;
    }

    @BeforeEach
    public void initTest() {
        foodMenu = createEntity(em);
    }

    @Test
    @Transactional
    void createFoodMenu() throws Exception {
        int databaseSizeBeforeCreate = foodMenuRepository.findAll().size();
        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);
        restFoodMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeCreate + 1);
        FoodMenu testFoodMenu = foodMenuList.get(foodMenuList.size() - 1);
        assertThat(testFoodMenu.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testFoodMenu.getFoodMenuItem()).isEqualTo(DEFAULT_FOOD_MENU_ITEM);
        assertThat(testFoodMenu.getQuantityOfItem()).isEqualTo(DEFAULT_QUANTITY_OF_ITEM);
        assertThat(testFoodMenu.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFoodMenu.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFoodMenuWithExistingId() throws Exception {
        // Create the FoodMenu with an existing ID
        foodMenu.setId(1L);
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        int databaseSizeBeforeCreate = foodMenuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodMenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFoodMenus() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        // Get all the foodMenuList
        restFoodMenuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].foodMenuItem").value(hasItem(DEFAULT_FOOD_MENU_ITEM.toString())))
            .andExpect(jsonPath("$.[*].quantityOfItem").value(hasItem(DEFAULT_QUANTITY_OF_ITEM)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getFoodMenu() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        // Get the foodMenu
        restFoodMenuMockMvc
            .perform(get(ENTITY_API_URL_ID, foodMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foodMenu.getId().intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.foodMenuItem").value(DEFAULT_FOOD_MENU_ITEM.toString()))
            .andExpect(jsonPath("$.quantityOfItem").value(DEFAULT_QUANTITY_OF_ITEM))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFoodMenu() throws Exception {
        // Get the foodMenu
        restFoodMenuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFoodMenu() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();

        // Update the foodMenu
        FoodMenu updatedFoodMenu = foodMenuRepository.findById(foodMenu.getId()).get();
        // Disconnect from session so that the updates on updatedFoodMenu are not directly saved in db
        em.detach(updatedFoodMenu);
        updatedFoodMenu
            .itemName(UPDATED_ITEM_NAME)
            .foodMenuItem(UPDATED_FOOD_MENU_ITEM)
            .quantityOfItem(UPDATED_QUANTITY_OF_ITEM)
            .price(UPDATED_PRICE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION);
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(updatedFoodMenu);

        restFoodMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodMenuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodMenuDTO))
            )
            .andExpect(status().isOk());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
        FoodMenu testFoodMenu = foodMenuList.get(foodMenuList.size() - 1);
        assertThat(testFoodMenu.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testFoodMenu.getFoodMenuItem()).isEqualTo(UPDATED_FOOD_MENU_ITEM);
        assertThat(testFoodMenu.getQuantityOfItem()).isEqualTo(UPDATED_QUANTITY_OF_ITEM);
        assertThat(testFoodMenu.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFoodMenu.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();
        foodMenu.setId(count.incrementAndGet());

        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodMenuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();
        foodMenu.setId(count.incrementAndGet());

        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();
        foodMenu.setId(count.incrementAndGet());

        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodMenuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoodMenuWithPatch() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();

        // Update the foodMenu using partial update
        FoodMenu partialUpdatedFoodMenu = new FoodMenu();
        partialUpdatedFoodMenu.setId(foodMenu.getId());

        partialUpdatedFoodMenu.foodMenuItem(UPDATED_FOOD_MENU_ITEM).itemDescription(UPDATED_ITEM_DESCRIPTION);

        restFoodMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodMenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodMenu))
            )
            .andExpect(status().isOk());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
        FoodMenu testFoodMenu = foodMenuList.get(foodMenuList.size() - 1);
        assertThat(testFoodMenu.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testFoodMenu.getFoodMenuItem()).isEqualTo(UPDATED_FOOD_MENU_ITEM);
        assertThat(testFoodMenu.getQuantityOfItem()).isEqualTo(DEFAULT_QUANTITY_OF_ITEM);
        assertThat(testFoodMenu.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFoodMenu.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFoodMenuWithPatch() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();

        // Update the foodMenu using partial update
        FoodMenu partialUpdatedFoodMenu = new FoodMenu();
        partialUpdatedFoodMenu.setId(foodMenu.getId());

        partialUpdatedFoodMenu
            .itemName(UPDATED_ITEM_NAME)
            .foodMenuItem(UPDATED_FOOD_MENU_ITEM)
            .quantityOfItem(UPDATED_QUANTITY_OF_ITEM)
            .price(UPDATED_PRICE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION);

        restFoodMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodMenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodMenu))
            )
            .andExpect(status().isOk());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
        FoodMenu testFoodMenu = foodMenuList.get(foodMenuList.size() - 1);
        assertThat(testFoodMenu.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testFoodMenu.getFoodMenuItem()).isEqualTo(UPDATED_FOOD_MENU_ITEM);
        assertThat(testFoodMenu.getQuantityOfItem()).isEqualTo(UPDATED_QUANTITY_OF_ITEM);
        assertThat(testFoodMenu.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFoodMenu.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();
        foodMenu.setId(count.incrementAndGet());

        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foodMenuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();
        foodMenu.setId(count.incrementAndGet());

        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodMenuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();
        foodMenu.setId(count.incrementAndGet());

        // Create the FoodMenu
        FoodMenuDTO foodMenuDTO = foodMenuMapper.toDto(foodMenu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(foodMenuDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoodMenu() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        int databaseSizeBeforeDelete = foodMenuRepository.findAll().size();

        // Delete the foodMenu
        restFoodMenuMockMvc
            .perform(delete(ENTITY_API_URL_ID, foodMenu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
