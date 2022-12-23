package com.rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.rocks.zipcode.domain.FoodMenu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FoodMenuDTO implements Serializable {

    private Long id;

    private String itemName;

    private String foodMenuItem;

    private Integer quantityOfItem;

    private Integer price;

    private String itemDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFoodMenuItem() {
        return foodMenuItem;
    }

    public void setFoodMenuItem(String foodMenuItem) {
        this.foodMenuItem = foodMenuItem;
    }

    public Integer getQuantityOfItem() {
        return quantityOfItem;
    }

    public void setQuantityOfItem(Integer quantityOfItem) {
        this.quantityOfItem = quantityOfItem;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodMenuDTO)) {
            return false;
        }

        FoodMenuDTO foodMenuDTO = (FoodMenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, foodMenuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodMenuDTO{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", foodMenuItem='" + getFoodMenuItem() + "'" +
            ", quantityOfItem=" + getQuantityOfItem() +
            ", price=" + getPrice() +
            ", itemDescription='" + getItemDescription() + "'" +
            "}";
    }
}
