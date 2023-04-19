import { Page } from "./pageable.model";
import { CartItem } from "./cart.model";
import { Comment } from "./comment.model";

export interface ProductsPackage {
    page: Page<Product>;
    currentPage: number;
    moreProducts: boolean;
    recommendedProducts: Product[];
}

export interface Product{
    title: string;
    price: number;
    description: string;
    id: number;
    comments: Comment[];
    numberOfImages: number;
    cartItems: CartItem[];
    //Imágenes
}

