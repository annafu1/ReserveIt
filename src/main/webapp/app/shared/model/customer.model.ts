import { IUser } from 'app/shared/model/user.model';
import { IReservation } from 'app/shared/model/reservation.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  phoneNumber?: number | null;
  user?: IUser | null;
  reservations?: IReservation[] | null;
}

export const defaultValue: Readonly<ICustomer> = {};
