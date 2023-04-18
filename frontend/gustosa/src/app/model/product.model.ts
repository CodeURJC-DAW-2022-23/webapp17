import { Page } from "./pageable.model";

export interface ProductsPackage {
    page: Page<Product>;
    recommendedProducts: Product[];
}

export interface Product{
    title: string;
    price: number;
    summary: string;
    id: number;
}