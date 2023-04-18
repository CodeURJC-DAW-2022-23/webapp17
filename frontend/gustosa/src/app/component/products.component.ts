import { Component } from '@angular/core';
import { ProductsService } from '../service/products.service';
import { Observable, catchError, map, of } from 'rxjs';

@Component({
  selector: 'products',
  templateUrl: './products.component.html',
  styleUrls: []
})

export class ProductsComponent {

    products: Observable<any>;
    
    constructor(private productsService: ProductsService){
        this.products = productsService.getProducts();
    }

}