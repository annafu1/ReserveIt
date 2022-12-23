import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FoodMenu from './food-menu';
import FoodMenuDetail from './food-menu-detail';
import FoodMenuUpdate from './food-menu-update';
import FoodMenuDeleteDialog from './food-menu-delete-dialog';

const FoodMenuRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FoodMenu />} />
    <Route path="new" element={<FoodMenuUpdate />} />
    <Route path=":id">
      <Route index element={<FoodMenuDetail />} />
      <Route path="edit" element={<FoodMenuUpdate />} />
      <Route path="delete" element={<FoodMenuDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FoodMenuRoutes;
