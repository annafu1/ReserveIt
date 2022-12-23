import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FoodMenu from './food-menu';
import Restaurant from './restaurant';
import RestaurantTable from './restaurant-table';
import Customer from './customer';
import Reservation from './reservation';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="food-menu/*" element={<FoodMenu />} />
        <Route path="restaurant/*" element={<Restaurant />} />
        <Route path="restaurant-table/*" element={<RestaurantTable />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="reservation/*" element={<Reservation />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
