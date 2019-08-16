import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  authorization?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  shopifyTransactionId?: number;
  orderId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public authorization?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public shopifyTransactionId?: number,
    public orderId?: number
  ) {}
}
