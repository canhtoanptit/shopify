export interface IProduct {
    id?: number;
    name?: string;
    title?: string;
    cost?: number;
    shopInfoId?: number;
}

export class Product implements IProduct {
    constructor(public id?: number, public name?: string, public title?: string, public cost?: number, public shopInfoId?: number) {}
}
