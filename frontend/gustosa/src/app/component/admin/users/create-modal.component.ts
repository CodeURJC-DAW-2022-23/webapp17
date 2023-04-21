import { Component} from '@angular/core';
import { NgForm } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'modal',
  templateUrl: './create-modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class CreateUserModalComponent {

  admin: boolean | undefined
  bsModalRef: BsModalRef | undefined
  name: string | undefined
  email: string | undefined
  bio: string | undefined
  role = ""
  form = {}
  passwordsMatch = true;
  myForm!: NgForm;

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
    if(this.admin){
      this.role = "admin"
    }
    this.form = { //TODO Se pueden crear usuarios vacíos
      "name" : this.name,
      "email" : this.email,
      "bio": this.bio,
      "password" : (document.getElementById("password1") as HTMLInputElement).value,
      "role" : this.role,
    }
    this.userService.createUser(this.form).subscribe(() => {
      this.modalRef.hide();
    })
  }
}