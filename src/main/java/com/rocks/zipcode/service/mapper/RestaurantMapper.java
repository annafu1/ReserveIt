package com.rocks.zipcode.service.mapper;

import com.rocks.zipcode.domain.FoodMenu;
import com.rocks.zipcode.domain.Restaurant;
import com.rocks.zipcode.service.dto.FoodMenuDTO;
import com.rocks.zipcode.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {
    @Mapping(target = "foodMenu", source = "foodMenu", qualifiedByName = "foodMenuId")
    RestaurantDTO toDto(Restaurant s);

    @Named("foodMenuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodMenuDTO toDtoFoodMenuId(FoodMenu foodMenu);
}
