import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservation.reducer';

export const ReservationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reservationEntity = useAppSelector(state => state.reservation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reservationDetailsHeading">
          <Translate contentKey="reserveItApp.reservation.detail.title">Reservation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.id}</dd>
          <dt>
            <span id="numberOfPeople">
              <Translate contentKey="reserveItApp.reservation.numberOfPeople">Number Of People</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.numberOfPeople}</dd>
          <dt>
            <span id="reservationTime">
              <Translate contentKey="reserveItApp.reservation.reservationTime">Reservation Time</Translate>
            </span>
          </dt>
          <dd>
            {reservationEntity.reservationTime ? (
              <TextFormat value={reservationEntity.reservationTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="reservationDate">
              <Translate contentKey="reserveItApp.reservation.reservationDate">Reservation Date</Translate>
            </span>
          </dt>
          <dd>
            {reservationEntity.reservationDate ? (
              <TextFormat value={reservationEntity.reservationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="reserveItApp.reservation.customer">Customer</Translate>
          </dt>
          <dd>
            {reservationEntity.customers
              ? reservationEntity.customers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {reservationEntity.customers && i === reservationEntity.customers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="reserveItApp.reservation.restaurant">Restaurant</Translate>
          </dt>
          <dd>
            {reservationEntity.restaurants
              ? reservationEntity.restaurants.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {reservationEntity.restaurants && i === reservationEntity.restaurants.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/reservation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reservation/${reservationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReservationDetail;
