import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IRestaurantTable } from 'app/shared/model/restaurant-table.model';
import { StatusTable } from 'app/shared/model/enumerations/status-table.model';
import { getEntity, updateEntity, createEntity, reset } from './restaurant-table.reducer';

export const RestaurantTableUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const restaurants = useAppSelector(state => state.restaurant.entities);
  const restaurantTableEntity = useAppSelector(state => state.restaurantTable.entity);
  const loading = useAppSelector(state => state.restaurantTable.loading);
  const updating = useAppSelector(state => state.restaurantTable.updating);
  const updateSuccess = useAppSelector(state => state.restaurantTable.updateSuccess);
  const statusTableValues = Object.keys(StatusTable);

  const handleClose = () => {
    navigate('/restaurant-table');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRestaurants({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...restaurantTableEntity,
      ...values,
      restaurant: restaurants.find(it => it.id.toString() === values.restaurant.toString()),
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
          status: 'Open',
          ...restaurantTableEntity,
          restaurant: restaurantTableEntity?.restaurant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reserveItApp.restaurantTable.home.createOrEditLabel" data-cy="RestaurantTableCreateUpdateHeading">
            <Translate contentKey="reserveItApp.restaurantTable.home.createOrEditLabel">Create or edit a RestaurantTable</Translate>
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
                  id="restaurant-table-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reserveItApp.restaurantTable.maxCapacity')}
                id="restaurant-table-maxCapacity"
                name="maxCapacity"
                data-cy="maxCapacity"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.restaurantTable.status')}
                id="restaurant-table-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {statusTableValues.map(statusTable => (
                  <option value={statusTable} key={statusTable}>
                    {translate('reserveItApp.StatusTable.' + statusTable)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="restaurant-table-restaurant"
                name="restaurant"
                data-cy="restaurant"
                label={translate('reserveItApp.restaurantTable.restaurant')}
                type="select"
              >
                <option value="" key="0" />
                {restaurants
                  ? restaurants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/restaurant-table" replace color="info">
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

export default RestaurantTableUpdate;
