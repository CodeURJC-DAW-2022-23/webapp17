import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Observable, catchError, concat, map, of, retry } from 'rxjs';
import { Router } from '@angular/router';
import { SessionService } from '../../service/session.service';

@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  styleUrls: []
})
export class RegisterComponent {

    email? : string;
    password? : string;
    name? : string;

    constructor(private router: Router, private userService:UserService, private sessionService : SessionService){
    }

    sumbitRegisterForm(){
        if(this.email != null && this.password != null && this.name != null)
          this.userService.register(this.email, this.password, this.name).subscribe(()=>{
            this.sessionService.updateProfile();
            this.router.navigateByUrl("");
        });
    }
}