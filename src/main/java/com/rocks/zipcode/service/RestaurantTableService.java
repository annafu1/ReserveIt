package com.rocks.zipcode.service;

import com.rocks.zipcode.domain.RestaurantTable;
import com.rocks.zipcode.repository.RestaurantTableRepository;
import com.rocks.zipcode.service.dto.RestaurantTableDTO;
import com.rocks.zipcode.service.mapper.RestaurantTableMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RestaurantTable}.
 */
@Service
@Transactional
public class RestaurantTableService {

    private final Logger log = LoggerFactory.getLogger(RestaurantTableService.class);

    private final RestaurantTableRepository restaurantTableRepository;

    private final RestaurantTableMapper restaurantTableMapper;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository, RestaurantTableMapper restaurantTableMapper) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.restaurantTableMapper = restaurantTableMapper;
    }

    /**
     * Save a restaurantTable.
     *
     * @param restaurantTableDTO the entity to save.
     * @return the persisted entity.
     */
    public RestaurantTableDTO save(RestaurantTableDTO restaurantTableDTO) {
        log.debug("Request to save RestaurantTable : {}", restaurantTableDTO);
        RestaurantTable restaurantTable = restaurantTableMapper.toEntity(restaurantTableDTO);
        restaurantTable = restaurantTableRepository.save(restaurantTable);
        return restaurantTableMapper.toDto(restaurantTable);
    }

    /**
     * Update a restaurantTable.
     *
     * @param restaurantTableDTO the entity to save.
     * @return the persisted entity.
     */
    public RestaurantTableDTO update(RestaurantTableDTO restaurantTableDTO) {
        log.debug("Request to update RestaurantTable : {}", restaurantTableDTO);
        RestaurantTable restaurantTable = restaurantTableMapper.toEntity(restaurantTableDTO);
        restaurantTable = restaurantTableRepository.save(restaurantTable);
        return restaurantTableMapper.toDto(restaurantTable);
    }

    /**
     * Partially update a restaurantTable.
     *
     * @param restaurantTableDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RestaurantTableDTO> partialUpdate(RestaurantTableDTO restaurantTableDTO) {
        log.debug("Request to partially update RestaurantTable : {}", restaurantTableDTO);

        return restaurantTableRepository
            .findById(restaurantTableDTO.getId())
            .map(existingRestaurantTable -> {
                restaurantTableMapper.partialUpdate(existingRestaurantTable, restaurantTableDTO);

                return existingRestaurantTable;
            })
            .map(restaurantTableRepository::save)
            .map(restaurantTableMapper::toDto);
    }

    /**
     * Get all the restaurantTables.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RestaurantTableDTO> findAll() {
        log.debug("Request to get all RestaurantTables");
        return restaurantTableRepository
            .findAll()
            .stream()
            .map(restaurantTableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one restaurantTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RestaurantTableDTO> findOne(Long id) {
        log.debug("Request to get RestaurantTable : {}", id);
        return restaurantTableRepository.findById(id).map(restaurantTableMapper::toDto);
    }

    /**
     * Delete the restaurantTable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RestaurantTable : {}", id);
        restaurantTableRepository.deleteById(id);
    }
}
