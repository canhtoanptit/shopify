import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  orderNumber?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  storeId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderNumber?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public storeId?: number
  ) {}
}
