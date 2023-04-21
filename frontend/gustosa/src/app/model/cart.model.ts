
import { Product } from "./product.model";
import { Coupon } from "./coupon.model";
import { UserProfile } from "./user.model";

export interface CartPackage {
    cartItems: CartItem[];
    existingCart: boolean;
    couponApplied: boolean;
    cartSize: number;
    totalPrice: number;
    coupon: Coupon;
    couponList: Coupon[];
    user: UserProfile;
    id: number,
    status: number
}

export interface CartItem{
    product: Product;
    quantity: number;
    id: number;
}