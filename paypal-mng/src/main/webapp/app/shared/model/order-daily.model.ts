import { Moment } from 'moment';

export interface IOrderDaily {
  id?: number;
  name?: string;
  email?: string;
  financialStatus?: string;
  paidAt?: Moment;
  lineItemQuantity?: number;
  lineItemName?: string;
  shipingName?: string;
  shipingAddress?: string;
  shipingStreet?: string;
  shipingAddress2?: string;
  shipingCompany?: string;
  shipingCity?: string;
  shipingZip?: string;
  shipingProvince?: string;
  shipingCountry?: string;
  shipingPhone?: string;
  note?: string;
}

export class OrderDaily implements IOrderDaily {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public financialStatus?: string,
    public paidAt?: Moment,
    public lineItemQuantity?: number,
    public lineItemName?: string,
    public shipingName?: string,
    public shipingAddress?: string,
    public shipingStreet?: string,
    public shipingAddress2?: string,
    public shipingCompany?: string,
    public shipingCity?: string,
    public shipingZip?: string,
    public shipingProvince?: string,
    public shipingCountry?: string,
    public shipingPhone?: string,
    public note?: string
  ) {}
}
