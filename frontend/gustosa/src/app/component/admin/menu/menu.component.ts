import { Component, TemplateRef } from "@angular/core";
import { BsModalRef, BsModalService } from "ngx-bootstrap/modal";
import { Observable } from "rxjs";
import { ProductsService } from "src/app/service/products.service";


@Component({
    selector: 'menuadmin',
    templateUrl: 'menu.component.html',
    styleUrls: ['./../general-admin-style.css']
  })
  export class AdminMenuComponent {
    
    modalRef?: BsModalRef;
    content : Observable<any>;

    constructor(private modalService : BsModalService, private productService : ProductsService){
        this.content =  productService.getMenu();
    }

    openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    sendMenu(menu : string){
        this.productService.postMenu(menu).subscribe(()=>{
            this.content =  this.productService.getMenu();
            this.modalRef?.hide();
        });
        
    }
  }