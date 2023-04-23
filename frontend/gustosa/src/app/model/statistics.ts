import { Product } from "./product.model"

export interface Statistics{
    processOrders: number,
    totalComments: number,
    finishedOrders: number,
    cartOrders: number,
    topProducts: Array<Product>,
    userEmail: string,
    ratingAVG: number,
    topSales: Array<number>
    users: number
    products: number
}