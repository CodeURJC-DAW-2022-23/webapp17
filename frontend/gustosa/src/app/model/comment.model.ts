import { Timestamp } from "rxjs";
import { Product } from "./product.model";
import { UserProfile } from "./user.model";

export interface CommentPackage {
    comments: Comment[];
}

export interface Comment{
    id: number;
    userProfile: UserProfile;
    product: Product;
    rating: number;
    description: string;
    createdAt: Timestamp<any>;
}