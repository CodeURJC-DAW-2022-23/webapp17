import { Component} from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { UserProfile } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class EditUserModalComponent {

  bsModalRef: BsModalRef | undefined;
  user: UserProfile | undefined
  id = ""
  name: string | undefined
  email: string | undefined
  bio: string | undefined
  password: string | undefined
  passwordsMatch = true;

  constructor(private modalRef: BsModalRef, private userService: UserService) {
  }

  hide() {
    this.modalRef.hide();
  }

  onSubmit(){
    if ((document.getElementById("password1") as HTMLInputElement).value !==
    (document.getElementById("password2") as HTMLInputElement).value) {
      this.passwordsMatch = false;
      return;
    }
    this.name = (document.getElementById('name') as HTMLInputElement).value,
    this.email = (document.getElementById('email') as HTMLInputElement).value,
    this.bio = (document.getElementById('bio') as HTMLInputElement).value,
    this.password = (document.getElementById('password1') as HTMLInputElement).value,
    this.id = (document.getElementById('id') as HTMLInputElement).value;
    this.userService.modifyUser(this.email, this.password, this.name, this.bio).subscribe(() => {
      this.modalRef.hide();
    })
  }
}