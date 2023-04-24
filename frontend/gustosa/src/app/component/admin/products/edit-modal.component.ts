import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Product } from 'src/app/model/product.model';
import { ProductsService } from 'src/app/service/products.service';

@Component({
  selector: 'modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class EditProductModalComponent {

  bsModalRef: BsModalRef | undefined;
  product: Product | undefined
  id = ""
  form = {}

  constructor(private modalRef: BsModalRef, private productService: ProductsService) {
  }

  hide() {
    this.modalRef.hide();
  }

  onSubmit(){
    this.form = {
      "price" : (document.getElementById('price') as HTMLInputElement).value,
      "name" : (document.getElementById('name') as HTMLInputElement).value,
      "tags": (document.getElementById('tags') as HTMLInputElement).value,
      "description" : (document.getElementById('description') as HTMLInputElement).value,
    }
    this.id = (document.getElementById('id') as HTMLInputElement).value;
    this.productService.modifyProduct(this.id, this.form).subscribe(() => {
      this.modalRef.hide();
    })
  }
  openImage(input : HTMLInputElement, id? : number){
    
    var reader = new FileReader();
    reader.onload = (res)=>{
      
      if(res.target != null && res.target.result != null){
        if(id != null) this.productService.modifyProductImage(id,input.files![0]).subscribe();
      }
    };
    reader.readAsDataURL(input.files![0]);
  }

  
}