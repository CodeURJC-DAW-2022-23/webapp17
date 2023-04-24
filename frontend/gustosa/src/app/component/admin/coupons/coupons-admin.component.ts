import { Component } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { Observable } from 'rxjs';
import { Coupon } from 'src/app/model/coupon.model';
import { Page } from 'src/app/model/pageable.model';
import { CouponService } from 'src/app/service/coupon.service';
import { CreateCouponModalComponent } from './create-modal.component';
import { EditCouponModalComponent } from './edit-modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'admin',
  templateUrl: './coupons-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class CouponsAdminComponent {

    bsModalRef: BsModalRef | undefined;
    coupons: Observable<Page<Coupon>>;
    currentPage = 1;
    queryParam : string;
  
    constructor (private modalService: BsModalService, private couponService: CouponService,
      private router: Router){
      this.queryParam = router.url.slice(20);
      if (this.queryParam.length != 0) {
        if (Number.parseInt(this.queryParam) > 0){
          this.currentPage = Number.parseInt(this.queryParam);
        }
      }
      this.coupons = this.couponService.getCoupons(this.currentPage - 1);
    }

    onEdit(coupon: Coupon) {
      this.bsModalRef = this.modalService.show(EditCouponModalComponent, {initialState : { coupon: coupon }})
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
      this.bsModalRef = this.modalService.show(CreateCouponModalComponent);
      this.bsModalRef.onHide?.subscribe(() => {
        this.coupons = this.couponService.getCoupons((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
        this.router.navigate(['/admin/coupons'], { queryParams: { page : this.currentPage } });
      }
      this.coupons = this.couponService.getCoupons((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
        this.router.navigate(['/admin/coupons'], { queryParams: { page : this.currentPage } });
      }
      this.coupons = this.couponService.getCoupons(this.currentPage - 1)
    }
  
}