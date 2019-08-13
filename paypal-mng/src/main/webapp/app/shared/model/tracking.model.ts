import { Moment } from 'moment';

export interface ITracking {
  id?: number;
  tracking_number?: number;
  tracking_url?: string;
  paypal_tracker_id?: string;
  created_at?: Moment;
  updated_at?: Moment;
  orderId?: number;
}

export class Tracking implements ITracking {
  constructor(
    public id?: number,
    public tracking_number?: number,
    public tracking_url?: string,
    public paypal_tracker_id?: string,
    public created_at?: Moment,
    public updated_at?: Moment,
    public orderId?: number
  ) {}
}
