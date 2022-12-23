package com.rocks.zipcode.repository;

import com.rocks.zipcode.domain.FoodMenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FoodMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {}
