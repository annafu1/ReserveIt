import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { IRestaurantTable } from 'app/shared/model/restaurant-table.model';
import { IReservation } from 'app/shared/model/reservation.model';
import { CuisineType } from 'app/shared/model/enumerations/cuisine-type.model';

export interface IRestaurant {
  id?: number;
  restaurantName?: string | null;
  restaurantDescription?: string | null;
  cuisine?: CuisineType | null;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  state?: string;
  foodMenu?: IFoodMenu | null;
  restaurantTables?: IRestaurantTable[] | null;
  reservations?: IReservation[] | null;
}

export const defaultValue: Readonly<IRestaurant> = {};
