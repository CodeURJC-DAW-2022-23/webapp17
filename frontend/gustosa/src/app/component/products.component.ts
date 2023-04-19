import { Component } from '@angular/core';
import { ProductsService } from '../service/products.service';
import { Observable, catchError, map, of } from 'rxjs';
import { CartService } from '../service/cart.service';
import { SessionService } from '../service/session.service';

@Component({
  selector: 'products',
  templateUrl: './products.component.html',
  styleUrls: []
})

export class ProductsComponent {

    products: Observable<any>;
    
    constructor(private productsService: ProductsService, private cartService : CartService, 
      private sessionService : SessionService){
        this.products = productsService.getProducts();
    }

    addToCart(event:Event, id : number){
      this.cartService.moreQuantity(id).subscribe(() => {
        this.sessionService.updateProfile();
      });
    }

}