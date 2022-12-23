import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './restaurant.reducer';

export const RestaurantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const restaurantEntity = useAppSelector(state => state.restaurant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="restaurantDetailsHeading">
          <Translate contentKey="reserveItApp.restaurant.detail.title">Restaurant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.id}</dd>
          <dt>
            <span id="restaurantName">
              <Translate contentKey="reserveItApp.restaurant.restaurantName">Restaurant Name</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.restaurantName}</dd>
          <dt>
            <span id="restaurantDescription">
              <Translate contentKey="reserveItApp.restaurant.restaurantDescription">Restaurant Description</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.restaurantDescription}</dd>
          <dt>
            <span id="cuisine">
              <Translate contentKey="reserveItApp.restaurant.cuisine">Cuisine</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.cuisine}</dd>
          <dt>
            <span id="streetAddress">
              <Translate contentKey="reserveItApp.restaurant.streetAddress">Street Address</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.streetAddress}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="reserveItApp.restaurant.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.postalCode}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="reserveItApp.restaurant.city">City</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="reserveItApp.restaurant.state">State</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.state}</dd>
          <dt>
            <Translate contentKey="reserveItApp.restaurant.foodMenu">Food Menu</Translate>
          </dt>
          <dd>{restaurantEntity.foodMenu ? restaurantEntity.foodMenu.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/restaurant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/restaurant/${restaurantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RestaurantDetail;
