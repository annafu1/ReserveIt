package com.rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rocks.zipcode.domain.enumeration.CuisineType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_description")
    private String restaurantDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine")
    private CuisineType cuisine;

    @NotNull
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @ManyToOne
    @JsonIgnoreProperties(value = { "restaurants" }, allowSetters = true)
    private FoodMenu foodMenu;

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "restaurant" }, allowSetters = true)
    private Set<RestaurantTable> restaurantTables = new HashSet<>();

    @ManyToMany(mappedBy = "restaurants")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers", "restaurants" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restaurant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public Restaurant restaurantName(String restaurantName) {
        this.setRestaurantName(restaurantName);
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDescription() {
        return this.restaurantDescription;
    }

    public Restaurant restaurantDescription(String restaurantDescription) {
        this.setRestaurantDescription(restaurantDescription);
        return this;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    public CuisineType getCuisine() {
        return this.cuisine;
    }

    public Restaurant cuisine(CuisineType cuisine) {
        this.setCuisine(cuisine);
        return this;
    }

    public void setCuisine(CuisineType cuisine) {
        this.cuisine = cuisine;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Restaurant streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Restaurant postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public Restaurant city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Restaurant state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public FoodMenu getFoodMenu() {
        return this.foodMenu;
    }

    public void setFoodMenu(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    public Restaurant foodMenu(FoodMenu foodMenu) {
        this.setFoodMenu(foodMenu);
        return this;
    }

    public Set<RestaurantTable> getRestaurantTables() {
        return this.restaurantTables;
    }

    public void setRestaurantTables(Set<RestaurantTable> restaurantTables) {
        if (this.restaurantTables != null) {
            this.restaurantTables.forEach(i -> i.setRestaurant(null));
        }
        if (restaurantTables != null) {
            restaurantTables.forEach(i -> i.setRestaurant(this));
        }
        this.restaurantTables = restaurantTables;
    }

    public Restaurant restaurantTables(Set<RestaurantTable> restaurantTables) {
        this.setRestaurantTables(restaurantTables);
        return this;
    }

    public Restaurant addRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTables.add(restaurantTable);
        restaurantTable.setRestaurant(this);
        return this;
    }

    public Restaurant removeRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTables.remove(restaurantTable);
        restaurantTable.setRestaurant(null);
        return this;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.removeRestaurant(this));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.addRestaurant(this));
        }
        this.reservations = reservations;
    }

    public Restaurant reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public Restaurant addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.getRestaurants().add(this);
        return this;
    }

    public Restaurant removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.getRestaurants().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", restaurantDescription='" + getRestaurantDescription() + "'" +
            ", cuisine='" + getCuisine() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
