import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Observable, catchError, concat, map, of, retry } from 'rxjs';
import { Router } from '@angular/router';
import { SessionService } from '../../service/session.service';

@Component({
  selector: 'logout',
  template: '',
  styleUrls: []
})
export class LogoutComponent {
    e : number = 12;
    constructor(private router: Router, userService:UserService, sessionService : SessionService){
        userService.logout().subscribe(()=>{
          sessionService.updateProfile();
          router.navigateByUrl("");
        });
        
    }
}