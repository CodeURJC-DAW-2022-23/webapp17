import { Booking } from "./booking.model";

export interface UserProfile{
    id: number,
    name: string,
    bio: string,
    email: string,
    role: string[],
    cartLength: string,
    bookings: Booking[],
    lastModified: Date
}