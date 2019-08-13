import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  authorization?: string;
  created_at?: Moment;
  updated_at?: Moment;
  orderId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public authorization?: string,
    public created_at?: Moment,
    public updated_at?: Moment,
    public orderId?: number
  ) {}
}
