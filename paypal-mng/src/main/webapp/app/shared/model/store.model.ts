import { Moment } from 'moment';

export interface IStore {
  id?: number;
  shopifyApiKey?: string;
  shopifyApiPassword?: string;
  storeName?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  shopifyApiUrl?: string;
  automationStatus?: boolean;
  sinceId?: number;
  paypalId?: number;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public shopifyApiKey?: string,
    public shopifyApiPassword?: string,
    public storeName?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public shopifyApiUrl?: string,
    public automationStatus?: boolean,
    public sinceId?: number,
    public paypalId?: number
  ) {
    this.automationStatus = this.automationStatus || false;
  }
}
