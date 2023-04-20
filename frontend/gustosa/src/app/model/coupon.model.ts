export interface Coupon{
   
    id : string;
    discount : number;
    code : string;
    usesRemaining : BigInt;
    //image
    userEmail : string;
    secret: boolean;
    hasImage: boolean;
}