import { Moment } from 'moment';

export interface ITracking {
  id?: number;
  trackingNumber?: string;
  trackingCompany?: string;
  trackingUrl?: string;
  paypalTrackerId?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  orderId?: number;
  orderNumber?: number;
}

export class Tracking implements ITracking {
  constructor(
    public id?: number,
    public trackingNumber?: string,
    public trackingCompany?: string,
    public trackingUrl?: string,
    public paypalTrackerId?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public orderId?: number,
    public orderNumber?: number,
  ) {}
}
