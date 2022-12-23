import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRestaurantTable } from 'app/shared/model/restaurant-table.model';
import { getEntities } from './restaurant-table.reducer';

export const RestaurantTable = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const restaurantTableList = useAppSelector(state => state.restaurantTable.entities);
  const loading = useAppSelector(state => state.restaurantTable.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="restaurant-table-heading" data-cy="RestaurantTableHeading">
        <Translate contentKey="reserveItApp.restaurantTable.home.title">Restaurant Tables</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reserveItApp.restaurantTable.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/restaurant-table/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reserveItApp.restaurantTable.home.createLabel">Create new Restaurant Table</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {restaurantTableList && restaurantTableList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reserveItApp.restaurantTable.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.restaurantTable.maxCapacity">Max Capacity</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.restaurantTable.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="reserveItApp.restaurantTable.restaurant">Restaurant</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {restaurantTableList.map((restaurantTable, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/restaurant-table/${restaurantTable.id}`} color="link" size="sm">
                      {restaurantTable.id}
                    </Button>
                  </td>
                  <td>{restaurantTable.maxCapacity}</td>
                  <td>
                    <Translate contentKey={`reserveItApp.StatusTable.${restaurantTable.status}`} />
                  </td>
                  <td>
                    {restaurantTable.restaurant ? (
                      <Link to={`/restaurant/${restaurantTable.restaurant.id}`}>{restaurantTable.restaurant.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/restaurant-table/${restaurantTable.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/restaurant-table/${restaurantTable.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/restaurant-table/${restaurantTable.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="reserveItApp.restaurantTable.home.notFound">No Restaurant Tables found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RestaurantTable;
