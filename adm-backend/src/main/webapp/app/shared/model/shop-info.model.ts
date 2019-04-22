import { IUser } from 'app/core/user/user.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IShopInfo {
    id?: number;
    apiKey?: string;
    password?: string;
    url?: string;
    users?: IUser[];
    products?: IProduct[];
}

export class ShopInfo implements IShopInfo {
    constructor(
        public id?: number,
        public apiKey?: string,
        public password?: string,
        public url?: string,
        public users?: IUser[],
        public products?: IProduct[]
    ) {}
}
