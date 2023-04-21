import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../service/products.service';
import { Observable, catchError, combineLatest, map, of } from 'rxjs';
import { SessionService } from '../../service/session.service';
import { ActivatedRoute } from '@angular/router';
import { CartService } from 'src/app/service/cart.service';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductsComponent } from './products.component';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'description',
  templateUrl: './description.component.html',
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

export class DescriptionComponent implements OnInit{

  bgState: string = 'start';
  id: number;
  description: Observable<any>;
    
  constructor(private productsService: ProductsService, private cartService: CartService, private sessionService: SessionService, private route: ActivatedRoute) {
    this.id = +this.route.snapshot.paramMap.get('id')!; // Asignación segura de tipo
    //console.log(this.id);
    this.description = productsService.getIndividualProduct(this.id,0);
    this.description.subscribe((data) => {
      //console.log(data);
    });
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

  ngOnInit() {
    window.addEventListener('load', () => {
      const spinner = document.getElementById('spinner') as HTMLElement;
      if (spinner) {
        setTimeout(() => {
          spinner.hidden = true;
        }, 100);
      }
    });
  }

  moreComments(page: number, id:number) {
    //Mostrar spinner
    const spinner = document.getElementById('spinner') as HTMLElement;
    const buttonMoreResults = document.getElementById('buttonMoreResults') as HTMLElement;
    buttonMoreResults.style.display = 'none';
    spinner.hidden = false;

    //Combinar productos
    const newComments = this.productsService.getIndividualProduct(id,page);
    let newArray: any[] = []
    const combinedObservable: Observable<any[]> = combineLatest([this.description, newComments]);

    combinedObservable.subscribe(([currentData, newData]) => {
      newArray = [...currentData.productComments.content, ...newData.productComments.content];
      const updatedData = { ...newData, productComments: { ...newData.productComments, content: newArray } };
      this.description = of(updatedData);

      //Esconder spinner
      spinner.hidden = true;
      buttonMoreResults.style.display = 'block';

    });
    this.description.subscribe((data) => {
      //console.log(data);
    });

    //Falta testear con dos veces más resultados
  }

  addComment(id: number, currentPage:number){
    const selStars = document.querySelector('#selStars') as HTMLSelectElement;
    const contentTextArea = document.querySelector('#contentTextArea') as HTMLTextAreaElement;

    const stars = selStars.value;
    const content = contentTextArea.value;
    //console.log(stars);
    //console.log(content);

    this.productsService.addComent(id, stars, content).subscribe((data) => {
      //console.log(data);
    });
  
    this.description = this.productsService.getIndividualProduct(this.id,0);
    this.description.subscribe((data) => {
      //console.log(data);
    });

  }
}