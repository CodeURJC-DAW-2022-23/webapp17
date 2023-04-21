import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Observable } from 'rxjs';
import { Coupon } from 'src/app/model/coupon.model';
import { UserProfile } from 'src/app/model/user.model';
import { CouponService } from 'src/app/service/coupon.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class EditCouponModalComponent {

  bsModalRef: BsModalRef | undefined;
  coupon: Coupon | undefined
  id = ""
  usersList : Observable<Array<UserProfile>>
  form = {}

  constructor(private modalRef: BsModalRef, userService : UserService, private couponsService : CouponService) {
    this.usersList = userService.getNoPaginatedUsers();
  }

  cancel() {
    this.modalRef.hide();
  }

  onSubmit(){
    this.form = {
      "code" : (document.getElementById('code') as HTMLInputElement).value,
      "discount" : (document.getElementById('discount') as HTMLInputElement).value,
      "newUserEmail": (document.getElementById('newUser') as HTMLInputElement).value,
      "uses" : (document.getElementById('uses') as HTMLInputElement).value,
    }
    this.id = (document.getElementById('id') as HTMLInputElement).value;
    this.couponsService.modifyCoupon(this.id, this.form).subscribe(() => {
      this.modalRef.hide();
    })
  }
}