import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  order_number?: number;
  created_at?: Moment;
  updated_at?: Moment;
  storeId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public order_number?: number,
    public created_at?: Moment,
    public updated_at?: Moment,
    public storeId?: number
  ) {}
}
