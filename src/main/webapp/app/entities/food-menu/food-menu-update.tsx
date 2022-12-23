import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { getEntity, updateEntity, createEntity, reset } from './food-menu.reducer';

export const FoodMenuUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const foodMenuEntity = useAppSelector(state => state.foodMenu.entity);
  const loading = useAppSelector(state => state.foodMenu.loading);
  const updating = useAppSelector(state => state.foodMenu.updating);
  const updateSuccess = useAppSelector(state => state.foodMenu.updateSuccess);

  const handleClose = () => {
    navigate('/food-menu');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...foodMenuEntity,
      ...values,
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
          ...foodMenuEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reserveItApp.foodMenu.home.createOrEditLabel" data-cy="FoodMenuCreateUpdateHeading">
            <Translate contentKey="reserveItApp.foodMenu.home.createOrEditLabel">Create or edit a FoodMenu</Translate>
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
                  id="food-menu-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reserveItApp.foodMenu.itemName')}
                id="food-menu-itemName"
                name="itemName"
                data-cy="itemName"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.foodMenu.foodMenuItem')}
                id="food-menu-foodMenuItem"
                name="foodMenuItem"
                data-cy="foodMenuItem"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.foodMenu.quantityOfItem')}
                id="food-menu-quantityOfItem"
                name="quantityOfItem"
                data-cy="quantityOfItem"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.foodMenu.price')}
                id="food-menu-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('reserveItApp.foodMenu.itemDescription')}
                id="food-menu-itemDescription"
                name="itemDescription"
                data-cy="itemDescription"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/food-menu" replace color="info">
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

export default FoodMenuUpdate;
