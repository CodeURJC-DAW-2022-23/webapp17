import { Component } from '@angular/core';
import { CartService } from '../service/cart.service';
import { Observable, catchError, map, of } from 'rxjs';
import { SessionService } from '../service/session.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

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
  
  decreaseQuantity(id: number){
    this.cartService.lessQuantity(id);
  }

  increaseQuantity(id:number){
    this.cartService.moreQuantity(id);
  }
    
  changeCoupon(code: string): void {
    const redeemText = document.getElementById('redeemText') as HTMLInputElement;
    redeemText.value = code;
  }



redeemCode(): void {
  const redeemText = document.getElementById("redeemText") as HTMLInputElement;
  const code = redeemText.value;
  this.cartService.redeemCoupon(code);
  }


unredeemCode(): void {
  const redeemText = document.getElementById("redeemText") as HTMLInputElement;
  const code = redeemText.value;
  this.cartService.unredeemCoupon();
  }

  redeemCode1(): void {
    const elemento = document.getElementById("redeemedCoupon");
    if (elemento) {
        this.unredeemCode();
    }
    setTimeout(() => {
      // Código que se ejecutará después de dos segundos
      this.redeemCode();
    }, 2000);
  }


}

