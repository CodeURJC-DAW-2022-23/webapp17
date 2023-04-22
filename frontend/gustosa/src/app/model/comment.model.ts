import { Timestamp } from "rxjs";
import { Product } from "./product.model";
import { UserProfile } from "./user.model";

export interface CommentPackage {
    comments: Comment[];
}

export interface Comment{
    id: number;
    productName: string;
    rating: number;
    description: string;
    userEmail: string;
    date: Date
    userId: number
}