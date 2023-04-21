import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Observable, catchError, map, of } from 'rxjs';
import { SessionService } from '../../service/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent {

  googleLink = "https://localhost:8443/oauth2/authorization/google";

  email? : string
  password? : string

  constructor(private router: Router, private userService : UserService, private sessionService: SessionService){
    userService.isUserLoggedIn().subscribe((val)=>{
      if(val){
          sessionService.updateProfile();
          router.navigateByUrl("");
      }
    });
  }

  sumbitLoginForm(){
    if(this.email != null && this.password != null)
      this.userService.login(this.email, this.password).subscribe(()=>{
        this.sessionService.updateProfile();
        this.router.navigateByUrl("");
      });
  }
}