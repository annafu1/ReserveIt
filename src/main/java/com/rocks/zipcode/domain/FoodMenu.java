package com.rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FoodMenu.
 */
@Entity
@Table(name = "food_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FoodMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "food_menu_item")
    private String foodMenuItem;

    @Column(name = "quantity_of_item")
    private Integer quantityOfItem;

    @Column(name = "price")
    private double price;

    @Column(name = "item_description")
    private String itemDescription;

    @OneToMany(mappedBy = "foodMenu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "foodMenu", "restaurantTables", "reservations" }, allowSetters = true)
    private Set<Restaurant> restaurants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FoodMenu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return this.itemName;
    }

    public FoodMenu itemName(String itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFoodMenuItem() {
        return this.foodMenuItem;
    }

    public FoodMenu foodMenuItem(String foodMenuItem) {
        this.setFoodMenuItem(foodMenuItem);
        return this;
    }

    public void setFoodMenuItem(String foodMenuItem) {
        this.foodMenuItem = foodMenuItem;
    }

    public Integer getQuantityOfItem() {
        return this.quantityOfItem;
    }

    public FoodMenu quantityOfItem(Integer quantityOfItem) {
        this.setQuantityOfItem(quantityOfItem);
        return this;
    }

    public void setQuantityOfItem(Integer quantityOfItem) {
        this.quantityOfItem = quantityOfItem;
    }

    public Double getPrice() {
        return this.price;
    }

    public FoodMenu price(Integer price) {
        this.setPrice(Double.valueOf(price));
        return this;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public FoodMenu itemDescription(String itemDescription) {
        this.setItemDescription(itemDescription);
        return this;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Set<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        if (this.restaurants != null) {
            this.restaurants.forEach(i -> i.setFoodMenu(null));
        }
        if (restaurants != null) {
            restaurants.forEach(i -> i.setFoodMenu(this));
        }
        this.restaurants = restaurants;
    }

    public FoodMenu restaurants(Set<Restaurant> restaurants) {
        this.setRestaurants(restaurants);
        return this;
    }

    public FoodMenu addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        restaurant.setFoodMenu(this);
        return this;
    }

    public FoodMenu removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
        restaurant.setFoodMenu(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodMenu)) {
            return false;
        }
        return id != null && id.equals(((FoodMenu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodMenu{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", foodMenuItem='" + getFoodMenuItem() + "'" +
            ", quantityOfItem=" + getQuantityOfItem() +
            ", price=" + getPrice() +
            ", itemDescription='" + getItemDescription() + "'" +
            "}";
    }
}
