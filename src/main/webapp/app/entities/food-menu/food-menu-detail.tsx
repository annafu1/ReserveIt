import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './food-menu.reducer';

export const FoodMenuDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const foodMenuEntity = useAppSelector(state => state.foodMenu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="foodMenuDetailsHeading">
          <Translate contentKey="reserveItApp.foodMenu.detail.title">FoodMenu</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{foodMenuEntity.id}</dd>
          <dt>
            <span id="itemName">
              <Translate contentKey="reserveItApp.foodMenu.itemName">Item Name</Translate>
            </span>
          </dt>
          <dd>{foodMenuEntity.itemName}</dd>
          <dt>
            <span id="foodMenuItem">
              <Translate contentKey="reserveItApp.foodMenu.foodMenuItem">Food Menu Item</Translate>
            </span>
          </dt>
          <dd>{foodMenuEntity.foodMenuItem}</dd>
          <dt>
            <span id="quantityOfItem">
              <Translate contentKey="reserveItApp.foodMenu.quantityOfItem">Quantity Of Item</Translate>
            </span>
          </dt>
          <dd>{foodMenuEntity.quantityOfItem}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="reserveItApp.foodMenu.price">Price</Translate>
            </span>
          </dt>
          <dd>{foodMenuEntity.price}</dd>
          <dt>
            <span id="itemDescription">
              <Translate contentKey="reserveItApp.foodMenu.itemDescription">Item Description</Translate>
            </span>
          </dt>
          <dd>{foodMenuEntity.itemDescription}</dd>
        </dl>
        <Button tag={Link} to="/food-menu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/food-menu/${foodMenuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FoodMenuDetail;
