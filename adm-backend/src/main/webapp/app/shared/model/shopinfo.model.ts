import { IUser } from 'app/core/user/user.model';

export interface IShopinfo {
    id?: number;
    apiKey?: string;
    password?: string;
    url?: string;
    users?: IUser[];
}

export class Shopinfo implements IShopinfo {
    constructor(public id?: number, public apiKey?: string, public password?: string, public url?: string, public users?: IUser[]) {}
}
