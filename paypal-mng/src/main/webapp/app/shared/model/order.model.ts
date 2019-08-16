import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  orderNumber?: number;
  shopifyOrderId?: number;
  storeId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public orderNumber?: number,
    public shopifyOrderId?: number,
    public storeId?: number
  ) {}
}
