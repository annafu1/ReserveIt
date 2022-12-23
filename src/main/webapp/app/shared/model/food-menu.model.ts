import { IRestaurant } from 'app/shared/model/restaurant.model';
import { FoodMenuItem } from 'app/shared/model/enumerations/food-menu-item.model';

export interface IFoodMenu {
  id?: number;
  itemName?: string | null;
  foodMenuItem?: FoodMenuItem | null;
  quantityOfItem?: number | null;
  price?: number | null;
  itemDescription?: string | null;
  restaurants?: IRestaurant[] | null;
}

export const defaultValue: Readonly<IFoodMenu> = {};
