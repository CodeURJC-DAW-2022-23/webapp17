import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Coupon } from 'src/app/model/coupon.model';

@Component({
  selector: 'modal',
  templateUrl: './create-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class CreateModalComponent {

  bsModalRef: BsModalRef | undefined;
  coupon: Coupon | undefined

  constructor(public modalRef: BsModalRef) {
  }

  cancel() {
    this.modalRef.hide();
  }
}