package com.rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rocks.zipcode.domain.enumeration.StatusTable;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RestaurantTable.
 */
@Entity
@Table(name = "restaurant_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestaurantTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTable status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "foodMenu", "restaurantTables", "reservations" }, allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RestaurantTable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxCapacity() {
        return this.maxCapacity;
    }

    public RestaurantTable maxCapacity(Integer maxCapacity) {
        this.setMaxCapacity(maxCapacity);
        return this;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public StatusTable getStatus() {
        return this.status;
    }

    public RestaurantTable status(StatusTable status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusTable status) {
        this.status = status;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RestaurantTable restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantTable)) {
            return false;
        }
        return id != null && id.equals(((RestaurantTable) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantTable{" +
            "id=" + getId() +
            ", maxCapacity=" + getMaxCapacity() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
