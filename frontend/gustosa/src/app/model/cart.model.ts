
import { Product } from "./product.model";
import { Coupon } from "./coupon.model";

export interface CartPackage {
    cartItems: CartItem[];
    existingCart: boolean;
    couponApplied: boolean;
    cartSize: number;
    totalPrice: number;
    coupon: Coupon;
}

export interface CartItem{
    product: Product;
    quantity: number;
    id: number;
}