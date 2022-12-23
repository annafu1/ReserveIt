package com.rocks.zipcode.service.mapper;

import com.rocks.zipcode.domain.Customer;
import com.rocks.zipcode.domain.Reservation;
import com.rocks.zipcode.domain.Restaurant;
import com.rocks.zipcode.service.dto.CustomerDTO;
import com.rocks.zipcode.service.dto.ReservationDTO;
import com.rocks.zipcode.service.dto.RestaurantDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "customers", source = "customers", qualifiedByName = "customerIdSet")
    @Mapping(target = "restaurants", source = "restaurants", qualifiedByName = "restaurantIdSet")
    ReservationDTO toDto(Reservation s);

    @Mapping(target = "removeCustomer", ignore = true)
    @Mapping(target = "removeRestaurant", ignore = true)
    Reservation toEntity(ReservationDTO reservationDTO);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("customerIdSet")
    default Set<CustomerDTO> toDtoCustomerIdSet(Set<Customer> customer) {
        return customer.stream().map(this::toDtoCustomerId).collect(Collectors.toSet());
    }

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);

    @Named("restaurantIdSet")
    default Set<RestaurantDTO> toDtoRestaurantIdSet(Set<Restaurant> restaurant) {
        return restaurant.stream().map(this::toDtoRestaurantId).collect(Collectors.toSet());
    }
}
