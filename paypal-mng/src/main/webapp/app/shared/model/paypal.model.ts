import { Moment } from 'moment';

export interface IPaypal {
  id?: number;
  client_id?: string;
  secret?: string;
  created_at?: Moment;
  updated_at?: Moment;
}

export class Paypal implements IPaypal {
  constructor(
    public id?: number,
    public client_id?: string,
    public secret?: string,
    public created_at?: Moment,
    public updated_at?: Moment
  ) {}
}
