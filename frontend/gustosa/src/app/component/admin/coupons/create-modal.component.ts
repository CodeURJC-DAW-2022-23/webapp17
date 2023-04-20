import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Observable } from 'rxjs';
import { Coupon } from 'src/app/model/coupon.model';
import { UserProfile } from 'src/app/model/user.model';
import { CouponService } from 'src/app/service/coupon.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'modal',
  templateUrl: './create-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class CreateModalComponent {

  bsModalRef: BsModalRef | undefined;
  coupon: Coupon | undefined
  code : string | undefined
  discount: string | undefined
  uses: bigint | undefined
  user: string |undefined
  form = {}
  usersList : Observable<Array<UserProfile>>

  constructor(private modalRef: BsModalRef, userService : UserService, private couponsService : CouponService) {
    this.usersList = userService.getNoPaginatedUsers();
  }

  hide() {
    this.modalRef.hide();
  }

  onSubmit(){
    this.form = {
      "code" : this.code,
      "discount" : this.discount,
      "uses" : this.uses,
      "user": this.user,
    }
    this.couponsService.createCoupon(this.form).subscribe()
  }
}