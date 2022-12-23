package com.rocks.zipcode.service.mapper;

import com.rocks.zipcode.domain.Customer;
import com.rocks.zipcode.domain.User;
import com.rocks.zipcode.service.dto.CustomerDTO;
import com.rocks.zipcode.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CustomerDTO toDto(Customer s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
