import { Component } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ModalComponent } from './modal.component';
import { SidebarComponent } from './sidebar.component';


@Component({
  selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

    bsModalRef: BsModalRef | undefined;
  
    constructor (public modalService: BsModalService){
        
    }
    openModal() {
      this.bsModalRef = this.modalService.show(ModalComponent);
    }
}