import { Component, OnInit} from '@angular/core';
import { ProductsService } from '../service/products.service';
import { Observable, catchError, map, of, concat, combineLatest } from 'rxjs';

@Component({
  selector: 'products',
  templateUrl: './products.component.html',
  styleUrls: []
})

export class ProductsComponent implements OnInit{

    products: Observable<any>;
    
    constructor(private productsService: ProductsService){
      this.products = this.productsService.getProducts(0) 
    }

    ngOnInit() {
      window.addEventListener('load', () => {
        const spinner = document.getElementById('spinner') as HTMLElement; // Reemplace 'spinner' con el ID de su spinner
        spinner.style.display = 'none';
      });
    }

    moreResults(page: number) {
      //Mostrar spinner
      const spinner = document.getElementById('spinner') as HTMLElement;
      const buttonMoreResults = document.getElementById('buttonMoreResults') as HTMLElement;
      buttonMoreResults.style.display = 'none';
      spinner.style.display = 'block';

      //Combinar productos
      const newProducts = this.productsService.getProducts(page);
      let newArray: any[] = []
      const combinedObservable: Observable<any[]> = combineLatest([this.products, newProducts]);

      combinedObservable.subscribe(([currentData, newData]) => {
        newArray = [...currentData.page.content, ...newData.page.content];
        const updatedData = { ...newData, page: { ...newData.page, content: newArray } };
        this.products = of(updatedData);
        //Esconder spinner
        spinner.style.display = 'none';
        buttonMoreResults.style.display = 'block';

      });
      this.products.subscribe((data) => {
        console.log(data.page.content);
      });

      //Falta testear con dos veces más resultados
      
    }

}