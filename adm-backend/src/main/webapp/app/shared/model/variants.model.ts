export interface IVariants {
    id?: number;
    productId?: number;
    title?: string;
    productTitle?: string;
    cost?: number;
    price?: number;
    mo?: number;
}

export class Variants implements IVariants {
    constructor(
        public id?: number,
        public productId?: number,
        public title?: string,
        public productTitle?: string,
        public cost?: number,
        public price?: number,
        public mo?: number
    ) {}
}
