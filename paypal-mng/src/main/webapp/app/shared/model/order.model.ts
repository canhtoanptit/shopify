import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  orderNumber?: number;
  shopifyOrderId?: number;
  orderName?: string;
  storeId?: number;
  storeName?: string;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public orderNumber?: number,
    public shopifyOrderId?: number,
    public orderName?: string,
    public storeId?: number,
    public storeName?: string,
  ) {}
}
