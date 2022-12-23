import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RestaurantTable from './restaurant-table';
import RestaurantTableDetail from './restaurant-table-detail';
import RestaurantTableUpdate from './restaurant-table-update';
import RestaurantTableDeleteDialog from './restaurant-table-delete-dialog';

const RestaurantTableRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RestaurantTable />} />
    <Route path="new" element={<RestaurantTableUpdate />} />
    <Route path=":id">
      <Route index element={<RestaurantTableDetail />} />
      <Route path="edit" element={<RestaurantTableUpdate />} />
      <Route path="delete" element={<RestaurantTableDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RestaurantTableRoutes;
