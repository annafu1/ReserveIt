package com.rocks.zipcode.service;

import com.rocks.zipcode.domain.FoodMenu;
import com.rocks.zipcode.repository.FoodMenuRepository;
import com.rocks.zipcode.service.dto.FoodMenuDTO;
import com.rocks.zipcode.service.mapper.FoodMenuMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FoodMenu}.
 */
@Service
@Transactional
public class FoodMenuService {

    private final Logger log = LoggerFactory.getLogger(FoodMenuService.class);

    private final FoodMenuRepository foodMenuRepository;

    private final FoodMenuMapper foodMenuMapper;

    public FoodMenuService(FoodMenuRepository foodMenuRepository, FoodMenuMapper foodMenuMapper) {
        this.foodMenuRepository = foodMenuRepository;
        this.foodMenuMapper = foodMenuMapper;
    }

    /**
     * Save a foodMenu.
     *
     * @param foodMenuDTO the entity to save.
     * @return the persisted entity.
     */
    public FoodMenuDTO save(FoodMenuDTO foodMenuDTO) {
        log.debug("Request to save FoodMenu : {}", foodMenuDTO);
        FoodMenu foodMenu = foodMenuMapper.toEntity(foodMenuDTO);
        foodMenu = foodMenuRepository.save(foodMenu);
        return foodMenuMapper.toDto(foodMenu);
    }

    /**
     * Update a foodMenu.
     *
     * @param foodMenuDTO the entity to save.
     * @return the persisted entity.
     */
    public FoodMenuDTO update(FoodMenuDTO foodMenuDTO) {
        log.debug("Request to update FoodMenu : {}", foodMenuDTO);
        FoodMenu foodMenu = foodMenuMapper.toEntity(foodMenuDTO);
        foodMenu = foodMenuRepository.save(foodMenu);
        return foodMenuMapper.toDto(foodMenu);
    }

    /**
     * Partially update a foodMenu.
     *
     * @param foodMenuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FoodMenuDTO> partialUpdate(FoodMenuDTO foodMenuDTO) {
        log.debug("Request to partially update FoodMenu : {}", foodMenuDTO);

        return foodMenuRepository
            .findById(foodMenuDTO.getId())
            .map(existingFoodMenu -> {
                foodMenuMapper.partialUpdate(existingFoodMenu, foodMenuDTO);

                return existingFoodMenu;
            })
            .map(foodMenuRepository::save)
            .map(foodMenuMapper::toDto);
    }

    /**
     * Get all the foodMenus.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoodMenuDTO> findAll() {
        log.debug("Request to get all FoodMenus");
        return foodMenuRepository.findAll().stream().map(foodMenuMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one foodMenu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoodMenuDTO> findOne(Long id) {
        log.debug("Request to get FoodMenu : {}", id);
        return foodMenuRepository.findById(id).map(foodMenuMapper::toDto);
    }

    /**
     * Delete the foodMenu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoodMenu : {}", id);
        foodMenuRepository.deleteById(id);
    }
}
