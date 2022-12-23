import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IFoodMenu {
  id?: number;
  itemName?: string | null;
  foodMenuItem?: string | null;
  quantityOfItem?: number | null;
  price?: number | null;
  itemDescription?: string | null;
  restaurants?: IRestaurant[] | null;
}

export const defaultValue: Readonly<IFoodMenu> = {};
