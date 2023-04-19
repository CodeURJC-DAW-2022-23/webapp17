import { Page } from "./pageable.model";

export interface ProductsPackage {
    page: Page<Product>;
    currentPage: number;
    moreProducts: boolean;
    recommendedProducts: Product[];
}

export interface Product{
    title: string;
    price: number;
    summary: string;
    id: number;
}