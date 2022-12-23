import { IRestaurant } from 'app/shared/model/restaurant.model';
import { StatusTable } from 'app/shared/model/enumerations/status-table.model';

export interface IRestaurantTable {
  id?: number;
  maxCapacity?: number | null;
  status?: StatusTable | null;
  restaurant?: IRestaurant | null;
}

export const defaultValue: Readonly<IRestaurantTable> = {};
