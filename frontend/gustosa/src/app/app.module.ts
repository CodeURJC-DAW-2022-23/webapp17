import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule }
    from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CollapseModule } from 'ngx-bootstrap/collapse';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './component/app.component';
import { HeaderComponent } from './component/header.component';
import { HomeComponent } from './component/home.component';
import { ProductsComponent } from './component/products.component';
import { AdminComponent } from './component/admin/admin.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ModalComponent } from './component/admin/modal.component';
import { SidebarComponent } from './component/admin/sidebar.component';

@NgModule({
  declarations: [
    AppComponent, HeaderComponent, HomeComponent, ProductsComponent, AdminComponent, ModalComponent, SidebarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ModalModule.forRoot(),
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
