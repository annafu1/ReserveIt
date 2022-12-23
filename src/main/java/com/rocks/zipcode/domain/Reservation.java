package com.rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "reservation_time")
    private ZonedDateTime reservationTime;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @ManyToMany
    @JoinTable(
        name = "rel_reservation__customer",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "reservations" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_reservation__restaurant",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "foodMenu", "restaurantTables", "reservations" }, allowSetters = true)
    private Set<Restaurant> restaurants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfPeople() {
        return this.numberOfPeople;
    }

    public Reservation numberOfPeople(Integer numberOfPeople) {
        this.setNumberOfPeople(numberOfPeople);
        return this;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public ZonedDateTime getReservationTime() {
        return this.reservationTime;
    }

    public Reservation reservationTime(ZonedDateTime reservationTime) {
        this.setReservationTime(reservationTime);
        return this;
    }

    public void setReservationTime(ZonedDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public LocalDate getReservationDate() {
        return this.reservationDate;
    }

    public Reservation reservationDate(LocalDate reservationDate) {
        this.setReservationDate(reservationDate);
        return this;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Reservation customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Reservation addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.getReservations().add(this);
        return this;
    }

    public Reservation removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.getReservations().remove(this);
        return this;
    }

    public Set<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Reservation restaurants(Set<Restaurant> restaurants) {
        this.setRestaurants(restaurants);
        return this;
    }

    public Reservation addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        restaurant.getReservations().add(this);
        return this;
    }

    public Reservation removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
        restaurant.getReservations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", numberOfPeople=" + getNumberOfPeople() +
            ", reservationTime='" + getReservationTime() + "'" +
            ", reservationDate='" + getReservationDate() + "'" +
            "}";
    }
}
