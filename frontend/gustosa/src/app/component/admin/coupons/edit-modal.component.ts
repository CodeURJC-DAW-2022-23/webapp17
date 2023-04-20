import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Observable } from 'rxjs';
import { Coupon } from 'src/app/model/coupon.model';
import { UserProfile } from 'src/app/model/userProfile.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class EditModalComponent {

  bsModalRef: BsModalRef | undefined;
  coupon: Coupon | undefined
  code : string | undefined
  discount: number | undefined
  uses: number | undefined
  usersList : Observable<Array<UserProfile>>

  constructor(private modalRef: BsModalRef, userService : UserService) {
    this.usersList = userService.getNoPaginatedUsers();
  }

  cancel() {
    this.modalRef.hide();
  }

  onSubmit(){

  }
}