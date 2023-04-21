import { Component } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { EditUserModalComponent } from './edit-modal.component';
import { CreateUserModalComponent } from './create-modal.component';
import { Observable } from 'rxjs';
import { UserProfile } from 'src/app/model/user.model';
import { Page } from 'src/app/model/pageable.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'admin',
  templateUrl: './users-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class UsersAdminComponent {

    bsModalRef: BsModalRef | undefined;
    users: Observable<Page<UserProfile>>;
    currentPage = 1;
  
    constructor (private modalService: BsModalService, private userService: UserService){
      this.users = this.userService.getUsers(0);
    }

    onEdit(user: UserProfile) {
      this.bsModalRef = this.modalService.show(EditUserModalComponent, {initialState : { user: user }})
      this.bsModalRef.onHide?.subscribe(() => {
        this.users = this.userService.getUsers((this.currentPage - 1))
      });
    }

    onDelete(userId: number) {
      this.userService.deleteUser(userId.toString()).subscribe(() => {
        this.users = this.userService.getUsers((this.currentPage - 1))
      });
    }

    onCreate(admin : boolean){
      this.bsModalRef = this.modalService.show(CreateUserModalComponent, {initialState : { admin: admin }});
      this.bsModalRef.onHide?.subscribe(() => {
        this.users = this.userService.getUsers((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
      }
      this.users = this.userService.getUsers((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
      }
      this.users = this.userService.getUsers(this.currentPage - 1)
    }
  
}