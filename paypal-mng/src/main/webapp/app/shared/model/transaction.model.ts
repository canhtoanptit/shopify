import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  authorization?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  transactionId?: number;
  orderId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public authorization?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public transactionId?: number,
    public orderId?: number
  ) {}
}
