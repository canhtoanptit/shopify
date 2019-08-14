import { Moment } from 'moment';

export interface IPaypal {
  id?: number;
  secret?: string;
  clientId?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class Paypal implements IPaypal {
  constructor(public id?: number, public secret?: string, public clientId?: string, public createdAt?: Moment, public updatedAt?: Moment) {}
}
