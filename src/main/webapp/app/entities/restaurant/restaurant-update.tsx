import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { getEntities as getFoodMenus } from 'app/entities/food-menu/food-menu.reducer';
import { IReservation } from 'app/shared/model/reservation.model';
import { getEntities as getReservations } from 'app/entities/reservation/reservation.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { CuisineType } from 'app/shared/model/enumerations/cuisine-type.model';
import { getEntity, updateEntity, createEntity, reset } from './restaurant.reducer';

export const RestaurantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const foodMenus = useAppSelector(state => state.foodMenu.entities);
  const reservations = useAppSelector(state => state.reservation.entities);
  const restaurantEntity = useAppSelector(state => state.restaurant.entity);
  const loading = useAppSelector(state => state.restaurant.loading);
  const updating = useAppSelector(state => state.restaurant.updating);
  const updateSuccess = useAppSelector(state => state.restaurant.updateSuccess);
  const cuisineTypeValues = Object.keys(CuisineType);

  const handleClose = () => {
    navigate('/restaurant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFoodMenus({}));
    dispatch(getReservations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...restaurantEntity,
      ...values,
      foodMenu: foodMenus.find(it => it.id.toString() === values.foodMenu.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          cuisine: 'Mexican',
          ...restaurantEntity,
          foodMenu: restaurantEntity?.foodMenu?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reserveItApp.restaurant.home.createOrEditLabel" data-cy="RestaurantCreateUpdateHeading">
            <Translate contentKey="reserveItApp.restaurant.home.createOrEditLabel">Create or edit a Restaurant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="restaurant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reserveItApp.restaurant.restaurantName')}
                id="restaurant-restaurantName"
                name="restaurantName"
                data-cy="restaurantName"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.restaurant.restaurantDescription')}
                id="restaurant-restaurantDescription"
                name="restaurantDescription"
                data-cy="restaurantDescription"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.restaurant.cuisine')}
                id="restaurant-cuisine"
                name="cuisine"
                data-cy="cuisine"
                type="select"
              >
                {cuisineTypeValues.map(cuisineType => (
                  <option value={cuisineType} key={cuisineType}>
                    {translate('reserveItApp.CuisineType.' + cuisineType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('reserveItApp.restaurant.streetAddress')}
                id="restaurant-streetAddress"
                name="streetAddress"
                data-cy="streetAddress"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('reserveItApp.restaurant.postalCode')}
                id="restaurant-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('reserveItApp.restaurant.city')}
                id="restaurant-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('reserveItApp.restaurant.state')}
                id="restaurant-state"
                name="state"
                data-cy="state"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="restaurant-foodMenu"
                name="foodMenu"
                data-cy="foodMenu"
                label={translate('reserveItApp.restaurant.foodMenu')}
                type="select"
              >
                <option value="" key="0" />
                {foodMenus
                  ? foodMenus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/restaurant" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RestaurantUpdate;
