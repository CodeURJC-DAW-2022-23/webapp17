import { Component, OnInit} from '@angular/core';
import { ProductsService } from '../service/products.service';
import { Observable, of, combineLatest } from 'rxjs';
import { CartService } from '../service/cart.service';
import { SessionService } from '../service/session.service';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'products',
  templateUrl: './products.component.html',
  styleUrls: [],
  animations: [
    trigger('bgChange', [
      state('start', style({
        backgroundColor: 'white',
        color: 'black'
      })),
      state('end', style({
        backgroundColor: 'rgb(0, 255, 0)',
        color:'black'
      })),
      transition('start => end', animate('500ms ease-in')),
      transition('end => start', animate('500ms ease-out'))
    ])
  ]
})

export class ProductsComponent implements OnInit{
    
    bgState: string = 'start';
    products: Observable<any>;
    
    constructor(private productsService: ProductsService, private cartService : CartService, 
      private sessionService : SessionService){
        this.products = productsService.getProducts(0);
    }

    ngOnInit() {
      window.onload = () => {
        console.log("Hola")
        setTimeout(() => {
          // Código que se ejecutará después de medio segundo
          const spinner = document.getElementById('spinner') as HTMLElement;
          spinner.hidden = true;
        }, 100);
      };
    }

    moreResults(page: number) {
      //Mostrar spinner
      const spinner = document.getElementById('spinner') as HTMLElement;
      const buttonMoreResults = document.getElementById('buttonMoreResults') as HTMLElement;
      buttonMoreResults.style.display = 'none';
      spinner.hidden = false;

      //Combinar productos
      const newProducts = this.productsService.getProducts(page);
      let newArray: any[] = []
      const combinedObservable: Observable<any[]> = combineLatest([this.products, newProducts]);

      combinedObservable.subscribe(([currentData, newData]) => {
        newArray = [...currentData.page.content, ...newData.page.content];
        const updatedData = { ...newData, page: { ...newData.page, content: newArray } };
        this.products = of(updatedData);

        //Esconder spinner
        spinner.hidden = true;
        buttonMoreResults.style.display = 'block';

      });
      this.products.subscribe((data) => {
        console.log(data.page.content);
      });

      //Falta testear con dos veces más resultados
    }
    
    addToCart(event:Event, id : number){
      this.cartService.addToCart(id).subscribe(() => {
        this.sessionService.updateProfile();
        //Si da error irse a login para no hacer changeBgColor
      });
      this.changeBgColor();
    }

    changeBgColor() {
      this.bgState = 'end';
      setTimeout(() => {
        this.bgState = 'start';
      }, 250);
    }

}