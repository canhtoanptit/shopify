import { Moment } from 'moment';

export interface IStore {
  id?: number;
  shopify_api_key?: string;
  shopify_api_password?: string;
  store_name?: string;
  created_at?: Moment;
  updated_at?: Moment;
  paypalId?: number;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public shopify_api_key?: string,
    public shopify_api_password?: string,
    public store_name?: string,
    public created_at?: Moment,
    public updated_at?: Moment,
    public paypalId?: number
  ) {}
}
