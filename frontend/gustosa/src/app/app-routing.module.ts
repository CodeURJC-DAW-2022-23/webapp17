import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/auth/login.component';
import { HomeComponent } from './component/home.component';
import { LogoutComponent } from './component/auth/logout.component';
import { ProductsComponent } from './component/products.component';
import { CartComponent } from './component/cart.component';
import { DescriptionComponent } from './component/description.component';
import { AdminDashboardComponent } from './component/admin/dashboard/admin.component';
import { CouponsAdminComponent } from './component/admin/coupons/coupons-admin.component';
import { CheckoutComponent } from './component/checkout.component';
import { RegisterComponent } from './component/auth/register.component';
import { ProfileComponent } from './component/user/profile.component';
import { OrderComponent } from './component/order/order.component';
import { MenuComponent } from './component/menu/menu.component';
import { BookingFormComponent } from './component/book/bookingform.component';


const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "logout", component: LogoutComponent},
  {path: "user", component: ProfileComponent},
  {path: "products", component: ProductsComponent},
  {path: "cart", component: CartComponent},
  {path: "description", component: DescriptionComponent},
  {path: "", component: HomeComponent},
  {path: "admin/dashboard", component: AdminDashboardComponent},
  {path: "admin/coupons", component: CouponsAdminComponent},
  {path: "checkout", component: CheckoutComponent},
  {path: "order/:id", component: OrderComponent},
  {path: "menu", component: MenuComponent},
  {path: "book", component: BookingFormComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
