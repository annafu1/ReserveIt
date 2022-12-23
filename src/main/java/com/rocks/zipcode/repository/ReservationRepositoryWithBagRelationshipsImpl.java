package com.rocks.zipcode.repository;

import com.rocks.zipcode.domain.Reservation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ReservationRepositoryWithBagRelationshipsImpl implements ReservationRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reservation> fetchBagRelationships(Optional<Reservation> reservation) {
        return reservation.map(this::fetchCustomers).map(this::fetchRestaurants);
    }

    @Override
    public Page<Reservation> fetchBagRelationships(Page<Reservation> reservations) {
        return new PageImpl<>(
            fetchBagRelationships(reservations.getContent()),
            reservations.getPageable(),
            reservations.getTotalElements()
        );
    }

    @Override
    public List<Reservation> fetchBagRelationships(List<Reservation> reservations) {
        return Optional.of(reservations).map(this::fetchCustomers).map(this::fetchRestaurants).orElse(Collections.emptyList());
    }

    Reservation fetchCustomers(Reservation result) {
        return entityManager
            .createQuery(
                "select reservation from Reservation reservation left join fetch reservation.customers where reservation is :reservation",
                Reservation.class
            )
            .setParameter("reservation", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Reservation> fetchCustomers(List<Reservation> reservations) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, reservations.size()).forEach(index -> order.put(reservations.get(index).getId(), index));
        List<Reservation> result = entityManager
            .createQuery(
                "select distinct reservation from Reservation reservation left join fetch reservation.customers where reservation in :reservations",
                Reservation.class
            )
            .setParameter("reservations", reservations)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Reservation fetchRestaurants(Reservation result) {
        return entityManager
            .createQuery(
                "select reservation from Reservation reservation left join fetch reservation.restaurants where reservation is :reservation",
                Reservation.class
            )
            .setParameter("reservation", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Reservation> fetchRestaurants(List<Reservation> reservations) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, reservations.size()).forEach(index -> order.put(reservations.get(index).getId(), index));
        List<Reservation> result = entityManager
            .createQuery(
                "select distinct reservation from Reservation reservation left join fetch reservation.restaurants where reservation in :reservations",
                Reservation.class
            )
            .setParameter("reservations", reservations)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
