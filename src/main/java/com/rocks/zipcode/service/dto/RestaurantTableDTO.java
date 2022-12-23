package com.rocks.zipcode.service.dto;

import com.rocks.zipcode.domain.enumeration.StatusTable;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.rocks.zipcode.domain.RestaurantTable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestaurantTableDTO implements Serializable {

    private Long id;

    private Boolean isReserved;

    private Integer maxCapacity;

    private StatusTable status;

    private RestaurantDTO restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public StatusTable getStatus() {
        return status;
    }

    public void setStatus(StatusTable status) {
        this.status = status;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantTableDTO)) {
            return false;
        }

        RestaurantTableDTO restaurantTableDTO = (RestaurantTableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restaurantTableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantTableDTO{" +
            "id=" + getId() +
            ", isReserved='" + getIsReserved() + "'" +
            ", maxCapacity=" + getMaxCapacity() +
            ", status='" + getStatus() + "'" +
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
