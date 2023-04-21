import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../service/products.service';
import { Observable, catchError, map, of } from 'rxjs';
import { SessionService } from '../service/session.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'description',
  templateUrl: './description.component.html',
  styleUrls: []
})

export class DescriptionComponent{

  id: number;
  description: Observable<any>;
    
  constructor(private productsService: ProductsService, private sessionService: SessionService, private route: ActivatedRoute) {
    this.id = +this.route.snapshot.paramMap.get('id')!; // Asignación segura de tipo
    console.log(this.id)
    this.description = this.productsService.getIndividualProduct(this.id);
  }
  
}
