import { Component } from '@angular/core';
import { CartService } from '../service/cart.service';
import { Observable, catchError, map, of } from 'rxjs';
import { SessionService } from '../service/session.service';

@Component({
  selector: 'cart',
  templateUrl: './cart.component.html',
  styleUrls: []
})

export class CartComponent {

  cart: Observable<any>;
    
  constructor(private cartService : CartService, private sessionService : SessionService){
      this.cart = cartService.getCart();
      this.cart.subscribe((data) => {
        console.log(data.cartItems);
      });
  }
  
    

}