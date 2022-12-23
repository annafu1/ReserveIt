package com.rocks.zipcode.service.mapper;

import com.rocks.zipcode.domain.Restaurant;
import com.rocks.zipcode.domain.RestaurantTable;
import com.rocks.zipcode.service.dto.RestaurantDTO;
import com.rocks.zipcode.service.dto.RestaurantTableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RestaurantTable} and its DTO {@link RestaurantTableDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantTableMapper extends EntityMapper<RestaurantTableDTO, RestaurantTable> {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    RestaurantTableDTO toDto(RestaurantTable s);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
