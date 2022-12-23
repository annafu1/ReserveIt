package com.rocks.zipcode.service.mapper;

import com.rocks.zipcode.domain.FoodMenu;
import com.rocks.zipcode.service.dto.FoodMenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoodMenu} and its DTO {@link FoodMenuDTO}.
 */
@Mapper(componentModel = "spring")
public interface FoodMenuMapper extends EntityMapper<FoodMenuDTO, FoodMenu> {}
