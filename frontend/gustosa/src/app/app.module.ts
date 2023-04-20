import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule }
    from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { BsDropdownDirective, BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { PopoverModule } from 'ngx-bootstrap/popover';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './component/app.component';
import { HeaderComponent } from './component/header.component';
import { HomeComponent } from './component/home.component';
import { ProductsComponent } from './component/products.component';
import { LoginComponent } from './component/login.component';
import { LogoutComponent } from './component/logout.component';

import { CouponsAdminComponent } from './component/admin/coupons/coupons-admin.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { EditModalComponent } from './component/admin/coupons/edit-modal.component';
import { SidebarComponent } from './component/admin/sidebar.component';
import { CartComponent } from './component/cart.component';
import { AdminDashboardComponent } from './component/admin/dashboard/admin.component';
import { CreateModalComponent } from './component/admin/coupons/create-modal.component';
import { CheckoutComponent } from './component/checkout.component';
import { DescriptionComponent } from './component/description.component';

@NgModule({
  declarations: [
    AppComponent, HeaderComponent, HomeComponent, ProductsComponent, 
    CouponsAdminComponent, CreateModalComponent, EditModalComponent, SidebarComponent, LoginComponent,
    LogoutComponent, CartComponent, AdminDashboardComponent, CheckoutComponent, DescriptionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ModalModule.forRoot(),
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    PopoverModule.forRoot(),
    HttpClientModule,
    FormsModule
  ],
  providers: [BsDropdownDirective],
  bootstrap: [AppComponent]
})
export class AppModule { }
