package com.rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rocks.zipcode.IntegrationTest;
import com.rocks.zipcode.domain.RestaurantTable;
import com.rocks.zipcode.domain.enumeration.StatusTable;
import com.rocks.zipcode.repository.RestaurantTableRepository;
import com.rocks.zipcode.service.dto.RestaurantTableDTO;
import com.rocks.zipcode.service.mapper.RestaurantTableMapper;
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
 * Integration tests for the {@link RestaurantTableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestaurantTableResourceIT {

    private static final Boolean DEFAULT_IS_RESERVED = false;
    private static final Boolean UPDATED_IS_RESERVED = true;

    private static final Integer DEFAULT_MAX_CAPACITY = 1;
    private static final Integer UPDATED_MAX_CAPACITY = 2;

    private static final StatusTable DEFAULT_STATUS = StatusTable.Open;
    private static final StatusTable UPDATED_STATUS = StatusTable.In_progress;

    private static final String ENTITY_API_URL = "/api/restaurant-tables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private RestaurantTableMapper restaurantTableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestaurantTableMockMvc;

    private RestaurantTable restaurantTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestaurantTable createEntity(EntityManager em) {
        RestaurantTable restaurantTable = new RestaurantTable()
            .isReserved(DEFAULT_IS_RESERVED)
            .maxCapacity(DEFAULT_MAX_CAPACITY)
            .status(DEFAULT_STATUS);
        return restaurantTable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestaurantTable createUpdatedEntity(EntityManager em) {
        RestaurantTable restaurantTable = new RestaurantTable()
            .isReserved(UPDATED_IS_RESERVED)
            .maxCapacity(UPDATED_MAX_CAPACITY)
            .status(UPDATED_STATUS);
        return restaurantTable;
    }

    @BeforeEach
    public void initTest() {
        restaurantTable = createEntity(em);
    }

    @Test
    @Transactional
    void createRestaurantTable() throws Exception {
        int databaseSizeBeforeCreate = restaurantTableRepository.findAll().size();
        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);
        restRestaurantTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeCreate + 1);
        RestaurantTable testRestaurantTable = restaurantTableList.get(restaurantTableList.size() - 1);
        assertThat(testRestaurantTable.getIsReserved()).isEqualTo(DEFAULT_IS_RESERVED);
        assertThat(testRestaurantTable.getMaxCapacity()).isEqualTo(DEFAULT_MAX_CAPACITY);
        assertThat(testRestaurantTable.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createRestaurantTableWithExistingId() throws Exception {
        // Create the RestaurantTable with an existing ID
        restaurantTable.setId(1L);
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        int databaseSizeBeforeCreate = restaurantTableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRestaurantTables() throws Exception {
        // Initialize the database
        restaurantTableRepository.saveAndFlush(restaurantTable);

        // Get all the restaurantTableList
        restRestaurantTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurantTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].isReserved").value(hasItem(DEFAULT_IS_RESERVED.booleanValue())))
            .andExpect(jsonPath("$.[*].maxCapacity").value(hasItem(DEFAULT_MAX_CAPACITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getRestaurantTable() throws Exception {
        // Initialize the database
        restaurantTableRepository.saveAndFlush(restaurantTable);

        // Get the restaurantTable
        restRestaurantTableMockMvc
            .perform(get(ENTITY_API_URL_ID, restaurantTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurantTable.getId().intValue()))
            .andExpect(jsonPath("$.isReserved").value(DEFAULT_IS_RESERVED.booleanValue()))
            .andExpect(jsonPath("$.maxCapacity").value(DEFAULT_MAX_CAPACITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRestaurantTable() throws Exception {
        // Get the restaurantTable
        restRestaurantTableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRestaurantTable() throws Exception {
        // Initialize the database
        restaurantTableRepository.saveAndFlush(restaurantTable);

        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();

        // Update the restaurantTable
        RestaurantTable updatedRestaurantTable = restaurantTableRepository.findById(restaurantTable.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurantTable are not directly saved in db
        em.detach(updatedRestaurantTable);
        updatedRestaurantTable.isReserved(UPDATED_IS_RESERVED).maxCapacity(UPDATED_MAX_CAPACITY).status(UPDATED_STATUS);
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(updatedRestaurantTable);

        restRestaurantTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restaurantTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isOk());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
        RestaurantTable testRestaurantTable = restaurantTableList.get(restaurantTableList.size() - 1);
        assertThat(testRestaurantTable.getIsReserved()).isEqualTo(UPDATED_IS_RESERVED);
        assertThat(testRestaurantTable.getMaxCapacity()).isEqualTo(UPDATED_MAX_CAPACITY);
        assertThat(testRestaurantTable.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRestaurantTable() throws Exception {
        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();
        restaurantTable.setId(count.incrementAndGet());

        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restaurantTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRestaurantTable() throws Exception {
        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();
        restaurantTable.setId(count.incrementAndGet());

        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRestaurantTable() throws Exception {
        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();
        restaurantTable.setId(count.incrementAndGet());

        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantTableMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRestaurantTableWithPatch() throws Exception {
        // Initialize the database
        restaurantTableRepository.saveAndFlush(restaurantTable);

        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();

        // Update the restaurantTable using partial update
        RestaurantTable partialUpdatedRestaurantTable = new RestaurantTable();
        partialUpdatedRestaurantTable.setId(restaurantTable.getId());

        partialUpdatedRestaurantTable.isReserved(UPDATED_IS_RESERVED).maxCapacity(UPDATED_MAX_CAPACITY);

        restRestaurantTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurantTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurantTable))
            )
            .andExpect(status().isOk());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
        RestaurantTable testRestaurantTable = restaurantTableList.get(restaurantTableList.size() - 1);
        assertThat(testRestaurantTable.getIsReserved()).isEqualTo(UPDATED_IS_RESERVED);
        assertThat(testRestaurantTable.getMaxCapacity()).isEqualTo(UPDATED_MAX_CAPACITY);
        assertThat(testRestaurantTable.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRestaurantTableWithPatch() throws Exception {
        // Initialize the database
        restaurantTableRepository.saveAndFlush(restaurantTable);

        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();

        // Update the restaurantTable using partial update
        RestaurantTable partialUpdatedRestaurantTable = new RestaurantTable();
        partialUpdatedRestaurantTable.setId(restaurantTable.getId());

        partialUpdatedRestaurantTable.isReserved(UPDATED_IS_RESERVED).maxCapacity(UPDATED_MAX_CAPACITY).status(UPDATED_STATUS);

        restRestaurantTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurantTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurantTable))
            )
            .andExpect(status().isOk());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
        RestaurantTable testRestaurantTable = restaurantTableList.get(restaurantTableList.size() - 1);
        assertThat(testRestaurantTable.getIsReserved()).isEqualTo(UPDATED_IS_RESERVED);
        assertThat(testRestaurantTable.getMaxCapacity()).isEqualTo(UPDATED_MAX_CAPACITY);
        assertThat(testRestaurantTable.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRestaurantTable() throws Exception {
        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();
        restaurantTable.setId(count.incrementAndGet());

        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restaurantTableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRestaurantTable() throws Exception {
        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();
        restaurantTable.setId(count.incrementAndGet());

        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRestaurantTable() throws Exception {
        int databaseSizeBeforeUpdate = restaurantTableRepository.findAll().size();
        restaurantTable.setId(count.incrementAndGet());

        // Create the RestaurantTable
        RestaurantTableDTO restaurantTableDTO = restaurantTableMapper.toDto(restaurantTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantTableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurantTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RestaurantTable in the database
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRestaurantTable() throws Exception {
        // Initialize the database
        restaurantTableRepository.saveAndFlush(restaurantTable);

        int databaseSizeBeforeDelete = restaurantTableRepository.findAll().size();

        // Delete the restaurantTable
        restRestaurantTableMockMvc
            .perform(delete(ENTITY_API_URL_ID, restaurantTable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
        assertThat(restaurantTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
