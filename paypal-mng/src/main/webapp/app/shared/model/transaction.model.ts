import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  authorization?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  orderId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public authorization?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public orderId?: number
  ) {}
}
