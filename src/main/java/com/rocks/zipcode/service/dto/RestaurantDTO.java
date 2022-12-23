package com.rocks.zipcode.service.dto;

import com.rocks.zipcode.domain.enumeration.CuisineType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.rocks.zipcode.domain.Restaurant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestaurantDTO implements Serializable {

    private Long id;

    private String restaurantName;

    private String restaurantDescription;

    private CuisineType cuisine;

    @NotNull
    private String streetAddress;

    @NotNull
    private String postalCode;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private FoodMenuDTO foodMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    public CuisineType getCuisine() {
        return cuisine;
    }

    public void setCuisine(CuisineType cuisine) {
        this.cuisine = cuisine;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public FoodMenuDTO getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenuDTO foodMenu) {
        this.foodMenu = foodMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantDTO)) {
            return false;
        }

        RestaurantDTO restaurantDTO = (RestaurantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restaurantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + getId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", restaurantDescription='" + getRestaurantDescription() + "'" +
            ", cuisine='" + getCuisine() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", foodMenu=" + getFoodMenu() +
            "}";
    }
}
