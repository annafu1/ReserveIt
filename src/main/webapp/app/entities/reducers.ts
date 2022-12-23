import foodMenu from 'app/entities/food-menu/food-menu.reducer';
import restaurant from 'app/entities/restaurant/restaurant.reducer';
import restaurantTable from 'app/entities/restaurant-table/restaurant-table.reducer';
import customer from 'app/entities/customer/customer.reducer';
import reservation from 'app/entities/reservation/reservation.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  foodMenu,
  restaurant,
  restaurantTable,
  customer,
  reservation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
