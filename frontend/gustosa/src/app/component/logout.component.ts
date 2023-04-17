import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Observable, catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'logout',
  template: '',
  styleUrls: []
})
export class LogoutComponent {
    constructor(private router: Router, userService:UserService){
        userService.logout().subscribe(()=>router.navigateByUrl(""));
    }
}