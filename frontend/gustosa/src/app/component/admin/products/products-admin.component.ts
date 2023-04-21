import { Component } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { EditProductModalComponent } from './edit-modal.component';
import { CreateProductModalComponent } from './create-modal.component';
import { Observable } from 'rxjs';
import { ProductsService } from 'src/app/service/products.service';
import { Product, ProductsPackage } from 'src/app/model/product.model';

@Component({
  selector: 'admin',
  templateUrl: './products-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class ProductsAdminComponent {

    bsModalRef: BsModalRef | undefined;
    products: Observable<ProductsPackage>;
    currentPage = 1;
  
    constructor (private modalService: BsModalService, private productService: ProductsService){
      this.products = this.productService.getProducts(0);
    }

    onEdit(product: Product) {
      this.bsModalRef = this.modalService.show(EditProductModalComponent, {initialState : { product: product }})
      this.bsModalRef.onHide?.subscribe(() => {
        this.products = this.productService.getProducts((this.currentPage - 1))
      });
    }

    onDelete(productId: number) {
      this.productService.deleteProduct(productId.toString()).subscribe(() => {
        this.products = this.productService.getProducts((this.currentPage - 1))
      });
    }

    onCreate(){
      this.bsModalRef = this.modalService.show(CreateProductModalComponent);
      this.bsModalRef.onHide?.subscribe(() => {
        this.products = this.productService.getProducts((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
      }
      this.products = this.productService.getProducts((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
      }
      this.products = this.productService.getProducts(this.currentPage - 1)
    }
  
}