import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { getEntities } from './food-menu.reducer';

export const FoodMenu = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const foodMenuList = useAppSelector(state => state.foodMenu.entities);
  const loading = useAppSelector(state => state.foodMenu.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="food-menu-heading" data-cy="FoodMenuHeading">
        <Translate contentKey="reserveItApp.foodMenu.home.title">Food Menus</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reserveItApp.foodMenu.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/food-menu/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reserveItApp.foodMenu.home.createLabel">Create new Food Menu</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {foodMenuList && foodMenuList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reserveItApp.foodMenu.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.foodMenu.itemName">Item Name</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.foodMenu.foodMenuItem">Food Menu Item</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.foodMenu.quantityOfItem">Quantity Of Item</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.foodMenu.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.foodMenu.itemDescription">Item Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {foodMenuList.map((foodMenu, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/food-menu/${foodMenu.id}`} color="link" size="sm">
                      {foodMenu.id}
                    </Button>
                  </td>
                  <td>{foodMenu.itemName}</td>
                  <td>{foodMenu.foodMenuItem}</td>
                  <td>{foodMenu.quantityOfItem}</td>
                  <td>{foodMenu.price}</td>
                  <td>{foodMenu.itemDescription}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/food-menu/${foodMenu.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/food-menu/${foodMenu.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/food-menu/${foodMenu.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="reserveItApp.foodMenu.home.notFound">No Food Menus found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FoodMenu;
