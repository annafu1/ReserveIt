import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './restaurant-table.reducer';

export const RestaurantTableDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const restaurantTableEntity = useAppSelector(state => state.restaurantTable.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="restaurantTableDetailsHeading">
          <Translate contentKey="reserveItApp.restaurantTable.detail.title">RestaurantTable</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{restaurantTableEntity.id}</dd>
          <dt>
            <span id="isReserved">
              <Translate contentKey="reserveItApp.restaurantTable.isReserved">Is Reserved</Translate>
            </span>
          </dt>
          <dd>{restaurantTableEntity.isReserved ? 'true' : 'false'}</dd>
          <dt>
            <span id="maxCapacity">
              <Translate contentKey="reserveItApp.restaurantTable.maxCapacity">Max Capacity</Translate>
            </span>
          </dt>
          <dd>{restaurantTableEntity.maxCapacity}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="reserveItApp.restaurantTable.status">Status</Translate>
            </span>
          </dt>
          <dd>{restaurantTableEntity.status}</dd>
          <dt>
            <Translate contentKey="reserveItApp.restaurantTable.restaurant">Restaurant</Translate>
          </dt>
          <dd>{restaurantTableEntity.restaurant ? restaurantTableEntity.restaurant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/restaurant-table" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/restaurant-table/${restaurantTableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RestaurantTableDetail;
