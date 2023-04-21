import { Component } from '@angular/core';
import { CartService } from '../../service/cart.service';
import { Observable, catchError, map, of } from 'rxjs';
import { SessionService } from '../../service/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'checkout',
  templateUrl: './checkout.component.html',
  styleUrls: []
})

export class CheckoutComponent {

  checkout: Observable<any>;
    
  constructor(private router:Router, private cartService : CartService, private sessionService : SessionService){
      this.checkout = cartService.getCart();
      this.checkout.subscribe((data) => {
      });
  }

  verifyFields(): boolean {
    const requiredFields = document.querySelectorAll('[required]') as NodeListOf<HTMLInputElement>;
    for (let i = 0; i < requiredFields.length; i++) {
      if (!requiredFields[i].value) {
        alert('Por favor, rellena todos los campos requeridos.');
        return false;
      }
    }
    this.confirmOrder();
    return true;
  }

  confirmOrder(){
    this.cartService.doCheckout().subscribe(() => {
      this.router.navigate(['/user']); // la ruta debe ser la que apunte a la página de inicio en tu aplicación
    });;
  }

}