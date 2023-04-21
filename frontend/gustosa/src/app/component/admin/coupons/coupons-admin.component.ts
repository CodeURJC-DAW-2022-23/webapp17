import { Component } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { EditModalComponent } from './edit-modal.component';
import { CreateModalComponent } from './create-modal.component';
import { Observable } from 'rxjs';
import { Coupon } from 'src/app/model/coupon.model';
import { Page } from 'src/app/model/pageable.model';
import { CouponService } from 'src/app/service/coupon.service';

@Component({
  selector: 'admin',
  templateUrl: './coupons-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class CouponsAdminComponent {

    bsModalRef: BsModalRef | undefined;
    coupons: Observable<Page<Coupon>>;
    currentPage = 1;
  
    constructor (private modalService: BsModalService, private couponService: CouponService){
      this.coupons = this.couponService.getCoupons(0);
    }

    onEdit(coupon: Coupon) {
      this.bsModalRef = this.modalService.show(EditModalComponent, {initialState : { coupon: coupon }})
      this.bsModalRef.onHide?.subscribe(() => {
        this.coupons = this.couponService.getCoupons((this.currentPage - 1))
      });
    }

    onDelete(couponId: string) {
      this.couponService.deleteCoupon(couponId).subscribe(() => {
        this.coupons = this.couponService.getCoupons((this.currentPage - 1))
      });
    }

    onCreate(){
      this.bsModalRef = this.modalService.show(CreateModalComponent);
      this.bsModalRef.onHide?.subscribe(() => {
        this.coupons = this.couponService.getCoupons((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
      }
      this.coupons = this.couponService.getCoupons((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
      }
      this.coupons = this.couponService.getCoupons(this.currentPage - 1)
    }
  
}