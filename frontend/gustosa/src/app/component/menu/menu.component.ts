import { Component } from "@angular/core";
import { Observable } from "rxjs";
import { ProductsService } from "src/app/service/products.service";

@Component({
    selector: 'menu',
    templateUrl: 'menu.component.html',
    styleUrls: []
  })
  export class MenuComponent {
  
    content : Observable<any>;
    constructor(productsService : ProductsService){
        this.content = productsService.getMenu();
    }
  }
  