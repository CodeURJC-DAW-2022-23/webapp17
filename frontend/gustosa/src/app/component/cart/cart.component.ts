import { Component } from '@angular/core';
import { CartService } from '../../service/cart.service';
import { Observable, catchError, map, of } from 'rxjs';
import { SessionService } from '../../service/session.service';
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
        //console.log(data.cartItems);
      });
  }
  
  decreaseQuantity(id: number){
    this.cartService.lessQuantity(id).subscribe(() => {
      this.cart = this.cartService.getCart();
    });;
  }

  increaseQuantity(id:number){
    this.cartService.moreQuantity(id).subscribe(() => {
      this.cart = this.cartService.getCart();
    });;
  }
    
  changeCoupon(code: string): void {
    const redeemText = document.getElementById('redeemText') as HTMLInputElement;
    redeemText.value = code;
  }


  redeemCode(): void {
    const redeemText = document.getElementById('redeemText') as HTMLInputElement;
    console.log(redeemText.value);
    this.cartService.redeemCoupon(redeemText.value).subscribe(() => {
      this.cart = this.cartService.getCart();
    });;
  }


  unredeemCode(): void {
    this.cartService.unredeemCoupon().subscribe(() => {
      this.cart = this.cartService.getCart();
    });;
  }

  redeemCode1(): void {
    const elemento = document.getElementById("redeemedCoupon");
    if (elemento) {
        this.unredeemCode();
    }
    setTimeout(() => {
      // Código que se ejecutará después de dos segundos
      this.redeemCode();
    }, 500);
  }


}

