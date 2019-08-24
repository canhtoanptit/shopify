import { Moment } from 'moment';

export interface IPaypalHistory {
  id?: number;
  shopifyOrderId?: number;
  shopifyTrackingNumber?: string;
  shopifyAuthorizationKey?: string;
  shopifyShippingStatus?: string;
  carrier?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  shopifyOrderNumber?: number;
  shopifyOrderName?: string;
}

export class PaypalHistory implements IPaypalHistory {
  constructor(
    public id?: number,
    public shopifyOrderId?: number,
    public shopifyTrackingNumber?: string,
    public shopifyAuthorizationKey?: string,
    public shopifyShippingStatus?: string,
    public carrier?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public shopifyOrderNumber?: number,
    public shopifyOrderName?: string
  ) {}
}
