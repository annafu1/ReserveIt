package com.rocks.zipcode.repository;

import com.rocks.zipcode.domain.RestaurantTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RestaurantTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {}
