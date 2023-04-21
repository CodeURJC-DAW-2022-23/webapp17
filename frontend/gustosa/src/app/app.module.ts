import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule }
    from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { BsDropdownDirective, BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { CarouselModule } from 'ngx-bootstrap/carousel';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './component/app.component';
import { HeaderComponent } from './component/header.component';
import { HomeComponent } from './component/home.component';
import { ProductsComponent } from './component/productsInfo/products.component';
import { LoginComponent } from './component/auth/login.component';
import { LogoutComponent } from './component/auth/logout.component';

import { CouponsAdminComponent } from './component/admin/coupons/coupons-admin.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { EditCouponModalComponent } from './component/admin/coupons/edit-modal.component';
import { SidebarComponent } from './component/admin/sidebar.component';
import { CartComponent } from './component/cart/cart.component';
import { AdminDashboardComponent } from './component/admin/dashboard/admin.component';
import { CreateCouponModalComponent } from './component/admin/coupons/create-modal.component';
import { CheckoutComponent } from './component/cart/checkout.component';
import { DescriptionComponent } from './component/productsInfo/description.component';
import { RegisterComponent } from './component/auth/register.component';
import { ProfileComponent } from './component/user/profile.component';
import { AngularCropperjsModule } from 'angular-cropperjs';
import { LoaderComponent } from './component/common/loader.component';
import { OrderComponent } from './component/order/order.component';
import { MenuComponent } from './component/menu/menu.component';
import { BookingFormComponent } from './component/book/bookingform.component';
import { CommentsAdminComponent } from './component/admin/comments/comments-admin.component';
import { ProductsAdminComponent } from './component/admin/products/products-admin.component';
import { CreateProductModalComponent } from './component/admin/products/create-modal.component';
import { EditProductModalComponent } from './component/admin/products/edit-modal.component';

@NgModule({
  declarations: [
    AppComponent, HeaderComponent, HomeComponent, ProductsComponent, 
    CouponsAdminComponent, CreateCouponModalComponent, EditCouponModalComponent, SidebarComponent, LoginComponent,
    LogoutComponent, CartComponent, AdminDashboardComponent, CheckoutComponent, DescriptionComponent,
    SidebarComponent, LoginComponent, LogoutComponent,
    RegisterComponent, ProfileComponent, LoaderComponent, OrderComponent, MenuComponent, BookingFormComponent,
    CommentsAdminComponent, ProductsAdminComponent, CreateProductModalComponent, EditProductModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CarouselModule.forRoot(),
    ModalModule.forRoot(),
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    PopoverModule.forRoot(),
    HttpClientModule,
    AngularCropperjsModule,
    FormsModule
  ],
  providers: [BsDropdownDirective],
  bootstrap: [AppComponent]
})
export class AppModule { }
