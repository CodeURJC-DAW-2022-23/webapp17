import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/auth/login.component';
import { HomeComponent } from './component/home.component';
import { LogoutComponent } from './component/auth/logout.component';
import { ProductsComponent } from './component/productsInfo/products.component';
import { CartComponent } from './component/cart/cart.component';
import { DescriptionComponent } from './component/productsInfo/description.component';
import { AdminDashboardComponent } from './component/admin/dashboard/admin.component';
import { CouponsAdminComponent } from './component/admin/coupons/coupons-admin.component';
import { CheckoutComponent } from './component/cart/checkout.component';
import { RegisterComponent } from './component/auth/register.component';
import { ProfileComponent } from './component/user/profile.component';
import { OrderComponent } from './component/order/order.component';
import { MenuComponent } from './component/menu/menu.component';
import { BookingFormComponent } from './component/book/bookingform.component';
import { CommentsAdminComponent } from './component/admin/comments/comments-admin.component';
import { ProductsAdminComponent } from './component/admin/products/products-admin.component';
import { UsersAdminComponent } from './component/admin/users/users-admin.component';
import { BookingsAdminComponent } from './component/admin/bookings/bookings-admin.component';
import { OrdersAdminComponent } from './component/admin/orders/orders-admin.component';


const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "logout", component: LogoutComponent},
  {path: "user", component: ProfileComponent},
  {path: "products", component: ProductsComponent},
  {path: "cart", component: CartComponent},
  {path: "description/:id", component: DescriptionComponent},
  {path: "", component: HomeComponent},
  {path: "admin/dashboard", component: AdminDashboardComponent},
  {path: "admin/coupons", component: CouponsAdminComponent},
  {path: "checkout", component: CheckoutComponent},
  {path: "order/:id", component: OrderComponent},
  {path: "menu", component: MenuComponent},
  {path: "book", component: BookingFormComponent},
  {path: "admin/comments", component: CommentsAdminComponent},
  {path: "admin/products", component: ProductsAdminComponent},
  {path: "admin/users", component: UsersAdminComponent},
  {path: "admin/bookings", component: BookingsAdminComponent},
  {path: "admin/orders", component: OrdersAdminComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
