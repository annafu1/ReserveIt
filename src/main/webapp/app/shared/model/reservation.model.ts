import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IReservation {
  id?: number;
  numberOfPeople?: number | null;
  reservationTime?: string | null;
  reservationDate?: string | null;
  customers?: ICustomer[] | null;
  restaurants?: IRestaurant[] | null;
}

export const defaultValue: Readonly<IReservation> = {};
