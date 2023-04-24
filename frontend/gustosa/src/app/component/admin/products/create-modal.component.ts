import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Product } from 'src/app/model/product.model';
import { ProductsService } from 'src/app/service/products.service';

@Component({
  selector: 'modal',
  templateUrl: './create-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class CreateProductModalComponent {

  bsModalRef: BsModalRef | undefined;
  product: Product | undefined
  name: string | undefined;
  price: string | undefined
  tags: bigint | undefined
  description: string |undefined
  form = {}

  constructor(private modalRef: BsModalRef, private productService: ProductsService) {
  }

  hide() {
    this.modalRef.hide();
  }

  onSubmit(){
    this.form = {
      "name" : this.name,
      "price" : this.price,
      "tags": this.tags,
      "description" : this.description,
    }
    this.productService.createProduct(this.form).subscribe(() => {
      this.modalRef.hide();
    })
  }

  
}