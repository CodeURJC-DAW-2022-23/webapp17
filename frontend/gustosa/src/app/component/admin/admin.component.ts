import { Component } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ModalComponent } from './modal.component';
import { Observable } from 'rxjs';
import { Coupon } from 'src/app/model/coupon.model';
import { Page } from 'src/app/model/pageable.model';
import { CouponService } from 'src/app/service/coupon.service';

@Component({
  selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

    bsModalRef: BsModalRef | undefined;
    coupons: Observable<Page<Coupon>>;
  
    constructor (private modalService: BsModalService, private couponService: CouponService){
      this.coupons = this.couponService.getCoupons(0);
    }
    openModal() {
      this.bsModalRef = this.modalService.show(ModalComponent);
    }

    onDelete(couponId: string) {
      this.couponService.deleteCoupon(couponId);
    }
  
}