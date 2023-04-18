import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login.component';
import { HomeComponent } from './component/home.component';
import { LogoutComponent } from './component/logout.component';
import { ProductsComponent } from './component/products.component';
import { CartComponent } from './component/cart.component';
import { DescriptionComponent } from './component/description.component';


const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "logout", component: LogoutComponent},
  {path: "products", component: ProductsComponent},
  {path: "cart", component: CartComponent},
  {path: "description", component: DescriptionComponent},
  {path: "", component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
